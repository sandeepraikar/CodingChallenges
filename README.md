xAd Coding Challenge

Prerequisites:
JDK 1.8 version
Maven build tool

How to build this application?
This app uses Maven to build the jar file. The following commands will build this application along with the dependent jars, class files in the target jar file.

1. mvn clean
2. mvn package assembly: single

How to run this application?
The jar file generated from the above step (xad-coding-challenge-0.0.1-SNAPSHOT-jar-with-dependencies.jar) should be placed in the same directory where the input files are present ("in" folder)

Configuration options:
1. This app runs with the default thread pool size of 5, but can run with user defined thread pool size with the -p option [Example: -p 15]
2. The input folder location has been made configurable, which can be changed from the config.properties provided.
3. The default thread pool size can also be changed from the config.properties (xad.etl.default.thread.pool.size)


