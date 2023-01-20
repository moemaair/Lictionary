import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Definition

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)