import kotlinx.cinterop.*
import stdio.*

fun main(args: Array<String>) {
    val fileName = args[0]
    val file = fopen(fileName, "r")
    if (file == null) {
        perror("cannot open input file $fileName")
        return
    }

    val lines = mutableListOf<String>()
    try {
        memScoped {
            val bufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(bufferLength)

            while (true) {
                val line = fgets(buffer, bufferLength, file)?.toKString()
                if (line == null || line.isEmpty()) break
                lines.add(line.trimEnd())
            }
        }
    } finally {
        fclose(file)
    }

    println(lines)
}
