package com.smith.light.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import com.smith.light.R
import com.smith.light.databinding.FragmentBooksBinding
import com.smith.light.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupInsets(binding)
        return binding.root
    }

    private fun setupInsets(binding: FragmentHomeBinding) {
        binding.container.setOnApplyWindowInsetsListener{_,insets->
            binding.container.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
    }
}
