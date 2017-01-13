SET /P project_name= Enter the project name:
@echo off
mkdir %project_name%
mkdir .\%project_name%\lib
mkdir .\%project_name%\project

mkdir .\%project_name%\src
mkdir .\%project_name%\src\main
mkdir .\%project_name%\src\resources
mkdir .\%project_name%\src\scala
      
mkdir .\%project_name%\test
mkdir .\%project_name%\test\java
mkdir .\%project_name%\test\resources
mkdir .\%project_name%\test\scala
      
mkdir .\%project_name%\target

echo name := "%project_name%" >> .\%project_name%/build.sbt
echo. >> .\%project_name%\build.sbt
echo version := "1.0" >> .\%project_name%\build.sbt
echo. >> .\%project_name%\build.sbt
echo scalaVersion := "2.11.8" >> .\%project_name%\build.sbt

echo. >> .\%project_name%\src\main\%project_name%.scala