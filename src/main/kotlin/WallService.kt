object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = -1
    private var comments = emptyArray<Comment>()

    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((i, target) in posts.withIndex()) {
            if (target.id == post.id) {
                posts[i] = post.copy(id = target.id, ownerId = target.ownerId, date = target.date)
                return true
            }
        }
        return false
    }

    fun getPosts() = posts

    fun getComments() = comments

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (postId == post.id) {
                comments += comment
                return comment
            }
        }
        throw PostNotFoundException("Post ID $postId is not found")
    }
}