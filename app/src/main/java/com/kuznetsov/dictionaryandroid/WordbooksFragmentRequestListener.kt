package com.kuznetsov.dictionaryandroid

import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

interface WordbooksFragmentRequestListener {
    fun onWordbooksFragmentRequest(wordbookGroup: WordbookGroup)
}