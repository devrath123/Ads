package com.asyncdroid.ads.mvp.presenter;


import androidx.annotation.NonNull;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.model.LoginSignUpResponse;
import com.asyncdroid.ads.mvp.view.iview.LoginView;
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

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView loginView;
    private SharedPrefManager sharedPrefManager;
    private APIInterface apiInterface;

    @Inject
    LoginPresenter(SharedPrefManager sharedPrefManager, APIInterface apiInterface) {
        this.sharedPrefManager = sharedPrefManager;
        this.apiInterface = apiInterface;
    }

    @Override
    public void bind(LoginView view) {
        this.loginView = view;
    }

    public void login(String email, String password, String socialUserId) {

        try {
            JsonObject loginJsonObject = new JsonObject();
            loginJsonObject.addProperty(RequestProperty.PROPERTY_EMAIL, email);
            loginJsonObject.addProperty(RequestProperty.PROPERTY_PASSWORD, password);
            loginJsonObject.addProperty(RequestProperty.PROPERTY_SOCIAL_USER_ID, socialUserId);

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
        }
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
