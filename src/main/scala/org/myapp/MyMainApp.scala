package org.myapp

import canoe.api._
import canoe.syntax._
import cats.effect.{ExitCode, IO, IOApp}
//import cats.syntax.functor._
import fs2.Stream

import java.time.{LocalDate, Month}

object MyMainApp extends IOApp {

    override def main(args: Array[String]): Unit = {
        println("Hello")
        val event1 = Event(DateTime(LocalDate.of(2004, Month.MAY, 5)), "1")
        val event2 = Event(DateTime(LocalDate.of(2004, Month.MAY, 5)), "2")
        val event3 = Event(DateTime(LocalDate.of(2007, Month.MAY, 5)), "3")
        val event4 = Event(DateTime(LocalDate.of(2002, Month.MAY, 5)), "4")
        Calendar.listOfEvents += event1
        Calendar.listOfEvents += event2
        Calendar.listOfEvents += event3
        Calendar.listOfEvents += event4
        println(Calendar.listOfEvents)
    }

    //далее какой-то код-пример

    /**
     * URL to which Telegram updates will be sent.
     * This address must be reachable for the Telegram, so in case you're using local environment
     * you'd have to expose your local host to the Internet.
     * It can be achieved using ngrok simply following
     * this [[https://developer.github.com/webhooks/configuring/#using-ngrok comprehensive guide]].
     */

    val url: String = "<your server url>"

    val token: String = "<your telegram token>"

    def run(args: List[String]): IO[ExitCode] =
        Stream
          .resource(TelegramClient.global[IO](token))
          .flatMap { implicit client =>
              Stream.resource(Bot.hook[IO](url)).flatMap(_.follow(greetings))
          }
          .compile.drain.as(ExitCode.Success)

    def greetings[F[_]: TelegramClient]: Scenario[F, Unit] =
        for {
            chat <- Scenario.expect(command("hi").chat)
            _    <- Scenario.eval(chat.send("Hello. What's your name?"))
            name <- Scenario.expect(text)
            _    <- Scenario.eval(chat.send(s"Nice to meet you, $name"))
        } yield ()

}
