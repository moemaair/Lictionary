package com.moemaair.lictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Query("SELECT * FROM lictionary_table WHERE word LIKE '%' || :words || '%'")
    suspend fun getWordInfo(words: List<WordInfoEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntity>)


    @Query("DELETE FROM lictionary_table WHERE word IN (:words)")
    suspend fun deteleWprdInfo(words: List<String>)



}