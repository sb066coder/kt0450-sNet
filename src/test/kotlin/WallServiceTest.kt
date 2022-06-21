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
}