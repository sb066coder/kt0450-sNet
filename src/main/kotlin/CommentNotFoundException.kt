class CommentNotFoundException(message: String) : RuntimeException(message)
class CommentDeletedException(s: String) : RuntimeException(s)