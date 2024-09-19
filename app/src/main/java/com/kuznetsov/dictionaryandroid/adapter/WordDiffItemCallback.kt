package com.kuznetsov.dictionaryandroid.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.dictionaryandroid.entity.Word

class WordDiffItemCallback: DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}