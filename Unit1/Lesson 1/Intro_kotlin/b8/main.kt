fun main() {
    val steps = 4000
    val caloriesBurned = PEDOMETERstepsTOcalories(steps)
    println("Walking $steps steps burns $caloriesBurned calories")
}

fun PEDOMETERstepsTOcalories(numberOFStepS: Int): Double {
    val caloriesBurnedForEachStep = 0.04
    val totalCaloriesBurned = numberOFStepS * caloriesBurnedForEachStep
    return totalCaloriesBurned
}