package org.myapp

import java.time.LocalDate

trait EventStorage[F[_]] {

  def getEventsByDate(date: LocalDate): F[List[Event]]

  def addEvent(event: Event): F[Unit]

  def updateEvent(event: Event): F[Unit]

}
