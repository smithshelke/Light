package com.smith.light.ui.search

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smith.light.data.entities.Verse
import com.smith.light.databinding.ItemSearchVerseBinding

class SearchAdapter(
    private val onVerseSelectedListener: OnVerseSelectedListener
) :
    PagedListAdapter<Verse, SearchAdapter.VerseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VerseViewHolder.VerseDetailsViewHolder(
            ItemSearchVerseBinding.inflate(inflater, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        when (holder) {
            is VerseViewHolder.VerseDetailsViewHolder -> {
                holder.binding.apply {
                    val verse = getItem(position)
                    title.text = "${verse?.bookName}  ${verse?.chapter} : ${verse?.verseNo}"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        verseContent.text = Html.fromHtml(verse?.verse, Html.FROM_HTML_MODE_COMPACT)
                    }
                    container.setOnClickListener {
                        onVerseSelectedListener.onVerseSelected(position)
                    }
                    executePendingBindings()
                }
            }
        }
    }

    sealed class VerseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class VerseDetailsViewHolder(
            val binding: ItemSearchVerseBinding
        ) : VerseViewHolder(binding.root)
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

