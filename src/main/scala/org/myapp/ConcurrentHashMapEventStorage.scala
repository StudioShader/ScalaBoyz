package org.myapp

import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

class ConcurrentHashMapEventStorage extends EventStorage[({type T[A] = ConcurrentHashMap[Int, A]})#T] {

  val hashMap: ConcurrentHashMap[Int, Event] = new ConcurrentHashMap[Int, Event]()

  var nextId: Int = 1

  override def getEventsByDate(date: LocalDate): List[Event] = {
    var result: List[Event] = List.empty
    hashMap.forEach { (_, event: Event) =>
      if (event.date.getDayOfYear == date.getDayOfYear) {
        result = {
          event :: result
        }
      }
    }
    result
  }

  override def addEvent(event: Event): Unit = {
    event.id match {
      case None =>
        event.id = Some(nextId)
        nextId += 1
        hashMap.put(nextId - 1, event)

      case a: Option[Int] =>
        hashMap.put(a.get, event)

    }
  }

  override def updateEvent(event: Event): Unit = {
    hashMap.replace(event.id.get, event)
  }
}
