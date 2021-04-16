package org.myapp

import scala.collection.immutable.Vector

trait EventStorage[F[_]] {
  val heap: F[Event]

  def getEventsByDate(date: DateTime): Vector[Event] = ???

  def addEvent(event: Event): Unit = ???

  def redactEvent(event: Event, event2: Event): Unit = ???

}
