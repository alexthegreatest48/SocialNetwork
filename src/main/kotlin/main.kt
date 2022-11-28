data class Post (var id: Int,
                 val authorId: Int,
                 val text: String = "Empty",
                 val editorId: Int = authorId,
                 var wasUpdated: Boolean? = false,
                 var Likes: Int = 0,
                 var views: Int = 0,
                 val private: Boolean = false,
                 var archived: Boolean = false,
                 var attachments: Array<Attachment> = emptyArray(), )

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

object WallService{
    private var posts = emptyArray<Post>()
    private var counter = 1;

    fun add(post: Post): Post{
        posts += post.copy(id = counter++)
        return posts.last()
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
    val video1: Video = Video(1,23,1, 24, "Best cats video 2022")
    val attach1 : VideoAttachment = VideoAttachment(video1)
    WallService.add(Post(1, 23, attachments =  arrayOf(attach1)))
    WallService.add(Post(1, 24, "Text"))
    WallService.printAll()
    println()

    WallService.update(Post(1, 23, "NewText", attachments =  arrayOf(attach1)))
    WallService.printAll()
}