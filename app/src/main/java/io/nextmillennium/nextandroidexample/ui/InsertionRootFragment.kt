package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import io.nextmillennium.nextandroidexample.R

class InsertionRootFragment : ListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_insertion_root, container, false)

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val action = when (listView.getItemAtPosition(position)) {
            "Activity" -> R.id.action_nav_insertion_to_insertionActivity
            else -> R.id.action_nav_insertion_to_insertionFragment
        }
        findNavController().navigate(action)
    }
}