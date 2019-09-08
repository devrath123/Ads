package com.asyncdroid.ads.mvp.view.iview;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface SignUpView extends BaseView {
    void displayNameValidated(boolean validationStatus);
    void emailValidated(boolean validationStatus);
    void passwordValidated(boolean validationStatus);
    void setDisplayNameErrorMessage(String errorMessage);
    void setEmailErrorMessage(String errorMessage);
    void setPasswordErrorMessage(String errorMessage);
    void signUpResult(Task<AuthResult> task);
}
