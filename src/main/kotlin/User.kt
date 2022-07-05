class User (
    val id: Int,
    val name: String = "Unknown",
    var age: Int = 18
): Comparable<User> {
    override fun compareTo(other: User): Int {
        return this.id - other.id
    }
}