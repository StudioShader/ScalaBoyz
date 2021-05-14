package org.myapp

import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

case class Event(var date: LocalDate,
                 var name: String,
                 var notificationTime: LocalDate = LocalDate.now(),
                 var id: Option[AtomicLong] = Some(new AtomicLong(1))) {}
