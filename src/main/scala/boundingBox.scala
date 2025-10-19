package shapes

import Shape.*

object boundingBox {
  // Returns Location(left, bottom, Rectangle(width, height))
  def apply(s: Shape): Location = {

    def bb(s2: Shape): Location = s2 match {
      case Rectangle(w, h) =>
        Location(0, 0, Rectangle(w, h))

      case Ellipse(rx, ry) =>
        // ellipse centered at origin -> bbox [-rx,-ry]..[rx,ry]
        Location(-rx, -ry, Rectangle(2 * rx, 2 * ry))

      case Location(x, y, inner) =>
        val innerBox: Location = bb(inner)
        val Location(u, v, Rectangle(w, h)) = innerBox
        Location(u + x, v + y, Rectangle(w, h))

      case Group(children) =>
        val boxes: List[Location] = children.map(bb).toList

        // seed bounds from first box
        val Location(u0, v0, Rectangle(w0, h0)) = boxes.head
        val init = (u0, v0, u0 + w0, v0 + h0) // (left, bottom, right, top)

        val (left, bottom, right, top) =
          boxes.tail.foldLeft(init) {
            case ((l, b, r, t), Location(u, v, Rectangle(w, h))) =>
              (math.min(l, u), math.min(b, v), math.max(r, u + w), math.max(t, v + h))
          }

        Location(left, bottom, Rectangle(right - left, top - bottom))
    }

    bb(s)
  }
}
