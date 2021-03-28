package com.domain.koin

import com.domain.operations.contact.GetAssistanceNumber
import com.domain.operations.login.CallLogin
import com.domain.operations.login.Logout
import com.domain.operations.marvel.GetCharacterById
import com.domain.operations.marvel.GetCharacters
import org.koin.dsl.module.module

class DomainKoinConfiguration {

    fun getModule() = module {
        //Servicios
        factory { GetAssistanceNumber(get()) }
        factory { Logout(get()) }
        factory { GetCharacters(get()) }
        factory { GetCharacterById(get()) }
        factory { CallLogin(get()) }

    }
}