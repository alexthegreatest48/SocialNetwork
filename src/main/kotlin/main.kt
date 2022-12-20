import javax.swing.UIManager.put

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

data class Note(
    var id: Int = 0,
    var title: String,
    var text: String,
    var privacy: Int,
    var comment_privacy: Int,
    var comments: MutableList<Comment> = mutableListOf()
)

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
                   val text: String,
                   var deleted: Boolean = false)

object WallService{
    private var posts = emptyArray<Post>()
    private var counter = 1
    val notes = mutableMapOf<Int,Note>()


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
        throw PostNotFoundException("No post with id: $postId")
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

    fun addNote(note:Note): Int {
        val id: Int = if (notes.isEmpty()) 1 else notes.size+1
        notes[id] = note
        return id
    }

    fun createCommentNote(id: Int, comment: Comment){
        val note: Note = notes[id]!!
        note.comments += comment
        notes[id] = note
    }

    fun deleteNote(id: Int): Int{
        notes.remove(id)
        return 0
    }

    fun deleteCommentNote(id: Int, comment: Comment){
        val note: Note = notes[id]!!
        note.comments.remove(comment)
        comment.deleted = true
        note.comments += comment
        notes[id] = note
    }

    fun editNote(note: Note){
        notes[note.id] = note
    }

    fun editComment(id: Int, comment: Comment){

    }

    fun getNote(id: Int){
        println(notes)
    }

    fun getNoteById(id: Int){
        println(notes[id])
    }

    fun getComments(id: Int, comment: Comment){
        val note: Note = notes[id]!!
        /*for (i in note.comments){
            if (!comments.deleted) println(note.comments) Если у комментария признак удаления false то выводим
        }*/
    }

    fun restoreComment(id: Int, comment: Comment){
        val note: Note = notes[id]!!
        note.comments.remove(comment)
        comment.deleted = false
        note.comments += comment
        notes[id] = note
    }

}

fun main(){
    val audio1 : Audio = Audio(1,234,24,23,"New Album")
    val video1 : Video = Video(1,23,1, 24, "Best cats video 2022")
    val attach1 : VideoAttachment = VideoAttachment(video1)
    val attach2 : AudioAttachment = AudioAttachment(audio1)
    val comment1 : Comment = Comment(12, text = "Nice post")
    val note1 : Note = Note(0,"title","text", 1, 1)
    val note2 : Note = Note(0,"title2","text2", 1, 1)
  /*  WallService.add(Post(1, 23, attachments =  arrayOf(attach1,attach2)))
    WallService.add(Post(1, 24, "Text"))
    WallService.add(Post(24, 23))
    WallService.printAll()
    println()

    note1.id = WallService.addNote(note1)
    note2.id = WallService.addNote(note2)

    WallService.createCommentNote(1, comment1)
    WallService.getNoteById(1)
    WallService.deleteCommentNote(1, comment1)
    WallService.getNoteById(1)
    note2.id = WallService.deleteNote(note2.id)



    /WallService.update(Post(1, 23, "NewText", attachments =  arrayOf(attach1)))
    WallService.printAll()
    println()

    WallService.createComment(4, comment1)
    WallService.printAll()*/
}