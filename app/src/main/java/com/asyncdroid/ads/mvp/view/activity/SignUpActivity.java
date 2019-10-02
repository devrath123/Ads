package com.asyncdroid.ads.mvp.view.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.di.AppDI;
import com.asyncdroid.ads.mvp.presenter.SignUpPresenter;
import com.asyncdroid.ads.mvp.view.iview.SignUpView;
import com.asyncdroid.ads.util.Util;
import com.google.android.material.snackbar.Snackbar;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @BindView(R.id.display_name_et)
    EditText display_name_et;

    @BindView(R.id.email_et)
    EditText email_et;

    @BindView(R.id.password_et)
    EditText password_et;

    @BindView(R.id.email_error_tv)
    TextView email_error_tv;

    @BindView(R.id.password_error_tv)
    TextView password_error_tv;

    @BindView(R.id.display_name_error_tv)
    TextView display_name_error_tv;

    @BindView(R.id.sign_up_btn)
    Button sign_up_btn;

    @BindView(R.id.sign_up_rl)
    RelativeLayout sign_up_rl;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    SignUpPresenter signUpPresenter;

    boolean emailValidationStatus;
    boolean passwordValidationStatus;
    boolean displayNameValidationStatus;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        AppDI.getActivityComponent(this).inject(this);
        signUpPresenter.bind(this);

        setPasswordEyeListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setPasswordEyeListener() {
        password_et.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (password_et.getRight() - password_et.getCompoundDrawables()[2].getBounds().width())) {
                    if (password_et.getText().length() > 0) {
                        if (passwordVisible) {
                            password_et.setTransformationMethod(new PasswordTransformationMethod());
                            password_et.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_menu_password_visibility), null);
                            passwordVisible = false;
                        } else {
                            password_et.setTransformationMethod(null);
                            password_et.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_menu_password_visibility_off), null);
                            passwordVisible = true;
                        }
                        Editable text = password_et.getText();
                        int position = text.length();
                        Selection.setSelection(text, position);
                    }
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @OnTextChanged(R.id.display_name_et)
    public void displayNameTextChanged(CharSequence charSequence) {
        signUpPresenter.checkDisplayNameValidation(charSequence.toString());
    }

    @OnTextChanged(R.id.email_et)
    public void emailTextChanged(CharSequence charSequence) {
        signUpPresenter.checkEmailValidation(charSequence.toString());
    }

    @OnTextChanged(R.id.password_et)
    public void passwordTextChanged(CharSequence charSequence) {
        signUpPresenter.checkPasswordValidation(charSequence.toString());
    }

    @OnClick(R.id.sign_up_btn)
    public void signUpButtonAction() {
        if (displayNameValidationStatus && emailValidationStatus && passwordValidationStatus) {
            signUpPresenter.signUp(email_et.getText().toString(), password_et.getText().toString(), display_name_et.getText().toString());
            Util.hideKeyboard(this);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            signUpPresenter.checkDisplayNameValidation(display_name_et.getText().toString());
            signUpPresenter.checkEmailValidation(email_et.getText().toString());
            signUpPresenter.checkPasswordValidation(password_et.getText().toString());
        }
    }

    @Override
    public void displayNameValidated(boolean validationStatus) {
        this.displayNameValidationStatus = validationStatus;
    }

    @Override
    public void emailValidated(boolean validationStatus) {
        this.emailValidationStatus = validationStatus;
    }

    @Override
    public void passwordValidated(boolean validationStatus) {
        this.passwordValidationStatus = validationStatus;
    }

    @Override
    public void setDisplayNameErrorMessage(String errorMessage) {
        display_name_error_tv.setText(errorMessage);
    }

    @Override
    public void setEmailErrorMessage(String errorMessage) {
        email_error_tv.setText(errorMessage);
    }

    @Override
    public void setPasswordErrorMessage(String errorMessage) {
        password_error_tv.setText(errorMessage);
    }

    @Override
    public void signUpResult() {
        progressBar.setVisibility(View.GONE);
//        if (task.isSuccessful()) {
//            Intent intent = new Intent(this, DashboardActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {
//            try {
//                throw Objects.requireNonNull(task.getException());
//            } catch (FirebaseAuthUserCollisionException existEmail) {
//                setEmailErrorMessage(getResources().getString(R.string.email_exists));
//            } catch (Exception e) {
//                Snackbar.make(sign_up_rl, getString(R.string.problem_with_registration),Snackbar.LENGTH_LONG).show();
//            }
//        }
    }

}
