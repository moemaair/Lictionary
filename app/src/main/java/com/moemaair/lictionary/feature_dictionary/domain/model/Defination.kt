import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Definition
data class Defination(   // mapper class for Defination Dto
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)

