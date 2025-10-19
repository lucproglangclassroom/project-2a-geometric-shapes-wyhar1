package shapes

import scala.annotation.targetName
import scala.CanEqual

sealed trait Shape
object Shape {

  final case class Rectangle(width: Int, height: Int) extends Shape {
    require(width >= 0 && height >= 0, s"Rectangle must have nonnegative dimensions, got ($width,$height)")
  }

  final case class Ellipse(rx: Int, ry: Int) extends Shape {
    require(rx >= 0 && ry >= 0, s"Ellipse must have nonnegative radii, got ($rx,$ry)")
  }

  final case class Location(x: Int, y: Int, shape: Shape) extends Shape

  final case class Group(children: Seq[Shape]) extends Shape {
    require(children.nonEmpty, "Group must contain at least one child")
  }

  // Companion for Group: allow Group(a, b, c) varargs syntax
  object Group {
    @targetName("groupVarargs")
    def apply(children: Shape*): Group = Group(children.toSeq)
  }

  // --- Equality support for tests (strict equality in Scala 3) ---
  given CanEqual[Shape, Shape] = CanEqual.derived

  given CanEqual[Shape, Rectangle] = CanEqual.derived
  given CanEqual[Rectangle, Shape] = CanEqual.derived

  given CanEqual[Shape, Ellipse] = CanEqual.derived
  given CanEqual[Ellipse, Shape] = CanEqual.derived

  given CanEqual[Shape, Location] = CanEqual.derived
  given CanEqual[Location, Shape] = CanEqual.derived

  given CanEqual[Shape, Group] = CanEqual.derived
  given CanEqual[Group, Shape] = CanEqual.derived
}

import Shape.*

// -------- Behaviors --------

object size {
  def apply(s: Shape): Int = s match {
    case Rectangle(_, _)        => 1
    case Ellipse(_, _)          => 1
    case Location(_, _, inner)  => apply(inner)
    case Group(children)        => children.map(apply).sum
  }
}

object height {
  def apply(s: Shape): Int = s match {
    case Rectangle(_, _)        => 1
    case Ellipse(_, _)          => 1
    case Location(_, _, inner)  => 1 + apply(inner)
    case Group(children)        => 1 + children.map(apply).foldLeft(0)(math.max)
  }
}

object scale {
  def apply(f: Int)(s: Shape): Shape = s match {
    case Rectangle(w, h)        => Rectangle(w * f, h * f)
    case Ellipse(rx, ry)        => Ellipse(rx * f, ry * f)
    case Location(x, y, inner)  => Location(x * f, y * f, apply(f)(inner))
    case Group(children)        => Group(children.map(apply(f)))
  }
}
