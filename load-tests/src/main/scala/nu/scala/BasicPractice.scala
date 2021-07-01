package nu.scala

object BasicPractice {
  def square(x: Double) = x * x

  def cube(x: Double) = x * x * x

  def sumOfSquares(x: Double, y: Double) = square(x) + square(y)

  def computeSumOnFunction(f: Double => Double, x: Double, y: Double): Double = {
    f(x) + f(y)
  }

  def computeFunctionOnFunctions(f: Double => Double, x: Double, y: Double, compute: Double => Double): Double = {
    f(x) + f(y)
  }

  def main(args: Array[String]): Unit = {

    val CONSTANT = "Constant Value"
    val CONSTANT_NUMBER = 9
    var changeableValue = "SomethingThatCanBeChanged"

    println(CONSTANT)
    println(changeableValue)
    changeableValue = "23232"
    println(changeableValue)
    println(CONSTANT)
    println(CONSTANT_NUMBER)

    println("a2+b2 = " + sumOfSquares(4, 3))

    // Function references
    println("computeSumOnFunction = " + computeSumOnFunction(square, 4, 3))
    println("computeSumOnFunction = " + computeSumOnFunction(cube, 4, 3))

    // Creating stream
    val stream = 1 #:: 2 #:: 8 #:: LazyList.empty
    // Use LazyList instead of Stream

  }
}
