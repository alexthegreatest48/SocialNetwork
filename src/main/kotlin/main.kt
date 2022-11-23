data class Post (var id: Int,
                 val authorId: Int,
                 val text: String = "Empty",
                 val editorId: Int = authorId,
                 var wasUpdated: Boolean = false,
                 val Likes: Int = 0,
                 val views: Int = 0,
                 val private: Boolean = false,
                 val archived: Boolean = false){

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
    }
}

fun main(){
    WallService.add(Post(1, 23))
    WallService.add(Post(1, 24, "Text"))
    WallService.printAll()
    println()

    WallService.update(Post(1, 23, "NewText"))
    WallService.printAll()
}