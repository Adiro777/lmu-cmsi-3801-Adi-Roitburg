import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Exercises {
    static Map<Integer, Long> change(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        var counts = new HashMap<Integer, Long>();
        for (var denomination : List.of(25, 10, 5, 1)) {
            counts.put(denomination, amount / denomination);
            amount %= denomination;
        }
        return counts;
    }

    static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> predicate){
        return strings.stream().filter(str -> predicate.test(str)).findFirst().map(String::toLowerCase);    
    }

    public static class Sentence {
        private List<String> finalSentence;
        public Sentence(List<String> s, String word){
            this.finalSentence = new ArrayList<>(s);
            this.finalSentence.add(word);
        }

        Sentence and(String newStr){
            return new Sentence(this.finalSentence, newStr);
        }

        String phrase(){
            String finalPhrase = "";
            if(this.finalSentence.size() == 0){
                return "";
            }
            for(int i = 0; i < this.finalSentence.size(); i++){
                if(finalPhrase == ""){
                    finalPhrase = finalPhrase + this.finalSentence.get(i);
                } else {
                    finalPhrase = finalPhrase + " " + this.finalSentence.get(i);
                }
            }           
            return finalPhrase;
        }
    }

    static Sentence say(String word){
        List<String> empty = new ArrayList<String>();
        return new Sentence(empty, word);
    }

    static Sentence say(){
        List<String> empty = new ArrayList<String>();
        return new Sentence(empty, "");
    }

    static long meaningfulLineCount(String filename) throws IOException{
        long count = 0;
        try (BufferedReader file = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = file.readLine()) != null) {
                if(!(line.trim().isEmpty())){
                    if(!(line.trim().charAt(0) == '#')){
                        count++;
                    }
                }
            }
        }
        return count;
    } 
}

record Quaternion(double a, double b, double c, double d) {
    public static final Quaternion ZERO = new Quaternion(0.0, 0.0, 0.0, 0.0);
    public static final Quaternion I = new Quaternion(0.0, 1.0, 0.0, 0.0);
    public static final Quaternion J = new Quaternion(0.0, 0.0, 1.0, 0.0);
    public static final Quaternion K = new Quaternion(0.0, 0.0, 0.0, 1.0);

    public Quaternion {
        if(Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)){
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }
    }

    public ArrayList<Double> coefficients(){
        ArrayList<Double> coeffecients =  new ArrayList<Double>();
        coeffecients.add(this.a);
        coeffecients.add(this.b);
        coeffecients.add(this.c);
        coeffecients.add(this.d);
        return coeffecients;
    }

    public Quaternion plus(Quaternion other){
        return new Quaternion(a + other.a, b + other.b, c + other.c, d + other.d);
    }
    
    public boolean equals(Quaternion other){
        return a == other.a && b == other.b  && c == other.c  && c == other.c;
    }

    public Quaternion times(Quaternion other){
        return new Quaternion(a * other.a - b * other.b - c * other.c - d * other.d,
                          a * other.b + b * other.a + c * other.d - d * other.c,
                          a * other.c - b * other.d + c * other.a + d * other.b,
                          a * other.d + b * other.c - c * other.b + d * other.a);
    }

    public Quaternion conjugate(){
        return new Quaternion(a, -b, -c, -d);
    }

    public String toString(){
        String finalString = "";

        if(a == 0 && b == 0 && c == 0 && d ==0){
            return "0";
        }

        if(a != 0){
            finalString += Double.toString(a);
        }

        if(b != 0){
            if(finalString == "" && Math.abs(b) != 1){
                finalString += Double.toString(b) + 'i';
            } else if(finalString == "" && b == 1){
                finalString += 'i';
            } else if(finalString == "" && b == -1){
                finalString += "-i";
            } else {
                if(b > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(b) != 1){
                    finalString += Double.toString(Math.abs(b)) + 'i';
                } else {
                    finalString += 'i';
                }
            }
        }

        if(c != 0){
            if(finalString == "" && Math.abs(c) != 1){
                finalString += Double.toString(c) + 'j';
            } else if(finalString == "" && c == 1){
                finalString += 'j';
            } else if(finalString == "" && c == -1){
                finalString += "-j";
            } else {
                if(c > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(c) != 1){
                    finalString += Double.toString(Math.abs(c)) + 'j';
                } else {
                    finalString += 'j';
                }
            }
        }

        if(d != 0){
            if(finalString == "" && Math.abs(d) != 1){
                finalString += Double.toString(d) + 'k';
            } else if(finalString == "" && d == 1){
                finalString += 'k';
            } else if(finalString == "" && d == -1){
                finalString += "-k";
            } else {
                if(d > 0){
                    finalString += '+';
                } else {
                    finalString += '-';
                }

                if (Math.abs(d) != 1){
                    finalString += Double.toString(Math.abs(d)) + 'k';
                } else {
                    finalString += 'k';
                }
            }
        }
        return finalString;
    }
}

sealed interface BinarySearchTree permits Empty, Node {
    int size();
    BinarySearchTree insert(String newVal);
    Boolean contains(String valToCheck);
    String toString();

}

final class Empty implements BinarySearchTree{
    public int size(){
        return 0;
    }

    public BinarySearchTree insert(String newVal){
        return new Node(newVal, new Empty(), new Empty());
    }

    public Boolean contains(String valToCheck){
        return false;
    }

    public String toString(){
        return "()";
    }
}

final class Node implements BinarySearchTree{
    private String value;
    private BinarySearchTree left;
    private BinarySearchTree right;
    public Node(String value, BinarySearchTree left, BinarySearchTree right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int size(){
        return 1 + this.left.size() + this.right.size();
    }

    public BinarySearchTree insert(String newVal) {
        if (newVal.compareTo(this.value) > 0) {
            return new Node(this.value, this.left, this.right.insert(newVal));
        } else if (newVal.compareTo(this.value) < 0) {
            return new Node(this.value, this.left.insert(newVal), this.right);
        } else {
            return this;
        }
    }

    public Boolean contains(String valToCheck){
        if (valToCheck.compareTo(this.value) == 0) {
            return true;
        } else if (valToCheck.compareTo(this.value) < 0) {
            return this.left.contains(valToCheck); 
        } else {
            return this.right.contains(valToCheck); 
        }
    }

    public String toString(){
        return ("(" + this.left + this.value + this.right + ")").replace("()", "");
    }
}


