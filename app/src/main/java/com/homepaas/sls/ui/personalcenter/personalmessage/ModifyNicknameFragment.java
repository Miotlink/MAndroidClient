package com.homepaas.sls.ui.personalcenter.personalmessage;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.mvp.presenter.personalcenter.ModifyNicknamePresenter;
import com.homepaas.sls.mvp.view.personalcenter.ModifyNicknameView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.widget.CommonDialog;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNicknameModifiedListener} interface
 * to handle interaction events.
 */
public class ModifyNicknameFragment extends CommonBaseFragment implements ModifyNicknameView {


    private String regx = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,10}$";
    private Pattern matcher = Pattern.compile(regx);
    private OnNicknameModifiedListener mListener;

    public ModifyNicknameFragment() {
    }

    @Inject
    ModifyNicknamePresenter mPresenter;

    @Bind(R.id.modify_nickname)
    EditText mModifyNickname;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNicknameModifiedListener) {
            mListener = (OnNicknameModifiedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNicknameModifiedListener");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_nick_name;
    }

    @Override
    protected void initView() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.setView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPersonalInfoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    @OnClick(R.id.save)
    void saveNickname() {
        String newNickname = mModifyNickname.getText().toString();
        if (TextUtils.isEmpty(newNickname)) {
            showMessage("昵称不能为空");
            mModifyNickname.requestFocus();
            return;
        } /*else if (newNickname.length() > 10) {
            showMessage("字数过多");
            mModifyNickname.requestFocus();
            return;
        }*/else if (!matcher.matcher(newNickname).matches()){
            showMessage("请输入1-10个中文或英文作为昵称");
            return;
        }
        mPresenter.modifyNickName(newNickname);
    }


    @OnEditorAction(R.id.modify_nickname)
    boolean endModify(TextView view) {
        String newNickname = mModifyNickname.getText().toString().trim();
        if (TextUtils.isEmpty(newNickname)) {
            showMessage("昵称不能为空");
            mModifyNickname.requestFocus();
            return false;
        } else if (!matcher.matcher(newNickname).matches()){
            showMessage("请输入1-10个中文或英文作为昵称");
            return false;
        }
        mPresenter.modifyNickName(newNickname);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }

    @Override
    public void afterNicknameModified(String newNickname) {
        if (mListener != null) {
            mListener.onNicknameModified(newNickname);
            //跳转后提示，保证能显示
            showMessage("昵称修改成功");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    public boolean showPrompt() {
        String newNickname = mModifyNickname.getText().toString().trim();
        if (TextUtils.isEmpty(newNickname)) {
            return false;
        } else {
            CommonDialog dialog = new CommonDialog.Builder()
                    .showTitle(false)
                    .setContent("是否放弃编辑昵称?")
                    .setCancelButton(getString(R.string.cancel), null)
                    .setConfirmButton(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFragmentManager().popBackStack();
                        }
                    })
                    .create();
            dialog.show(getFragmentManager(), null);
            return true;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnNicknameModifiedListener {

        void onNicknameModified(String newNickname);
    }
}
