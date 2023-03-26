package uk.ac.aber.cs31620.learningapp.datasource.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class Translation(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0,

    @NonNull
    @ColumnInfo(name = "translation_from")
    var from: String = "",

    @NonNull
    @ColumnInfo(name = "translation_to")
    var to: String = ""
){
    constructor(from: String, to: String) : this(0, from, to)
}