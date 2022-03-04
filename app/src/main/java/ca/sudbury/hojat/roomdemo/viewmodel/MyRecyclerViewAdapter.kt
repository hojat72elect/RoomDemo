package ca.sudbury.hojat.roomdemo.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ca.sudbury.hojat.roomdemo.R
import ca.sudbury.hojat.roomdemo.databinding.ListItemBinding
import ca.sudbury.hojat.roomdemo.model.Subscriber

/**
 * Created by Hojat Ghasemi at 2022-03-02
 * Contact the author at "https://github.com/hojat72elect"
 *
 * this file will define the 2 classes that are needed for
 * controlling the recycler view.
 */
class MyRecyclerViewAdapter(private val subscribersList: List<Subscriber>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // here we create each item of the recycler view.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position])
    }

    override fun getItemCount(): Int {
        // easy! just the number of items that are supposed to be drawn on the recycler view.
        return subscribersList.size
    }
}

/**
 * The @binding variable in this class will connect to the TextViews in the list_item.xml
 */
class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
    }

}