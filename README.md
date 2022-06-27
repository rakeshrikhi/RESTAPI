# RESTAPI
RESTAPI Experimentation

Introduction--
This is a sample implementation of Access and Performance Testing framework for REST APIs.
I have taken Kraken exchange's public APIs as an example

Dependencies--
Java 1.8 or higher
Apache Http Components library
Maven dependencies are provided in the pom.xml file
Docker file is also provided- but not tested

Command-
java -jar client-rest-0.0.1-SNAPSHOT-jar-with-dependencies.jar

HighLevelDesign-
The UML Class diagram of the package is available in png file at top folder
The coding statergy used is Prototyping model i.e. making a runnable code asap, tuning it and then making the design extensible
At the core code uses Java ExectorService (with configurable number of concurrent clients) to collect the performance metrices
Git checkins are provided as the solution statergy evoloved
First, getting the http client work
then, looking for perf enchancements of the core logic
Lastly, making the code extensible so that more APIs could be coded

UnitTesting-
Junit Tests cases are available in test package
cases are testing for performance threasholds
these can be invoked through a CI package to asset the health of the APIs


Next Steps/Enhacements-
Getting familiar with Karken APIs
Putting constant variables in the property files
Storing the metrices in files/db, so that historical analysis could be performed
