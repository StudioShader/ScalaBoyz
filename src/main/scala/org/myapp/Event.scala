package org.myapp

import java.time.LocalDate


//import java.util.Calendar
//import scalatime._

case class Event(var date: LocalDate,
                 var name: String,
                 var notificationTime: LocalDate = LocalDate.now(),
                 var id: Int) {}
