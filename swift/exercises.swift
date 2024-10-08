import Foundation

struct NegativeAmountError: Error {}
struct NoSuchFileError: Error {}

func change(_ amount: Int) -> Result<[Int:Int], NegativeAmountError> {
    if amount < 0 {
        return .failure(NegativeAmountError())
    }
    var (counts, remaining) = ([Int:Int](), amount)
    for denomination in [25, 10, 5, 1] {
        (counts[denomination], remaining) = 
            remaining.quotientAndRemainder(dividingBy: denomination)
    }
    return .success(counts)
}

func firstThenLowerCase(of strings: [String]?, satisfying predicate: (String) -> Bool) -> String?{
   return strings?.first(where: predicate)?.lowercased()
}

struct Sentence {
    var phrase: String
    var words: [String]
    init(newWord: String, array: [String]){
        words = array
        words.append(newWord)
        phrase = words.joined(separator: " ")
    }

    func and(_ newWord: String) -> Sentence{
        return Sentence(newWord: newWord, array: words)
    }
}

func say(_ word: String) -> Sentence{
    let empty: [String] = []
    return Sentence(newWord: word, array: empty)
}

func say() -> Sentence{
     let empty: [String] = []
     return Sentence(newWord: "", array: empty)
}

func meaningfulLineCount(_ filename: String) async -> Result<Int, Error>{
    var lineCount: Int = 0
    let filrUrl:URL = URL(fileURLWithPath: filename)
    do{
        for try await line in filrUrl.lines{
            print(line)
            let trimmedLine: String = line.trimmingCharacters(in: .whitespaces)
            if !trimmedLine.isEmpty{
                if trimmedLine[trimmedLine.startIndex] != "#"{
                    lineCount += 1
                }
            }
        } 
    } catch {
        return .failure(NoSuchFileError())
    }
    return .success(lineCount)
}

struct Quaternion: Equatable, CustomStringConvertible{
    var a: Double = 0.0
    var b: Double = 0.0
    var c: Double = 0.0
    var d: Double = 0.0

    static let ZERO: Quaternion = Quaternion(a: 0, b: 0, c: 0, d: 0)
    static let I: Quaternion = Quaternion(a: 0, b: 1.0, c: 0, d: 0)
    static let J: Quaternion = Quaternion(a: 0, b: 0, c: 1.0, d: 0)
    static let K: Quaternion = Quaternion(a: 0, b: 0, c: 0, d: 1.0)

    var coefficients: [Double]{
        return [self.a, self.b, self.c, self.d]
    }

    var conjugate: Quaternion {
        return Quaternion(a: self.a, b: -self.b, c: -self.c, d: -self.d)
    }
    
    static func + (quatOne: Quaternion, quatTwo: Quaternion) -> Quaternion {
        return Quaternion(a: quatOne.a + quatTwo.a, b: quatOne.b + quatTwo.b, c: quatOne.c + quatTwo.c, d: quatOne.d + quatTwo.d)
    }

     static func * (quatOne: Quaternion, quatTwo: Quaternion) -> Quaternion {
        return Quaternion(a: quatOne.a * quatTwo.a - quatOne.b * quatTwo.b - quatOne.c * quatTwo.c - quatOne.d * quatTwo.d,
                          b: quatOne.a * quatTwo.b + quatOne.b * quatTwo.a + quatOne.c * quatTwo.d - quatOne.d * quatTwo.c,
                          c: quatOne.a * quatTwo.c - quatOne.b * quatTwo.d + quatOne.c * quatTwo.a + quatOne.d * quatTwo.b,
                          d: quatOne.a * quatTwo.d + quatOne.b * quatTwo.c - quatOne.c * quatTwo.b + quatOne.d * quatTwo.a);
    }

    var description: String{
        var finalString: String = ""

        if self.a == 0.0 && self.b == 0.0 && self.c == 0.0 && self.d == 0.0{
            return "0"
        }

        if self.a != 0.0{
            finalString = finalString + String(self.a)
        }

        if(self.b != 0.0){
            if(finalString == "" && abs(self.b) != 1.0){
                finalString += String(self.b) + "i";
            } else if(finalString == "" && self.b == 1.0){
                finalString += "i";
            } else if(finalString == "" && self.b == -1.0){
                finalString += "-i";
            } else {
                if(self.b > 0){
                    finalString += "+";
                } else {
                    finalString += "-";
                }

                if (abs(self.b) != 1.0){
                    finalString += String(abs(self.b)) + "i";
                } else {
                    finalString += "i";
                }
            }
        }

        if(self.c != 0.0){
            if(finalString == "" && abs(self.c) != 1.0){
                finalString += String(self.c) + "j";
            } else if(finalString == "" && self.c == 1.0){
                finalString += "j";
            } else if(finalString == "" && self.c == -1.0){
                finalString += "-j";
            } else {
                if(self.c > 0){
                    finalString += "+";
                } else {
                    finalString += "-";
                }

                if (abs(self.c) != 1.0){
                    finalString += String(abs(self.c)) + "j";
                } else {
                    finalString += "j";
                }
            }
        }

        if(self.d != 0.0){
            if(finalString == "" && abs(self.d) != 1.0){
                finalString += String(self.d) + "k";
            } else if(finalString == "" && self.d == 1.0){
                finalString += "k";
            } else if(finalString == "" && self.d == -1.0){
                finalString += "-k";
            } else {
                if(self.d > 0){
                    finalString += "+";
                } else {
                    finalString += "-";
                }

                if (abs(self.d) != 1.0){
                    finalString += String(abs(self.d)) + "k";
                } else {
                    finalString += "k";
                }
            }
        }
        return finalString
    }
}

indirect enum BinarySearchTree: CustomStringConvertible{
    case empty
    case node(value: String, left: BinarySearchTree, right: BinarySearchTree)

    var size: Int{
        switch self{
            case .empty: return 0
            case .node(_, let left, let right):
                return left.size + 1 + right.size
        }
    }

    func insert(_ newVal: String) -> BinarySearchTree{
        switch self{
            case .empty:
                return .node(value: newVal, left: .empty, right: .empty)
            
            case .node(let value, let left, let right):
                if newVal < value{
                    return .node(value: value, left: left.insert(newVal), right: right)
                } else if newVal > value{
                     return .node(value: value, left: left, right: right.insert(newVal))
                } else {
                    return self
                }
        }
    }

    func contains(_ valToCheck: String) -> Bool {
        switch self{
            case .empty:
                return false
            
            case .node(let value, let left, let right):
                if(valToCheck == value){
                    return true
                } else if(valToCheck < value){
                    return left.contains(valToCheck)
                } else {
                    return right.contains(valToCheck)
                }
        }
    }

    var description: String{
            switch self{
                case .empty:
                    return "()"
                case .node(let value, let left, let right ):
                    return ("(\(left)\(value)\(right))").replacingOccurrences(of: "()", with: "")
            }
        }   
}
