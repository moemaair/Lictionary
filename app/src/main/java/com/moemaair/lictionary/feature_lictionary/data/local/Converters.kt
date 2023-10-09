package com.moemaair.lictionary.feature_lictionary.data.local


import com.moemaair.lictionary.feature_lictionary.domain.model.Meaning
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.moemaair.lictionary.feature_lictionary.data.util.JsonParser

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