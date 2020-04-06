package com.smith.light.ui.testaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.smith.light.databinding.FragmentTestamentTypeBinding
import com.smith.light.navigation.TestamentSafeArgs
import com.smith.light.utils.RoundedViewOutlineProvider

class TestamentTypeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTestamentTypeBinding.inflate(inflater, container, false)
        binding.testaments.oldTestamentLayout.apply {
            outlineProvider = RoundedViewOutlineProvider()
            clipToOutline = true
            setOnClickListener {
                val action =
                    TestamentTypeFragmentDirections.actionTestamentTypeFragmentToBooksFragment(
                        TestamentSafeArgs.OLD_TESTAMENT
                    )
                findNavController().navigate(action)
            }
        }
        binding.testaments.newTestamentLayout.apply {
            outlineProvider = RoundedViewOutlineProvider()
            clipToOutline = true
            setOnClickListener {
                val action =
                    TestamentTypeFragmentDirections.actionTestamentTypeFragmentToBooksFragment(
                        TestamentSafeArgs.NEW_TESTAMENT
                    )
                findNavController().navigate(action)
            }
        }
        setupInsets(binding)
        return binding.root
    }

    private fun setupInsets(binding: FragmentTestamentTypeBinding) {
        binding.container.setOnApplyWindowInsetsListener { _, insets ->
            binding.container.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
    }
}
