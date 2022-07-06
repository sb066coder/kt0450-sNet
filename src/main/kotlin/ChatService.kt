import java.util.Calendar

object ChatService {

    private val chats = mutableMapOf<Pair<User, User>, Chat>()

    fun testReset() {
        chats.clear()
    }

    fun testGetChats() = chats

    fun createChat(user0: User, user1: User): Chat {
        // Создать чат (чат создаётся тогда, когда пользователю,
        // с которым до этого не было чата, отправляется первое сообщение).
        return Chat().let { chats[keyOf(user0, user1)] = it; it }
    }

    fun deleteChat(user0: User, user1: User): Int {
        // Удалить чат (целиком удаляется все переписка).
        chats.remove(keyOf(user0, user1), findChat(user0, user1))
        return 0
    }

    fun getChats(user: User): List<Chat> {
        // Получить все чаты пользователя
        return chats.filter { it.key.first == user || it.key.second == user }.values.toList()
    }

    fun createMessage(user0: User, user1: User, text: String): Message {
        // Новое сообщение другому пользователю
        val chat = try {
            findChat(user0, user1)
        } catch (e: ChatNotFoundException) {
            createChat(user0, user1)
        }
        return Message(
            user0.id,
            text,
            date = Calendar.getInstance().time.time
        ).let {
            chat.lastMessageId = chat.lastMessageId + 1
            chat.messages[chat.lastMessageId] = it
            it }
    }

    fun editMessage(user0: User, user1: User, messageId: Int, text: String): Message {
        // Редактировать сообщение
        return findChat(user0, user1)
            .messages[messageId]
            .let {  it?.text = text; it } ?:
                throw MessageNotFoundException("Message ID $messageId not found")
    }

    fun keyOf(user0: User, user1: User) = Pair(minOf(user0, user1), maxOf(user0, user1))

    fun deleteMessage(user0: User, user1: User, messageId: Int): Int {
        // Удалить сообщение (при удалении последнего сообщения в чате весь чат удаляется).
        // Проработать удаление чата по отдельности для каждого пользователя
        chats.let {
            if (findChat(user0, user1).messages.let { m -> m.remove(messageId) ?:
                throw MessageNotFoundException("Message ID $messageId not found"); m }.isEmpty()) {
                it.remove(keyOf(user0, user1))
            }
        }
        return 0
    }

    fun getUnreadChatsCount(user: User): Int {
        // Получить количество чатов, в которых есть непрочитанные сообщения
        return getChats(user).count { chat -> chat.messages.values.any { it.senderId != user.id && !it.isRead } }
    }

    fun getUnreadChats(user: User): List<Chat> {
        // Получить чаты, в которых есть непрочитанные сообщения
        return getChats(user).filter { chat -> chat.messages.values.any { it.senderId != user.id && !it.isRead } }
    }

    fun getMessages(user0: User, user1: User, startMessageId: Int, messageCount: Int): List<Message> {
        // Получить список сообщений из чата (после того, как вызвана данная функция,
        // все отданные сообщения автоматически считаются прочитанными)
        return findChat(user0, user1)
            .messages
            .filter { it.key >= startMessageId }
            .values
            .toList()
            .takeLast(messageCount)
            .let { it.forEach { m -> if (m.senderId != user0.id) m.isRead = true }; it }
    }

    fun findChat(user0: User, user1: User): Chat {
        return chats[keyOf(user0, user1)] ?:
            throw ChatNotFoundException("Chat for ${user0.name} & ${user1.name} not found")
    }
}