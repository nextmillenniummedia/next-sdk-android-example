package io.nextmillennium.nextandroidexample.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.ItemHumanBinding
import io.nextmillennium.nextsdk.ui.banner.NextBannerView

class PeopleAdapter(private val people: List<Person>, ads: List<String> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<Any> = mutableListOf()
    private val ITEMS_PER_AD: Int = 1

    init {
        for (i in people.indices) {
            items.add(people[i])
            if (i % ITEMS_PER_AD == 0) {
                items.add(ads[i % ads.size])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.PERSON_VIEW.ordinal -> ViewHolder(
                ItemHumanBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                val adLayout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_container, parent, false)
                return AdsViewHolder(adLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.PERSON_VIEW.ordinal -> {
                val human = people[position]
                holder as ViewHolder
                holder.nameView.text = human.name
                holder.descriptionView.text = human.description
                Picasso.get()
                    .load(human.imageSrc)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(holder.avatarView)
            }
            else -> {
                val root = holder.itemView as FrameLayout
                val context = holder.itemView.context
                val id = items[position] as String
                val adView = NextBannerView(context)
                adView.unitId = id
                var initialLayoutComplete = false
                if (root.childCount > 0) root.removeAllViews()
                root.addView(adView)
                root.viewTreeObserver.addOnGlobalLayoutListener {
                    if (initialLayoutComplete) return@addOnGlobalLayoutListener
                    initialLayoutComplete = true
                    val density = context.resources.displayMetrics.density
                    adView.setContainerWidth((root.width.toFloat() / density).toInt())
                    adView.load()
                }
            }
        }

    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (position % (ITEMS_PER_AD + 1) == 0) ItemType.PERSON_VIEW.ordinal else ItemType.AD_VIEW.ordinal

    inner class ViewHolder(binding: ItemHumanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val avatarView = binding.image
        val nameView = binding.name
        val descriptionView = binding.desc
        val parentLayout = binding.parentLayout
    }

    class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

}