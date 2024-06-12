import com.indoorvivants.detective.Platform

lazy val BINARY_NAME = "sn-test"
lazy val common = Seq(
  scalaVersion := "3.5.0-RC1"
)

lazy val root = project.in(file(".")).aggregate(lib, bin)

lazy val lib =
  project
    .in(file("mod/lib"))
    .enablePlugins(ScalaNativePlugin)
    .settings(common)

lazy val bin = project
  .in(file("mod/bin"))
  .enablePlugins(ScalaNativePlugin)
  .dependsOn(lib)
  .settings(common)

lazy val buildDebugBinary = taskKey[File]("")
buildDebugBinary := {
  writeBinary(
    source = (bin / Compile / nativeLink).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "debug",
    log = sLog.value,
    platform = None
  )
}

lazy val buildReleaseBinary = taskKey[File]("")
buildReleaseBinary := {
  writeBinary(
    source = (bin / Compile / nativeLinkReleaseFast).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "release",
    log = sLog.value,
    platform = None
  )
}

lazy val buildPlatformBinary = taskKey[File]("")
buildPlatformBinary := {
  writeBinary(
    source = (bin / Compile / nativeLinkReleaseFast).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "release",
    log = sLog.value,
    platform = Some(Platform.target)
  )
}

def writeBinary(
    source: File,
    destinationDir: File,
    log: sbt.Logger,
    platform: Option[Platform.Target]
): File = {

  val name = platform match {
    case None => BINARY_NAME
    case Some(target) =>
      val ext = target.os match {
        case Platform.OS.Windows => ".exe"
        case _                   => ""
      }

      BINARY_NAME + "-" + ArtifactNames.coursierString(target) + ext
  }

  val dest = destinationDir / name

  IO.copyFile(source, dest)

  log.info(s"Binary [$name] built in ${dest}")

  dest
}
