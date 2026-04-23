package sn_test.bin

import sn_test.lib.Lib

@main def hello(x: Int, y: Int) =
  val z = x + y + 2
  println(Lib.addition(x, y))
