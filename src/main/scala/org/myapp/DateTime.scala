package org.myapp

import java.time._

case class DateTime(var date: LocalDate){

}

object DateTime {
  def now(): DateTime = {
    DateTime(LocalDate.now())
  }
}
