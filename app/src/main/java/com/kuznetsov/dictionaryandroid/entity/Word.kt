package com.kuznetsov.dictionaryandroid.entity

data class Word(
    var id: Int, var dictId: Int, var first: String, var second: String,
    var firstExample: String, var secondExample: String, var wordType: String
)
