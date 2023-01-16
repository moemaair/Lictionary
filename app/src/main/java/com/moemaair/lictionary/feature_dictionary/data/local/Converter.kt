import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Meaning
import com.moemaair.lictionary.feature_dictionary.data.util.JsonParser

@ProvidedTypeConverter
class Converter (
    private val jsonParser: JsonParser
){
    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }
}