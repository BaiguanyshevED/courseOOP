import org.jbibtex.CharacterFilterReader
import org.jbibtex.BibTeXDatabase
import org.jbibtex.BibTeXParser
import org.jbibtex.BibTeXEntry
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.json
import java.io.File
import java.io.FileReader
import java.io.Reader

fun prettyPrintJson(json: String) = println(JSONObject(json).toString(4))

fun prettyPrintCursor(cursor: Iterable<*>) = prettyPrintJson("{ result: ${cursor.json} }")

fun readFromFile(): Reader? {
    println("Введите путь до bib-файла (пример: D:/bibtexfiles/filename.bib):")
    val fileNamePath = readLine()
    val result: Reader?
    try {
        result = FileReader(File(fileNamePath!!))
        println("Успех!")
    } catch (anyException: java.lang.Exception) {
        print("Ошибка файл по пути '$fileNamePath' не найден. ")
        return readFromFile()
    }
    return result
}

fun bibtexFormatToList(reader: Reader): MutableList<BibTex> {
    val filterReader = CharacterFilterReader(reader)
    val bibTeX: BibTeXDatabase = BibTeXParser().parse(filterReader)
    val result: MutableList<BibTex> = mutableListOf<BibTex>()
    bibTeX.entries.forEach { (key, entry) ->
        result += BibTex(
            type = entry.type.value.lowercase(),
            ID = key.value,
            author = entry.getField(BibTeXEntry.KEY_AUTHOR)?.toUserString(),
            journal = entry.getField(BibTeXEntry.KEY_JOURNAL)?.toUserString(),
            pages = entry.getField(BibTeXEntry.KEY_PAGES)?.toUserString(),
            publisher = entry.getField(BibTeXEntry.KEY_PUBLISHER)?.toUserString(),
            title = entry.getField(BibTeXEntry.KEY_TITLE)?.toUserString(),
            year = entry.getField(BibTeXEntry.KEY_YEAR)?.toUserString()
        )
    }
    return result
}

fun main() {
    println("Вас приветствует утилита для экспорта bib-файла в mongo")
    val reader = readFromFile()
    val documents = bibtexFormatToList(reader!!)
    val client = KMongo
        .createClient("mongodb://localhost:27017")
    val mongoDatabase = client.getDatabase("test")
    val mdbCollection = mongoDatabase.getCollection<BibTex>().apply { drop() }
    mdbCollection.insertMany(documents)
    prettyPrintCursor(mdbCollection.find())
}