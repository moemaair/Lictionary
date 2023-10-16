package com.moemaair.lictionary.feature_lictionary.presentation

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.moemaair.lictionary.R

import androidx.compose.material3.ExperimentalMaterial3Api
@SuppressLint("RestrictedApi")
@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    audioIcon: ImageVector,

) {
    var context = LocalContext.current.applicationContext
    var mediaPlayer = MediaPlayer()

    var audioUrl: String? by remember {
        mutableStateOf("")
    }
    val audio = wordInfo.phonetics.forEach{ it ->
        if (!(it.audio == "")){
            audioUrl = it.audio
        }
    }
    var phonetic: String? by remember {
        mutableStateOf("")
    }
    val textPhonetic = wordInfo.phonetics.forEach{it ->  phonetic = it.text}

    Column(modifier = Modifier) {

        Row(modifier = Modifier.fillMaxSize().padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            //word
            Text(
                text = wordInfo.word.toString(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )

            //audio icon

            IconButton(onClick = {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(audioUrl.toString())
                mediaPlayer.prepare()
                mediaPlayer.start()
            }

            ) {

               if((audioUrl?.isNotEmpty() == true)){
                   Icon(imageVector = audioIcon, contentDescription ="" )
               }

            }
        }


        Text(text = phonetic.toString(), fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(16.dp))

       if(wordInfo.word.length <= 0){
           Image(painter = painterResource(id = R.drawable.man), contentDescription = "")
       }
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