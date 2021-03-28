package com.domain.operations.marvel

import java.util.*

data class GetCharactersDomainResponse (
    val code: Int?,
    val status: String?,
    val copyright : String?,
    val attributionText  : String?,
    val attributionHTML   : String?,
    val etag  : String?,
    val data : CharacterDataDomainContainer?
)

data class CharacterDataDomainContainer (
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: Array<CharacterDomain>?
)

data class CharacterDomain (
    val id: Int?,
    val name: String?,
    val description : String?,
    val modified: Date?,
    val thumbnail : ImageDomain?,
    val resourceURI : String?,
    val comics  : ListDataDomain?,
    val series    : ListDataDomain?,
    val stories   : ListDataDomain?,
    val events    : ListDataDomain?,
    val urls: Array<UrlDomain>?
)

data class UrlDomain (
    val type  : String?,
    val url : String?
)

data class ImageDomain (
    val path  : String?,
    val extension  : String?
)

data class ListDataDomain (
    val available   : Int?,
    val collectionURI  : String?,
    val items  : Array<SummaryDomain>?,
    val returned  : Int?
)

data class SummaryDomain (
    val resourceURI : String?,
    val name : String?,
    val type : String?
)

