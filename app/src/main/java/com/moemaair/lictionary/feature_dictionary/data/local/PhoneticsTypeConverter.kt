package com.moemaair.lictionary.feature_dictionary.data.local
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moemaair.lictionary.feature_dictionary.domain.model.Phonetics



class PhoneticsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<Phonetics> {
        val listType = object : TypeToken<List<Phonetics>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Phonetics>): String {
        return Gson().toJson(list)
    }
}