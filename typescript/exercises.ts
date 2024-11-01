import { promises } from "node:dns"
import { PathLike } from "node:fs"
import { open } from "node:fs/promises"

export function change(amount: bigint): Map<bigint, bigint> {
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let counts: Map<bigint, bigint> = new Map()
  let remaining = amount
  for (const denomination of [25n, 10n, 5n, 1n]) {
    counts.set(denomination, remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenApply<T, U>(items: T[], predicate: (item: T) => Boolean, consumer: (item: T) => U): U | undefined{
  const element: T | undefined = items.find((item) => predicate(item))
  if(element !== undefined){
    return consumer(element) 
  }
  return undefined
}

export function* powersGenerator(base: bigint): Generator<bigint>{
  for(let power = 1n; ;power *= base){
    yield power
  }
}

export async function meaningfulLineCount(filename: PathLike): Promise<number>{
  let num_of_lines = 0
  
  const data = await open(filename, 'r')
  for await(const line of data.readLines()){
    const trimmed_line = line.trim()
    if(trimmed_line === '' || trimmed_line[0] === '#'){
      continue
    } else {
      num_of_lines++
    }
  }    
  return num_of_lines
}

interface Sphere{
  kind: "Sphere"
  radius: number
}

interface Box{
  kind: "Box"
  width: number
  length: number
  depth: number
}

export type Shape = Sphere | Box

export function surfaceArea(shape: Shape): number{
  if(shape.kind === "Sphere"){
    return 4 * Math.PI * shape.radius ** 2
  } else {
    return 2 * (shape.width * shape.length + shape.width * shape.depth + shape.length * shape.depth)
  }
}

export function volume(shape: Shape): number{
  if(shape.kind === "Sphere"){
    return (4/3) * Math.PI * shape.radius ** 3
  } else {
    return shape.width * shape.length * shape.depth
  }
}

export interface BinarySearchTree<T>{
  size(): number
  insert(value: T): BinarySearchTree<T>
  contains(value: T): boolean
  inorder(): Iterable<T>
}

export class Empty<T> implements BinarySearchTree<T> {
  size(): number {
    return 0
  }

  insert(value: T): BinarySearchTree<T> {
    return new Node(value, new Empty, new Empty)
  }

  contains(value: T): boolean{
    return false
  }

  inorder(): Iterable<T> {
    return []
  }

  toString(): String{
    return "()"
  }
}

export class Node<T> implements BinarySearchTree<T>{
  private value: T
  private left: BinarySearchTree<T>
  private right: BinarySearchTree<T>

  constructor(value: T, left: BinarySearchTree<T>, right: BinarySearchTree<T>){
    this.value = value
    this.left = left
    this.right = right
  }

  size(): number{
    return 1 + this.left.size() + this.right.size()
  }

  insert(value: T): BinarySearchTree<T>{
      if (value > this.value) {
        return new Node(this.value, this.left, this.right.insert(value));
    } else if (value < this.value) {
        return new Node(this.value, this.left.insert(value), this.right);
    } else {
        return this;
    }
  }

  contains(value: T){
    if(value === this.value){
      return true
    } else if(value < this.value){
      return this.left.contains(value)
    } else {
      return this.right.contains(value)
    }
  }

  *inorder(): Iterable<T> {
    yield* this.left.inorder() 
    yield this.value;              
    yield* this.right.inorder()
  }

  toString(): String{
    return `(${this.left}${this.value}${this.right})`.replace(/\(\)/g, "");

  }
}
