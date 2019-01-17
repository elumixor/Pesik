import scala.io.Source
import sys.process._

object Hello {
  def main(args: Array[String]): Unit = {

    val iteration = 0
    println(s"Helo father, me = $iteration gen")

    val file = Source.fromFile("src/main/scala/Hello.scala")
    val source = file.getLines().mkString
    val pattern = "(?m).*val iteration = ([0-9]+)(?m).*".r
    val pattern(it) = source

    file.close()

    val file2 = Source.fromFile("src/main/scala/Hello.scala")
    val newSource = file2.getLines().mkString("\n")
      .replaceFirst(s"val iteration = $it", s"val iteration = ${it.toInt + 1}")

    file2.close()

    "rm src/main/scala/Hello.scala" !

    import java.io._
    val pw = new PrintWriter(new File("src/main/scala/Hello.scala"))

    pw.write(newSource)
    pw.close()

    "rm -rf classes" !;
    "mkdir classes" !;
    "scalac -d classes src/main/scala/Hello.scala" !;
    "scala -cp classes Hello" !
  }
}