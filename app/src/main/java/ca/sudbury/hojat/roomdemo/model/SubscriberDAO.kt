package ca.sudbury.hojat.roomdemo.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long
    // The Long variable which is returned from this function,
    // notifies us whether the insert operation to database was
    // successful or not.

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int
    // the Int variable which is returned from this function,
    // indicates the number of entries updated.

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int
    // the Int returned from this function indicates the number
    // of rows deleted.

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}