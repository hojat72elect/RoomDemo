package ca.sudbury.hojat.roomdemo

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sudbury.hojat.roomdemo.db.Subscriber
import ca.sudbury.hojat.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.launch

/**
 * Created by Hojat Ghasemi at 2022-03-01
 * Contact the author at "https://github.com/hojat72elect"
 *
 * As the name suggests, this class defines the ViewModel layer
 * for connection between our GUI classes and "Subscriber Repository".
 */
class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    // Pay attention that in ViewModel, we're getting our needed data from repository.
    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        // According to the state of this class, the text shown
        // on these buttons will change automatically.
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // This function is bound to a button in main UI layout
    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = null
        inputEmail.value = null
    }

    // This function is bound to a button in main UI layout
    fun clearAllOrDelete() {
        clearAll() // this is just for now
    }

    fun insert(subscriber: Subscriber) {
        // This function will work on a background thread
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}