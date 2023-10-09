import com.moemaair.lictionary.feature_lictionary.data.util.Claims

import com.auth0.android.jwt.JWT

data class GoogleUser(

    val email: String?,
    val fullName: String?,
    val picture: String?,

)

/**
 * Use this function to extract [GoogleUser] information from a token id that you're
 * getting from One-Tap Sign in.
 * */
fun getUserFromTokenId(tokenId: String): GoogleUser {
    val jwt = JWT(tokenId)
    return GoogleUser(
        email = jwt.claims[Claims.EMAIL]?.asString(),
        fullName = jwt.claims[Claims.FUll_NAME]?.asString(),
        picture = jwt.claims[Claims.PICTURE]?.asString(),

    )
}
