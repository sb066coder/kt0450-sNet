import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun testAdd() {
        assertTrue(WallService.add(examplePost.copy(id = -1)).id != -1)
    }

    @Test
    fun testUpdate() {
        val existingPost = WallService.add(examplePost.copy(text = "Update test"))
        val wrongId = existingPost.id + 10
        val nonExistentPost = existingPost.copy(id = wrongId)
        assertTrue(WallService.update(existingPost.copy(text = "Updated post")))
        assertFalse(WallService.update(nonExistentPost.copy(text = "Wrong id post")))
    }

    @Test
    fun testCreateComment() {
        val existingPostId = WallService.add(examplePost.copy(text = "New test post")).id
        val commentToCreate = exampleComment.copy(text = "Test comment")
        assertTrue(WallService.createComment(existingPostId, commentToCreate) === WallService.getComments().last())
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val nonExistentPostId = WallService.getPosts().last().id + 10
        WallService.createComment(nonExistentPostId, exampleComment.copy(text = "Wrong postId comment"))
    }

    @Test
    fun testCreateReportComment() {
        val existingCommentId = WallService.createComment(
            0, exampleComment.copy(text = "Test comment for report")
        ).id
        assertTrue(
            WallService.createReportComment(exampleReport.copy(commentId = existingCommentId)) ===
                    WallService.getReports().last()
        )
    }

    @Test(expected = CommentNotFoundException::class)
    fun shouldThrowCommentEx() {
        val existingPostId = WallService.add(examplePost.copy(text = "New test post")).id
        WallService.createComment(
            existingPostId, exampleComment.copy(text = "Test comment for report")
        )
        var nonExistentCommentId = WallService.getComments().map { comment -> comment.id }.max() + 1
        WallService.createReportComment(exampleReport.copy(commentId = nonExistentCommentId))
    }
}