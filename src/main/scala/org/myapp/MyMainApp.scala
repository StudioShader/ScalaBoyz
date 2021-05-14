package org.myapp

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, IOApp}
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.cats.{Polling, TelegramBot}
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.client3.{SttpBackend, UriContext, asFile, basicRequest}

import java.io.File

object MyMainApp extends IOApp {
  //    override def main(args: Array[String]): Unit = {
  //      println("Hello")
  //      val hashMap = new ConcurrentHashMapEventStorage[IO]
  //      hashMap.addEvent(Event(LocalDate.now(), "present"))
  //      println(hashMap.getEventsByDate(LocalDate.now()))
  //    }

  val token: String = "1795996613:AAG9Iop1-hSNFxoXAw8rwKQnaEBzu8AqnOo"

  val uri: String = "https://emkn.ru/events.ics"

  class MyTGBot(implicit val backend: SttpBackend[IO, Any])
    extends TelegramBot[IO](token, backend)
      with Polling[IO]
      with Commands[IO] {
    onCommand("/ping") { implicit msg =>
      IO {
        downloadIcal()
        print(msg.from.get.id)
      }
      //case  class CalendarType : EVENTS|ASSIGNMENTS|CLASSES
      //      basicRequest
      //        .get(uri"$uri")
      //        .send(backend)
      //        .flatMap { response =>
      //          reply(response.body.fold(identity, identity))
      //        }.void
    }
    //    onCommand("/reg") {implicit msg =>
    //      id - номер пользователя телеграма или например msg.from.get.username И id - то id которое он ввёл в сообщении, по которому нужно будет обращаться к мкн
    //    }
    onMessage { implicit msg =>
      reply(msg.text.getOrElse("???")).void
    }
  }

  override def run(args: List[String]): IO[ExitCode] =
    AsyncHttpClientCatsBackend[IO]().flatMap { implicit backend =>
      val bot = new MyTGBot()
      bot.run()
    }.as(ExitCode.Success)

  def downloadIcal(): Unit = {
    val targetFile = new File("classes.ics")
    val request = basicRequest
      .get(uri"https://emkn.ru/users/152/classes.ics") //  https://emkn.ru/users/152/assignments.ics
      .response(asFile(targetFile))
    val value = AsyncHttpClientCatsBackend[IO]()
      .flatMap(request.send(_))
      .map(resp => println(resp.headers))
    value.unsafeRunSync()
  }

  //  def GetCalendar(val msg: Message,val cal: CalendarType): Unit ={
  //    скачать соответствующий календарь
  //    распарсить его в EventStorage
  //    понять какой юзер к нам обращается (по msg найти ID того, кто к нам обращается и в UserStorage найти соответствующего userа)
  //  для этого юзера записать необходимое количество евентов в EventStorage соответствующий этому user
  //  И   вернуть эти евенты в виде листа
  //  }
}
