package com.kuznetsov.dictionaryandroid.entity

data class Wordbook(
    var id: Int,
    var name: String,
    var groupId: Int,
    var result: Int,
    var lastDate: String
) {
    fun resultToString() = "Last result: $result"

    fun lastDateToString() = "Last date: $lastDate"
}
