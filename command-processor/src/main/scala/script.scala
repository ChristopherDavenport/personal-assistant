import org.http4s._
import org.http4s.circe._
import org.http4s.circe.middleware._
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._
import cats.syntax.all._
import cats._
import cats.data._
import cats.effect._
import cats.effect.syntax.all._
import cats.effect.std._
import com.comcast.ip4s._
import fs2._
import scala.concurrent.duration._
import java.time.Instant
import scala.collection.immutable

object ExampleScript extends cats.effect.IOApp.Simple {

  def routes(s: Say[IO]) = HttpRoutes.of[IO]{
    case req => 
      for {
        json <- req.asJson
        text <- json.hcursor.downField("text").as[String].liftTo[IO]
        words = text.split(" ").toList
        _ <- words match {
          case "personal" :: "assistant" :: "say" :: rest => 
            s.say(rest)
          case "personal" :: "assistant" :: rest => 
            val text = rest.mkString(" ")
            Console[IO].println(s"Unknown Command: $text")
          case "" :: Nil | Nil => IO.unit
          case other => Console[IO].println(s"Non-Command: ${other.mkString(" ")}")
        }
        out <- Ok()
      } yield out
  }
  def run = {
    val tts = TextToSpeach.impl[IO]
    val say = Say.impl(tts)
    for {
      _ <- EmberServerBuilder.default[IO]
        .withHttpApp(routes(say).orNotFound)
        .withHost(host"0.0.0.0")
        .withPort(port"8080")
        .build
      _ <- Resource.eval(TextToSpeach.impl[IO].speakText("personal assistant online"))
    } yield ()
  }.use(_ => IO.never)

}

trait Say[F[_]]{
  def say(l: List[String]): F[Unit]
}
object Say{
  def impl[F[_]: Applicative](tts: TextToSpeach[F]): Say[F] = new Say[F]{
    def say(l: List[String]): F[Unit] = l match {
      case Nil => Applicative[F].unit
      case "personal" :: "assistant" :: rest => say(rest)
      case others => tts.speakText(others.mkString(" "))
    }
  }
}

// Important Command Parsing vs Output semantics are important to avoid loops
// All commands start with "personal assistant" but nothing that is voiced
// should ever contain that phrase. By this mechanism even when the microphone
// picks up itself, it can never trigger a command execution.
trait TextToSpeach[F[_]]{
  def speakText(text: String): F[Unit]
}
object TextToSpeach {
  import _root_.io.chrisdavenport.shellfish.Shell
  import _root_.io.chrisdavenport.shellfish.SubProcess
  // sudo apt-get install mbrola-us1 espeak-ng

  def impl[F[_]: Async] = new TextToSpeach[F]{
    val pr = SubProcess.ProcessRunner[F]
    def speakText(text: String): F[Unit] = pr.run(System.getProperty("user.dir"), 
      "espeak-ng" :: "-v" ::  "us-mbrola-1" :: "\"" ++ text ++ "\"" :: Nil
    ).use(p => p.exitCode).timeout(30.seconds).void

  }
}


