package io.adrop.ads.example.helper;

import io.adrop.ads.model.AdropErrorCode;

public class ErrorUtils {
    public static String descriptionOf(AdropErrorCode errorCode) {
        switch (errorCode) {
            case ERROR_CODE_NETWORK:
                return "The network status is unstable";
            case ERROR_CODE_INTERNAL:
                return "Exception in SDK";
            case ERROR_CODE_INITIALIZE:
                return "Initialize Adrop first before you act something";
            case ERROR_CODE_INVALID_UNIT:
                return "Ad unit ID is not valid";
            case ERROR_CODE_AD_INACTIVE:
                return "There are no active advertising campaigns";
            case ERROR_CODE_AD_NO_FILL:
                return "Unable to receive ads that meet the criteria. Please retry";
            case ERROR_CODE_AD_LOAD_DUPLICATED:
                return "You can't load again after ad received successfully";
            case ERROR_CODE_AD_LOADING:
                return "Waiting ad response from server after request ad";
            case ERROR_CODE_AD_EMPTY:
                return "There is no ad received";
            case ERROR_CODE_AD_SHOWN:
                return "This ad was shown already";
            case ERROR_CODE_AD_HIDE_FOR_TODAY:
                return "You can't load ad for today.";
            case ERROR_CODE_ACCOUNT_USAGE_LIMIT_EXCEEDED:
                return "App key (account) usage exceeded limit";
            default:
                return "";
        }
    }
}
