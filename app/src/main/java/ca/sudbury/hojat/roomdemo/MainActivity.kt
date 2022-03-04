package ca.sudbury.hojat.roomdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ca.sudbury.hojat.roomdemo.databinding.ActivityMainBinding
import ca.sudbury.hojat.roomdemo.model.Subscriber
import ca.sudbury.hojat.roomdemo.model.SubscriberDataBase
import ca.sudbury.hojat.roomdemo.model.SubscriberRepository
import ca.sudbury.hojat.roomdemo.viewmodel.MyRecyclerViewAdapter
import ca.sudbury.hojat.roomdemo.viewmodel.SubscriberViewModel
import ca.sudbury.hojat.roomdemo.viewmodel.SubscriberViewModelFactory

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
        // here we set the ViewModel that controls the bindings in the
        // View layer (bindings between xml UI and View classes).
        binding.myViewModel = subscriberViewModel
        // but that binding exists just as long as this Activity lives:
        binding.lifecycleOwner = this
        initRecyclerView()

        // register an observer to respond to messages from ViewModel.
        subscriberViewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // The observable that looks at data received and draws it to the UI
    private fun displaySubscribersList() {
        // we're in the View layer so all our data-related communications will be with ViewModel
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            binding.subscriberRecyclerView.adapter =
                MyRecyclerViewAdapter(
                    it
                ) { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        })
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}