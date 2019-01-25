package com.homepaas.sls.ui.personalcenter.personalmessage;

import android.os.Bundle;
import android.view.MenuItem;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.component.PersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.ChoosePictureFragment.OnPictureChoseListener;
import com.homepaas.sls.ui.personalcenter.personalmessage.ModifyNicknameFragment.OnNicknameModifiedListener;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalInfoFragment.OnFragmentItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class PersonalMessageActivity extends CommonBaseActivity implements HasComponent<PersonalInfoComponent>
        , OnFragmentItemClickListener, OnNicknameModifiedListener, OnPictureChoseListener {

    private PersonalInfoComponent mComponent;

    private static final String PMF_TAG = "PersonalInfoFragment";

    private static final String MNF_TAG = "ModifyNicknameFragment";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, PMF_TAG)
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPersonalInfoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public PersonalInfoComponent getComponent() {
        return mComponent;
    }


    @Override
    public void onModifyNickname() {
        ModifyNicknameFragment fragment = new ModifyNicknameFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, MNF_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPhotoClick() {
        ChoosePictureFragment fragment = ChoosePictureFragment.newInstance("头像设置");
        fragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onPictureChose(File filePath) {
        PersonalInfoFragment fragment = (PersonalInfoFragment) getSupportFragmentManager().findFragmentByTag(PMF_TAG);
        fragment.requestModifyPhoto(filePath.getAbsolutePath());
    }

    @Override
    public void onNicknameModified(String newNickname) {
        getSupportFragmentManager().popBackStack();
        PersonalInfoFragment fragment = (PersonalInfoFragment) getSupportFragmentManager().findFragmentByTag(PMF_TAG);
        fragment.mNickname.setText(newNickname);
        EventBus.getDefault().post(new EventPersonalInfo(EventPersonalInfo.PERSONAL_INFO));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ModifyNicknameFragment fragment = (ModifyNicknameFragment) getSupportFragmentManager().findFragmentByTag(MNF_TAG);
        if (fragment == null || !fragment.showPrompt()) {
            super.onBackPressed();
        }
    }


}
