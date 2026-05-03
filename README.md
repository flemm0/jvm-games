# JVM Games

A small collection of JVM console games built with Scala 3, Java, sbt, and
Cats Effect.

The launcher currently includes:

- Number Guesser, implemented in Java
- Number Guesser, implemented in Scala
- Tic Tac Toe, implemented in Java
- Tic Tac Toe, implemented in Scala

## Project Layout

```text
core/                  Shared game interface
games/number-guesser/  Java and Scala number guessing games
games/tic-tac-toe/     Java and Scala Tic Tac Toe games
launcher/              Console menu for choosing and running games
```

## Requirements

- JDK 17 or newer
- sbt 1.12.8
- Scala 3.8.2

This repository includes a `.sdkmanrc` for local SDKMAN users:

```sh
sdk env
```

## Run

Start the launcher:

```sh
make run
```

Or run the sbt task directly:

```sh
sbt launcher/run
```

## Build And Test

Compile the project:

```sh
sbt compile
```

Run the same test set used by CI:

```sh
sbt core/test numberGuesser/test ticTacToe/test launcher/test root/test
```

Check formatting:

```sh
sbt scalafmtSbtCheck scalafmtCheckAll
```

Apply formatting:

```sh
sbt scalafmtSbt scalafmtAll
```

## Adding A Game

1. Create a new game module under `games/`.
2. Implement `org.jvmgames.core.Game`.
3. Add the module to `build.sbt`.
4. Register the game in `launcher/src/main/scala/Launcher.scala`.
