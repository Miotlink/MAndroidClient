package com.homepaas.sls.ui.personalcenter.personalmessage;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * 选择头像
 */
public class ChoosePictureFragment extends DialogFragment {

    private static final String TAG = "ChoosePictureFragment";
    private static final String KEY_TITLE = "TITLE";

    private static final int CODE_TAKE_PHOTO = 1;

    private static final int CODE_GALLERY = 2;

    private static final int CODE_CUT = 3;

    private static final int PERMISSION_CAMERA = 1;

    private static final int PERMISSION_READ_SD = 2;

    private static final String SAVED_PATH = "sls_temp_file";

    private OnPictureChoseListener mOnPictureChoseListener;

    private File tempFile;

    private String filePath = BuildConfig.PICTURE_PATH;

    @Bind(R.id.title)
    TextView titleTextView;

    private String title;

    public static ChoosePictureFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        ChoosePictureFragment fragment = new ChoosePictureFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
        if (savedInstanceState != null) {
            tempFile = (File) savedInstanceState.getSerializable(SAVED_PATH);
        }
        if (getArguments() != null) {
            title = getArguments().getString(KEY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_picture, container, false);
        ButterKnife.bind(this, view);
        titleTextView.setText(title);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tempFile != null) {
            outState.putSerializable(SAVED_PATH, tempFile);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPictureChoseListener) {
            mOnPictureChoseListener = (OnPictureChoseListener) context;
        } else {
            throw new RuntimeException(context.getClass().getName() + " must implement OnPictureChoseListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnPictureChoseListener = null;
    }

    @OnClick(R.id.select_from_gallery)
    void selectPictureFromGallery() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_SD);
            return;
        }

        //打开相册选择图片
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODE_GALLERY);
    }

    @OnClick(R.id.select_take_photo)
    void selectPictureViaTakingPhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            tempFile = createTempFile(filePath);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
        } else {
            Toast.makeText(getActivity(), "没有可用的相机", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.dismiss)
    void dismissDialog() {
        dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_READ_SD:
                //打开相册选择图片
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY);
                break;
            case PERMISSION_CAMERA:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // 设置系统相机拍照后的输出路径
                    // 创建临时文件
                    tempFile = createTempFile(filePath);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
                } else {
                    Toast.makeText(getActivity(), "没有可用的相机", Toast.LENGTH_SHORT).show();
                }
                break;
        }
//        if (requestCode == PERMISSION_CAMERA) {
//
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: " + (data != null));
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String picturePath;
                        if ("file".equals(selectedImage.getScheme())) {
                            picturePath = selectedImage.getPath();
                            tempFile = createTempFile(filePath);
                            crop(picturePath, 1, 1, 400, 400);
                        } else if ("content".equals(selectedImage.getScheme())) {

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
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

    //裁剪
    private void crop(String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, CODE_CUT);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private File createTempFile(String filePath) {
        String timeStamp = new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date());
        String externalStorageState = Environment.getExternalStorageState();
        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = getActivity().getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    public interface OnPictureChoseListener {

        void onPictureChose(File filePath);
    }
}
