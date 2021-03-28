package com.domain.operations.contact

import com.commons.UseCase
import com.domain.operations.AssistanceNumber
import commons.android.Either

class GetAssistanceNumber(private val repository: ContactRepository) : UseCase<Unit, AssistanceNumber, Unit>() {

    override suspend fun run(params: Unit): Either<Unit, AssistanceNumber> = repository.getAssistanceNumber()

}