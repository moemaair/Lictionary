package com.moemaair.lictionary.feature_dictionary.data.local

import Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntity


@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class WordInfoDatabase :RoomDatabase(){
    abstract val dao: WordInfoDao
}