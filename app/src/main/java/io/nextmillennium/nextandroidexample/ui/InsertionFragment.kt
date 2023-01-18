package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.FragmentInsertionBinding
import io.nextmillennium.nextsdk.NextAds
import io.nextmillennium.nextsdk.data.enums.BannerPosition
import io.nextmillennium.nextsdk.network.NextAdError
import io.nextmillennium.nextsdk.ui.BaseAdContainer
import io.nextmillennium.nextsdk.ui.NextAdListener

class InsertionFragment : Fragment() {

    private var binding: FragmentInsertionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsertionBinding.inflate(inflater)
        NextAds.insertAdsTo(binding?.root, "1674039781418-key")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NextAds.setListener(
            requireActivity(),
            BannerPosition.TOP,
            createAdListener(BannerPosition.TOP)
        )
        NextAds.setListener(
            requireActivity(),
            BannerPosition.BOTTOM,
            createAdListener(BannerPosition.BOTTOM)
        )
        val reloadButton = binding?.reload
        val positionGroup = binding?.positionGroup

        reloadButton?.setOnClickListener {
            val position =
                if (positionGroup?.checkedRadioButtonId == R.id.radioTop) BannerPosition.TOP else BannerPosition.BOTTOM
            NextAds.reload(requireActivity(), position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun createAdListener(position: BannerPosition): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer?) {
                if (activity != null) {
                    binding?.let {
                        Snackbar.make(
                            it.root,
                            "${position.name} banner successfully loaded",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onAdClicked() {
                binding?.let {
                    Snackbar.make(
                        it.root,
                        "${position.name} banner successfully clicked",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onAdLoadFail(adError: NextAdError?) {
                binding?.let {
                    Snackbar.make(
                        it.root,
                        "${adError?.code} : ${adError?.message}",
                        Snackbar.LENGTH_SHORT
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                it.root.context,
                                R.color.deep_orange_A700
                            )
                        )
                        .show()
                }
            }

            override fun onAdImpression() {
                binding?.let {
                    Snackbar.make(
                        it.root,
                        "${position.name} banner impression",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}