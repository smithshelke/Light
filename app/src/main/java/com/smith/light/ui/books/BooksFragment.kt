package com.smith.light.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.smith.light.databinding.FragmentBooksBinding
import com.smith.light.navigation.TestamentSafeArgs
import com.smith.light.ui.books.BooksAdapter.OnBookSelectedListener

class BooksFragment : Fragment() {
    private val args: BooksFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBooksBinding.inflate(inflater, container, false)
        binding.apply {
            bookList.adapter = BooksAdapter(args.testamentType, object : OnBookSelectedListener {
                override fun onBookSelected(testamentType: TestamentSafeArgs, name: String) {
                    val action = BooksFragmentDirections.actionBooksFragmentToVerseFragment(-1,name,testamentType)
                    findNavController().navigate(action)
                }
            })
            title.text = args.testamentType.type
        }

        setupInsets(binding)
        return binding.root
    }

    private fun setupInsets(binding: FragmentBooksBinding) {
        val appBarHeight = binding.titleBar.layoutParams.height
        binding.titleBar.setOnApplyWindowInsetsListener { _, insets ->
            val lp = (binding.titleBar.layoutParams as ConstraintLayout.LayoutParams)
            lp.height = appBarHeight + insets.systemWindowInsetTop
            binding.titleBar.layoutParams = lp
            insets
        }
    }
}
