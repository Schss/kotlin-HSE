import java.io.File
import kotlin.collections.HashSet
import kotlin.collections.ArrayList
import kotlin.math.abs
import java.util.Random
import java.io.FileWriter

const val inputFile = "words.txt"
const val outputFile = "userWords.txt"
const val minLength = 7

fun main()
{
    val allWords: HashSet<String> = HashSet()
    val longWords:  ArrayList<String> = ArrayList()

    File(inputFile).forEachLine{
        allWords.add(it)
        if(it.length >= minLength) {
            longWords.add(it)
        }
    }

    val random = Random()
    val randomWord = abs(random.nextInt()) % longWords.size

    println("Составьте слова из указанного слова\nПодробнее об итогах можно будет увидеть в полученном файле " +
            "userWords.txt\n")

    val initialWord = longWords[randomWord]
    println(initialWord)

    val userWords = ArrayList<String>()

    do {
        val word = readLine()?.trim()
        if (word != null && word.isNotEmpty()) {
            userWords.add(word)
        }
    } while (word != null && word.isNotEmpty())


    var score = 0
    val initialWordLetters: HashSet<Char> = initialWord.toHashSet()
    val newLineEscapeSequence = System.getProperty("line.separator")
    File(outputFile).writeText("Начальное слово - $initialWord$newLineEscapeSequence$newLineEscapeSequence")

    val fw = FileWriter(outputFile, true)
    fw.write("Составленные слова:$newLineEscapeSequence$newLineEscapeSequence")

    for (word in userWords) {

        fw.write("$word - ")
        var anyExtraChars = false
        word.forEach { char ->
            if (!initialWordLetters.contains(char)) {
                anyExtraChars = true
            }
        }

        when {
            anyExtraChars -> {
                fw.write("не засчитано, использованы лишние буквы$newLineEscapeSequence")
            }
            allWords.contains(word) -> {
                score += word.length
                fw.write("засчтитано, + " + word.length.toString() + " баллов" + newLineEscapeSequence)
            }
            else -> {
                fw.write("не засчитано, нет такого слова$newLineEscapeSequence")
            }
        }
    }

    fw.write(newLineEscapeSequence + "итоговый счёт - " + score.toString() + " баллов" + newLineEscapeSequence)
    fw.close()
    println(newLineEscapeSequence + "итоговый счёт - " + score.toString() + " баллов" + newLineEscapeSequence)
}