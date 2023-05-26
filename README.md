-----------------------------------
Readme for Flightpub 2.0

In order to run flighpub2.0 install/configure maven and java 17 in IDE of choice.

In the IDE menu for Run/Debug Configurations

Run clean compile from maven options

The program takes following arguments as program parameters in the form of compile time arguments

1) Absolute Filepath/filename e.g.,"C:\Users\Dell\Akshata\SENG4430\Software-Quality"

2) -m option list the matrices which the user wants to run, it can be a single metric or multiple metrics or all metrics at one go e.g., "fog_index halstead_complexity" or "all" respectively.

3) -r option list the output option and is routed to command prompt or file. Below is the example of runtime arguments. This can be either "cmd" or "file".

   "C:\Users\Dell\Akshata\SENG4430\Software-Quality" "-m" "fog_index halstead_complexity" "-r" "cmd"

There is no need for program parameters to execute the test folder.
