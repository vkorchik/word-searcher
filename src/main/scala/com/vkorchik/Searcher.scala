package com.vkorchik

import java.io.File

import scala.io.Source

class Searcher(scanner: Scanner) {
  private val wordRegex = "\\w+".r

  def searchAndRank(phrase: String, files: List[File]): List[(File, Score)] = {
    val words = filterWordTokens(phrase)
    files.map { f =>
      val content = Source.fromFile(f).getLines().toStream.flatMap(filterWordTokens)
      val (r, l) = scanner.scan(words, content)
      f -> Scorer.score(r, l)
    }
  }

  private def filterWordTokens(str: String): Words = {
    wordRegex.findAllIn(str).toList
  }
}
