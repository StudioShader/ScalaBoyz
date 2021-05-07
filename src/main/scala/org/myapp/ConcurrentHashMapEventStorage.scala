package org.myapp

import cats.effect.Sync

import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

class ConcurrentHashMapEventStorage[F[_] : Sync] extends EventStorage[F] {

  val hashMap: ConcurrentHashMap[Int, Event] = new ConcurrentHashMap[Int, Event]()

  var nextId: Int = 1

  override def getEventsByDate(date: LocalDate): F[List[Event]] = Sync[F].pure {
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



  override def addEvent(event: Event): F[Unit] = Sync[F].pure {
    event.id match {
      case None =>
        event.id = Some(nextId)
        nextId += 1
        hashMap.put(nextId - 1, event)
      case a: Option[Int] =>
        hashMap.put(a.get, event)
    }
  }

  override def updateEvent(id: Int, event: Event): F[Unit] = Sync[F].pure {
    if (id >= nextId) {
      throw new NoSuchElementException("no element with such index")
    }

    event.id = Some(id)
    hashMap.replace(id, hashMap.get(id), event)

  }
}
