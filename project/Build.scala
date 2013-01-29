import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "yabe"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.octo.captcha" % "jcaptcha" % "1.0",
      "mysql" % "mysql-connector-java" % "5.1.18"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
      resolvers += "JHlabs1" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/",
      resolvers += "JHlabs2" at "http://maven.jahia.org/maven2/"
    )

}
