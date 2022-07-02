import java.util.Calendar

object ChatService {

    private val chats = mutableListOf<Chat>()
    private var lastChatId: Int = -1

    fun testReset() {
        chats.clear()
        lastChatId = -1
    }

    fun testGetChats() = chats

    fun createChat(user0: User, user1: User): Chat {
        // Создать чат (чат создаётся тогда, когда пользователю,
        // с которым до этого не было чата, отправляется первое сообщение).
        val chat = Chat(id = ++lastChatId, ownersId = Pair(user0.id, user1.id))
        chats.add(chat)
        return chat
    }

    fun deleteChat(user0: User, user1: User): Int {
        // Удалить чат (целиком удаляется все переписка).
        val chat = findChat(user0, user1)
        chats.remove(chat)
        return 0
    }

    private fun getChats(user: User): List<Chat> {
        // Получить все чаты пользователя
        return chats.filter { it.ownersId.first == user.id || it.ownersId.second == user.id }
    }

    fun createMessage(user0: User, user1: User, text: String): Message {
        // Новое сообщение другому пользователю
        val chat = try {
            findChat(user0, user1)
        } catch (e: ChatNotFoundException) {
            createChat(user0, user1)
        }
        val message = Message(
            chat.lastMessageId + 1,
            chat.id,
            user0.id,
            text,
            date = Calendar.getInstance().time.time
        )
        chat.messages.add(message)
        chat.lastMessageId += 1
        return message
    }

    fun editMessage(user0: User, user1: User, messageId: Int, text: String): Message {
        // Редактировать сообщение
        val message = findChat(user0, user1).messages.firstOrNull() { it.id == messageId } ?:
            throw MessageNotFoundException("Message ID $messageId not found")
        message.text = text
        message.isEdited = true
        return message
    }

    fun deleteMessage(user0: User, user1: User, messageId: Int): Int {
        // Удалить сообщение (при удалении последнего сообщения в чате весь чат удаляется).
        // Проработать удаление чата по отдельности для каждого пользователя
        val chat = findChat(user0, user1)
        val message = chat.messages.firstOrNull { it.id == messageId } ?:
            throw MessageNotFoundException("Message ID $messageId not found")
        chat.messages.remove(message)
        if (chat.messages.size == 0)
            deleteChat(user0, user1)
        return 0
    }

    fun getUnreadChatsCount(user: User): Int {
        // Получить количество чатов, в которых есть непрочитанные сообщения
        return getChats(user).count { chat -> chat.messages.any { it.senderId != user.id && !it.isRead } }
    }

    fun getUnreadChats(user: User): List<Chat> {
        // Получить чаты, в которых есть непрочитанные сообщения
        return getChats(user).filter { chat -> chat.messages.any { it.senderId != user.id && !it.isRead } }
    }

    fun getMessages(user0: User, user1: User, startMessageId: Int, messageCount: Int): List<Message> {
        // Получить список сообщений из чата (после того, как вызвана данная функция,
        // все отданные сообщения автоматически считаются прочитанными)
        val filtered = findChat(user0, user1).messages.filter { it.id >= startMessageId }.takeLast(messageCount)
        filtered.forEach{ if (it.senderId != user0.id) it.isRead = true }
        return filtered
    }

    private fun findChat(user0: User, user1: User): Chat {
        return getChats(user0).firstOrNull{ it.ownersId.first == user1.id || it.ownersId.second == user1.id } ?:
            throw ChatNotFoundException("Chat for ${user0.name} & ${user1.name} not found")
    }
}