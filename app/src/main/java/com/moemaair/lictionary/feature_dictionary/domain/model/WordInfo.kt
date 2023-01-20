import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Meaning

data class WordInfo(  // Mapper class of WordInfoDto
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)