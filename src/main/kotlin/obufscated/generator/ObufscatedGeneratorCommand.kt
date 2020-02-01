package obufscated.generator

import com.secure.obfuscated.xorObfuscator
import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.security.SecureRandom

@Command(name = "obufscated-generator", description = ["..."],
        mixinStandardHelpOptions = true)
class ObufscatedGeneratorCommand : Runnable {

    @Option(names = ["-k", "--key"], description = ["Obfucsation key to use"], required = false)
    private var key: String? = null

    @Option(names = ["-v", "--value"], description = ["Value for obfuscation"], required = true)
    private var value: String? = null
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun run() {
        val keyTouse = if (key == null) {
            (1..value!!.length)
                    .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("");
        } else {
            key!!
        }

        val obfuscatedValue = xorObfuscator(keyTouse, value!!)
        println("Key: $keyTouse Obfuscated string $obfuscatedValue")
        println()
        println("\"$obfuscatedValue\".deobfuscate(\"$keyTouse\")")

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(ObufscatedGeneratorCommand::class.java, *args)
        }
    }
}
