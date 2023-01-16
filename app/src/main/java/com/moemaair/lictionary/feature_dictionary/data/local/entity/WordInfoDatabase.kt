import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.moemaair.lictionary.feature_dictionary.data.local.WordInfoDao
import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntry


@Database(
    entities = [WordInfoEntry::class],
    version = 1
)

@TypeConverters(Converter::class)
abstract class WordInfoDatabase :RoomDatabase(){
    abstract val dao: WordInfoDao
}