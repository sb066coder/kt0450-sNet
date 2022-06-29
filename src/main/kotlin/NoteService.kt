import java.util.Calendar

object NoteService {
    private var notes = mutableListOf<Note>()
    private var comments = mutableListOf<NoteComment>()
    private var lastNoteId = -1
    private var lastCId = -1

    fun add(title: String, text: String): Int {
        notes.add(Note(
            ++lastNoteId,
            title,
            text,
            Calendar.getInstance().time.time,
            0,
            false,
            false
        ))
        return lastNoteId
    }

    fun delete(noteId: Int): Int {
        for (note in notes)
            if (note.id == noteId) {
                note.deleted = true
                deleteCommentsByNoteId(noteId)
                return 1
            }
        throw NoteNotFoundException("Note ID $noteId is not found")
    }

    private fun deleteCommentsByNoteId(noteId: Int) {
        for (comment in comments)
            if (comment.noteId == noteId)
                comment.deleted = true
    }

    fun getById(noteId: Int): Note? {
        for (note in notes)
            if (note.id == noteId)
                return note
        return null
    }

    fun createComment(noteId: Int, message: String): Int {
        for (note in notes) {
            if (note.id == noteId) {
                comments.add(NoteComment(
                    ++lastCId,
                    noteId,
                    message,
                    Calendar.getInstance().time.time,
                ))
                return lastCId
            }
        }
        throw NoteNotFoundException("Note ID $noteId is not found")
    }

    fun deleteComment(cId: Int): Int {
        for (comment in comments) {
            if (comment.cId == cId) {
                comment.deleted = true
                return 1
            }
        }
        throw CommentNotFoundException("Comment ID $cId is not found")
    }

    fun edit(noteId: Int, title: String, text: String): Int {
        for ((i, note) in notes.withIndex()) {
            if (note.id == noteId) {
                if (!note.deleted) {
                    notes[i] = note.copy(title = title, text = text)
                    return 1
                } else
                    throw NoteDeletedException("Note ID $noteId deleted")
            }
        }
        throw NoteNotFoundException("Note ID $noteId is not found")
    }

    fun editComment(cId: Int, message: String): Int {
        for ((i, comment) in comments.withIndex()) {
            if (comment.cId == cId) {
                if (!comment.deleted){
                    comments[i] = comment.copy(message = message)
                    return 1
                } else
                    throw CommentDeletedException("Comment ID $cId deleted")
            }
        }
        throw CommentNotFoundException("Comment ID $cId is not found")
    }

    fun get(): List<Note> {
        var notesButDeleted = mutableListOf<Note>()
        for (note in notes)
            if (!note.deleted)
                notesButDeleted += note
        return notesButDeleted
    }

    fun getComments(): List<NoteComment> {
        var commentsButDeleted = mutableListOf<NoteComment>()
        for (comment in comments)
            if (!comment.deleted)
                commentsButDeleted += comment
        return commentsButDeleted
    }

    fun restoreComment(cId: Int): Int {
        for ((i, comment) in comments.withIndex()) {
            if (comment.cId == cId) {
                comment.deleted = false
                return 1
            }
        }
        throw CommentNotFoundException("Comment ID $cId is not found")
    }

    fun clearForTest() {
        notes.clear()
        comments.clear()
        lastNoteId = -1
        lastCId = -1
    }
}