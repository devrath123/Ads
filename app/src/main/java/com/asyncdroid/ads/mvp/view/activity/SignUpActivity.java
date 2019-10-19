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
import com.asyncdroid.ads.mvp.model.SignUpRequest;
import com.asyncdroid.ads.mvp.presenter.SignUpPresenter;
import com.asyncdroid.ads.mvp.view.iview.SignUpView;
import com.asyncdroid.ads.util.Constants;
import com.asyncdroid.ads.util.RequestProperty;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Util;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @BindView(R.id.name_et)
    EditText name_et;

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

    private boolean emailValidationStatus;
    private boolean passwordValidationStatus;
    private boolean nameValidationStatus;
    private boolean passwordVisible;

    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;

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

    private void googleSignIn() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @OnTextChanged(R.id.name_et)
    public void displayNameTextChanged(CharSequence charSequence) {
        signUpPresenter.checkNameValidation(charSequence.toString());
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
        if (nameValidationStatus && emailValidationStatus && passwordValidationStatus) {
            SignUpRequest signUpRequest = new SignUpRequest(email_et.getText().toString(),
                    password_et.getText().toString(), name_et.getText().toString(),
                    RequestProperty.REGISTRATION_TYPE_CUSTOM, StringUtil.EMPTY, StringUtil.EMPTY);
            signUpPresenter.signUp(signUpRequest);
            Util.hideKeyboard(this);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            signUpPresenter.checkNameValidation(name_et.getText().toString());
            signUpPresenter.checkEmailValidation(email_et.getText().toString());
            signUpPresenter.checkPasswordValidation(password_et.getText().toString());
        }
    }

    @Override
    public void nameValidated(boolean validationStatus) {
        this.nameValidationStatus = validationStatus;
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
    public void signUpSuccess() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.login_tv)
    public void loginAction() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void signUpFailed(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(sign_up_rl, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.google_sign_up_button)
    public void googleLoginAction() {
        googleSignIn();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.GOOGLE_SIGN_UP_REQUEST_CODE);
    }

    @OnClick(R.id.facebook_sign_up_button)
    public void facebookLoginAction(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult.getAccessToken() != null){
                    getFacebookUserInfo(loginResult.getAccessToken());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("Exception", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    private void getFacebookUserInfo(AccessToken accessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String email = object.getString("email");
                    String userId = object.getString("id");
                    String name = object.getString("name");
                    String profilePicUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
                    signUpPresenter.signUp(new SignUpRequest(email,StringUtil.EMPTY, name,
                            RequestProperty.REGISTRATION_TYPE_FACEBOOK, userId, profilePicUrl));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "id,name,email,picture.width(200)");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (callbackManager != null && requestCode != Constants.GOOGLE_SIGN_UP_REQUEST_CODE){
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_SIGN_UP_REQUEST_CODE) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    signUpPresenter.signUp(new SignUpRequest(googleSignInAccount.getEmail(), StringUtil.EMPTY,
                            googleSignInAccount.getDisplayName(), RequestProperty.REGISTRATION_TYPE_GOOGLE,
                            googleSignInAccount.getId(), Objects.requireNonNull(googleSignInAccount.getPhotoUrl()).getPath()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
