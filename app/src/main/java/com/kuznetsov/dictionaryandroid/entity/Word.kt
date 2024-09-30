package com.kuznetsov.dictionaryandroid.entity

import com.kuznetsov.dictionaryandroid.utils.AnswerStatus


data class Word(
    var id: Int, var dictId: Int, var russianWord: String, var foreignWord: String,
    var russianExample: String, var foreignExample: String, var wordType: String
) {

    var answerStatus = AnswerStatus.UNANSWERED
    override fun toString(): String {
        return "Word(id=$id, dictId=$dictId, russianWord='$russianWord', " +
                "foreignWord='$foreignWord', russianExample='$russianExample', " +
                "foreignExample='$foreignExample', wordType='$wordType', " +
                "answerStatus=$answerStatus)"
    }


}
