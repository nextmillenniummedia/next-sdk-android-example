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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannersBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val simpleBanner: InAppBannerView? = binding?.bannerSimple
        val mediumRectangle: InAppBannerView? = binding?.bannerMrec
        val anchoredBanner: InAppBannerView? = binding?.bannerAnchored
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        simpleBanner?.inAppUnitId = preferences.getString("banner", "751")
        simpleBanner?.load({ showLoaded(simpleBanner.inAppUnitId) }, { showError(it) })
        anchoredBanner?.inAppUnitId = preferences.getString("banner_anchored", "753")
        anchoredBanner?.load({ showLoaded(anchoredBanner.inAppUnitId) }, { showError(it) })
        mediumRectangle?.inAppUnitId = preferences.getString("banner_mrec", "752")
        mediumRectangle?.load({ showLoaded(mediumRectangle.inAppUnitId) }, { showError(it) })
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
}