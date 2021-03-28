package pruebatalento.operations.characterdetail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CharacterApp (
    val id: Int?,
    val name: String?,
    val description : String?,
    val modified: Date?,
    val thumbnail : ImageApp?,
    val resourceURI : String?,
    val comics : ListDataApp?,
    val series : ListDataApp?,
    val stories : ListDataApp?,
    val events : ListDataApp?,
    val urls: Array<UrlApp>?
): Parcelable

@Parcelize
data class UrlApp (
    val type : String?,
    val url: String?
): Parcelable

@Parcelize
data class ImageApp (
    val path : String?,
    val extension : String?
): Parcelable

@Parcelize
data class ListDataApp (
    val available : Int?,
    val collectionURI : String?,
    val items : Array<SummaryApp>?,
    val returned : Int?
): Parcelable

@Parcelize
data class SummaryApp (
    val resourceURI : String?,
    val name : String?,
    val type : String?
): Parcelable