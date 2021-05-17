package org.myapp

import java.time.LocalDate

import cats.effect.IO
import org.scalatest._
import matchers.should._

class Test extends funsuite.AnyFunSuite with Matchers {
  test("it runs") {
    println("It runs!")
  }
  val hashMap = new ConcurrentHashMapEventStorage[IO]
  hashMap.addEvent(Event(LocalDate.now(), "present"))
  hashMap.addEvent(Event(LocalDate.now(), "newDay"))
  hashMap.addEvent(Event(LocalDate.of(1724, 1, 28), "SPbSU"))

  test("addEvent works correctly") {
    assert(hashMap.hashMap.size() == 3)
    assert(hashMap.nextId == 4)

    assert(hashMap.hashMap.containsKey(1) == true)
    assert(hashMap.hashMap.containsValue(Event(LocalDate.now(), "present", LocalDate.now(), Some(1))) == true)
    assert(hashMap.hashMap.get(1) == Event(LocalDate.now(), "present", LocalDate.now(), Some(1)))

    assert(hashMap.hashMap.containsKey(2) == true)
    assert(hashMap.hashMap.containsValue(Event(LocalDate.now(), "newDay", LocalDate.now(), Some(2))) == true)
    assert(hashMap.hashMap.get(2) == Event(LocalDate.now(), "newDay", LocalDate.now(), Some(2)))

    assert(hashMap.hashMap.containsKey(3) == true)
    assert(hashMap.hashMap.containsValue(Event(LocalDate.of(1724, 1, 28), "SPbSU", LocalDate.now(), Some(3))) == true)
    assert(hashMap.hashMap.get(3) == Event(LocalDate.of(1724, 1, 28), "SPbSU", LocalDate.now(), Some(3)))

  }
  test("getEventsByDate works correctly") {
    val currentDate = LocalDate.now().toString

    assert(hashMap.getEventsByDate(LocalDate.now()).toString() ==
      "IO(List(Event(" + currentDate + ",newDay," + currentDate + ",Some(2)), Event(" + currentDate + ",present," + currentDate + ",Some(1))))")

    assert(hashMap.getEventsByDate(LocalDate.of(1724, 1, 28)).toString() ==
      "IO(List(Event(1724-01-28,SPbSU," + currentDate + ",Some(3))))")

  }

  test("updateEvent works correctly") {
    hashMap.updateEvent(1, Event(LocalDate.now(), "newLife"))
    assert(hashMap.hashMap.get(1) == Event(LocalDate.now(), "newLife", LocalDate.now(), Some(1)))

    assert(hashMap.hashMap.get(2) == Event(LocalDate.now(), "newDay", LocalDate.now(), Some(2)))

    hashMap.updateEvent(3, Event(LocalDate.of(1755, 1, 25), "MSU"))
    assert(hashMap.hashMap.get(3) == Event(LocalDate.of(1755, 1, 25), "MSU", LocalDate.now(), Some(3)))

    assert(hashMap.hashMap.get(4) == null)
  }

}

