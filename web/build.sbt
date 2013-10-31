name := "web"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

playJavaSettings

compile in Test <<= PostCompile(Test)
