package org.myapp


//import java.util.Calendar
//import scalatime._

case class Event(var date: DateTime,
                 var name: String) {

    var notificationTime: DateTime = DateTime.now()

}
