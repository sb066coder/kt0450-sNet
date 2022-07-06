import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    private val sasha = User(550, "Alexander", 25)
    private val dasha = User(855, "Darya", 22)
    private val masha = User(1445, "Мария Петровна", 55)

    @Before
    fun setUp() {
        ChatService.createMessage(sasha, dasha, "Привет, Даша")
        ChatService.createMessage(dasha, sasha, "И тебе привет")
        ChatService.createMessage(sasha, dasha, "Несколько ")
        ChatService.createMessage(dasha, sasha, "новых ")
        ChatService.createMessage(sasha, dasha, "сообщений ")
        ChatService.createMessage(dasha, sasha, "в чате, ")
        ChatService.createMessage(sasha, dasha, "типа ")
        ChatService.createMessage(dasha, sasha, "диалог")
        ChatService.createMessage(dasha, masha, "Здравствуйте")
        ChatService.createMessage(dasha, masha, "Как у вас дела?")
    }

    @After
    fun tearDown() {
        ChatService.testReset()
    }


    @Test
    fun testDeleteChat() {
        assertEquals(0, ChatService.deleteChat(sasha, dasha))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testDeleteChatShouldThrow() {
        ChatService.deleteChat(sasha, masha)
    }

    @Test
    fun testEditMessage() {
        ChatService.createMessage(dasha, masha, "Пгастити")
        val mId = ChatService.testGetChats()[ChatService.keyOf(dasha, masha)]!!.messages.keys.max()
        assertEquals("Простите", ChatService.editMessage(masha, dasha, mId, "Простите").text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun testEditMessageShouldThrow() {
        ChatService.editMessage(masha, dasha, 5, "shouldn't find")
    }

    @Test
    fun testDeleteMessage() {
        val prevMessage = ChatService.testGetChats()[ChatService.keyOf(dasha, masha)]!!.messages.keys.max()
        ChatService.createMessage(dasha, masha, "Message to delete")
        assertEquals(0, ChatService.deleteMessage(dasha, masha, prevMessage + 1))
        assertEquals(prevMessage, ChatService.testGetChats()[ChatService.keyOf(dasha, masha)]!!.messages.keys.max())
    }

    @Test(expected = MessageNotFoundException::class)
    fun testDeleteMessageShouldThrow() {
        ChatService.deleteMessage(dasha, masha, 5)
    }

    @Test
    fun testDeleteLastMessageInChat() {
        ChatService.deleteMessage(dasha, masha, 1)
        ChatService.deleteMessage(dasha, masha, 0)
        assertEquals(1, ChatService.testGetChats().size)
    }

    @Test
    fun testGetUnreadChatsCount() {
        ChatService.createMessage(masha, dasha, "Привет")
        assertEquals(1, ChatService.getUnreadChatsCount(sasha))
        assertEquals(2, ChatService.getUnreadChatsCount(dasha))
        assertEquals(1, ChatService.getUnreadChatsCount(masha))
    }

    @Test
    fun testGetUnreadChats() {
        assertEquals(listOf(
            ChatService.testGetChats()[ChatService.keyOf(sasha, dasha)]
        ), ChatService.getUnreadChats(sasha))

        ChatService.createMessage(masha, dasha, "Привет")

        assertEquals(listOf(
            ChatService.testGetChats()[ChatService.keyOf(sasha, dasha)],
            ChatService.testGetChats()[ChatService.keyOf(dasha, masha)]
        ), ChatService.getUnreadChats(dasha))
    }

    @Test
    fun testGetMessages() {
        assertEquals("в чате, типа диалог",
            ChatService.getMessages(sasha, dasha,2, 3).joinToString(separator = "") { it.text }
        )
    }
}