package com.asyncdroid.ads.mvp.presenter;

import android.text.TextUtils;


import com.asyncdroid.ads.mvp.view.iview.SignUpView;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Validator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

public class SignUpPresenter extends BasePresenter<SignUpView> {

    private FirebaseAuth firebaseAuth;
    private SignUpView signUpView;

    @Inject
    SignUpPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void bind(SignUpView view) {
        this.signUpView = view;
    }

    public void checkDisplayNameValidation(String displayName) {
        if (Validator.displayNameValidation(displayName)) {
            signUpView.displayNameValidated(true);
            signUpView.setDisplayNameErrorMessage(StringUtil.EMPTY);
        } else {
            signUpView.displayNameValidated(false);
            signUpView.setDisplayNameErrorMessage(StringUtil.DISPLAY_NAME_STRENGTH);
        }
    }

    public void checkEmailValidation(String email) {
        signUpView.emailValidated(Validator.emailValidation(email));
        if (Validator.emailValidation(email)) {
            signUpView.setEmailErrorMessage(StringUtil.EMPTY);
        } else {
            signUpView.setEmailErrorMessage(StringUtil.ENTER_VALID_EMAIL);
        }
    }

    public void checkPasswordValidation(String password) {
        if (Validator.passwordValidation(password)) {
            signUpView.passwordValidated(true);
            signUpView.setPasswordErrorMessage(StringUtil.EMPTY);
        } else {
            signUpView.passwordValidated(false);
            signUpView.setPasswordErrorMessage(StringUtil.PASSWORD_STRENGTH);
        }
    }

    public void signUp(String email, String password, String displayName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        signUpView.signUpResult(task);
                    } else {
                        updateDisplayName(task, displayName);
                    }
                });
    }

    private void updateDisplayName(Task<AuthResult> signUpTask, String displayName) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(displayName).build();
            firebaseUser.updateProfile(userProfileChangeRequest)
                    .addOnCompleteListener(task -> {
                        signUpView.signUpResult(signUpTask);
                    });
        }

    }
}
