package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.FragmentNativeBinding
import io.nextmillennium.nextsdk.network.callbacks.FetchListener

class NativeFragment : Fragment() {

    private lateinit var binding: FragmentNativeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNativeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val native = binding.nativeView
        val nativeType: RadioGroup = binding.nativeTypeGroup
        val load: Button = binding.loadNative
        val smallId = preferences.getString("native_small", "108")
        val mediumId = preferences.getString("native_medium", "109")
        load.setOnClickListener {
            val unitId =
                if (nativeType.checkedRadioButtonId == R.id.radio_native_small) smallId
                else mediumId
            native.unitId = unitId
            native.setFetchListener(object : FetchListener {
                override fun onSuccess() {
                    Snackbar.make(
                        binding.root,
                        "Successfully loaded banner : ${native.unitId}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onError(err: Throwable?) {
                    Snackbar.make(binding.root, "Error banner load: $err", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.deep_orange_A700
                            )
                        )
                        .show()
                }
            })
            if (nativeType.checkedRadioButtonId == R.id.radio_native_custom) {
                native.setResourceId(R.layout.custom_native)
            }
            native.load()
        }
    }
}