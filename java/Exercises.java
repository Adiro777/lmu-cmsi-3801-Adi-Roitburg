import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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

    // Write your first then lower case function here
    static Optional<String> firstThenLowerCase(List<String> strings, Predicate<String> pred){
        return strings.stream().filter(str -> pred.test(str)).findFirst().map(String::toLowerCase);    
    }

    // Write your say function here
    public static class Sentence {
        private List<String> final_sentence;
        public Sentence(List<String> s, String word){

            
            this.final_sentence = new ArrayList<>(s);
            this.final_sentence.add(word);
        }

        Sentence and(String new_str){
            return new Sentence(this.final_sentence, new_str);
        }

        String phrase(){
            String final_phrase = "";
            if(this.final_sentence.size() == 0){
                return "";
            }
            for(int i = 0; i < this.final_sentence.size(); i++){
                if(final_phrase == ""){
                    final_phrase = final_phrase + this.final_sentence.get(i);
                } else {
                    final_phrase = final_phrase + " " + this.final_sentence.get(i);
                }
                
            }

            System.out.println();
            System.out.println(final_phrase);
           
            return final_phrase;
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
    
    

    // Write your line count function here
    static long meaningfulLineCount(String filename) throws IOException{
       long count = 0;
        try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = file.readLine()) != null) {
                System.out.println(line.trim());
                if(!(line.trim().isEmpty())){
                    if(!(line.trim().charAt(0) == '#')){
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
        
        return count;
    } 

}




// Write your Quaternion record class here
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
        ArrayList<Double> coeff_list =  new ArrayList<Double>();
        coeff_list.add(this.a);
        coeff_list.add(this.b);
        coeff_list.add(this.c);
        coeff_list.add(this.d);
        return coeff_list;
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
        String final_string = "";

        if(a == 0 && b == 0 && c == 0 && d ==0){
            return "0";
        }

        if(a != 0){
            final_string += Double.toString(a);
        }

        if(b != 0){
            if(final_string == "" && Math.abs(b) != 1){
                final_string += Double.toString(b) + 'i';
            } else if(final_string == "" && b == 1){
                final_string += 'i';
            } else if(final_string == "" && b == -1){
                final_string += "-i";
            } else {
                if(b > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(b) != 1){
                    final_string += Double.toString(Math.abs(b)) + 'i';
                } else {
                    final_string += 'i';
                }
            }
        }

        if(c != 0){
            if(final_string == "" && Math.abs(c) != 1){
                final_string += Double.toString(c) + 'j';
            } else if(final_string == "" && c == 1){
                final_string += 'j';
            } else if(final_string == "" && c == -1){
                final_string += "-j";
            } else {
                if(c > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(c) != 1){
                    final_string += Double.toString(Math.abs(c)) + 'j';
                } else {
                    final_string += 'j';
                }
            }
        }

        if(d != 0){
            if(final_string == "" && Math.abs(d) != 1){
                final_string += Double.toString(d) + 'k';
            } else if(final_string == "" && d == 1){
                final_string += 'k';
            } else if(final_string == "" && d == -1){
                final_string += "-k";
            } else {
                if(d > 0){
                    final_string += '+';
                } else {
                    final_string += '-';
                }

                if (Math.abs(d) != 1){
                    final_string += Double.toString(Math.abs(d)) + 'k';
                } else {
                    final_string += 'k';
                }
            }
        }
        System.out.println();
        System.out.println(final_string);
        return final_string;
    }
}

// Write your BinarySearchTree sealed interface and its implementations here
// sealed interface BinarySearchTree permits Node, Empty{
//     int size();

// }

// final class Node implements BinarySearchTree{

    
// }

// final class Empty implements BinarySearchTree{

// }
