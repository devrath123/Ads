package com.asyncdroid.ads.mvp.view.iview;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface LoginView extends BaseView {
    void emailValidated(boolean validationStatus);
    void passwordValidated(boolean validationStatus);
    void setEmailErrorMessage(String errorMessage);
    void setPasswordErrorMessage(String errorMessage);
    void loginResult(Task<AuthResult> task);
}
