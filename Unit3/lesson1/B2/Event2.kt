data class Event2(
	val title: String,
    val description: String? = null,
    val daypart: Daypart,
    val durationInMinutes: Int
)
enum class Daypart {
    MORNING, AFTERNOON, EVENING
}

fun main() {
   val event2 = Event2(
       title="Study Kotlin", 
       description="Commit to studying Kotlin at least 15 minutes per day.", 
       daypart=Daypart.EVENING, 
       durationInMinutes=15
   )
   println(event2)
}