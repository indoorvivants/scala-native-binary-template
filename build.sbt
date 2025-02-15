import com.indoorvivants.detective.Platform

lazy val BINARY_NAME = "sn-test"
lazy val common = Seq(
  scalaVersion := "3.6.3",
  nativeConfig ~= { c =>
    import scala.scalanative.build.*
    c.withSourceLevelDebuggingConfig(SourceLevelDebuggingConfig.enabled)
  }
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

lazy val buildBinary = taskKey[File]("")
buildBinary := {
  writeBinary(
    source = (bin / Compile / nativeLink).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "debug",
    log = sLog.value,
    platform = None,
    debug = true
  )
}

lazy val buildReleaseBinary = taskKey[File]("")
buildReleaseBinary := {
  writeBinary(
    source = (bin / Compile / nativeLinkReleaseFast).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "release",
    log = sLog.value,
    platform = None,
    debug = false
  )
}

lazy val buildPlatformBinary = taskKey[File]("")
buildPlatformBinary := {
  writeBinary(
    source = (bin / Compile / nativeLinkReleaseFast).value,
    destinationDir = (ThisBuild / baseDirectory).value / "out" / "release",
    log = sLog.value,
    platform = Some(Platform.target),
    debug = false
  )
}

def writeBinary(
    source: File,
    destinationDir: File,
    log: sbt.Logger,
    platform: Option[Platform.Target],
    debug: Boolean
): File = {

  val name = platform match {
    case None => "app"
    case Some(target) =>
      val ext = target.os match {
        case Platform.OS.Windows => ".exe"
        case _                   => ""
      }

      BINARY_NAME + "-" + ArtifactNames.coursierString(target) + ext
  }

  val dest = destinationDir / name

  IO.copyFile(source, dest, CopyOptions.apply(true, true, true))

  import scala.sys.process.*

  if (debug && platform.exists(_.os == Platform.OS.MacOS))
    s"dsymutil $dest".!!

  log.info(s"Binary [$name] built in ${dest}")

  dest
}
