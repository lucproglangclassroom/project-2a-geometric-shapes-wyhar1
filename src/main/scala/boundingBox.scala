package shapes

import Shape.*
import com.typesafe.scalalogging.Logger

object boundingBox {

  // configurable logger (level controlled by your logging backend/config)
  private val logger = Logger("shapes.boundingBox")

  /** Returns Location(left, bottom, Rectangle(width, height)) */
  def apply(s: Shape): Location = {
    logger.debug(s"bbox: start for $s")
    val res = bb(s)
    logger.debug(s"bbox: result = $res")
    res
  }

  // actual worker (pure)
  private def bb(s: Shape): Location = s match {
    case Rectangle(w, h) =>
      logger.debug(s"rect($w,$h) -> (0,0,$w,$h)")
      Location(0, 0, Rectangle(w, h))

    case Ellipse(rx, ry) =>
      val w = 2 * rx
      val h = 2 * ry
      logger.debug(s"ellipse($rx,$ry) -> (-$rx,-$ry,$w,$h)")
      Location(-rx, -ry, Rectangle(w, h))

    case Location(x, y, inner) =>
      val innerBox: Location = bb(inner)
      // we know bb(inner) is a Location(_, _, Rectangle(_, _))
      val Location(u, v, Rectangle(w, h)) = innerBox: @unchecked
      logger.debug(s"loc($x,$y) + innerBox($u,$v,$w,$h)")
      Location(u + x, v + y, Rectangle(w, h))

    case Group(children) =>
      logger.debug(s"group with ${children.size} children")
      // map -> compute child bboxes
      val boxes: List[Location] = children.map(bb).toList

      // seed from first, then fold with min/max (left,bottom,right,top)
      val Location(u0, v0, Rectangle(w0, h0)) = boxes.head: @unchecked
      val init = (u0, v0, u0 + w0, v0 + h0)

      val (left, bottom, right, top) =
        boxes.tail.foldLeft(init) {
          case ((l, b, r, t), Location(u, v, Rectangle(w, h))) =>
            val nl = math.min(l, u)
            val nb = math.min(b, v)
            val nr = math.max(r, u + w)
            val nt = math.max(t, v + h)
            logger.debug(s"  fold with ($u,$v,$w,$h) -> ($nl,$nb,$nr,$nt)")
            (nl, nb, nr, nt)
        }

      val out = Location(left, bottom, Rectangle(right - left, top - bottom))
      logger.debug(s"group result -> $out")
      out
  }
}
