package ca.sudbury.hojat.roomdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ca.sudbury.hojat.roomdemo.databinding.ActivityMainBinding
import ca.sudbury.hojat.roomdemo.db.SubscriberDataBase
import ca.sudbury.hojat.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // the binding between View layer and UI xml files.
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel =
            subscriberViewModel // here we set the ViewModel that controls the bindings in the View layer (bindings between xml UI and View classes).
        binding.lifecycleOwner =
            this // but that binding exists just as long as this Activity lives.
        displaySubscribersList()

    }

    // The observable that looks at data received and draws it to the UI
    private fun displaySubscribersList() {
        // we're in the View layer so all our data-related communications will be with ViewModel
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG", it.toString())
        })
    }
}