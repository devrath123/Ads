package com.asyncdroid.ads.mvp.view.iview;

public interface LoginView extends BaseView {
    void emailValidated(boolean validationStatus);

    void passwordValidated(boolean validationStatus);

    void setEmailErrorMessage(String errorMessage);

    void setPasswordErrorMessage(String errorMessage);

    void loginSuccess();

    void loginFailed(String errorMessage);
}
