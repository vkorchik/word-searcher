package com.vkorchik

import java.io.File

object Main extends App {

  if (args.isEmpty) {
    throw new IllegalArgumentException("No directory given")
  }

  val dir = new File(args.head)
  val files = dir.listFiles((_, name) => name.toLowerCase.endsWith(".txt"))

  if (files.isEmpty) {
    throw new IllegalArgumentException(s"No txt files in ${dir.getAbsolutePath}")
  }

  val searcher = new Searcher(new Scanner)

  while (true) {
    print("search> ")
    val phrase = io.StdIn.readLine()

    searcher.searchAndRank(phrase, files.toList)
      .sortBy(-_._2)
      .take(10)
      .foreach { case (file, score) =>
        println(file.getName + " - " + score)
      }
  }
}
