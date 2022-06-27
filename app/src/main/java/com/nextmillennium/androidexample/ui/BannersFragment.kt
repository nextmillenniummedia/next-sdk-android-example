package com.nextmillennium.androidexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextmillennium.androidexample.R
import com.nextmillennium.androidexample.databinding.FragmentBannersBinding

class BannersFragment : Fragment() {

    private var binding: FragmentBannersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannersBinding.inflate(inflater)
        return binding?.root
    }

}