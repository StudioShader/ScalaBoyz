package org.myapp
import org.scalatest.funsuite.AnyFunSuite
import org.myapp.MyLibraryProvider.MY_CONST
class MySimpleTest extends AnyFunSuite {
  test ("foo is OK") {
    assert(MyLibraryProvider.foo(23) == 23 * MY_CONST)
  }

}
