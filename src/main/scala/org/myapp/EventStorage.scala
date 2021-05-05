package org.myapp

import java.time.LocalDate
import scala.collection.immutable.Vector

trait EventStorage[F[_]] {

  def getEventsByDate(date: LocalDate): Vector[Event]

  def addEvent(event: Event): Unit

  def updateEvent(event: Event): Unit

}
