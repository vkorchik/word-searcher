package com.vkorchik

import org.scalatest.{FreeSpecLike, Matchers}

class ScorerSpec extends Matchers with FreeSpecLike {

  "Scorer must" - {
    "score properly" in {
      Scorer.score(Nil, Nil) shouldBe 1.0
      Scorer.score(Nil, List("a")) shouldBe 0.0
      Scorer.score(List("a"), Nil) shouldBe 1.0
      Scorer.score(List("a", "b", "c"), List("d", "e")) shouldBe 0.6
    }
  }

}
