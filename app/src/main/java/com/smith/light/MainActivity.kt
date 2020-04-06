package com.smith.light

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smith.light.databinding.ActivityMainBinding
import com.smith.light.ui.home.HomeFragmentDirections
import com.smith.light.ui.search.SearchAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawBehindSystemBars()
        setupNavigation()
        setupSheet()
        binding.apply {
            setInsetsToBottomBar(binding.bottomBar)
            setInsetsToSheet()
            setInsetsToFab(binding.fab)
            fab.setOnClickListener {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment -> {
                        val action = HomeFragmentDirections.chooseTestament()
                        navController.navigate(action)
                    }
                    else -> {
                        val behavior = BottomSheetBehavior.from(sheet.searchSheet)
                        behavior.state = STATE_EXPANDED
                    }
                }
            }
        }
    }

    private fun setupSheet() {
        binding.apply {
            setupSearchList()
            behavior = BottomSheetBehavior.from(sheet.searchSheet)
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
            sheet.searchEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val searchTerm = s.toString()
                    if (searchTerm.length > 2) {
                        sheet.progressBar.visibility = View.VISIBLE
                        mainViewModel.searchVerse(searchTerm).observe(this@MainActivity, Observer {
                            searchAdapter.submitList(it)
                            sheet.progressBar.visibility = View.GONE
                        })
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

    private fun setupSearchList() {
        binding.sheet.apply {
            searchAdapter = SearchAdapter(object : SearchAdapter.OnVerseSelectedListener {
                override fun onVerseSelected(position: Int) {
                    val id = searchAdapter.currentList?.get(position)?.id
                    val bookName = searchAdapter.currentList?.get(position)?.bookName
                    behavior.state = STATE_COLLAPSED
                    navController.navigate(R.id.verseFragment, bundleOf("verseId" to id,"bookName" to bookName))
                }
            })
            searchList.adapter = searchAdapter
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomBar.performShow()
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.fab.setImageResource(R.drawable.ic_book)
                }
                else -> {
                    binding.fab.setImageResource(R.drawable.ic_search)
                }
            }
        }
    }

    private fun setInsetsToFab(fab: FloatingActionButton) {
        val lp: CoordinatorLayout.LayoutParams =
            fab.layoutParams as CoordinatorLayout.LayoutParams
        val fabMargin = lp.bottomMargin
        fab.setOnApplyWindowInsetsListener { v, insets ->
            val bottomInsets = insets.systemWindowInsetBottom
            lp.bottomMargin = fabMargin + bottomInsets
            v.layoutParams = lp
            insets
        }
    }

    private fun drawBehindSystemBars() {
        binding.apply {
            root.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    private fun setInsetsToBottomBar(bottomBar: BottomAppBar) {
        val bottomBarHeight = bottomBar.layoutParams.height
        bottomBar.setOnApplyWindowInsetsListener { v, insets ->
            val lp = bottomBar.layoutParams
            val bottomInsets = insets.systemWindowInsetBottom
            lp.height = bottomBarHeight + bottomInsets
            v.layoutParams = lp
            insets
        }
    }

    private fun setInsetsToSheet() {
        binding.sheet.searchSheet.setOnApplyWindowInsetsListener { v, insets ->
            v.updatePadding(
                top = insets.systemWindowInsetTop + resources.getDimension(R.dimen.extaSpace)
                    .toInt()
            )
            insets
        }
    }

    override fun onBackPressed() {
        if (behavior.state == STATE_EXPANDED)
            behavior.state = STATE_COLLAPSED
        else
            super.onBackPressed()
    }
}
