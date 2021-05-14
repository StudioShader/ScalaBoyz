package org.myapp

import cats.effect.IO
import cats.effect.unsafe.implicits.global
//import cats.effect.
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.cats.{Polling, TelegramBot}
import org.scalatest.funsuite.AnyFunSuite
import sttp.client3.{SttpBackend, UriContext, basicRequest}
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import java.io.FileInputStream

class TelegramTest extends AnyFunSuite {

  val token: String = "1795996613:AAG9Iop1-hSNFxoXAw8rwKQnaEBzu8AqnOo"

  class MyTGBot(implicit val backend: SttpBackend[IO, Any])
    extends TelegramBot[IO](token, backend)
      with Polling[IO]
      with Commands[IO] {
    onCommand("/ping") {implicit msg =>
      basicRequest
        .get(uri"https://httpbin.org/get")
        .send(backend)
        .flatMap{ response =>
          reply(response.body.fold(identity, identity))
        }.void
    }
    onMessage {implicit msg =>
      reply(msg.text.getOrElse("???")).void
    }
  }

  test("send TG message") {
    AsyncHttpClientCatsBackend[IO]().flatMap{implicit backend =>
      val bot = new MyTGBot()
      bot.run()
    }.unsafeRunSync()
  }

}
