package com.asyncdroid.ads.mvp.presenter;


import android.text.TextUtils;

import com.asyncdroid.ads.mvp.view.iview.LoginView;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView loginView;
    private FirebaseAuth firebaseAuth;

    @Inject
    LoginPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void bind(LoginView view) {
        this.loginView = view;
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    loginView.loginResult(task);
                });

    }

    public void checkEmailValidation(String email) {
        loginView.emailValidated(Validator.emailValidation(email));
        if (Validator.emailValidation(email)) {
            loginView.setEmailErrorMessage(StringUtil.EMPTY);
        } else {
            loginView.setEmailErrorMessage(StringUtil.ENTER_VALID_EMAIL);
        }
    }

    public void checkPasswordValidation(String password) {
        if (Validator.passwordValidation(password)) {
            loginView.passwordValidated(true);
            loginView.setPasswordErrorMessage(StringUtil.EMPTY);
        } else {
            loginView.passwordValidated(false);
            loginView.setPasswordErrorMessage(StringUtil.PASSWORD_STRENGTH);
        }
    }
}
