package com.homepaas.sls.ui.comment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerAddCommentComponent;
import com.homepaas.sls.di.module.EditCommentModule;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.RefreshEvent;
import com.homepaas.sls.mvp.presenter.comment.AddCommentPresenter;
import com.homepaas.sls.mvp.view.comment.AddCommentView;
import com.homepaas.sls.ui.comment.adapter.AddPhotoAdapter;
import com.homepaas.sls.ui.comment.adapter.AddPhotoAdapter.PhotoAdd;
import com.homepaas.sls.ui.comment.adapter.AddPhotoAdapter.PhotoItem;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.ChoosePictureFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加评论页面
 */
public class AddCommentActivity extends CommonBaseActivity implements AddCommentView ,ChoosePictureFragment.OnPictureChoseListener{

    private static final String TAG = "AddCommentActivity";
    private static final int PERMISSION_CAMERA = 1;

    public static void start(Fragment fragment, int resultCode, String orderId, String id, String type) {
        Intent starter = new Intent(fragment.getActivity(), AddCommentActivity.class);
        starter.putExtra(KEY_ORDER_ID, orderId);
        starter.putExtra(KEY_TYPE, type);
        starter.putExtra(KEY_ID, id);
        fragment.startActivityForResult(starter,resultCode);
    }

    public static void start(Context context, String orderId, String id, String type) {
        Intent starter = new Intent(context, AddCommentActivity.class);
        starter.putExtra(KEY_ORDER_ID, orderId);
        starter.putExtra(KEY_TYPE, type);
        starter.putExtra(KEY_ID, id);
        context.startActivity(starter);
    }

    private static final String KEY_ID = "ID";
    private static final String KEY_ORDER_ID = "ORDER_ID";
    private static final String KEY_TYPE = "PriceType";

    private static final int CODE_TAKE_PHOTO = 1;
    private static final int CODE_CUT = 2;
    private static final int CODE_LOGIN = 3;

    private static final String SAVED_LIST = "sls_photo_list";
    private static final String SAVED_TEMP_FILE = "sls_temp_file";

    @Bind(R.id.ratingbar)
    RatingBar ratingbar;

    @Bind(R.id.rating_score)
    TextView ratingScore;

    @Bind(R.id.comment_input)
    EditText commentInput;

    @Bind(R.id.add_photos)
    RecyclerView addPhotos;

    private ArrayList<PhotoItem> photoItems;
    private File tempFile;
    private String orderId;

    private static final int MAX_PHOTO_NUMBER = 5;

    @Inject
    AddCommentPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_comment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            tempFile = (File) savedInstanceState.getSerializable(SAVED_TEMP_FILE);
            photoItems = savedInstanceState.getParcelableArrayList(SAVED_LIST);
        } else {
            photoItems = new ArrayList<>();
            photoItems.add(new PhotoAdd());
        }
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AddPhotoAdapter adapter = new AddPhotoAdapter(photoItems);
        adapter.setOnItemClickListener(new AddPhotoAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position, PhotoItem item) {
                String path = photoItems.get(position).getUrl();
                photoItems.remove(position);
                adapter.notifyItemRemoved(position);
                deletePicture(path);
                if (photoItems.size() == MAX_PHOTO_NUMBER - 1) {
                    photoItems.add(new PhotoAdd());
                    adapter.notifyItemInserted(MAX_PHOTO_NUMBER - 1);
                }
            }

            @Override
            public void onAddClick() {
                //  takePhoto();
                ChoosePictureFragment fragment = ChoosePictureFragment.newInstance("选择图片");
                fragment.show(getSupportFragmentManager(),"");

            }
        });
        addPhotos.setAdapter(adapter);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String score = rating + "分";
                ratingScore.setText(score);
            }
        });
        presenter.setView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_LIST, photoItems);
        outState.putSerializable(SAVED_TEMP_FILE, tempFile);
    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        orderId = getIntent().getStringExtra(KEY_ORDER_ID);
        String id = getIntent().getStringExtra(KEY_ID);
        String type = getIntent().getStringExtra(KEY_TYPE);
        DaggerAddCommentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .editCommentModule(new EditCommentModule(orderId, type, id))
                .build()
                .inject(this);
    }

    @Deprecated
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
            return;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            tempFile = createTempFile(BuildConfig.PICTURE_PATH);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
        } else {
            showMessage("没有可用的相机");
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
//            LoginDialogFragment.show(this, null);
            mNavigator.login(AddCommentActivity.this,CODE_LOGIN);
        } else {
            showMessage(e.getMessage());
        }
    }

    @Deprecated
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
            File cacheDir = getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    // TODO: 2016/7/29 0029 上传完后删除图片
    private void deletePicture(String path) {

    }

    //裁剪
    @Deprecated
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_CAMERA){
            for (int gr : grantResults) {
                if (gr!=PackageManager.PERMISSION_GRANTED)
                    return;
            }
            takePhoto();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CODE_TAKE_PHOTO:
                    crop(tempFile.getAbsolutePath(), 1, 1, 400, 400);
                    break;
                case CODE_CUT:
                    photoItems.add(0, new AddPhotoAdapter.Photo(tempFile.getAbsolutePath()));
                    addPhotos.getAdapter().notifyItemInserted(0);
                    if (photoItems.size() == MAX_PHOTO_NUMBER + 1) {
                        photoItems.remove(MAX_PHOTO_NUMBER);
                        addPhotos.getAdapter().notifyItemInserted(MAX_PHOTO_NUMBER);
                    }
                    break;
                case CODE_LOGIN:
                    break;
                default:
                    break;
            }
        }
//        if (requestCode == CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
//            crop(tempFile.getAbsolutePath(), 1, 1, 400, 400);
//        } else if (requestCode == CODE_CUT && resultCode == RESULT_OK) {
//            photoItems.add(0, new AddPhotoAdapter.Photo(tempFile.getAbsolutePath()));
//            addPhotos.getAdapter().notifyItemInserted(0);
//            if (photoItems.size() == MAX_PHOTO_NUMBER + 1) {
//                photoItems.remove(MAX_PHOTO_NUMBER);
//                addPhotos.getAdapter().notifyItemInserted(MAX_PHOTO_NUMBER);
//            }
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @OnClick(R.id.submit)
    public void onClick() {
        String score = String.valueOf((int)ratingbar.getRating());
        String content = commentInput.getText().toString();
        if (TextUtils.isEmpty(orderId)) {
            presenter.submit(score, content, getStringPathList());
        } else {
            presenter.submitOrder(score, content, getStringPathList());

        }
    }


    private List<String> getStringPathList() {
        List<String> list = new ArrayList<>();
        for (PhotoItem item : photoItems) {
            if (item.getUrl() != null)
                list.add(item.getUrl());
        }
        return list;
    }

    @Override
    public void onSubmit(String msg) {
        showMessage("提交成功");
        setResult(Activity.RESULT_OK);
        EventBus.getDefault().post(new RefreshEvent());
        finish();
    }

    @Override
    public void onPictureChose(File filePath) {
        photoItems.add(0, new AddPhotoAdapter.Photo(filePath.getAbsolutePath()));
        addPhotos.getAdapter().notifyItemInserted(0);
        if (photoItems.size() == MAX_PHOTO_NUMBER + 1) {
            photoItems.remove(MAX_PHOTO_NUMBER);
            addPhotos.getAdapter().notifyItemInserted(MAX_PHOTO_NUMBER);
        }
    }
}
