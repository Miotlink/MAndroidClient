package com.homepaas.sls.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CJJ on 2016/9/16.
 */

public class ActionSheetPhotoPicker extends ActionSheet {

    private static final String SAVED_PATH = "SAVE_PATH";
    @Bind(R.id.take_photo)
    TextView takePhoto;
    @Bind(R.id.gallery_choose)
    TextView galleryChoose;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.outer_layout)
    LinearLayout outerLayout;

    private int sizeW;//图片尺寸
    private int sizeH;

    private static final int CODE_TAKE_PHOTO = 1;
    private static final int CODE_GALLERY = 2;

    private static final int CODE_CUT = 3;


    private String filePath = BuildConfig.PICTURE_PATH;
    private File tempFile;
    private OnPictureChoseListener mOnPictureChoseListener;

    public static ActionSheetPhotoPicker newInstance() {

        Bundle args = new Bundle();

        ActionSheetPhotoPicker fragment = new ActionSheetPhotoPicker();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.action_sheet_photo_picker;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this,getSheetView());
    }

    @OnClick({R.id.gallery_choose,R.id.take_photo})
    public void fetchPhoto(View view){
        switch (view.getId())
        {
            case R.id.gallery_choose:
                selectPictureFromAlbum();
                break;
            case R.id.take_photo:
                selectPictureViaTakingPhoto();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            tempFile = (File) savedInstanceState.getSerializable(SAVED_PATH);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPictureChoseListener)
            mOnPictureChoseListener = (OnPictureChoseListener) context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tempFile != null) {
            outState.putSerializable(SAVED_PATH, tempFile);
        }
    }

    void selectPictureViaTakingPhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ( cameraIntent.resolveActivity(getActivity().getPackageManager()) != null ) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            tempFile = createTempFile(filePath);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
        } else {
            Toast.makeText(getActivity(), "没有可用的相机", Toast.LENGTH_SHORT).show();
        }
    }
    public void selectPictureFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK ) {
            switch (requestCode) {
                case CODE_GALLERY:
                    if ( data != null ) {
                        Uri selectedImage = data.getData();
                        String picturePath;
                        if ( "file".equals(selectedImage.getScheme()) ) {
                            picturePath = selectedImage.getPath();
                            tempFile = createTempFile(filePath);
                            crop(picturePath, 1, 1, 400, 400);
                        } else if ( "content".equals(selectedImage.getScheme()) ) {

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if ( cursor != null ) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                                picturePath = cursor.getString(columnIndex);
                                cursor.close();
                                tempFile = createTempFile(filePath);
                                crop(picturePath, 1, 1, 400, 400);
                            } else {
                                Toast.makeText(getActivity(), "图片获取失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_TAKE_PHOTO:
                    crop(tempFile.getAbsolutePath(), 1, 1, 400, 400);
                    break;
                case CODE_CUT:
                    mOnPictureChoseListener.onPictureChose(tempFile);
                    dismiss();
                    break;
                default:
            }
        }
    }

    private File createTempFile(String filePath) {
        String timeStamp = new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date());
        String externalStorageState = Environment.getExternalStorageState();
        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        if ( externalStorageState.equals(Environment.MEDIA_MOUNTED) ) {
            if ( !dir.exists() ) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = getActivity().getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    //裁剪
    private void crop(String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", sizeW <=0?outputX:sizeW);
        intent.putExtra("outputY", sizeH <=0?outputY:sizeH);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, CODE_CUT);
    }

    public void setOnPictureChoseListener(OnPictureChoseListener mOnPictureChoseListener) {
        this.mOnPictureChoseListener = mOnPictureChoseListener;
    }

    public interface OnPictureChoseListener {

        void onPictureChose(File filePath);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
