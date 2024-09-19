package com.kuznetsov.dictionaryandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.databinding.WordbookGroupItemBinding
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

class WordbookGroupAdapter(
                private val clickListener: (WordbookGroup) -> Unit
            ):
            ListAdapter<WordbookGroup,
                    WordbookGroupAdapter.WordbookGroupHolder>(WordbookGroupDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordbookGroupHolder {
        return WordbookGroupHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: WordbookGroupHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class WordbookGroupHolder(private val binding: WordbookGroupItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(wordbookGroup: WordbookGroup, clickListener: (WordbookGroup) -> Unit) {
            binding.wordbookGroup = wordbookGroup
            binding.root.setOnClickListener {
                clickListener(wordbookGroup)
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): WordbookGroupHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordbookGroupItemBinding.inflate(
                    layoutInflater,
                    parent, false
                )
                return WordbookGroupHolder(binding)
            }
        }
    }
}