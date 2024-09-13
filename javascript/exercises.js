import { open } from "node:fs/promises"
import fs from 'fs'

export function change(amount) {
  if (!Number.isInteger(amount)) {
    throw new TypeError("Amount must be an integer")
  }
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let [counts, remaining] = [{}, amount]
  for (const denomination of [25, 10, 5, 1]) {
    counts[denomination] = Math.floor(remaining / denomination)
    remaining %= denomination
  }
  return counts
}

// Write your first then lower case function here
export function firstThenLowerCase(strings, predicate){
  for(let i = 0; i <= strings?.length - 1; i++){
    if(predicate(strings[i])){
      return strings[i].toLowerCase()
    }
  }
  return undefined

}

// Write your powers generator here
export function* powersGenerator({ofBase: base, upTo: limit}){
  let exp = 0
    
    while (true){
        let num = base ** exp
        if (num > limit){
            break
         } else{
            exp += 1
            yield num
        }
      }
    }

// Write your say function here
export function say(string){
  let sentence = []


  function newWord(new_string){
    if (new_string === undefined){
      return sentence.join(" ")
    } else {
      sentence.push(new_string)
      return newWord
    }

  }
  return newWord(string)

}

// Write your line count function here
export async function meaningfulLineCount(filename){
  let num_of_lines = 0
  try{
    const data = await open(filename, 'r')
    for await(const line of data.readLines()){
      const trimmed_line = line.trim()
      if(trimmed_line === '' || trimmed_line[0] === '#'){
        continue
      } else {
        num_of_lines++
      }
    }    
  } catch {
    throw new Error("throws if no such file") 
  }

  return num_of_lines
}

// Write your Quaternion class here
export class Quaternion{
  constructor(a, b, c, d){
    this.a = a
    this.b = b
    this.c = c
    this.d = d

    Object.freeze(this)
  }

  get coefficients(){
    return [this.a, this.b, this.c, this.d]
  }

  get conjugate(){
    return new Quaternion(this.a, -this.b, -this.c, -this.d)
  }

  plus(other){
    return new Quaternion(this.a + other.a, this.b + other.b, this.c + other.c, this.d + other.d)
  }

  times(other){
    return new Quaternion(this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d,
                          this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c,
                          this.a * other.c - this.b * other.d + this.c * other.a + this.d * other.b,
                          this.a * other.d + this.b * other.c - this.c * other.b + this.d * other.a)
  }

  toString(){
    let s = ""
    const eff = this.coefficients  
    const suffixes = ["", "i", "j", "k"];
    const pairs = eff.map((value, index) => [value, suffixes[index]])

    for(let i = 0; i < pairs.length; i++){
      const num = pairs[i][0]
      const suffix = pairs[i][1]

      if (num === 0){
        continue
      }

      if(s === ''){
        if(suffix === "" || Math.abs(num) !== 1){
          s += String(num)
        } else {
          if(num == -1){
            s += "-"
          } else {
            s += ""
          }
        }
      } else{
        if(num > 0){
          s += "+"
        } else {
          s += "-"
        }

        if(suffix == "" || Math.abs(num) !== 1){
          s += String(Math.abs(num))
        }
      }
      s += suffix
    }

    if(s === ""){
      return "0"
    } else {
      return s
    }
  }
}
