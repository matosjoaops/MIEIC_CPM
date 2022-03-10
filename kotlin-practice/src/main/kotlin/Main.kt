fun createCounter(): Pair<()->Unit,()-> Int > {

    var counter = 0

    fun inc() {
        counter++
    }

    fun value() = counter

    return Pair(::inc, ::value)
}

fun main() {
    val (inc, value) = createCounter()      // deconstruct a Pair

    for (k in 1..10){
        inc()
        println("Current value: $value")
    }
    println(value)
}