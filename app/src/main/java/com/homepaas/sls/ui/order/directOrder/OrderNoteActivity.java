package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.AddPhotoAdapter;
import com.homepaas.sls.ui.widget.ActionSheetPhotoPicker;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.view.View.VISIBLE;

public class OrderNoteActivity extends CommonBaseActivity implements ActionSheetPhotoPicker.OnPictureChoseListener {


    int color;
    StringBuilder stringBuilder;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.text_number)
    TextView textNumber;
    @Bind(R.id.activity_order_note)
    LinearLayout activityOrderNote;
    @Bind(R.id.add_photo)
    ImageView addPhoto;
    @Bind(R.id.album)
    RecyclerView album;
    private ActionSheetPhotoPicker photoPicker;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private AddPhotoAdapter photoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_note;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        color = textNumber.getCurrentTextColor();
        stringBuilder = new StringBuilder(200);
        init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            photoPaths = savedInstanceState.getStringArrayList("Photos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("Photos", photoPaths);
            intent.putExtra("Notes", edit.getText().toString().trim());
            setResult(Activity.RESULT_OK, intent);
            ActivityCompat.finishAfterTransition(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        photoAdapter = new AddPhotoAdapter();
        Intent intent = getIntent();
        String notes = intent.getStringExtra("Notes");
        ArrayList<String> photos = intent.getStringArrayListExtra("Photos");
        if (photos != null && !photos.isEmpty()) {
            photoPaths.clear();
            photoPaths.addAll(photos);
            album.setVisibility(VISIBLE);
        }
        edit.setText(notes);
        photoAdapter.setPaths(photoPaths);
        photoAdapter.setPhotoListener(new AddPhotoAdapter.AddPhotoListener() {
            @Override
            public void startAddPhoto() {
                initPhotoPicker();
            }
        });
        album.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        album.setAdapter(photoAdapter);
    }

    @OnTextChanged(R.id.edit)
    public void onTextChange(Editable editable) {
        int length = editable.toString().length();
        textNumber.setText(length + "");
        textNumber.setTextColor(length > 80 ? getResources().getColor(R.color.red) : color);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        onBackPressed();
        super.onSupportActionModeFinished(mode);
    }

    @OnClick(R.id.add_photo)
    public void addPhoto() {
//        album.setVisibility(VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    edit.getApplicationWindowToken(), 0);
        }
        initPhotoPicker();
    }

    private void initPhotoPicker() {
        if (photoPicker == null) {
            photoPicker = ActionSheetPhotoPicker.newInstance();
            photoPicker.setOnPictureChoseListener(this);
        }
        if (photoPaths.size() > 7) {
            showMessage("至多选择8张照片");
            return;
        }
        photoPicker.show(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Notes", edit.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        ActivityCompat.finishAfterTransition(this);
        super.onBackPressed();
    }

    @Override
    public void onPictureChose(File filePath) {
        photoPaths.add(filePath.getAbsolutePath());
        album.setVisibility(VISIBLE);
        photoAdapter.setPaths(photoPaths);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putStringArrayList("Photos", photoPaths);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            photoPaths = savedInstanceState.getStringArrayList("Photos");
    }

}
