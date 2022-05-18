import kotlinx.serialization.Serializable

@Serializable
data class BibTex(
    val type: String,
    val ID: String,
    val author: String? = null,
    val journal: String? = null,
    val pages: String? = null,
    val publisher: String? = null,
    val title: String? = null,
    val year: String? = null
)