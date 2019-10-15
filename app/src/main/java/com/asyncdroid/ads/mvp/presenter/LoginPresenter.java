package com.asyncdroid.ads.mvp.presenter;


import androidx.annotation.NonNull;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.model.LoginRequest;
import com.asyncdroid.ads.mvp.model.LoginSignUpResponse;
import com.asyncdroid.ads.mvp.view.iview.LoginView;
import com.asyncdroid.ads.util.APIInterface;
import com.asyncdroid.ads.util.Constants;
import com.asyncdroid.ads.util.RequestProperty;
import com.asyncdroid.ads.util.SharedPrefConstants;
import com.asyncdroid.ads.util.StringUtil;
import com.asyncdroid.ads.util.Validator;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView loginView;
    private SharedPrefManager sharedPrefManager;
    private APIInterface apiInterface;
    private FirebaseAnalytics firebaseAnalytics;

    @Inject
    LoginPresenter(SharedPrefManager sharedPrefManager, APIInterface apiInterface,
                   FirebaseAnalytics firebaseAnalytics) {
        this.sharedPrefManager = sharedPrefManager;
        this.apiInterface = apiInterface;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void bind(LoginView view) {
        this.loginView = view;
    }

    public void login(LoginRequest loginRequest) {

        try {
            trackEvent(firebaseAnalytics, "Login Clicked");
            JsonObject loginJsonObject = new JsonObject();
            loginJsonObject.addProperty(RequestProperty.PROPERTY_EMAIL, loginRequest.getEmail());
            loginJsonObject.addProperty(RequestProperty.PROPERTY_PASSWORD, loginRequest.getPassword());
            loginJsonObject.addProperty(RequestProperty.PROPERTY_SOCIAL_USER_ID, loginRequest.getSocialUserId());

            Call<JsonObject> loginCall = apiInterface.login(loginJsonObject);

            loginCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoginSignUpResponse loginResponse = new Gson().fromJson(response.body(), LoginSignUpResponse.class);
                    if (loginResponse != null) {
                        if (loginResponse.getStatusCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            updateLoginPreferences(loginResponse);
                            loginView.loginSuccess();
                        } else {
                            loginView.loginFailed(loginResponse.getMessage());
                        }
                    } else {
                        loginView.loginFailed(StringUtil.SOMETHING_WENT_WRONG);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    loginView.loginFailed(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLoginPreferences(LoginSignUpResponse loginResponse) {
        if (loginResponse.getUser() != null) {
            sharedPrefManager.putLong(SharedPrefConstants.USER_ID, loginResponse.getUser().getUserId());
            sharedPrefManager.putString(SharedPrefConstants.USER_NAME, loginResponse.getUser().getName());
            sharedPrefManager.putString(SharedPrefConstants.USER_EMAIL, loginResponse.getUser().getEmail());
            sharedPrefManager.putString(SharedPrefConstants.USER_REGISTRATION_TYPE, loginResponse.getUser().getRegistration_type());
            sharedPrefManager.putString(SharedPrefConstants.USER_PHOTO_URL, loginResponse.getUser().getProfile_pic_url());
        }
    }

    public void checkEmailValidation(String email) {
        loginView.emailValidated(Validator.emailValidation(email));
        if (Validator.emailValidation(email)) {
            loginView.setEmailErrorMessage(StringUtil.EMPTY);
        } else {
            if(email.length() > 0) {
                loginView.setEmailErrorMessage(StringUtil.ENTER_VALID_EMAIL);
            }else{
                loginView.setEmailErrorMessage(StringUtil.ENTER_EMAIL);
            }
        }
    }

    public void checkPasswordValidation(String password) {
        loginView.passwordValidated(Validator.passwordValidation(password));
        if (Validator.passwordValidation(password)) {
            loginView.setPasswordErrorMessage(StringUtil.EMPTY);
        } else {
            if (password.length() > 0) {
                loginView.setPasswordErrorMessage(StringUtil.PASSWORD_STRENGTH);
            }else{
                loginView.setPasswordErrorMessage(StringUtil.ENTER_PASSWORD);
            }
        }
    }
}
