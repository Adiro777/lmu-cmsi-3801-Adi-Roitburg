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

// Write your first then lower case function here
fun firstThenLowerCase(strings: List<String>, predicate: (String) -> Boolean): String?{
    var final_string: String? = null
    for(str in strings){
        if(predicate(str) == true){
            final_string = str.lowercase() 
            break
        }
    }
    return final_string
}

// Write your say function here
fun say(word: String): Sentence{
    var empty: MutableList<String> = mutableListOf()
    return Sentence(word, empty)
}


fun say(): Sentence{
    var empty: MutableList<String> = mutableListOf()
    return Sentence("", empty,)
   
}

class Sentence(var word: String, var str_list: MutableList<String>){
    var final_list: MutableList<String> = str_list
    var phrase: String = ""
    init{
        final_list.add(word)
        phrase = final_list.joinToString(" ")
    }
    
    fun and(new_word: String): Sentence{
        val newList = final_list.toMutableList()  // Create a copy of the current list
        return Sentence(new_word, newList) 
    }
}

// Write your meaningfulLineCount function here
fun meaningfulLineCount(filemame: String): Long {
    var line_count: Long = 0;
    try{
        val file: File = File(filemame)
        file.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                var trimmed_line: String = line.trim()
                if((trimmed_line.isNotEmpty())){
                    if(trimmed_line.first() != '#'){
                        line_count++
                    }
                }
            }
        

            
            }
    } catch(e: IOException) {
        throw IOException("No such file", e)
    }
    return line_count
 }

// Write your Quaternion data class here
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
        var final_string: String = ""

        if(a == 0.0 && b == 0.0 && c == 0.0 && d == 0.0){
            return "0";
        }

        if(a != 0.0){
            final_string += a.toString();
        }

        if(b != 0.0){
            if(final_string == "" && Math.abs(b) != 1.0){
                final_string += b.toString() + 'i';
            } else if(final_string == "" && b == 1.0){
                final_string += 'i';
            } else if(final_string == "" && b == -1.0){
                final_string += "-i";
            } else {
                if(b > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(b) != 1.0){
                    final_string += Math.abs(b).toString() + 'i';
                } else {
                    final_string += 'i';
                }
            }
        }

        if(c != 0.0){
            if(final_string == "" && Math.abs(c) != 1.0){
                final_string += c.toString() + 'j';
            } else if(final_string == "" && c == 1.0){
                final_string += 'j';
            } else if(final_string == "" && c == -1.0){
                final_string += "-j";
            } else {
                if(c > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(c) != 1.0){
                    final_string += Math.abs(c).toString() + 'j';
                } else {
                    final_string += 'j';
                }
            }
        }

        if(d != 0.0){
            if(final_string == "" && Math.abs(d) != 1.0){
                final_string += d.toString() + 'k';
            } else if(final_string == "" && d == 1.0){
                final_string += 'k';
            } else if(final_string == "" && d == -1.0){
                final_string += "-k";
            } else {
                if(d > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(d) != 1.0){
                    final_string += Math.abs(d).toString() + 'k';
                } else {
                    final_string += 'k';
                }
            }
        }
        return final_string
    }
    
}

// Write your Binary Search Tree interface and implementing classes here
sealed interface BinarySearchTree{
    fun size(): Int
    fun insert(new_val: String): BinarySearchTree
    fun contains(valToCheck: String): Boolean
    override fun toString(): String
    
    object Empty: BinarySearchTree{
        override fun size(): Int{
            return 0
        }

        override fun insert(new_val: String): BinarySearchTree{
            return Node(new_val, Empty, Empty)
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

        override fun insert(new_val: String): BinarySearchTree{
            if (new_val.compareTo(this.value) > 0) {
                return Node(this.value, this.left, this.right.insert(new_val))
            } else if (new_val.compareTo(this.value) < 0) {
                return Node(this.value, this.left.insert(new_val), this.right)
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


