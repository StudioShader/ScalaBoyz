package org.myapp

import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

trait EventStorage[F[_]] {

  def getEventsByDate(date: LocalDate): F[List[Event]]

  def addEvent(event: Event): F[Unit]

  def updateEvent(id: AtomicLong, event: Event): F[Unit]


}
