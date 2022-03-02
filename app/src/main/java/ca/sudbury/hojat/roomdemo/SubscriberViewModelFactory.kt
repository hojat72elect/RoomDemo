package ca.sudbury.hojat.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.sudbury.hojat.roomdemo.db.SubscriberRepository

/**
 * Created by Hojat Ghasemi at 2022-03-01
 * Contact the author at "https://github.com/hojat72elect"
 */
class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }
}