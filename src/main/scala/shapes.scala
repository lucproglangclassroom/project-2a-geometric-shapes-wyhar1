package edu.luc.cs.laufer.cs371.shapes

/** data Shape = Rectangle(w, h) | Location(x, y, Shape) | Ellipse(rx, ry) | Group(Shape*) */
enum Shape derives CanEqual:
case Rectangle(width: Int, height: Int)
case Location(x: Int, y: Int, shape: Shape)
// added cases:
case Ellipse(rx: Int, ry: Int)              // radii in x/y
case Group(children: Shape*)               // n-ary node for grouping
