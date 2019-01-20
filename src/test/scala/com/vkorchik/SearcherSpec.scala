package com.vkorchik

import java.io.{File, PrintWriter}

import org.scalatest.{BeforeAndAfterEach, FreeSpecLike, Matchers}


class SearcherSpec extends Matchers
  with FreeSpecLike
  with BeforeAndAfterEach
  with DeleteFilesBeforeAndAfterEach {

  val filenameA = "testA.txt"
  val filenameB = "testB.txt"
  val filenameC = "testC.txt"
  val filenameD = "testD.txt"

  def fileA = new File(filenameA)
  def fileB = new File(filenameB)
  def fileC = new File(filenameC)
  def fileD = new File(filenameD)

  override protected val files: Seq[File] = Seq(fileA, fileB, fileC, fileD)

  sealed trait TestContext {
    def writeFile(filename: String)(content: String): Unit = {
      new PrintWriter(filename) {
        write(content)
        close()
      }
    }

    def phrase: String
    val scanner = new Scanner
    val searcher = new Searcher(scanner)
  }

  "SearchApp must" -{
    "return proper files and theirs score" in new TestContext {
      writeFile(filenameA)("two one five\none four three two")
      writeFile(filenameB)("two eleven five\none four five")
      writeFile(filenameC)("two one six\none four three")
      writeFile(filenameD)("twoone tw,o six\none four thr ee")

      override def phrase = "one two two three"

      val result = searcher.searchAndRank(phrase, List(fileA, fileB, fileC, fileD))

      result should contain allElementsOf List(
        fileA -> 1.0,
        fileB -> 0.5,
        fileC -> 0.75,
        fileD -> 0.25
      )
    }
  }
}
