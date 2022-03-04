package ca.sudbury.hojat.roomdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.sudbury.hojat.roomdemo.model.SubscriberRepository

/**
 * Created by Hojat Ghasemi at 2022-03-01
 * Contact the author at "https://github.com/hojat72elect"
 *
 * The builder class that will create the "ViewModel" for us.
 */
class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // boilerplate that will be used for all ViewModel factories
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}