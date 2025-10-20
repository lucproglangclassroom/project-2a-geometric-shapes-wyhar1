package shapes

import org.scalatest.funsuite.AnyFunSuite

class TestConstructors extends AnyFunSuite:

  test("Rectangle must have nonnegative dimensions") {
    assertThrows[IllegalArgumentException] { Shape.Rectangle(-1, 2) }
    assertThrows[IllegalArgumentException] { Shape.Rectangle(1, -2) }
  }

  test("Ellipse must have nonnegative radii") {
    assertThrows[IllegalArgumentException] { Shape.Ellipse(-3, 4) }
    assertThrows[IllegalArgumentException] { Shape.Ellipse(5, -1) }
  }

  test("Group must be nonempty") {
    assertThrows[IllegalArgumentException] { Shape.Group() }
  }
