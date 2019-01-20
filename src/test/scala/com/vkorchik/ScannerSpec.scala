package com.vkorchik

import org.scalatest.{FreeSpecLike, Matchers}

class ScannerSpec extends Matchers with FreeSpecLike {

  sealed trait TestContext {
    def content: Stream[String]
    def phrase: List[String]
    lazy val scanner = new Scanner
  }

  "Scanner must" - {
    "find one word out of one" in new TestContext {
      def content = Stream("one", "two", "three")

      def phrase = List("two")

      scanner.scan(phrase, content) shouldContain(List("two"), Nil)
    }

    "find three words out of four" in new TestContext {
      def content = Stream("one", "two", "three")

      def phrase = List("one", "two", "three", "four")

      scanner.scan(phrase, content) shouldContain(List("one", "two", "three"), List("four"))
    }

    "find four words out of four" in new TestContext {
      def content = Stream("one","two","three","four","five")
      def phrase = List("one", "two", "three", "four")

      scanner.scan(phrase, content) shouldContain(List("one", "two", "three", "four"), Nil)
    }

    "find nothing" in new TestContext {
      def content = Stream("nothing")

      def phrase = List("one", "two", "three", "four")

      scanner.scan(phrase, content) shouldContain(Nil, List("one", "two", "three", "four"))
    }

    "find nothing in file with empty content" in new TestContext {
      def content = Stream("")
      def phrase = List("one")

      scanner.scan(phrase, content) shouldContain(Nil, List("one"))
    }
    "find nothing in file with no content" in new TestContext {
      def content = Stream()
      def phrase = List("one")

      scanner.scan(phrase, content) shouldContain(Nil, List("one"))
    }

    "find four out four with 2-2 reps" in new TestContext {
      def content = Stream("one", "two", "three", "four", "two", "five", "one")

      override def phrase = List("one", "two", "one", "two")

      scanner.scan(phrase, content) shouldContain(List("one", "one", "two", "two"), Nil)
    }

    "find three out four with 2 reps" in new TestContext {
      def content = Stream("one", "two", "three","four", "two", "five", "six")

      override def phrase = List("one", "two", "one", "two")

      scanner.scan(phrase, content) shouldContain(List("one", "two", "two"), List("one"))
    }
  }

  implicit class TupleOps(tuple: (List[String], List[String])) {
    def shouldContain(found: List[String], left: List[String]) = {
      tuple._1 should contain allElementsOf found
      tuple._2 should contain allElementsOf left
    }
  }
}
