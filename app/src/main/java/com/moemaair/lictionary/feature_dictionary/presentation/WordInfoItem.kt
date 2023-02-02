package com.moemaair.lictionary.feature_dictionary.presentation

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.runBlocking
import android.net.Uri

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    audioIcon: ImageVector,

) {
    var context = LocalContext.current.applicationContext
    val mediaPlayer = remember {
        mutableStateOf(MediaPlayer())
    }
    val url = wordInfo.phonetics.forEach{
           it.audio
    }


    Column(modifier = Modifier) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            //word
            Text(
                text = wordInfo.word,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
            //audio icon

            IconButton(onClick = {

                mediaPlayer.value.apply {
                    setDataSource(url.toString())
                    mediaPlayer
                    mediaPlayer
                }

            }
            ) {
               Icon(imageVector = audioIcon, contentDescription ="" )
            }
        }


        Text(text = wordInfo.phonetic.toString(), fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(16.dp))

        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)
            meaning.definitions.forEachIndexed { i, definition ->
                Text(text = "${i + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example", fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
                }
                definition
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    
}