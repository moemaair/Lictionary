package com.moemaair.lictionary.ui.theme



import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moemaair.lictionary.R

val playfair_display_font = FontFamily(
    Font(R.font.playfair_display_font, weight = FontWeight.Normal),

)
val montserrat_font = FontFamily(
    Font(R.font.montserrat_thin, weight = FontWeight.Light),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal)
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = montserrat_font,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    )
    ,
    subtitle1 = TextStyle(
        fontFamily = montserrat_font,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = montserrat_font,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    ),
    h3 = TextStyle(  // title in appbar
        fontFamily = montserrat_font,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    )
    ,
    h4 = TextStyle(  // title in appbar
        fontFamily = montserrat_font,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    )
    ,
    h2 = TextStyle(  // word from api
        fontFamily = playfair_display_font,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 1.2.sp
    ),
    h5 = TextStyle(  // word from api
        fontFamily = montserrat_font,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = Color.LightGray
    )





    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)