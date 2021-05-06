package org.myapp

import canoe.api._
import canoe.syntax._
import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream

import java.time.LocalDate
//import scala.concurrent.Future
//import cats.implicits._

object MyMainApp extends IOApp {
  //import scala.concurrent.ExecutionContext.Implicits.global
  override def main(args: Array[String]): Unit = {
    println("Hello")
    val hashMap = new ConcurrentHashMapEventStorage[IO]
    hashMap.addEvent(Event(LocalDate.now(), "present"))
    println(hashMap.getEventsByDate(LocalDate.now()))
  }

  //далее какой-то код-пример
  //java concurrent list
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

  def greetings[F[_] : TelegramClient]: Scenario[F, Unit] =
    for {
      chat <- Scenario.expect(command("hi").chat)
      _ <- Scenario.eval(chat.send("Hello. What's your name?"))
      name <- Scenario.expect(text)
      _ <- Scenario.eval(chat.send(s"Nice to meet you, $name"))
    } yield ()

}
