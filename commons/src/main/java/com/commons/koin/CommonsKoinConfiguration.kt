package com.commons.koin

import com.commons.json.JsonParser
import com.commons.json.JsonParserImpl
import org.koin.dsl.module.module

class CommonsKoinConfiguration {

    fun getModule() = module {
        single<JsonParser> { JsonParserImpl() }

/*
        single { FieldValidator() }
        single<JsonParser> { MoshiJsonParser() }
        single { StringFormatter() }
        factory { ServiceManager() }
        factory { DocumentValidator() }
*/
    }
}