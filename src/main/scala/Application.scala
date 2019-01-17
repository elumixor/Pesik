import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Application {
  def main(args: Array[String]): Unit = {
    val bot = new RandomBot("733310946:AAHL5DR_2S2VCkG4RZKlWZhijtv2mkDrjHs")
    val eol = bot.run()
    println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()
    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
  }
}
