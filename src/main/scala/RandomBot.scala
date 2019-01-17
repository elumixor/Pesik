import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.methods.SendMessage
import com.bot4s.telegram.models.Message

import scala.util.Try

/** Generates random values.
  */
class RandomBot(val token: String) extends TelegramBot with Polling

  with Commands {
  val client = new ScalajHttpClient(token)
  val rng = new scala.util.Random(System.currentTimeMillis())

  var subscriber: Long = 0
  var interrupted = false

    override def receiveMessage(msg: Message): Unit = {
      onCommand("/subscribe") { implicit msg =>
        println("Subscriber: " + msg.source)
        subscriber = msg.source
        thread.run()
      }

      for (text <- msg.text) {
        print(text)
        request(SendMessage(msg.source, "da, " + text + ", pidor. Haf!"))
      }
    }



  val thread: Thread = new Thread() {
    override def run() {
      while (!interrupted) {
        println(subscriber)

        if (subscriber != 0) {
          request(SendMessage(subscriber, "hello"))
        }
        Thread.sleep(1000)
      }
    }
  }

  /* Int(n) extractor */
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}