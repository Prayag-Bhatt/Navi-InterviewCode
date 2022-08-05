# How to run the code

 * Maven, Java11 required
 * `mvn clean install -DskipTests assembly:single -q` - This will create a jar file `geektrust.jar` in the `target` folder.
 * Make sure that sample_input/input1.txt file is exits
 * `java -jar target/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument
 * Or Just run the `run.bat` file on windows or `run.sh` file on Mac

 # How to execute the unit tests

* `mvn clean test` will execute the unit test cases.
* Unit test depend on input1.txt file, so it will break if the file content changed manually
* Make changes to input2.txt if you want to test for manually

