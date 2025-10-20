package shapes

import org.scalatest.funsuite.AnyFunSuite
import Shape.*

class TestScale extends AnyFunSuite:

  test("scale rectangle and ellipse") {
    assert(scale(2)(Rectangle(2,3)) == Rectangle(4,6))
    assert(scale(3)(Ellipse(1,2)) == Ellipse(3,6))
  }

  test("scale location and its inner shape") {
    val s = Location(2,3, Rectangle(2,3))
    val scaled = scale(2)(s).asInstanceOf[Location]
    assert(scaled.x == 4)
    assert(scaled.y == 6)
    assert(scaled.shape == Rectangle(4,6))
  }

  test("scale group recursively") {
    val g = Group(Rectangle(1,2), Ellipse(2,3))
    val gs = scale(2)(g).asInstanceOf[Group]
    assert(gs.children(0) == Rectangle(2,4))
    assert(gs.children(1) == Ellipse(4,6))
  }
