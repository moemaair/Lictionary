package com.moemaair.lictionary

import android.util.Log
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.moemaair.lictionary.feature_lictionary.data.local.Claim
import com.moemaair.lictionary.feature_lictionary.data.local.UserDetail
import com.stevdzasan.messagebar.rememberMessageBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainVm {

    fun getUserFromTokenId(
        tokenId: String,

    ): UserDetail {
        val jwt = JWT(tokenId)


        val res = UserDetail(
                    email = jwt.claims[Claim.EMAIL]?.asString(),
                    fullName = jwt.claims[Claim.FUll_NAME]?.asString(),
                    picture = jwt.claims[Claim.PICTURE]?.asString(),
                    //sub = jwt.claims[Claim.SUB]?.asString(),
            )

        return res





    }
}