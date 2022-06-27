package com.nextmillennium.androidexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.nextmillennium.androidexample.R
import com.nextmillennium.androidexample.databinding.FragmentInterstitialBinding
import com.nextmillennium.androidexample.databinding.LoadPartBinding
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppInterstitialAd

class InterstitialFragment : Fragment() {

    private var binding: FragmentInterstitialBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterstitialBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadBinding: LoadPartBinding? = binding?.loadPartInterstitial
        val unitInput: EditText? = loadBinding?.unitId
        val clearQuery: ImageButton? = loadBinding?.clearSearchQueryButton
        val loadButton: Button? = loadBinding?.loadButton
        clearQuery?.setOnClickListener {
            unitInput?.text?.clear()
        }
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("interstitial", "415")
        unitInput?.setText(id)
        loadButton?.setOnClickListener {
            val enteredId: String = unitInput?.text.toString()
            if (enteredId.isEmpty()) {
                binding?.let {
                    Snackbar.make(it.root, "You need to enter unit id!", Snackbar.LENGTH_SHORT)
                        .show()
                }
                return@setOnClickListener
            }
            InAppInterstitialAd(requireContext(), enteredId).load()
        }
    }
}