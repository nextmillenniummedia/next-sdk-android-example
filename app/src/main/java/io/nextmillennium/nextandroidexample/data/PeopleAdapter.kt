package io.nextmillennium.nextandroidexample.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import io.nextmillennium.nextandroidexample.databinding.ItemHumanBinding
import java.util.*

class PeopleAdapter(private val people: List<Person>, private val ads: List<String> = emptyList()) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    private val items: LinkedList<String> = LinkedList()
    private val ITEMS_PER_AD: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHumanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val human = people[position]
        holder.nameView.text = human.name
        holder.descriptionView.text = human.description
        Picasso.get()
            .load(human.imageSrc)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(holder.avatarView)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (position % ITEMS_PER_AD == 0) ItemType.PERSON_VIEW.ordinal else ItemType.AD_VIEW.ordinal

    inner class ViewHolder(binding: ItemHumanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val avatarView = binding.image
        val nameView = binding.name
        val descriptionView = binding.desc
        val parentLayout = binding.parentLayout
    }

    class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

}