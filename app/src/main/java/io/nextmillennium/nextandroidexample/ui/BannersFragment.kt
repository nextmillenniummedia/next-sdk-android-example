package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.FragmentBannersBinding
import io.nextmillennium.nextsdk.network.callbacks.FetchListener
import io.nextmillennium.nextsdk.ui.banner.NextBannerView

class BannersFragment : Fragment() {

    private lateinit var binding: FragmentBannersBinding
    private lateinit var simpleBanner: NextBannerView
    private lateinit var mediumRectangle: NextBannerView
    private lateinit var anchoredBanner: NextBannerView
    private lateinit var inlineBanner: NextBannerView
    private val showSnackbar = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        simpleBanner = binding.bannerSimple
        mediumRectangle = binding.bannerMrec
        anchoredBanner = binding.bannerAnchored
        inlineBanner = binding.bannerInline
        simpleBanner.unitId = preferences.getString("banner", "103")
        mediumRectangle.unitId = preferences.getString("banner_mrec", "104")
        anchoredBanner.unitId = preferences.getString("banner_anchored", "105")
        inlineBanner.unitId = preferences.getString("banner_inline", "112")
        val load = binding.loadBanners
        mediumRectangle.isCollapsible = true
        inlineBanner.setContainerWidth(binding.inlineBannerContainer.width)
        load.setOnClickListener {
            simpleBanner.setFetchListener(createListener(simpleBanner.unitId))
            simpleBanner.load()
            anchoredBanner.setFetchListener(createListener(anchoredBanner.unitId))
            anchoredBanner.load()
            mediumRectangle.setFetchListener(createListener(mediumRectangle.unitId))
            mediumRectangle.load()
            inlineBanner.setFetchListener(createListener(inlineBanner.unitId))
            inlineBanner.load()
        }
    }

    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                if (!showSnackbar) return
                Snackbar.make(
                    binding.root,
                    "Successfully loaded banner : $unitId",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                if (!showSnackbar) return
                Snackbar.make(binding.root, "Error banner load: $err", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.deep_orange_A700
                        )
                    ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        simpleBanner.resume()
        anchoredBanner.resume()
        mediumRectangle.resume()
    }

    override fun onPause() {
        simpleBanner.pause()
        anchoredBanner.pause()
        mediumRectangle.pause()
        super.onPause()
    }

    override fun onDestroyView() {
        simpleBanner.destroy()
        anchoredBanner.destroy()
        mediumRectangle.destroy()
        super.onDestroyView()
    }
}