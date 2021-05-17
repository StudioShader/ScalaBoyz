package org.myapp

import cats.effect.Sync

import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class ConcurrentHashMapEventStorage[F[_] : Sync] extends EventStorage[F] {

  val hashMap: ConcurrentHashMap[Int, Event] = new ConcurrentHashMap[Int, Event]()

  val nextId: AtomicLong = new AtomicLong(1)

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
        hashMap.put(nextId.get().toInt, Event(event.date, event.name, event.notificationTime, Some(nextId.get().toInt)))
        nextId.addAndGet(1)
      case a: Option[Int] =>
        hashMap.put(a.get, event)
    }
  }

  override def updateEvent(id: Int, event: Event): F[Unit] = Sync[F].pure {
    hashMap.replace(id, hashMap.get(id), Event(event.date, event.name, event.notificationTime, Some(id)))

  }
}
