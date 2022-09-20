package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import io.nextmillennium.nextandroidexample.databinding.FragmentRewardedBinding
import io.nextmillennium.nextsdk.ui.fullscreen.rewarded.InAppReward
import io.nextmillennium.nextsdk.ui.fullscreen.rewarded.NextRewardedAd
import io.nextmillennium.nextsdk.ui.fullscreen.rewarded.RewardedAdListener
import io.nextmillennium.nextsdk.ui.fullscreen.rewarded.RewardedAdProvider


class RewardedFragment : Fragment(), RewardedAdListener {

    private lateinit var binding: FragmentRewardedBinding
    private var provider: RewardedAdProvider? = null
    private var rewardedAd: NextRewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRewardedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadButton: Button = binding.loadRewarded
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("rewarded", "107")
        provider = RewardedAdProvider(requireContext(), id)
        provider?.setListener(this)
        provider?.load()
        loadButton.setOnClickListener {
            rewardedAd?.show()
        }
    }

    override fun onAdLoaded(rewarded: NextRewardedAd?) {
        rewardedAd = rewarded
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        Toast.makeText(
            requireContext(),
            "User earned ${reward?.amount} ${reward?.rewardType}",
            Toast.LENGTH_SHORT
        ).show()
    }
}