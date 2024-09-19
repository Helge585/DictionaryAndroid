package com.kuznetsov.dictionaryandroid.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.dictionaryandroid.entity.Wordbook

class WordbookDiffItemCallback: DiffUtil.ItemCallback<Wordbook>() {
    override fun areItemsTheSame(oldItem: Wordbook, newItem: Wordbook): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wordbook, newItem: Wordbook): Boolean {
        return oldItem == newItem
    }
}