package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.databinding.ActivityInsertionBinding
import io.nextmillennium.nextsdk.NextAds
import io.nextmillennium.nextsdk.data.enums.BannerPosition
import io.nextmillennium.nextsdk.network.NextAdError
import io.nextmillennium.nextsdk.ui.BaseAdContainer
import io.nextmillennium.nextsdk.ui.NextAdListener

class InsertionActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NextAds.insertAdsTo(binding.root, "1674039792471-key")
        NextAds.setListener(this, BannerPosition.TOP, createAdListener(BannerPosition.TOP))
        NextAds.setListener(this, BannerPosition.BOTTOM, createAdListener(BannerPosition.BOTTOM))
    }

    private fun createAdListener(position: BannerPosition): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer?) {
                binding.let {
                    Snackbar.make(
                        it.root,
                        "${position.name} banner successfully loaded",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onAdClicked() {
                binding.let {
                    Snackbar.make(
                        it.root,
                        "${position.name} banner successfully clicked",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onAdLoadFail(adError: NextAdError?) {
                binding.let {
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
                binding.let {
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