package com.moemaair.lictionary.feature_dictionary.data.local


import com.moemaair.lictionary.feature_dictionary.domain.model.Meaning
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.moemaair.lictionary.feature_dictionary.data.util.JsonParser

@ProvidedTypeConverter
class Converters (
    private val jsonParser: JsonParser
){
    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }
    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJSon(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }
}