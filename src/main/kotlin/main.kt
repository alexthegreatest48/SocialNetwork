class PostNotFoundException(message: String) : RuntimeException(message)

data class Post (var id: Int,
                 val authorId: Int,
                 val text: String = "Empty",
                 val editorId: Int = authorId,
                 var wasUpdated: Boolean? = false,
                 var Likes: Int = 0,
                 var views: Int = 0,
                 val private: Boolean = false,
                 var archived: Boolean = false,
                 var comments: Array<Comment> = emptyArray(),
                 var attachments: Array<Attachment> = emptyArray())

interface Attachment{
    val type: String
}

data class Video(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val title: String,
)

data class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val title: String,
)

data class VideoAttachment(val video: Video): Attachment{
    override val type: String = "video"
}

data class PhotoAttachment(val photo: Photo): Attachment{
    override val type: String = "photo"
}

data class Doc(
    val id: Int,
    val format: String,
    val ownerId: Int,
    val userId: Int,
    val title: String,
)

data class Audio(
    val id: Int,
    val duration: Int,
    val ownerId: Int,
    val userId: Int,
    val title: String,
)

data class Link(
    val id: Int,
    val internal: Boolean,
    val ownerId: Int,
    val userId: Int,
    val title: String,
)

data class DocAttachment(val doc: Doc): Attachment{
    override val type: String = "doc"
}

data class AudioAttachment(val audio: Audio): Attachment{
    override val type: String = "audio"
}

data class LinkAttachment(val link: Link): Attachment{
    override val type: String = "link"
}

data class Comment(val authorId: Int,
                   val date: Int = 1012022,
                   val text: String)

object WallService{
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var counter = 1;

    fun add(post: Post): Post{
        posts += post.copy(id = counter++)
        return posts.last()
    }

    fun createComment (postId: Int, comment: Comment): Boolean{
        var wasAdded : Boolean = false
        for ((i, post) in posts.withIndex()){
                if (postId == post.id) {
                    post.comments += comment
                    wasAdded = true
                    return true
                }
            }
        if (!wasAdded) {
            throw PostNotFoundException("No post with id: $postId")
        }
        return false
    }


    fun update(newPost: Post): Boolean{
        for ((i, post) in posts.withIndex()){
            if (newPost.id == post.id) {
                posts[i] = newPost.copy()
                posts[i].wasUpdated = true
                return true
            }
        }
        return false
    }

    fun printAll(){
        for(post in posts)
            println(post)
    }

    fun clear() {
        posts = emptyArray()
        counter = 1
    }
}

fun main(){
    val audio1 : Audio = Audio(1,234,24,23,"New Album")
    val video1 : Video = Video(1,23,1, 24, "Best cats video 2022")
    val attach1 : VideoAttachment = VideoAttachment(video1)
    val attach2 : AudioAttachment = AudioAttachment(audio1)
    val comment1 : Comment = Comment(12, text = "Nice post")

    WallService.add(Post(1, 23, attachments =  arrayOf(attach1,attach2)))
    WallService.add(Post(1, 24, "Text"))
    WallService.add(Post(24, 23))
    WallService.printAll()
    println()

    WallService.update(Post(1, 23, "NewText", attachments =  arrayOf(attach1)))
    WallService.printAll()
    println()

    WallService.createComment(4, comment1)
    WallService.printAll()
}