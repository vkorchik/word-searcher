package com.vkorchik

class Scanner {
  def scan(wordsToSearch: Words, content: Stream[Word]) = {
    val initialTokensMap =
      wordsToSearch
        .groupBy(w => w)
        .map(p => p._1 -> p._2.size)

    val wordsLeft = unmatchedWords(content, initialTokensMap)
    val wordsFound = initialTokensMap.map { case (k, v) =>
      k -> (v - wordsLeft.getOrElse(k, 0))
    }

    def mapToList(map: Map[Word, Int]) = map.toList.flatMap { case (k, v) => List.fill(v)(k) }

    (mapToList(wordsFound), mapToList(wordsLeft))
  }

  private def unmatchedWords(content: Stream[Word], wordsLeft: Map[Word, Int]): Map[Word, Int] = {
      content match {
        case w #:: tail =>
          val newLeft = wordsLeft.get(w) match {
            case None => wordsLeft
            case Some(1) => wordsLeft - w
            case Some(n) => wordsLeft + (w -> (n - 1))
          }
          if (newLeft.isEmpty) Map() else unmatchedWords(tail, newLeft)
        case _ => wordsLeft
      }
  }
}
