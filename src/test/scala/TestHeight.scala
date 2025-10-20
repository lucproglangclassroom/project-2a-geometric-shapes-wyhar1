package shapes

import org.scalatest.funsuite.AnyFunSuite
import Shape.*

class TestHeight extends AnyFunSuite:

  test("leaf shapes have height 1") {
    assert(height(Rectangle(2,3)) == 1)
    assert(height(Ellipse(4,5)) == 1)
  }

  test("location adds 1 to child height") {
    val s = Location(1, 2, Rectangle(2,3))
    assert(height(s) == 2)
  }

  test("group height is 1 + max of child heights") {
    val g = Group(
      Rectangle(1,1),                   // height = 1
      Location(0,0, Ellipse(2,3)),      // height = 2
      Group(Rectangle(1,1), Rectangle(2,2)) // height = 1+1 = 2
    )
    assert(height(g) == 3) // 1 + max(1,2,2)
  }
