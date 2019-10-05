package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?):Pair<String?, String?>{
        val parts : List<String>? = fullName?.split(" ")

        var firstName= parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName.isNullOrBlank())
        {
            firstName = null
        }
        if (lastName.isNullOrBlank())
        {
            lastName = null
        }

        return firstName to lastName
    }


    private fun cyr2Lat(currentChar: Char): String
    {
        when (currentChar)
        {
            'а' -> return "a"
            'А' -> return "A"
            'б' -> return "b"
            'Б' -> return "b"
            'в' -> return "v"
            'В' -> return "V"
            'г' -> return "g"
            'Г' -> return "G"
            'д' -> return "d"
            'Д' -> return "D"
            'е' -> return "e"
            'Е' -> return "E"
            'ё' -> return "e"
            'Ё' -> return "E"
            'ж' -> return "zh"
            'Ж' -> return "Zh"
            'з' -> return "z"
            'З' -> return "Z"
            'и' -> return "i"
            'И' -> return "I"
            'й' -> return "i"
            'Й' -> return "I"
            'к' -> return "k"
            'К' -> return "K"
            'л' -> return "l"
            'Л' -> return "L"
            'м' -> return "m"
            'М' -> return "M"
            'н' -> return "n"
            'Н' -> return "N"
            'о' -> return "o"
            'О' -> return "O"
            'п' -> return "p"
            'П' -> return "P"
            'р' -> return "r"
            'Р' -> return "R"
            'с' -> return "s"
            'С' -> return "S"
            'т' -> return "t"
            'Т' -> return "T"
            'у' -> return "u"
            'У' -> return "U"
            'ф' -> return "f"
            'Ф' -> return "F"
            'х' -> return "h"
            'Х' -> return "H"
            'ц' -> return "c"
            'Ц' -> return "C"
            'ч' -> return "ch"
            'Ч' -> return "Ch"
            'ш' -> return "sh"
            'Ш' -> return "Sh"
            'щ' -> return "sh"
            'Щ' -> return "Sh"
            'ъ' -> return ""
            'Ъ' -> return ""
            'ы' -> return "i"
            'Ы' -> return "I"
            'ь' -> return ""
            'Ь' -> return ""
            'э' -> return "e"
            'Э' -> return "E"
            'ю' -> return "yu"
            'Ю' -> return "Yu"
            'я' -> return "ya"
            'Я' -> return "Ya"

            else -> return currentChar.toString()
        }
    }

    //Utils.transliteration("Женя Стереотипов") //Zhenya Stereotipov
    //Utils.transliteration("Amazing Петр","_") //Amazing_Petr
    fun transliteration(payload: String, divider:String= " "): String {
        val parts : List<String>? = payload?.split(" ")

        val firstName= parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        var result: String = ""

        if (firstName != null)
        {
            firstName.toCharArray().forEach{
                result += cyr2Lat(it)
            }

            result += divider
        }

        if (lastName != null)
        {
            lastName.toCharArray().forEach{
                result += cyr2Lat(it)
            }
        }


        return result
    }

    //Utils.toInitials("john" ,"doe") //JD
    //Utils.toInitials("John", null) //J
    //Utils.toInitials(null, null) //null
    //Utils.toInitials(" ", "") //null
    fun toInitials(firstName: String?, lastName: String?): String? {

        var result: String? = null


        if (!firstName.isNullOrBlank()) {
            result = firstName.trimIndent()[0].toUpperCase().toString()
        }

        if (!lastName.isNullOrBlank())  {
            if (result.isNullOrBlank()) {
                result = lastName.trimIndent()[0].toUpperCase().toString()
            }
            else {
                result += lastName.trimIndent()[0].toUpperCase().toString()
            }
        }

        return result
    }
}