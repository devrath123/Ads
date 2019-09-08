package com.asyncdroid.ads.util;

import android.text.TextUtils;
import android.util.Patterns;

public class Validator {

    public static boolean displayNameValidation(String displayName){
        return displayName.length() > Constants.DISPLAY_NAME_MIN_LENGTH;
    }

    public static boolean emailValidation(String email){
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean passwordValidation(String password){
        return password.length() > Constants.PASSWORD_MIN_LENGTH;
    }
}
