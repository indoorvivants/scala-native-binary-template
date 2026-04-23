import com.indoorvivants.detective.Platform

lazy val common = Seq(
  scalaVersion := "3.8.3",
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
  .enablePlugins(ScalaNativePlugin, ForgeNativeBinaryPlugin)
  .dependsOn(lib)
  .settings(common)
  .settings(
    buildBinaryConfig ~= {_.withName("app")}
  )

