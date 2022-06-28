package com.nextmillennium.androidexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.nextmillennium.androidexample.R
import com.nextmillennium.androidexample.databinding.FragmentBannersBinding
import com.nextmillennium.androidexample.databinding.FragmentNativeBinding
import com.nextmillennium.inappsdk.core.ui.InAppBannerView

class NativeFragment : Fragment() {

    private var binding: FragmentNativeBinding? = null

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
        val native: InAppBannerView? = binding?.nativeView
        val nativeType: RadioGroup? = binding?.nativeTypeGroup
        val load: Button? = binding?.loadNative
        val smallId = preferences.getString("native_small", "756")
        val mediumId = preferences.getString("native_medium", "757")
        load?.setOnClickListener {
            val unitId =
                if (nativeType?.checkedRadioButtonId == R.id.radio_native_small) smallId
                else mediumId
            native?.inAppUnitId = unitId
            native?.load({ showLoaded(native.inAppUnitId) }, { showError(it) })
        }
    }

    fun showLoaded(message: String = "") =
        binding?.let {
            Snackbar.make(
                it.root,
                "Successfully loaded simple banner : $message",
                Snackbar.LENGTH_SHORT
            ).show()
        }


    fun showError(error: Throwable) =
        binding?.let {
            Snackbar.make(it.root, "Error banner load: $error", Snackbar.LENGTH_SHORT)
                .show()
        }

}