scalaVersion := "2.13.5"
name := "script"
enablePlugins(JavaAppPackaging)


libraryDependencies += "org.typelevel" %% "cats-effect" % "3.1.0"
libraryDependencies += "co.fs2" %% "fs2-io" % "3.0.1"
libraryDependencies += "org.http4s" %% "http4s-ember-server" % "1.0.0-M21"
libraryDependencies += "org.http4s" %% "http4s-circe" % "1.0.0-M21"
libraryDependencies += "org.http4s" %% "http4s-dsl" % "1.0.0-M21"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "io.chrisdavenport" %% "shellfish" % "0.0.5"