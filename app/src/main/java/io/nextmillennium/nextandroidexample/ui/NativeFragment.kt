package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.FragmentNativeBinding
import io.nextmillennium.nextsdk.network.NextAdError
import io.nextmillennium.nextsdk.network.callbacks.FetchListener
import io.nextmillennium.nextsdk.ui.BaseAdContainer
import io.nextmillennium.nextsdk.ui.NextAdListener
import io.nextmillennium.nextsdk.ui.nativeads.NextNativeView
import io.nextmillennium.nextsdk.ui.nativeads.NextVideoListener

class NativeFragment : Fragment(), NextAdListener {

    private var binding: FragmentNativeBinding? = null
    private var nativeView: NextNativeView? = null
    private val LOG_TAG = "NEXT_SDK"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNativeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        nativeView = binding?.nativeView
        val nativeType = binding?.nativeTypeGroup
        val load = binding?.loadNative
        val smallId = preferences.getString("native_small", "108")
        val mediumId = preferences.getString("native_medium", "109")
        load?.setOnClickListener {
            nativeView?.destroy()
            val unitId =
                if (nativeType?.checkedRadioButtonId == R.id.radio_native_small) smallId
                else mediumId
            nativeView?.unitId = unitId
            nativeView?.setAdListener(createAdListener(unitId ?: ""))
            nativeView?.setFetchListener(object : FetchListener {
                override fun onSuccess() {
                    binding?.let { b ->
                        Snackbar.make(
                            b.root,
                            "Successfully loaded banner : ${nativeView?.unitId}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(err: Throwable?) {
                    binding?.let { b ->
                        Snackbar.make(b.root, "Error banner load: $err", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.deep_orange_A700
                                )
                            )
                            .show()
                    }
                }
            })
            if (nativeType?.checkedRadioButtonId == R.id.radio_native_custom) {
                nativeView?.options?.resourceId = R.layout.custom_native
            }
            nativeView?.options?.videoListener = createVideoListener(nativeView?.unitId ?: "")
            nativeView?.load()
        }
    }

    private fun createVideoListener(unitId: String): NextVideoListener {
        return object : NextVideoListener {
            override fun onStart() {
                logEvent("Started video event for unit $unitId")
            }

            override fun onPlay() {
                logEvent("Play video event for unit $unitId")
            }

            override fun onPause() {
                logEvent("Paused video ad event for unit $unitId")
            }

            override fun onEnd() {
                logEvent("End video event for unit $unitId")
            }

            override fun onMute(isMuted: Boolean) {
                logEvent("Video ad muted: $isMuted for unit $unitId")
            }
        }
    }

    private fun createAdListener(unitId: String): NextAdListener {
        return object : NextAdListener {
            override fun onAdClicked() {
                logEvent("Successfully tracked click for unit $unitId")
            }

            override fun onAdImpression() {
                logEvent("Successfully tracked impression for unit $unitId")
            }

            override fun onAdLoadFail(adError: NextAdError?) {
                logEvent("Error loading unit $unitId ${adError.toString()}")
            }

            override fun onAdLoaded(container: BaseAdContainer?) {
                logEvent("Successfully loaded ad : ${container?.id}. container is NextNativeView: ${container is NextNativeView}")
                nativeView = container as NextNativeView
            }

            override fun onAdClosed() {
                logEvent("Closed ad $unitId")
            }

            override fun onAdOpened() {
                logEvent("Opened ad $unitId")
            }
        }
    }

    fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        binding?.let { b ->
            Snackbar
                .make(b.root, message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nativeView?.destroy()
        binding = null
    }
}