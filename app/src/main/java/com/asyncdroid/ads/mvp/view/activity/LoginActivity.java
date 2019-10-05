package com.asyncdroid.ads.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.di.AppDI;
import com.asyncdroid.ads.mvp.presenter.LoginPresenter;
import com.asyncdroid.ads.mvp.view.iview.LoginView;
import com.asyncdroid.ads.util.Constants;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Util;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_rl)
    RelativeLayout login_rl;

    @BindView(R.id.email_et)
    EditText email_et;

    @BindView(R.id.password_et)
    EditText password_et;

    @BindView(R.id.email_error_tv)
    TextView email_error_tv;

    @BindView(R.id.password_error_tv)
    TextView password_error_tv;

    @BindView(R.id.login_btn)
    Button login_btn;

    @BindView(R.id.register_tv)
    TextView register_tv;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    @Inject
    LoginPresenter loginPresenter;

    boolean emailValidationStatus;
    boolean passwordValidationStatus;
    boolean passwordVisible;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        AppDI.getActivityComponent(this).inject(this);
        loginPresenter.bind(this);

        setPasswordEyeListener();
        googleSignIn();
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
        return R.layout.activity_login;
    }

    @OnTextChanged(R.id.email_et)
    public void emailTextChanged(CharSequence charSequence) {
        loginPresenter.checkEmailValidation(charSequence.toString());
    }

    @OnTextChanged(R.id.password_et)
    public void passwordTextChanged(CharSequence charSequence) {
        loginPresenter.checkPasswordValidation(charSequence.toString());
    }

    @OnClick(R.id.login_btn)
    public void loginButtonAction() {
        if (emailValidationStatus && passwordValidationStatus) {
            loginPresenter.login(email_et.getText().toString(), password_et.getText().toString(), StringUtil.EMPTY);
            Util.hideKeyboard(this);
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            loginPresenter.checkEmailValidation(email_et.getText().toString());
            loginPresenter.checkPasswordValidation(password_et.getText().toString());
        }
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
    public void setEmailErrorMessage(String errorMessage) {
        email_error_tv.setText(errorMessage);
    }

    @Override
    public void setPasswordErrorMessage(String errorMessage) {
        password_error_tv.setText(errorMessage);
    }

    @Override
    public void loginSuccess() {
        progress_bar.setVisibility(View.GONE);
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String errorMessage) {
        progress_bar.setVisibility(View.GONE);
        Snackbar.make(login_rl, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.register_tv)
    public void signUpAction() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void googleSignIn(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @OnClick(R.id.google_login_button)
    public void googleLoginAction(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.GOOGLE_LOGIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_LOGIN_REQUEST_CODE) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null){
                    loginPresenter.login(googleSignInAccount.getEmail(), StringUtil.EMPTY, googleSignInAccount.getId());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
