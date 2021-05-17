package org.myapp

import java.time.LocalDate

case class Event(date: LocalDate,
                 name: String,
                 notificationTime: LocalDate = LocalDate.now(),
                 id: Option[Int] = None) {}
