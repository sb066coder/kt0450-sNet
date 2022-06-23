sealed class Attachment {
    abstract val type: String
}

data class PhotoAttachment(
    override val type: String = "photo",
    val content: Photo = Photo()
) : Attachment()

class Photo {}

data class VideoAttachment(
    override val type: String = "video",
    val content: Video = Video()
) : Attachment()

class Video {}

class AudioAttachment(
    override val type: String = "audio",
    val content: Audio = Audio()
) : Attachment()

class Audio {}

class DocumentAttachment(
    override val type: String = "document",
    val content: Document = Document()
) : Attachment()

class Document {}

class StickerAttachment(
    override val type: String = "sticker",
    val content: Sticker = Sticker()
) : Attachment()

class Sticker {}