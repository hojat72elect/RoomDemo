package ca.sudbury.hojat.roomdemo.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /*ViewModel needs to implement the "androidx.databinding.Observable" interface so it can access/manipulate bindings in View layer. This interface defines 2 functions:
    * 1- addOnPropertyChangedCallback()
    * 2- removeOnPropertyChangedCallback()
    * */

    // Pay attention that in ViewModel, we're getting our needed data from repository.
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

    init {
        // According to the state of this class, the text shown
        // on these buttons will change automatically.
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // This function is bound to a button in main UI layout
    fun saveOrUpdate() {
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
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.update(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.delete(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.deleteAll()
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