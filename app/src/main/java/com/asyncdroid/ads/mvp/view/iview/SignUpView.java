package com.asyncdroid.ads.mvp.view.iview;


public interface SignUpView extends BaseView {
    void displayNameValidated(boolean validationStatus);

    void emailValidated(boolean validationStatus);

    void passwordValidated(boolean validationStatus);

    void setDisplayNameErrorMessage(String errorMessage);

    void setEmailErrorMessage(String errorMessage);

    void setPasswordErrorMessage(String errorMessage);

    void signUpSuccess();

    void signUpFailed(String errorMessage);
}
