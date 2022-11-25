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

class NativeFragment : Fragment(), NextAdListener {

    private var binding: FragmentNativeBinding? = null
    private var nativeView: NextNativeView? = null

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
        nativeView?.setAdListener(this)
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
            nativeView?.setFetchListener(object : FetchListener {
                override fun onSuccess() {
                    binding?.let {
                        Snackbar.make(
                            it.root,
                            "Successfully loaded banner : ${nativeView?.unitId}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(err: Throwable?) {
                    binding?.let {
                        Snackbar.make(it.root, "Error banner load: $err", Snackbar.LENGTH_SHORT)
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
                nativeView?.setResourceId(R.layout.custom_native)
            }
            nativeView?.load()
        }
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        nativeView = container as NextNativeView
        Log.d("NEXT_SDK", "Successfully loaded ad : ${container.id}")
    }

    override fun onAdClicked() {
        Log.d("NEXT_SDK", "Successfully tracked click")
    }

    override fun onAdImpression() {
        Log.d("NEXT_SDK", "Successfully tracked impression")
    }

    override fun onAdLoadFail(adError: NextAdError?) {
        Log.e("NEXT_SDK", adError.toString())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        nativeView?.destroy()
        binding = null
    }
}