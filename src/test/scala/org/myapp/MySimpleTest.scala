package org.myapp

import org.scalatest._
import matchers.should._

class MySimpleTest extends funsuite.AnyFunSuite with Matchers {
  test("it works") {
    println("It works")
  }

}

