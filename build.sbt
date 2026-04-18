val scala3Version = "3.8.2"
val java25Amzn = file(sys.props("user.home")) / ".sdkman" / "candidates" / "java" / "25.0.2-amzn"

lazy val commonSettings = Seq(
  scalaVersion := scala3Version,
  javaHome := Some(java25Amzn),
  javacOptions  ++= Seq("--release", "17"),
  scalacOptions ++= Seq("-deprecation", "-feature", "-Wnonunit-statement"),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.7.0"
  )
)

lazy val core = project.in(file("core"))
  .settings(commonSettings)

lazy val numberGuesser = project
  .in(file("games/number-guesser"))
  .settings(commonSettings,
    name := "number-guesser",
    version := "0.1.0-SNAPSHOT"
  )
  .dependsOn(core)

lazy val ticTacToe = project
  .in(file("games/tic-tac-toe"))
  .settings(commonSettings,
    name := "tic-tac-toe",
    version := "0.1.0-SNAPSHOT"
  )
  .dependsOn(core)

lazy val launcher = project.in(file("launcher"))
  .settings(
    commonSettings,
    name := "launcher",
    Compile / mainClass := Some("org.jvmgames.launcher.Launcher")
  )
  .dependsOn(core, numberGuesser, ticTacToe)

lazy val root = project
  .in(file("."))
  .settings(
    name := "jvm-games",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.4" % Test
  )
  .dependsOn(core, numberGuesser, launcher, ticTacToe)
