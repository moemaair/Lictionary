package com.moemaair.lictionary

import android.view.View
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.moemaair.lictionary.feature_lictionary.data.local.Claim
import com.moemaair.lictionary.feature_lictionary.data.local.UserDetail

class MainVm : ViewModel(){
    fun getUserFromTokenId(tokenId: String): UserDetail {
        val jwt = JWT(tokenId)

        return UserDetail(
            email = jwt.claims[Claim.EMAIL]?.asString(),
            fullName = jwt.claims[Claim.FUll_NAME]?.asString(),
            picture = jwt.claims[Claim.PICTURE]?.asString(),
            sub = jwt.claims[Claim.SUB]?.asString(),
        )

    }
}