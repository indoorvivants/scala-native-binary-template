package sn_test.bin

import sn_test.lib.Lib

@main def hello(x: Int, y: Int) =
  val z = x + y
  println(Lib.addition(x, y))
