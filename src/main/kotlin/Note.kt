data class Note(
    val id: Int = -1,
    val title: String = "",
    val text: String = "",
    val date: Long = 0,
    val comments: Int = 0,
    val readComments: Boolean = false,
    var deleted: Boolean = false
)

data class NoteComment(
    val cId: Int = -1,
    val noteId: Int,
    val message: String = "",
    val date: Long = 0,
    var deleted: Boolean = false
)