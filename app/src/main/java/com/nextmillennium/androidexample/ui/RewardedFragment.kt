package com.nextmillennium.androidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.nextmillennium.androidexample.databinding.FragmentRewardedBinding
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppReward
import com.nextmillennium.inappsdk.core.ui.fullscreen.InAppRewardedAd
import com.nextmillennium.inappsdk.core.ui.fullscreen.RewardedListener


class RewardedFragment : Fragment(), RewardedListener {

    private var binding: FragmentRewardedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardedBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadButton: Button? = binding?.loadRewarded
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("rewarded", "755")
        loadButton?.setOnClickListener {
            InAppRewardedAd(requireContext(), id).setListener(this).load()
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