package com.domain.operations.contact

import com.domain.operations.AssistanceNumber
import commons.android.Either

interface ContactRepository {

    fun getAssistanceNumber(): Either<Unit, AssistanceNumber>

}