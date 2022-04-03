package ca.sudbury.hojat.roomdemo.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.sudbury.hojat.roomdemo.Event
import ca.sudbury.hojat.roomdemo.model.Subscriber
import ca.sudbury.hojat.roomdemo.model.SubscriberRepository
import kotlinx.coroutines.launch

/**
 * Created by Hojat Ghasemi at 2022-03-01
 * Contact the author at "https://github.com/hojat72elect"
 *
 * As the name suggests, this class defines the ViewModel layer
 * for connection between our GUI classes and "Subscriber Repository".
 */
class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    /** ViewModel needs to implement the "androidx.databinding.Observable" interface, so it can access/manipulate
     * bindings in View layer. This interface defines 2 functions:
     * 1- addOnPropertyChangedCallback()
     * 2- removeOnPropertyChangedCallback()
     *
     *
     * Pay attention that in ViewModel, we're getting our
     * needed data from repository.*/

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // for handling the events that might happen in View layer.
    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage
    // The public field is immutable live data. And when other
    // classes access this field, we return a private mutable
    // live data. This is a good practice for data security.

    init {
        // According to the state of this class, the text shown
        // on these buttons will change automatically.
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // This function is bound to a button in main UI layout
    fun saveOrUpdate() {

        // This part is for user input validation.
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            // input is validated and ready to be used:
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    // This function is bound to a button in main UI layout
    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) {
        // This function will work on a background thread
        viewModelScope.launch {
            val newRowId = repository.insert(subscriber)
            if (newRowId > -1) {
                statusMessage.value = Event("Subscriber inserted successfully:($newRowId)")
            } else {
                statusMessage.value = Event("Error occurred.")
            }
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            val numberOfRows = repository.update(subscriber)
            if (numberOfRows > 0) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$numberOfRows rows updated successfully.")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            val numberOfRowsDeleted = repository.delete(subscriber)
            if (numberOfRowsDeleted > 0) {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$numberOfRowsDeleted rows deleted successfully.")
            } else {
                statusMessage.value = Event("Error occurred.")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            val numberOfRowsDeleted = repository.deleteAll()
            if (numberOfRowsDeleted > 0) {
                statusMessage.value = Event("$numberOfRowsDeleted rows deleted successfully.")
            } else {
                statusMessage.value = Event("Error occurred")
            }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}