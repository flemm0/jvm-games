val scala3Version = "3.8.2"
val munitVersion = "1.2.4"

lazy val commonSettings = Seq(
  scalaVersion := scala3Version,
  javacOptions ++= Seq("--release", "17"),
  scalacOptions ++= {
    val options = Seq("-deprecation", "-feature", "-Wnonunit-statement")
    if (sys.env.contains("CI")) options :+ "-Werror" else options
  },
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.7.0",
    "org.scalameta" %% "munit" % munitVersion % Test
  ),
  initialCommands :=
    """
      |import cats.effect.IO
      |import cats.effect.unsafe.implicits.global
      |import cats.syntax._
    """.stripMargin
)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)

lazy val numberGuesser = project
  .in(file("games/number-guesser"))
  .settings(commonSettings, name := "number-guesser", version := "0.1.0-SNAPSHOT")
  .dependsOn(core)

lazy val ticTacToe = project
  .in(file("games/tic-tac-toe"))
  .settings(commonSettings, name := "tic-tac-toe", version := "0.1.0-SNAPSHOT")
  .dependsOn(core)

lazy val launcher = project
  .in(file("launcher"))
  .settings(
    commonSettings,
    name := "launcher",
    Compile / mainClass := Some("org.jvmgames.launcher.Launcher")
  )
  .dependsOn(core, numberGuesser, ticTacToe)

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "jvm-games",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version
  )
  .dependsOn(core, numberGuesser, launcher, ticTacToe)
