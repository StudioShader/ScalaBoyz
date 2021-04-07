package org.myapp
import org.scalatest._
import matchers.should._
import org.myapp.MyLibraryProvider.MY_CONST

class MySimpleTest extends funsuite.AnyFunSuite with Matchers {
  test ("foo is OK") {
    assert(MyLibraryProvider.foo(23) == 23 * MY_CONST)
  }

}

