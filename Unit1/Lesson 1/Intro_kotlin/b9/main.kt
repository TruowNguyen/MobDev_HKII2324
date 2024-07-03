fun main() {
    println("Have I spent more time using my phone today: ${compareDay(300, 250)}")
    println("Have I spent more time using my phone today: ${compareDay(300, 300)}")
    println("Have I spent more time using my phone today: ${compareDay(200, 220)}")
}


fun compareDay(timeSpentToday: Int, timeSpentYesterday: Int): Boolean {
    return timeSpentToday > timeSpentYesterday
}