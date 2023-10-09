package com.moemaair.lictionary.feature_lictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moemaair.lictionary.feature_lictionary.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract val dao: WordInfoDao
}