package shapes

import org.scalatest.funsuite.AnyFunSuite
import Shape.*

class TestSize extends AnyFunSuite:

  test("leaf shapes have size 1") {
    assert(size(Rectangle(2,3)) == 1)
    assert(size(Ellipse(4,5)) == 1)
  }

  test("location delegates to inner shape") {
    val s = Location(10, 20, Rectangle(3,4))
    assert(size(s) == 1)
  }

  test("group sums sizes") {
    val g = Group(Rectangle(1,1), Ellipse(2,3), Location(5,5, Rectangle(4,4)))
    assert(size(g) == 3)
  }
