data class Chat(
    val messages: MutableMap<Int, Message> = mutableMapOf(),
    var lastMessageId: Int = -1
)

data class Message(
    val senderId: Int,
    var text: String,
    var isRead: Boolean = false,
    var isEdited: Boolean = false,
    var date: Long
)