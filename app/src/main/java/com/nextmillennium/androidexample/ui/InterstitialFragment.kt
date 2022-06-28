package com.nextmillennium.androidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.nextmillennium.androidexample.databinding.FragmentInterstitialBinding
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
        val loadButton: Button? = binding?.loadInterstitial
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("interstitial", "754")
        loadButton?.setOnClickListener {
            InAppInterstitialAd(requireContext(), id).load()
        }
    }
}