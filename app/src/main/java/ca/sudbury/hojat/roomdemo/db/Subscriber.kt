package ca.sudbury.hojat.roomdemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @ColumnInfo(name = "subscriber_id")
    val id: Int,

    @ColumnInfo(name = "subscriber_name")
    val name: String,

    @ColumnInfo(name = "subscriber_email")
    val email: String
)