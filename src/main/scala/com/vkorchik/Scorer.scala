package com.vkorchik

object Scorer {
  def score(found: Words, left: Words): Score =
    (found, left) match {
      case (_, Nil) => 1.0
      case (Nil, _) => 0.0
      case _ => found.size / (found.size + left.size).toDouble
    }
}
