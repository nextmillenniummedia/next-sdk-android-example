package com.nextmillennium.androidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.nextmillennium.androidexample.databinding.FragmentBannersBinding
import com.nextmillennium.inappsdk.core.ui.InAppBannerView

class BannersFragment : Fragment() {

    private var binding: FragmentBannersBinding? = null
    private var simpleBanner: InAppBannerView? = null
    private var mediumRectangle: InAppBannerView? = null
    private var anchoredBanner: InAppBannerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannersBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        simpleBanner = binding?.bannerSimple
        mediumRectangle = binding?.bannerMrec
        anchoredBanner = binding?.bannerAnchored
        simpleBanner?.inAppUnitId = preferences.getString("banner", "751")
        mediumRectangle?.inAppUnitId = preferences.getString("banner_mrec", "752")
        anchoredBanner?.inAppUnitId = preferences.getString("banner_anchored", "753")
        val load = binding?.loadBanners
        load?.setOnClickListener {
            simpleBanner?.load({ showLoaded(simpleBanner?.inAppUnitId ?: "") }, { showError(it) })
            anchoredBanner?.load(
                { showLoaded(anchoredBanner?.inAppUnitId ?: "") },
                { showError(it) })
            mediumRectangle?.load(
                { showLoaded(mediumRectangle?.inAppUnitId ?: "") },
                { showError(it) })
        }
    }

    fun showLoaded(message: String = "") {
        binding?.let {
            Snackbar.make(
                it.root,
                "Successfully loaded banner : $message",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun showError(error: Throwable) {
        binding?.let {
            Snackbar.make(it.root, "Error banner load: $error", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        simpleBanner?.resume()
        anchoredBanner?.resume()
        mediumRectangle?.resume()
    }

    override fun onPause() {
        simpleBanner?.pause()
        anchoredBanner?.pause()
        mediumRectangle?.pause()
        super.onPause()
    }

    override fun onDestroyView() {
        simpleBanner?.destroy()
        anchoredBanner?.destroy()
        mediumRectangle?.destroy()
        super.onDestroyView()
    }
}