package com.SENG4430;

import com.SENG4430.FogIndex.FogIndexList;
import com.SENG4430.Print.commandLinePrintResults;

import org.apache.commons.cli.*;
import spoon.Launcher;
import java.util.*;
public class TestApplication {
    private static Launcher launcher;   // launchers used by metrics
    private static LinkedList<MetricsList> metricLists; // list of all metrics
    private static  LinkedList<TestResult> testresults; //list all metrics results

    public static void main(String[] args ) {
        if (args.length < 4) {
            System.out.println("Error: Please give correct Arguments");
            System.out.println("Correct Arguments should be: SourceFileOrDirectory -m metric-flag -r result-flag");
            System.exit(1);
        }
        processArgs(args); // process arguments to read arguments, create launcher, create metrics and outputs
        executeMetrics(launcher);                        // execute each metrics measurement
        LinkedList<String> metricResults = getResults(); // get results from metrics
        create(metricResults);                          // pass metric results for printing it
    }

    private static void processArgs(String[] args) {
        // Add command line options
        Options opt = new Options();
        Option metricOption = new Option("m", true, "metrics name");
        Option resultOption = new Option("r", true, "result on commandline");
        opt.addOption(metricOption);
        opt.addOption(resultOption);
        // parse options
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(opt, args);
        } catch (ParseException e) {
            System.err.println("Error parsing command line options: " + e.getMessage());
            printHelp(opt);
            System.exit(1);
        }

        // create metrics based on the runtime input received as -m
        createMetrics(cmd.getOptionValues("m"));

        // If no result options are given, default result option is cmd
        String[] resultOptions = cmd.getOptionValues("r");
        if (resultOptions == null) resultOptions = new String[] {"cmd"};
        testResults(resultOptions);

        launcher = new Launcher();
        launcher.addInputResource(args[0]);
        launcher.buildModel();
    }
    //helper method for the catch block, it runs only if parse exception is encountered
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("myprogram", options);
    }

    // create the corresponding metric list for each metric passed
    private static void createMetrics(String[] metricDefinitions) {
        metricLists = new LinkedList<>();
        for (String mdefinition : metricDefinitions) {
            String[] arr = mdefinition.split(" ");
            MetricsList mlist;
            //Metrics list offered by the test application
            if (arr[0].equals("fog_index")) {
               // FogIndexList mlist;
                mlist = new FogIndexList(Arrays.copyOfRange(arr, 1, arr.length));
                metricLists.add(mlist);
            }else if (arr[0].equals("halstead_complexity")) {
              //  mlist = new HalsteadComplexityList(Arrays.copyOfRange(arr, 1, arr.length));
            } else {
                throw new IllegalArgumentException("Invalid " + arr[0] + " metrics argument");
            }
        }
    }
    private static void executeMetrics(Launcher launcher) {
        for (MetricsList mlist : metricLists) {
                mlist.execute(launcher);
        }
    }
    private static LinkedList<String> getResults() {
        LinkedList<String> results = new LinkedList<String>();
        // sort the metric trackers, this is done so that system testing
        // has same order of the metrics output
        Collections.sort(metricLists, Comparator.comparing(o -> o.getClass().toString()));
        for (MetricsList mlist : metricLists) {
            results.add(mlist.toJson());
        }
        return results;
    }

    private static void testResults (String[] testResultsDtl) {
        testresults = new LinkedList<>();
        int i = 0;
        while (i < testResultsDtl.length){
            String def = testResultsDtl[i];
            String[] arr = def.split(" ");
            TestResult testresult = null;

            if (arr[0].equals("cmd")) {
                testresult = new commandLinePrintResults(Arrays.copyOfRange(arr, 1, arr.length));
            }
            else {
                throw new IllegalArgumentException("Invalid " + arr[0] + " Result");
            }

            testresults.add(testresult);
            i++;
        }
    }
    private static void create(LinkedList<String> resultlist) {
        Iterator<TestResult> iterator = testresults.iterator();
        while (iterator.hasNext()) {
            TestResult result = iterator.next();
            result.create(resultlist);;
        }
    }
}
