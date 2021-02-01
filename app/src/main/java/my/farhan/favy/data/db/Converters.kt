package my.farhan.favy.data.db

import androidx.room.TypeConverter


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