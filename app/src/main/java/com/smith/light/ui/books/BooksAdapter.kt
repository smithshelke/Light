package com.smith.light.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smith.light.databinding.ItemBookBinding
import com.smith.light.navigation.TestamentSafeArgs
import com.smith.light.ui.books.BooksAdapter.BooksViewHolder.BookDetailsViewHolder

class BooksAdapter(
    private val testamentType: TestamentSafeArgs,
    private val onBookSelectedListener: OnBookSelectedListener
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
    private lateinit var oldTestamentBookList: ArrayList<String>
    private lateinit var oldTestamentAuthorsList: ArrayList<String>
    private lateinit var newTestamentBookList: ArrayList<String>
    private lateinit var newTestamentAuthorsList: ArrayList<String>

    init {
        initLists()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookDetailsViewHolder(
            ItemBookBinding.inflate(inflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return when (testamentType) {
            TestamentSafeArgs.OLD_TESTAMENT -> oldTestamentBookList.size
            TestamentSafeArgs.NEW_TESTAMENT -> newTestamentBookList.size
        }
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        when (holder) {
            is BookDetailsViewHolder -> {
                holder.binding.apply {
                    when (testamentType) {
                        TestamentSafeArgs.OLD_TESTAMENT -> {
                            title.text = oldTestamentBookList[position]
                            author.text = oldTestamentAuthorsList[position]
                        }
                        TestamentSafeArgs.NEW_TESTAMENT -> {
                            title.text = newTestamentBookList[position]
                            author.text = newTestamentAuthorsList[position]
                        }
                    }
                    container.setOnClickListener {
                        onBookSelectedListener.onBookSelected(testamentType, title.text.toString())
                    }
                    executePendingBindings()
                }
            }
        }
    }

    private fun initLists() {
        val bookList: ArrayList<String>
        val authorsList: ArrayList<String>
        when (testamentType) {
            TestamentSafeArgs.OLD_TESTAMENT -> {
                bookList = arrayListOf(
                    "Genesis",
                    "Exodus",
                    "Leviticus",
                    "Numbers",
                    "Deuteronomy",
                    "Joshua",
                    "Judges",
                    "Ruth",
                    "1 Samuel",
                    "2 Samuel",
                    "1 Kings",
                    "2 Kings",
                    "1 Chronicles",
                    "2 Chronicles",
                    "Ezra",
                    "Nehemiah",
                    "Esther",
                    "Job",
                    "Psalm",
                    "Proverbs",
                    "Ecclesiastes",
                    "Song of Solomon",
                    "Isaiah",
                    "Jeremiah",
                    "Lamentations",
                    "Ezekiel",
                    "Daniel",
                    "Hosea",
                    "Joel",
                    "Amos",
                    "Obadiah",
                    "Jonah",
                    "Micah",
                    "Nahum",
                    "Habakkuk",
                    "Zephaniah",
                    "Haggai",
                    "Zechariah",
                    "Malachi"
                )
                authorsList = arrayListOf(
                    "Moses",
                    "Moses",
                    "Moses",
                    "Moses",
                    "Moses",
                    "Joshua",
                    "Samuel, Nathan, Gad",
                    "Samuel, Nathan, Gad",
                    "Samuel, Nathan, Gad",
                    "Samuel, Nathan, Gad",
                    "Jeremiah",
                    "Jeremiah",
                    "Ezra",
                    "Ezra",
                    "Ezra",
                    "Nehemiah, Ezra",
                    "Mordecai",
                    "Job",
                    "David and several others",
                    "Solomon",
                    "Solomon",
                    "Solomon",
                    "Isaiah",
                    "Jeremiah",
                    "Jeremiah",
                    "Ezekiel",
                    "Daniel",
                    "Hosea",
                    "Joel",
                    "Amos",
                    "Obadiah",
                    "Jonah",
                    "Micah",
                    "Nahum",
                    "Habakkuk",
                    "Zephaniah",
                    "Haggai",
                    "Zechariah",
                    "Malachi"
                )
                oldTestamentBookList = bookList
                oldTestamentAuthorsList = authorsList
            }
            TestamentSafeArgs.NEW_TESTAMENT -> {
                bookList = arrayListOf(
                    "Matthew",
                    "Mark",
                    "Luke",
                    "John",
                    "Acts",
                    "Romans",
                    "1 Corinthians",
                    "2 Corinthians",
                    "Galatians",
                    "Ephesians",
                    "Philippians",
                    "Colossians",
                    "1 Thessalonians",
                    "2 Thessalonians",
                    "1 Timothy",
                    "2 Timothy",
                    "Titus",
                    "Philemon",
                    "Hebrews",
                    "James",
                    "1 Peter",
                    "2 Peter",
                    "1 John",
                    "2 John",
                    "3 John",
                    "Jude",
                    "Revelation"
                )
                authorsList = arrayListOf(
                    "Matthew",
                    "John Mark",
                    "Luke",
                    "John, the Apostle",
                    "Luke",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul",
                    "Paul, Luke, Barnabas, Apollos",
                    "James",
                    "Peter",
                    "Peter",
                    "John, the Apostle",
                    "John, the Apostle",
                    "John, the Apostle",
                    "Jude",
                    "John, the Apostle"
                )
                newTestamentBookList = bookList
                newTestamentAuthorsList = authorsList
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    sealed class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class BookDetailsViewHolder(
            val binding: ItemBookBinding
        ) : BooksViewHolder(binding.root)
    }

    interface OnBookSelectedListener {
        fun onBookSelected(testamentType: TestamentSafeArgs, name: String)
    }
}

