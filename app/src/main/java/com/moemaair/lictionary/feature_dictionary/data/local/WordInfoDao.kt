package com.moemaair.lictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntry
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.WordInfoDto

@Dao
interface WordInfoDao {

    @Query("SELECT * FROM LICTIONARY_TABLE WHERE word LIKE '%' || :words || '%'")
    suspend fun getWordInfo(words: List<WordInfoEntry>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntry>)


    @Query("DELETE FROM lictionary_table WHERE word IN (:words)")
    suspend fun deteleWprdInfo(words: List<String>)



}