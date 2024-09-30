package com.kuznetsov.dictionaryandroid.utils

enum class TestMode {
    WRITE_RUSSIAN, WRITE_FOREIGN;

    companion object {
        fun createFromString(stringValue: String): TestMode? {
            return when (stringValue) {
                "Write Russian" -> WRITE_RUSSIAN
                "Write foreign" -> WRITE_FOREIGN
                else -> null;
            }
        }
    }
}

enum class AnswerStatus {
    RIGHT, WRONG, UNANSWERED
}