package nu.scala

object ListTest {

  def isPalindrome(aList: List[Int]): Boolean = {
    aList.reverse.equals(aList)
  }

  def main(args: Array[String]): Unit = {

    val list = 99 :: (8 :: (2 :: (1 :: Nil)))

    println("Last Element = " + list.last)
    println("Last But one Element = " + list.reverse.tail.reverse.last)
    println("nthElement = " + list(2))
    println("length = " + list.length)
    println("isPalindrome = " + isPalindrome(list))

    val listOfListsAnother: List[List[Int]] = List(List(1, 1), List(5, 8))
    println(listOfListsAnother.flatten)
  }
}
