
val examplePost = Post(100,
    6,
    43,
    21,
    1245,
    "Example post",
    56,
    22,
    false,
    Comments(0,true,true),
    Likes(0, true, true, true),
    Reposts(0, true),
    "post",
    emptyArray(),
    5,
    true,
    true,
    true,
    false,
    false,
    false
)

fun main() {

    val emptyPost = Post()
    WallService.add(examplePost)
    WallService.add(examplePost)
    WallService.add(emptyPost)
    println(WallService.update(examplePost.copy(id = 10, text = "Wrong updated post")))
    println(WallService.update(examplePost.copy(id = 1, text = "Correctly updated post")))
    for (post in WallService.getPosts())
        println(post)
}