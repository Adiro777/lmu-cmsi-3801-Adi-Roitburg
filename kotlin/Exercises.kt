import java.io.File 
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import Quaternion

fun change(amount: Long): Map<Int, Long> {
    require(amount >= 0) { "Amount cannot be negative" }
    
    val counts = mutableMapOf<Int, Long>()
    var remaining = amount
    for (denomination in listOf(25, 10, 5, 1)) {
        counts[denomination] = remaining / denomination
        remaining %= denomination
    }
    return counts
}

fun firstThenLowerCase(strings: List<String>, predicate: (String) -> Boolean): String?{
    var loweredString: String? = null
    for(str in strings){
        if(predicate(str) == true){
            loweredString = str.lowercase() 
            break
        }
    }
    return loweredString
}

fun say(word: String): Sentence{
    var empty: MutableList<String> = mutableListOf()
    return Sentence(word, empty)
}

fun say(): Sentence{
    var empty: MutableList<String> = mutableListOf()
    return Sentence("", empty,)
}

class Sentence(var word: String, var previousWords: MutableList<String>){
    var words: MutableList<String> = previousWords
    var phrase: String = ""
    init{
        words.add(word)
        phrase = words.joinToString(" ")
    }
    
    fun and(newWord: String): Sentence{
        val newWords = words.toMutableList()  
        return Sentence(newWord, newWords) 
    }
}

fun meaningfulLineCount(filemame: String): Long {
    var lineCount: Long = 0;
    val file: File = File(filemame)
        file.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                var trimmedLine: String = line.trim()
                if((trimmedLine.isNotEmpty())){
                    if(trimmedLine.first() != '#'){
                        lineCount++
                    }
                }
            }
        }
    return lineCount
 }

data class Quaternion(val a: Double, val b: Double, val c: Double, val d: Double){
    companion object{
        val ZERO: Quaternion = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I: Quaternion = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J: Quaternion = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K: Quaternion = Quaternion(0.0, 0.0, 0.0, 1.0)

    }
    fun coefficients(): List<Double>{
        return listOf(a, b, c, d)
    }

    fun conjugate(): Quaternion{
        return Quaternion(a, -b, -c, -d)
    }

    operator fun plus(other: Quaternion): Quaternion{
        return Quaternion(a + other.a, b + other.b, c + other.c, d + other.d)
    }

    operator fun times(other: Quaternion): Quaternion{
        return Quaternion(a * other.a - b * other.b - c * other.c - d * other.d,
                          a * other.b + b * other.a + c * other.d - d * other.c,
                          a * other.c - b * other.d + c * other.a + d * other.b,
                          a * other.d + b * other.c - c * other.b + d * other.a);
    }

    override fun equals(other: Any?): Boolean{
        if(other !is Quaternion){
            return false
        }
        return a == other.a && b == other.b && c == other.c && d == other.d
    }

    override fun toString(): String{
        var finalString: String = ""

        if(a == 0.0 && b == 0.0 && c == 0.0 && d == 0.0){
            return "0";
        }

        if(a != 0.0){
            finalString += a.toString();
        }

        if(b != 0.0){
            if(finalString == "" && Math.abs(b) != 1.0){
                finalString += b.toString() + 'i';
            } else if(finalString == "" && b == 1.0){
                finalString += 'i';
            } else if(finalString == "" && b == -1.0){
                finalString += "-i";
            } else {
                if(b > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(b) != 1.0){
                    finalString += Math.abs(b).toString() + 'i';
                } else {
                    finalString += 'i';
                }
            }
        }

        if(c != 0.0){
            if(finalString == "" && Math.abs(c) != 1.0){
                finalString += c.toString() + 'j';
            } else if(finalString == "" && c == 1.0){
                finalString += 'j';
            } else if(finalString == "" && c == -1.0){
                finalString += "-j";
            } else {
                if(c > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(c) != 1.0){
                    finalString += Math.abs(c).toString() + 'j';
                } else {
                    finalString += 'j';
                }
            }
        }

        if(d != 0.0){
            if(finalString == "" && Math.abs(d) != 1.0){
                finalString += d.toString() + 'k';
            } else if(finalString == "" && d == 1.0){
                finalString += 'k';
            } else if(finalString == "" && d == -1.0){
                finalString += "-k";
            } else {
                if(d > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(d) != 1.0){
                    finalString += Math.abs(d).toString() + 'k';
                } else {
                    finalString += 'k';
                }
            }
        }
        return finalString
    }
    
}

sealed interface BinarySearchTree{
    fun size(): Int
    fun insert(newVal: String): BinarySearchTree
    fun contains(valToCheck: String): Boolean
    override fun toString(): String
    
    object Empty: BinarySearchTree{
        override fun size(): Int{
            return 0
        }

        override fun insert(newVal: String): BinarySearchTree{
            return Node(newVal, Empty, Empty)
        }

        override fun contains(valToCheck: String): Boolean{
            return false
        }

        override fun toString(): String{
            return "()"
        }
    }

    data class Node(var value: String, var left: BinarySearchTree, var right: BinarySearchTree): BinarySearchTree{
        override fun size(): Int{
            return 1 + this.left.size() + this.right.size()
        }

        override fun insert(newVal: String): BinarySearchTree{
            if (newVal.compareTo(this.value) > 0) {
                return Node(this.value, this.left, this.right.insert(newVal))
            } else if (newVal.compareTo(this.value) < 0) {
                return Node(this.value, this.left.insert(newVal), this.right)
            } else {
                return this
            }
        }

        override fun contains(valToCheck: String): Boolean{
            if (valToCheck.compareTo(this.value) == 0) {
                return true
            } else if (valToCheck.compareTo(this.value) < 0) {
                return this.left.contains(valToCheck)
            } else {
                return this.right.contains(valToCheck)
            }
        }

        override fun toString(): String{
            return ("(" + this.left + this.value + this.right + ")").replace("()", "")
        }
    }

    
}


