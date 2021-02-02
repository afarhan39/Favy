package my.farhan.favy.data.db

import androidx.room.TypeConverter

/***
 * An util class for Room to read and write
 */
class Converters {
    @TypeConverter
    fun fromListOfStrings(list: List<String>?): String {
        return list?.joinToString(separator = ";") { it } ?: ""
    }

    @TypeConverter
    fun toListOfStrings(string: String?): List<String> {
        return ArrayList(string?.split(";")?.map { it } ?: emptyList())
    }
}