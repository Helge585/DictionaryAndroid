package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.WordbookOpenListener
import com.kuznetsov.dictionaryandroid.WordsOpenListener
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

private const val WORDBOOK_GROUP_ID_ARG = "WORDBOOK_GROUP_ID_ARG"

class WordbooksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wordbooks, container, false)
        val groupId = arguments?.getInt(WORDBOOK_GROUP_ID_ARG) ?: -1

        val recyclerView = view.findViewById<RecyclerView>(R.id.wordbooks_list)
        recyclerView.adapter = WordbookAdapter(Repository.wordbooks.value ?: emptyList())
        {
            (activity as WordsOpenListener).onWordsFragmentOpen(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)

        val button = view.findViewById<Button>(R.id.download_wordbooks_button)
        button.setOnClickListener {
            Repository.downloadWordbooks(groupId)
        }
        Repository.wordbooks.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as WordbookAdapter).wordbooks = it
            (recyclerView.adapter as WordbookAdapter).notifyDataSetChanged()
        })
        return view
    }

    private inner class WordbookHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(wordbook: Wordbook, clickListener: (Wordbook) -> Unit) {
            val textView = view.findViewById<TextView>(R.id.wordbookTextView)
            textView.text = wordbook.toString()
            view.setOnClickListener {
                clickListener(wordbook)
            }
        }

    }

    private inner class WordbookAdapter(var wordbooks: List<Wordbook>,
                                        val clickListener: (Wordbook) -> Unit)
        : RecyclerView.Adapter<WordbookHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordbookHolder {
            val view = layoutInflater.inflate(R.layout.wordbook_item, parent, false)
            return WordbookHolder(view)
        }

        override fun getItemCount(): Int {
            return wordbooks.size
        }

        override fun onBindViewHolder(holder: WordbookHolder, position: Int) {
            holder.bind(wordbooks[position], clickListener)
        }

    }

    companion object {
        fun newInstance(wordbookGroup: WordbookGroup): WordbooksFragment {
            val args = Bundle()
            args.putInt(WORDBOOK_GROUP_ID_ARG, wordbookGroup.id)
            val fragment = WordbooksFragment()
            fragment.arguments = args
            return fragment
        }
    }
}