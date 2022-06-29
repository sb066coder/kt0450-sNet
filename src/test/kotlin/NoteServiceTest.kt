import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Before
    fun setUp() {
        NoteService.add("First note", "Text of the first note")
        NoteService.add("Second note", "Text of the second note")
        NoteService.add("Third note", "Text of the third note")
        NoteService.createComment(0, "First comment for the first note")
        NoteService.createComment(0, "Second comment for the first note")
        NoteService.createComment(1, "First comment for the second note")
    }

    @After
    fun tearDown() {
        NoteService.clearForTest()
    }

    @Test
    fun testAdd() {
        assertEquals(3, NoteService.add("test", "test note"))
    }

    @Test
    fun testDelete() {
        assertEquals(1, NoteService.delete(0))
        assertEquals(2, NoteService.get().size)
        assertEquals(1, NoteService.getComments().size)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testDeleteException() {
        NoteService.delete(3)
    }

    @Test
    fun testGetById() {
        assertEquals(NoteService.get().first(), NoteService.getById(0))
        assertEquals(NoteService.get().last(), NoteService.getById(2))
    }

    @Test
    fun testCreateComment() {
        val expectedId = NoteService.getComments().size
        assertEquals(expectedId, NoteService.createComment(2, "test comment"))
    }

    @Test(expected = NoteNotFoundException::class)
    fun testCreateCommentShouldThrow() {
        NoteService.createComment(5, "test should throw exception")
    }


    @Test
    fun testDeleteComment() {
        assertEquals(1, NoteService.deleteComment(2))
        assertEquals(2, NoteService.getComments().size)
    }


    @Test(expected = CommentNotFoundException::class)
    fun testDeleteCommentShouldThrow() {
        NoteService.deleteComment(3)
    }

    @Test
    fun testEdit() {
        assertEquals(1, NoteService.edit(2, "Edited", "edited message"))
        assertEquals("edited message", NoteService.getById(2)?.text)
    }

    @Test(expected = NoteDeletedException::class)
    fun testEditShouldThrowDeleted() {
        NoteService.delete(2)
        NoteService.edit(2, "should be deleted", "should be deleted")
    }

    @Test(expected = NoteNotFoundException::class)
    fun testEditShouldThrowNotFound() {
        NoteService.edit(3, "shouldn't find", "shouldn't find")
    }

    @Test
    fun testEditComment() {
        assertEquals(1, NoteService.editComment(1, "edited comment"))
        assertEquals("edited comment", NoteService.getComments()[1].message)
    }

    @Test(expected = CommentDeletedException::class)
    fun testEditCommentShouldThrowDeleted() {
        NoteService.deleteComment(2)
        NoteService.editComment(2, "should be deleted")
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment() {
        NoteService.editComment(3, "shouldn't find")
    }

    @Test
    fun testGet() {
        assertEquals(3, NoteService.get().size)
        NoteService.delete(2)
        assertEquals(2, NoteService.get().size)
    }

    @Test
    fun testGetComments() {
        assertEquals(3, NoteService.getComments().size)
        NoteService.deleteComment(2)
        assertEquals(2, NoteService.getComments().size)
    }

    @Test
    fun testRestoreComment() {
        NoteService.deleteComment(2)
        assertEquals(1, NoteService.restoreComment(2))
        assertEquals(3, NoteService.getComments().size)
    }

    @Test(expected = CommentNotFoundException::class)
    fun testRestoreCommentShouldThrow() {
        NoteService.restoreComment(3)
    }
}