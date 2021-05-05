package org.myapp

import java.time.LocalDate

case class Event(var date: LocalDate,
                 var name: String,
                 var notificationTime: LocalDate = LocalDate.now(),
                 var id: Option[Int] = None) {}
