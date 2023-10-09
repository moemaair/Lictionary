import com.google.gson.Gson
import com.moemaair.lictionary.feature_lictionary.data.util.JsonParser
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
) : JsonParser{
    override fun <T> fromJson(json: String, type: Type): T? {
       return gson.fromJson(json,type)
    }

    override fun <T> toJSon(obj: T, type: Type): String? {
       return gson.toJson(obj, type)
    }

}