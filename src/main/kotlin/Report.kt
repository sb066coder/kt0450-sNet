abstract class Report

data class ReportComment(
    val ownerId: Int,
    val commentId: Int,
    val reason: Reason
): Report()

enum class Reason {
    SPAM,
    CHILD_PORNOGRAPHY,
    EXTREMISM,
    VIOLENCE,
    DRUG_PROPAGANDA,
    ADULT_MATERIAL,
    INSULT_OR_ABUSE,
}