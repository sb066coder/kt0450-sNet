
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

val exampleComment = Comment(
    1,
    2,
    3,
    "Example comment",
    1,
    1,
    emptyArray(),
    emptyArray()
)

val exampleReport = ReportComment(1,1, Reason.DRUG_PROPAGANDA)

fun main() {

    val emptyPost = Post()
    WallService.add(examplePost)
    WallService.add(examplePost)
    WallService.add(emptyPost)
    println(WallService.update(examplePost.copy(id = 10, text = "Wrong updated post")))
    println(WallService.update(examplePost.copy(id = 1, text = "Correctly updated post")))
    for (post in WallService.getPosts())
        println(post)
    WallService.createComment(0, exampleComment)
    println(WallService.getComments().last())
    WallService.createComment(2, exampleComment.copy(id = 5))
    println(WallService.getComments().last())
    //WallService.createComment(5, exampleComment.copy(id = 10))  // PostNotFoundException
    println(WallService.getComments().last())
    WallService.createReportComment(exampleReport)
    println(WallService.getReports().last())
    WallService.createReportComment(exampleReport.copy(commentId = 10, reason = Reason.ADULT_MATERIAL))
    println(WallService.getReports().last())
}