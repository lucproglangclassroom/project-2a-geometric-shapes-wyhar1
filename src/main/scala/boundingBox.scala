package edu.luc.cs.laufer.cs371.shapes

// TODO: implement this behavior
import Shape.*

object boundingBox:
  def apply(s: Shape): Location =

  def bb(s: Shape): Location = s match
  case Rectangle(w, h) =>
  // rectangle anchored at origin
  Location(0, 0, Rectangle(w, h))

  case Ellipse(rx, ry) =>
  // ellipse centered at origin -> bbox spans [-rx, -ry] .. [rx, ry]
  Location(-rx, -ry, Rectangle(2 * rx, 2 * ry))

  case Location(x, y, inner) =>
  // translate child bbox
  val Location(u, v, Rectangle(w, h)) = bb(inner)
  Location(u + x, v + y, Rectangle(w, h))

  case Group(children*) =>
  // compute each child’s bbox, then union all via foldLeft
  val locs = children.map(bb).toList
  // assume tests never create empty groups; if they might, add a require
  val Location(u0, v0, Rectangle(w0, h0)) = locs.head
  val (l, b, r, t) =
    locs.tail.foldLeft((u0, v0, u0 + w0, v0 + h0)) {
      case ((left, bottom, right, top), Location(u, v, Rectangle(w, h))) =>
        val rgt = u + w
        val top_ = v + h
        (
          math.min(left,  u),
          math.min(bottom, v),
          math.max(right,  rgt),
          math.max(top,    top_)
        )
    }
  Location(l, b, Rectangle(r - l, t - b))

  bb(s)
  end boundingBox
