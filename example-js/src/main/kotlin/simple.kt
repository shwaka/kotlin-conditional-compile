import com.github.shwaka.kococo.debugOnly

fun main() {
    println("Hello world!")
    debugOnly {
        println("This should be shown only in debug mode!")
    }
}
