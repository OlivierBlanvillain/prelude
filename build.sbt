scalaVersion       in ThisBuild := "2.12.0"
crossScalaVersions in ThisBuild := Seq("2.11.8", "2.12.0")
scalacOptions      in ThisBuild := Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Yno-imports",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard")

lazy val root = project.in(file("."))
  .aggregate(preludeJS, preludeJVM)
  .settings(noPublishSettings: _*)

lazy val preludeJS  = prelude.js
lazy val preludeJVM = prelude.jvm
lazy val prelude    = crossProject
  .crossType(CrossType.Pure).in(file("."))
  .settings(publishSettings: _*)

lazy val publishSettings = Seq(
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  organization := "in.nvilla",
  licenses := Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  homepage := Some(url("https://github.com/OlivierBlanvillain/prelude")),
  publishMavenStyle := true,
  publishTo := {
    if (isSnapshot.value) Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
    else                  Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
  },
  pomExtra := {
    <scm>
      <url>git@github.com:OlivierBlanvillain/prelude.git</url>
      <connection>scm:git:git@github.com:OlivierBlanvillain/prelude.git</connection>
    </scm>
    <developers>
      <developer>
        <id>OlivierBlanvillain</id>
        <name>Olivier Blanvillain</name>
        <url>https://github.com/OlivierBlanvillain/</url>
      </developer>
    </developers>
  }
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)
