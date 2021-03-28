package com.data.operations.marvel

import java.util.*

data class GetCharactersResponse (
    val code: Int?,
    val status: String?,
    val copyright : String?,
    val attributionText : String?,
    val attributionHTML : String?,
    val etag : String?,
    val data : CharacterDataContainer?
)

data class CharacterDataContainer (
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: Array<Character>?
)

data class Character (
    val id: Int?,
    val name: String?,
    val description : String?,
    val modified: Date?,
    val thumbnail : Image?,
    val resourceURI : String?,
    val comics : ListData?,
    val series : ListData?,
    val stories : ListData?,
    val events : ListData?,
    val urls: Array<Url>?
)

data class Url (
    val type : String?,
    val url: String?
)

data class Image (
    val path : String?,
    val extension : String?
)

data class ListData (
    val available : Int?,
    val collectionURI : String?,
    val items : Array<Summary>?,
    val returned : Int?
)

data class Summary (
    val resourceURI : String?,
    val name : String?,
    val type : String?
)



