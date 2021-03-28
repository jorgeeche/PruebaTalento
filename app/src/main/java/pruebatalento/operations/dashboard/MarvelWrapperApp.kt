package pruebatalento.operations.dashboard

import com.domain.operations.marvel.*
import pruebatalento.operations.characterdetail.*


 fun CharacterDomain.toApp() =
    CharacterApp(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI,
        urls = urls?.map { it.toApp() }?.toTypedArray(),
        thumbnail = thumbnail?.toApp(),
        comics = comics?.toApp(),
        stories = stories?.toApp(),
        events = events?.toApp(),
        series = series?.toApp()
    )

 fun UrlDomain.toApp() =
    UrlApp(
        type = type,
        url = url
    )

 fun ImageDomain.toApp() =
    ImageApp(
        path = path,
        extension = extension
    )

 fun ListDataDomain.toApp() =
    ListDataApp(
        available = available,
        returned = returned,
        collectionURI = collectionURI,
        items = items?.map { it.toApp() }?.toTypedArray()
    )

 fun SummaryDomain.toApp() =
    SummaryApp(
        resourceURI = resourceURI,
        name = name,
        type = type
    )