package ca.sudbury.hojat.roomdemo.model

/**
 * Created by Hojat Ghasemi at 2022-03-01
 * Contact the author at "https://github.com/hojat72elect"
 *
 * The access-point between Model layer and ViewModel layer
 */
class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber): Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}