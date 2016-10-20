package example

import prelude._

object test {
  def main(a: Array[String]): Unit = {
    assert(1 -> "s" == ((1, "s")))

    assert(identity(1) == 1, "what's wrong with you?")

    trait TC
    implicit val tc: TC = new TC {}
    implicitly[TC]

    try {
      ???
    } catch {
      case e: NotImplementedError => ()
    }

    try {
      assert(false)
    } catch {
      case e: AssertionError => ()
    }

    try {
      assert(false, "nope")
    } catch {
      case e: AssertionError => ()
    }

    print(1)
    println(2)
    println()

    1.abs == 1
    1l.abs == 1
    1f.abs == 1
    1d.abs == 1
    '1'.abs == 1
    (1: Byte).abs == 1
    (1: Short).abs == 1

    type L = Long
    implicitly[Long =:= L]
    implicitly[Long =:= Long]
    implicitly[Long <:< AnyVal]

    println("======== Test passed ========")
  }
}
