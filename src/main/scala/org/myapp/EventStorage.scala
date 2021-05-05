package org.myapp

import java.time.LocalDate

trait EventStorage[F[_]] {

  def getEventsByDate(date: LocalDate): List[Event]

  def addEvent(event: Event): Unit

  def updateEvent(event: Event): Unit

}
