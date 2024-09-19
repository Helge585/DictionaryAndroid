package com.kuznetsov.dictionaryandroid.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

class WordbookGroupDiffItemCallback: DiffUtil.ItemCallback<WordbookGroup>() {

    override fun areItemsTheSame(oldItem: WordbookGroup, newItem: WordbookGroup): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordbookGroup, newItem: WordbookGroup): Boolean {
        return oldItem == newItem
    }
}