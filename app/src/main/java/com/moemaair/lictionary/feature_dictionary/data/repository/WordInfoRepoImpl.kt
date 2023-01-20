import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_dictionary.domain.repository.WordInfoRepo
import kotlinx.coroutines.flow.Flow

class WordInfoRepoImpl : WordInfoRepo{
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {
        TODO("Not yet implemented")
    }

}