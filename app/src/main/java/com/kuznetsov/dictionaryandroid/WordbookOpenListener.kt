package com.kuznetsov.dictionaryandroid

import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

interface WordbookOpenListener {

    fun onWordbookFragmentOpen(wordbookGroup: WordbookGroup)
}