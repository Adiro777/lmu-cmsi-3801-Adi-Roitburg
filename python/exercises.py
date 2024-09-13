from dataclasses import dataclass
from collections.abc import Callable
from typing import *
import os


def change(amount: int) -> dict[int, int]:
    if not isinstance(amount, int):
        raise TypeError('Amount must be an integer')
    if amount < 0:
        raise ValueError('Amount cannot be negative')
    counts, remaining = {}, amount
    for denomination in (25, 10, 5, 1):
        counts[denomination], remaining = divmod(remaining, denomination)
    return counts


# Write your first then lower case function here
def first_then_lower_case(strings: list[str], function: Callable[[str], bool]) -> str:
    for string in strings:
        if function(string) == True:
            return string.lower()
    
    return None

# Write your powers generator here
def powers_generator(base: int, limit: int) -> Generator:
    exp: int = 0
    
    while True:
        num = pow(base, exp)
        if (num > limit):
            break
        else:
            exp += 1
            yield num





# Write your say function here
def say(string: Optional[str] = None) -> Callable[..., str]:
    list_of_words: list[str] = []

    if string is None and len(list_of_words) == 0:
        return ""

    def inner(next_string: Optional[str] = None) -> str:
        if next_string is None:
            return " ".join(list_of_words)
        else:
            list_of_words.append(next_string)
            return inner

    if string is not None:
        list_of_words.append(string)

    return inner

# Write your line count function here

def meaningful_line_count(filename: str) -> int:
    line_count: int = 0
    try:
       with open(filename, 'r') as file:
           for line in file:
               if (line.strip() != "" and line.strip()[0] != '#'):
                   line_count += 1
               
    except:
        raise FileNotFoundError('No such file')
    
    return line_count

# Write your Quaternion class here
@dataclass(frozen = True)
class Quaternion:
    a: float
    b: float
    c: float
    d: float
    
    def __str__(self) -> str:
        s = ""
        co_eff = self.coefficients  
        pairs = zip(co_eff, ["", "i", "j", "k"])

        for num, suffix in pairs:
            if num == 0:
                continue
            
            #Condition when string is empty
            if s == '':
                if suffix == "" or abs(num) != 1:  
                    s += str(num)
                else:
                    s += "-" if num == -1 else ""  
            else:
                # If string is NOT empty, checks what sign to add
                if num > 0:
                    s += "+"
                else:
                    s += "-"
                
                # Adds number
                if suffix == "" or abs(num) != 1:
                    s += str(abs(num))
            
            # Adds suffix
            s += suffix
        
        if s == "":
            return "0"
        
        return s


    def __add__(self, other) -> object:
        return Quaternion(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
    
    def __mul__(self, other: object) -> object:
        return Quaternion(self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d,
                          self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c,
                          self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b,
                          self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a)
    
    def __eq__(self, value: object) -> bool:
        return self.a == value.a and self.b == value.b and self.c == value.c and self.d == value.d
    
    @property
    def conjugate(self) -> object:
        return Quaternion(self.a, -self.b, -self.c, -self.d)

    @property
    def coefficients(self) -> object:
        return (self.a, self.b, self.c, self.d)
    
