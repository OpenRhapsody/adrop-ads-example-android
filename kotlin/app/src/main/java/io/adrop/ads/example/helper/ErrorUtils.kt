package io.adrop.ads.example.helper

import io.adrop.ads.model.AdropErrorCode

object ErrorUtils {

    @JvmStatic
    fun descriptionOf(errorCode: AdropErrorCode?): String {
        return when (errorCode) {
            AdropErrorCode.ERROR_CODE_NETWORK -> "The network status is unstable"
            AdropErrorCode.ERROR_CODE_INTERNAL -> "Exception in SDK"
            AdropErrorCode.ERROR_CODE_INITIALIZE -> "Initialize Adrop first before you act something"
            AdropErrorCode.ERROR_CODE_INVALID_UNIT -> "Ad unit ID is not valid"
            AdropErrorCode.ERROR_CODE_AD_INACTIVE -> "There are no active advertising campaigns"
            AdropErrorCode.ERROR_CODE_AD_NO_FILL -> "Unable to receive ads that meet the criteria. Please retry"
            AdropErrorCode.ERROR_CODE_AD_LOAD_DUPLICATED -> "You can't load again after ad received successfully"
            AdropErrorCode.ERROR_CODE_AD_LOADING -> "Waiting ad response from server after request ad"
            AdropErrorCode.ERROR_CODE_AD_EMPTY -> "There is no ad received"
            AdropErrorCode.ERROR_CODE_AD_SHOWN -> "This ad was shown already"
            else -> ""
        }
    }
}
