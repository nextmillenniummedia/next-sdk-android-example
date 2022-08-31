package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.FragmentInterstitialBinding
import io.nextmillennium.nextsdk.network.NextAdError
import io.nextmillennium.nextsdk.ui.fullscreen.interstitial.InterstitialAdListener
import io.nextmillennium.nextsdk.ui.fullscreen.interstitial.InterstitialAdProvider
import io.nextmillennium.nextsdk.ui.fullscreen.interstitial.NextInterstitialAd

class InterstitialFragment : Fragment(), InterstitialAdListener {

    private lateinit var binding: FragmentInterstitialBinding
    private var provider: InterstitialAdProvider? = null
    private var interstitial: NextInterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterstitialBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadButton: Button = binding.loadInterstitial
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val id = preferences.getString("interstitial", "106")
        loadButton.setOnClickListener {
            provider = InterstitialAdProvider(requireContext(), id)
            provider?.setListener(this)
            provider?.load()
        }
    }

    override fun onAdLoaded(ad: NextInterstitialAd?) {
        interstitial = ad
        interstitial?.show()
    }

    override fun onFullScreenAdLoadFail(loadError: NextAdError?) {
        super.onFullScreenAdLoadFail(loadError)
        Snackbar.make(binding.root, "Interstitial load error: $loadError", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.deep_orange_A700))
            .show()
    }
}