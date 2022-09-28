package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.databinding.FragmentAppOpenBinding
import io.nextmillennium.nextsdk.ui.fullscreen.app_open.AppOpenAdObserver
import io.nextmillennium.nextsdk.ui.fullscreen.app_open.AppOpenAdProvider

private const val ARG_UNIT = "unit_id"

/**
 * A simple [Fragment] subclass.
 * Use the [AppOpenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppOpenFragment : Fragment() {

    private var unitId: String? = null
    private lateinit var binding: FragmentAppOpenBinding
    private var loaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { unitId = it.getString(ARG_UNIT) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppOpenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val load = binding.loadAppOpen
        load.setOnClickListener {
            if (loaded) {
                Snackbar.make(
                    binding.root,
                    "You have already loaded App Open ad",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val unitId = preferences.getString("app_open", "110")
            val observer = AppOpenAdObserver(AppOpenAdProvider(requireContext(), unitId))
            ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
            loaded = true
            load.isEnabled = false
        }
    }

    companion object {
        /**
         * @param unitId Unit ID in Next SDK system
         * @return A new instance of fragment AppOpenFragment.
         */
        @JvmStatic
        fun newInstance(unitId: String) =
            AppOpenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UNIT, unitId)
                }
            }
    }
}