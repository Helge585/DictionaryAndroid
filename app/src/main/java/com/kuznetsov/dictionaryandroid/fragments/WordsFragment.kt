package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Word

private const val ARG_WORDBOOK_ID = "ARG_WORDBOOK_ID"


class WordsFragment : Fragment() {

    private var wordbookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordbookId = it.getInt(ARG_WORDBOOK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_words, container, false)

        val downloadWordsButton = view.findViewById<Button>(R.id.download_words_button)
        downloadWordsButton.setOnClickListener {
            wordbookId?.let {
                Repository.downloadWords(it)
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.words_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = WordAdapter(Repository.words.value ?: emptyList())

        Repository.words.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as WordAdapter).words = it
            (recyclerView.adapter as WordAdapter).notifyDataSetChanged()
        })

        return view
    }

    private inner class WordHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(word: Word) {
            view.findViewById<TextView>(R.id.wordTextView).text = word.toString()
        }

    }

    private inner class WordAdapter(var words: List<Word>): RecyclerView.Adapter<WordHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
            val view = layoutInflater.inflate(R.layout.word_item, parent, false)
            return WordHolder(view)
        }

        override fun getItemCount(): Int {
            return words.size
        }

        override fun onBindViewHolder(holder: WordHolder, position: Int) {
            holder.bind(words[position])
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(wordbbookId: Int) =
            WordsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WORDBOOK_ID, wordbbookId)
                }
            }
    }
}