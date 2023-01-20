import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Definition

data class Meaning(  // Mapper class of Meaning dto
    val definitions: List<Definition>,
    val partOfSpeech: String
)