package com.asyncdroid.ads.mvp.presenter;


import androidx.annotation.NonNull;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.model.LoginSignUpResponse;
import com.asyncdroid.ads.mvp.model.SignUpRequest;
import com.asyncdroid.ads.mvp.view.iview.SignUpView;
import com.asyncdroid.ads.util.APIInterface;
import com.asyncdroid.ads.util.Constants;
import com.asyncdroid.ads.util.RequestProperty;
import com.asyncdroid.ads.util.SharedPrefConstants;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Validator;
import com.google.gson.Gson;
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

    public void checkNameValidation(String name) {
        signUpView.nameValidated(true);
        if (Validator.displayNameValidation(name)) {
            signUpView.setDisplayNameErrorMessage(StringUtil.EMPTY);
        } else {
            if (name.length() > 0){
                signUpView.setDisplayNameErrorMessage(StringUtil.NAME_STRENGTH);
            }else {
                signUpView.setDisplayNameErrorMessage(StringUtil.ENTER_NAME);
            }
        }
    }

    public void checkEmailValidation(String email) {
        signUpView.emailValidated(Validator.emailValidation(email));
        if (Validator.emailValidation(email)) {
            signUpView.setEmailErrorMessage(StringUtil.EMPTY);
        } else {
            if (email.length() > 0) {
                signUpView.setEmailErrorMessage(StringUtil.ENTER_VALID_EMAIL);
            } else {
                signUpView.setEmailErrorMessage(StringUtil.ENTER_EMAIL);
            }
        }
    }

    public void checkPasswordValidation(String password) {
        signUpView.passwordValidated(Validator.passwordValidation(password));
        if (Validator.passwordValidation(password)) {
            signUpView.setPasswordErrorMessage(StringUtil.EMPTY);
        } else {
            if (password.length() > 0) {
                signUpView.setPasswordErrorMessage(StringUtil.PASSWORD_STRENGTH);
            } else {
                signUpView.setPasswordErrorMessage(StringUtil.ENTER_PASSWORD);
            }
        }
    }

    public void signUp(SignUpRequest signUpRequest) {
        JsonObject signUpJsonObject = new JsonObject();
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_NAME, signUpRequest.getDisplayName());
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_EMAIL, signUpRequest.getEmail());
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_PASSWORD, signUpRequest.getPassword());
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_REGISTRATION_TYPE, signUpRequest.getRegistrationType());
        signUpJsonObject.addProperty(RequestProperty.PROPERTY_SOCIAL_USER_ID, signUpRequest.getSocialUserId());

        Call<JsonObject> signUpCall = apiInterface.signUp(signUpJsonObject);
        signUpCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoginSignUpResponse signUpResponse = new Gson().fromJson(response.body(), LoginSignUpResponse.class);
                if (signUpResponse.getStatusCode() == Constants.RESPONSE_CODE_SUCCESS) {
                    updateSignUpPreferences(signUpResponse);
                    signUpView.signUpSuccess();
                } else {
                    signUpView.signUpFailed(signUpResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                signUpView.signUpFailed(t.getMessage());
            }
        });
    }

    private void updateSignUpPreferences(LoginSignUpResponse signUpResponse) {
        if (signUpResponse.getUser() != null) {
            sharedPrefManager.putLong(SharedPrefConstants.USER_ID, signUpResponse.getUser().getUserId());
            sharedPrefManager.putString(SharedPrefConstants.USER_NAME, signUpResponse.getUser().getName());
            sharedPrefManager.putString(SharedPrefConstants.USER_EMAIL, signUpResponse.getUser().getEmail());
            sharedPrefManager.putString(SharedPrefConstants.USER_REGISTRATION_TYPE, signUpResponse.getUser().getRegistration_type());
        }
    }
}
