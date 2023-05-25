# Software-Quality
New Repo for SENG4430 because the old one got really confusing

To run the program, pass this paramter as compile time argument: "D:\Software Quality\testResult.java" "-m" "halstead_complexity" "-r" "cmd"

To display results with multiple parameters: "D:\Software Quality\testResult.java" "-m" "fog_index halstead_complexity" "-r" "cmd"

-----------------------------------
ReadMe for Assignment Submission

In order to run flighpub2.0 install/configure maven in IDE of choice.

In the IDE menu for Run/Debug Configurations

Run clean compile from maven options

The program takes following arguments 

1) Filepath/filename e.g.,"D:\Software Quality\testResult.java"

2) -m option list the matrices which the user wants to run, it can be a single metrice or multiple metrices at one go e.g., "fog_index halstead_complexity"

3) -r option list the output option by default output is routed to command prompt. below is the example of runtime arguments

   "D:\Software Quality\testResult.java" "-m" "fog_index halstead_complexity" "-r" "cmd"

``
java ./target/classes/com/SENG4430/TestApplication "C:\Users\danie\IdeaProjects\Software-Quality\src\main\java\com\SENG4430" "-m" "fog_index halstead_complexity" "-r" "cmd"
``
