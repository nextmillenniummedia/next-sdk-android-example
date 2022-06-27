package com.nextmillennium.androidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.nextmillennium.androidexample.databinding.FragmentRewardedInterstitialBinding
import com.nextmillennium.androidexample.databinding.LoadPartBinding
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppReward
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppRewardedAd
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppRewardedInterstitialAd
import com.nextmillennium.inappsdk.core.ui.fullscreen.RewardedInterstitialListener

class RewardedInterstitialFragment : Fragment(), RewardedInterstitialListener {
    private var binding: FragmentRewardedInterstitialBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardedInterstitialBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadBinding: LoadPartBinding? = binding?.loadPartRewardedInterstitial
        val unitInput: EditText? = loadBinding?.unitId
        val clearQuery: ImageButton? = loadBinding?.clearSearchQueryButton
        val loadButton: Button? = loadBinding?.loadButton
        clearQuery?.setOnClickListener {
            unitInput?.text?.clear()
        }
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("rewarded_interstitial", "425")
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
            InAppRewardedInterstitialAd(requireContext(), enteredId).setListener(this).load()
        }
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        Toast.makeText(
            requireContext(),
            "User earned ${reward?.amount} ${reward?.rewardType}",
            Toast.LENGTH_SHORT
        ).show()
    }
}