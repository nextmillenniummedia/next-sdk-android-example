package com.nextmillennium.androidexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextmillennium.androidexample.R
import com.nextmillennium.androidexample.databinding.FragmentBannersBinding
import com.nextmillennium.androidexample.databinding.FragmentInjectionBinding
import com.nextmillennium.androidexample.databinding.LoadPartBinding


class InjectionFragment : Fragment() {

    private var binding: FragmentInjectionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInjectionBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}