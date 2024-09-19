package com.kuznetsov.dictionaryandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.databinding.WordbookItemBinding
import com.kuznetsov.dictionaryandroid.entity.Wordbook

class WordbookAdapter(private val rootClickListener: (Wordbook) -> Unit):
        ListAdapter<Wordbook, WordbookAdapter.WordbookHolder>(WordbookDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordbookHolder {
        return WordbookHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: WordbookHolder, position: Int) {
        holder.bind(getItem(position), rootClickListener)
    }

    class WordbookHolder(val binding: WordbookItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(wordbook: Wordbook, rootClickListener: (Wordbook) -> Unit) {
            binding.wordbook = wordbook
            binding.root.setOnClickListener {
                rootClickListener(wordbook)
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): WordbookHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordbookItemBinding.inflate(layoutInflater, parent, false)
                return WordbookHolder(binding)
            }
        }
    }
}