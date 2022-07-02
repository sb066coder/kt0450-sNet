data class Chat(
    val id: Int,
    var ownersId: Pair<Int, Int>,
    val messages: MutableList<Message> = mutableListOf(),
    var lastMessageId: Int = -1
)

data class Message(
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    var text: String,
    var isRead: Boolean = false,
    var isEdited: Boolean = false,
    var date: Long
)