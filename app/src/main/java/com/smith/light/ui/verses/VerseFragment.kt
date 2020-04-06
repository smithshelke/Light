package com.smith.light.ui.verses

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.smith.light.R
import com.smith.light.databinding.FragmentVerseBinding

class VerseFragment : Fragment() {
    private lateinit var verseViewModel: VerseViewModel
    private val args: VerseFragmentArgs by navArgs()
    private lateinit var binding: FragmentVerseBinding
    private lateinit var adapter: VerseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        verseViewModel = ViewModelProvider(this).get(VerseViewModel::class.java)
        binding = FragmentVerseBinding.inflate(inflater, container, false)
        binding.bookTitle.text = args.bookName
        setupAdapter()
        setupInsets()
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        val animatorId = if (enter) R.anim.fade_in else R.anim.fade_out
        val anim = AnimatorInflater.loadAnimator(activity, animatorId)
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                getVerses(adapter)
                getNumberOfChapters()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        return anim
    }

    private fun setupAdapter() {
        adapter = VerseAdapter(object : VerseAdapter.OnVerseSelectedListener {
            override fun onVerseSelected(position: Int) {
            }
        })
        binding.verseList.adapter = adapter
    }

    private fun getVerses(adapter: VerseAdapter) {
        binding.progressBar.visibility = View.VISIBLE
        verseViewModel.getVerseByBook(args.bookName)
            .observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
                binding.verseList.scheduleLayoutAnimation()
                binding.progressBar.visibility = View.GONE
                val startId = it[0]!!.id
                if (args.verseId >= 0) {
                    val position = args.verseId - startId
                    binding.verseList.scrollToPosition(position)
                    adapter.setHighlight(position)
                }
            })
    }

    private fun getNumberOfChapters() {
        verseViewModel.getNumberOfChapters(args.bookName)
            .observe(viewLifecycleOwner, Observer {

            })
    }

    private fun setupInsets() {
        val appBarHeight = binding.titleBar.layoutParams.height
        binding.titleBar.setOnApplyWindowInsetsListener { _, insets ->
            val lp = (binding.titleBar.layoutParams as ConstraintLayout.LayoutParams)
            lp.height = appBarHeight + insets.systemWindowInsetTop
            binding.titleBar.layoutParams = lp
            insets
        }
    }
}
