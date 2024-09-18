package com.kuznetsov.dictionaryandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.WordbookOpenListener
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

private const val TAG = "WordbookGroupFragment"

class WordbookGroupFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_wordbook_group,
            container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.wordbook_group_list)
        recyclerView.adapter = WordbookGroupAdapter(
            Repository.wordbookGroups.value ?: emptyList()) {
            //Repository.downloadWordbooks(it)
            (activity as WordbookOpenListener).onWordbookFragmentOpen(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        Repository.wordbookGroups.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString())
            (recyclerView.adapter as WordbookGroupAdapter).groups = it
            (recyclerView.adapter as WordbookGroupAdapter).notifyDataSetChanged()
        })

        val downloadButton = view.findViewById<Button>(R.id.download_groups_button)
        downloadButton.setOnClickListener {
            Repository.downloadWordbookGroups()
        }

        return view
    }

    private inner class WordbookGroupHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(wordbookGroup: WordbookGroup, clickListener: (WordbookGroup) -> Unit) {
            Log.i(TAG, "Holder is binding")
            view.findViewById<TextView>(R.id.wordbookGroupTextView).text = wordbookGroup.toString()
            view.setOnClickListener {
                clickListener(wordbookGroup)
            }
        }
    }

    private inner class WordbookGroupAdapter(var groups: List<WordbookGroup>,
                                             val clickListener: (WordbookGroup) -> Unit)
        : RecyclerView.Adapter<WordbookGroupHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordbookGroupHolder {
            val view = layoutInflater.inflate(R.layout.wordbook_group_item,
                parent, false)
            return WordbookGroupHolder(view)
        }

        override fun getItemCount(): Int {
            return groups.size
        }

        override fun onBindViewHolder(holder: WordbookGroupHolder, position: Int) {
            holder.bind(groups[position], clickListener)
        }

    }
}