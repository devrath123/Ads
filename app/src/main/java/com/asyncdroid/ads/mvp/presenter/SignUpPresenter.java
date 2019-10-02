package com.asyncdroid.ads.mvp.presenter;

import android.util.Log;


import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.view.iview.SignUpView;
import com.asyncdroid.ads.util.APIInterface;
import com.asyncdroid.ads.util.RequestProperty;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Validator;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter extends BasePresenter<SignUpView> {

    private APIInterface apiInterface;
    private SharedPrefManager sharedPrefManager;
    private SignUpView signUpView;

    @Inject
    SignUpPresenter(APIInterface apiInterface, SharedPrefManager sharedPrefManager) {
        this.apiInterface = apiInterface;
        this.sharedPrefManager = sharedPrefManager;
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

        JsonObject signUpJsonObject = new JsonObject();
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_NAME, displayName);
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_EMAIL, email);
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_PASSWORD, password);
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_REGISTRATION_TYPE, RequestProperty.REGISTRATION_TYPE_CUSTOM);

        Call<JsonObject> signUpCall = apiInterface.signUp(signUpJsonObject);
        signUpCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Response", response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("Response", "");
            }
        });


    }
}
