package com.smith.light.ui.verses

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smith.light.R
import com.smith.light.data.entities.Verse
import com.smith.light.databinding.ItemVerseBinding

class VerseAdapter(
    private val onVerseSelectedListener: OnVerseSelectedListener
) :
    PagedListAdapter<Verse, VerseAdapter.VerseViewHolder>(DIFF_CALLBACK) {

    private var highlightPosition: Int = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VerseViewHolder.VerseDetailsViewHolder(
            ItemVerseBinding.inflate(inflater, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        when (holder) {
            is VerseViewHolder.VerseDetailsViewHolder -> {
                holder.binding.apply {
                    if (highlightPosition >= 0 && position == highlightPosition) {
                        container.setBackgroundColor(
                            ContextCompat.getColor(
                                container.context,
                                R.color.yellowOrange
                            )
                        )
                    } else {
                        val outVal = TypedValue()
                        container.context.theme.resolveAttribute(
                            android.R.attr.selectableItemBackground,
                            outVal,
                            true
                        )
                        container.setBackgroundResource(outVal.resourceId)
                    }
                    val verse = getItem(position)
                    verseInfo.text = "${verse?.chapter} : ${verse?.verseNo}"
                    if (verse?.verseNo == 1) {
                        chapter.visibility = View.VISIBLE
                        chapter.text = "Chapter ${verse.chapter}"
                    } else {
                        chapter.visibility = View.GONE
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        verseContent.text =
                            Html.fromHtml(verse?.verse ?: "", Html.FROM_HTML_MODE_COMPACT)
                    }
                    container.setOnClickListener {
                        showMenu(it)
                        onVerseSelectedListener.onVerseSelected(position)
                    }
                    executePendingBindings()
                }
            }
        }
    }

    private fun showMenu(anchor: View) {
       /* val popup = PopupMenu(anchor.context, anchor)
        popup.gravity = Gravity.END
        popup.menuInflater.inflate(R.menu.verse_popup_menu, popup.menu)
        popup.show()*/
        val popup = VersePopupWindow(anchor.context)
        popup.show(anchor)
    }

    fun setHighlight(position: Int) {
        highlightPosition = position
    }

    sealed class VerseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class VerseDetailsViewHolder(
            val binding: ItemVerseBinding
        ) : VerseViewHolder(binding.root) {

        }
    }

    interface OnVerseSelectedListener {
        fun onVerseSelected(position: Int)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Verse>() {

            override fun areItemsTheSame(oldItem: Verse, newItem: Verse) =
                oldItem.chapter == newItem.chapter && oldItem.verseNo == newItem.verseNo

            override fun areContentsTheSame(oldItem: Verse, newItem: Verse) =
                oldItem.verse == newItem.verse
        }
    }
}

