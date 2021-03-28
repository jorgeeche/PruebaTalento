package com.data.operations.contact

import com.data.operations.padel.PadelPreferencesDataSource
import com.domain.operations.AssistanceNumber
import com.domain.operations.contact.ContactRepository
import commons.android.Either

class ContactRepositoryImpl(private val padelPreferencesDataSource: PadelPreferencesDataSource) :
    ContactRepository {

    override fun getAssistanceNumber(): Either<Unit, AssistanceNumber> {
        val assistanceNumber: String? = padelPreferencesDataSource.getAssistanceNumber()

        return if (assistanceNumber.isNullOrBlank()) Either.Left(Unit) else Either.Right(
            assistanceNumber
        )
    }

}