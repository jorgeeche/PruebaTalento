package com.data.operations.marvel

import com.domain.operations.marvel.*

fun GetCharactersResponse.toDomain() =
    GetCharactersDomainResponse(
        code = code,
        status = status,
        copyright = copyright,
        attributionText = attributionText,
        attributionHTML = attributionHTML,
        data = data?.toDomain(),
        etag = etag
    )

private fun CharacterDataContainer.toDomain() =
    CharacterDataDomainContainer(
        offset = offset,
        limit = limit,
        total = total,
        count = count,
        results = results?.map { it.toDomain() }?.toTypedArray()
    )

private fun Character.toDomain() =
    CharacterDomain(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI,
        urls = urls?.map { it.toDomain() }?.toTypedArray(),
        thumbnail = thumbnail?.toDomain(),
        comics = comics?.toDomain(),
        stories = stories?.toDomain(),
        events = events?.toDomain(),
        series = series?.toDomain()
    )

private fun Url.toDomain() =
    UrlDomain(
        type = type,
        url = url
    )

private fun Image.toDomain() =
    ImageDomain(
        path = path,
        extension = extension
    )

private fun ListData.toDomain() =
    ListDataDomain(
        available = available,
        returned = returned,
        collectionURI = collectionURI,
        items = items?.map { it.toDomain() }?.toTypedArray()
    )

private fun Summary.toDomain() =
    SummaryDomain(
        resourceURI = resourceURI,
        name = name,
        type = type
    )

