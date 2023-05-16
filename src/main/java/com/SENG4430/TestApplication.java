/*
 * Developer: Akshata Dhuraji
 * Program Name: TestApplication
 * Description: Test Application class , reads 5 inputs at runtime in the form of arguments:
 * 1) path/file name of the program on which various metrics should be run
 * 2) -m denotes the metrics names
 * 3) metrics name , it can have single or multiple metrics names in the user defined order
 * 4) -t denotes the result option
 * 5) cmd denotes print on command line
 * The input is validated and listed metrics functionalities are invoked.
 * Results of the various metrics are printed in the same order via passing the results to print package
 */
package com.SENG4430;

import com.SENG4430.ClassCoupling.ClassCouplingList;
import com.SENG4430.Comments.CommentsList;
import com.SENG4430.Fan.FanInOutList;
import com.SENG4430.FogIndex.FogIndexList;
import com.SENG4430.HalsteadComplexity.HalsteadComplexityList;
import com.SENG4430.PlainTextCredentials.PlainTextCredentialsList;
import com.SENG4430.Print.commandLinePrintResults;

import com.SENG4430.ResponseForClass.ResponseForClassList;
import com.SENG4430.WeightedMethods.WeightedMethodsList;
import org.apache.commons.cli.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import java.util.*;
public class TestApplication {
    private static Launcher launcher;   // launchers used by metrics
    static LinkedList<MetricsList> metricLists; // list of all metrics
    static  LinkedList<TestResult> testresults; //list all metrics results

    //TO DO: Based on the feedback received enhance the below code to allow metrics to be
    // to be routed to an output file and output file to be displayed
    public static void main(String[] args ) {
        if (args.length < 3) {
            System.out.println("Error: Please give correct Arguments");
            System.out.println("Correct Arguments should be: SourceFileOrDirectory -m metric-flag -r result-flag");
            System.exit(1);
        }
        launcher= processArgs(args); // process arguments to read arguments, create launcher, create metrics and outputs
        executeMetrics(launcher);                        // execute each metrics measurement
        LinkedList<String> metricResults = getResults(); // get results from metrics
        create(metricResults);                          // pass metric results for printing it
    }

    public static Launcher processArgs(String[] args) {
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

        launcher = new Launcher();             // create a Spoon Launcher
        launcher.addInputResource(args[0]);    // set the input file
        CtModel model = launcher.buildModel(); // build the Spoon model
        boolean hasComments = false;
        for (CtElement element : model.getElements(e -> true)) {
            if (element.getComments() != null) {
                hasComments = true;
                break;
            }
        }
        launcher.getEnvironment().setCommentEnabled(hasComments);
        return launcher;
    }
    //helper method for the catch block, it runs only if parse exception is encountered
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("myprogram", options);
    }

    // create the corresponding metric list for each metric passed
    public static void createMetrics(String[] metricDefinitions) {
        metricLists = new LinkedList<>();
        for (String mdefinition : metricDefinitions) {
            String[] arr = mdefinition.split(" ");
            for (int i=0; i< arr.length; i++) {
                MetricsList userSelectedMetrics;
                //Metrics list offered by the test application
                if (arr[i].equals("fog_index")) {
                    userSelectedMetrics = new FogIndexList(Arrays.copyOfRange(arr, 1, arr.length));
                } else if (arr[i].equals("halstead_complexity")) {
                    userSelectedMetrics = new HalsteadComplexityList(Arrays.copyOfRange(arr, 1, arr.length));
                }else if (arr[i].equals("weighted_methods")) {
                    userSelectedMetrics = new WeightedMethodsList(Arrays.copyOfRange(arr, 1, arr.length));
                }else if (arr[i].equals("plaintext_credentials")) {
                    userSelectedMetrics = new PlainTextCredentialsList(Arrays.copyOfRange(arr, 1, arr.length));
                }
                else if (arr[i].equals("class_coupling")) {
                    userSelectedMetrics = new ClassCouplingList(Arrays.copyOfRange(arr, 1, arr.length));
                }
                else if (arr[i].equals("rfc")) {
                    userSelectedMetrics = new ResponseForClassList(Arrays.copyOfRange(arr, 1, arr.length));
                }else if (arr[i].equals("fanin_fanout")) {
                    userSelectedMetrics = new FanInOutList(Arrays.copyOfRange(arr, 1, arr.length));
                }else if (arr[i].equals("comments")) {
                    userSelectedMetrics = new CommentsList(Arrays.copyOfRange(arr, 1, arr.length));
                } else {
                    throw new IllegalArgumentException("Invalid " + arr[i] + " metrics argument");
                }
                metricLists.add(userSelectedMetrics);
            }
        }
    }
    public static void executeMetrics(Launcher launcher) {
        for (MetricsList userSelectedMetrics : metricLists) {
            userSelectedMetrics.execute(launcher);
        }
    }
    public static LinkedList<String> getResults() {
        LinkedList<String> results = new LinkedList<String>();
        // sort the metric trackers, this is done so that system testing
        // has same order of the metrics output
        Collections.sort(metricLists, Comparator.comparing(o -> o.getClass().toString()));
        for (MetricsList userSelectedMetrics : metricLists) {
            results.add(userSelectedMetrics.toJson());
        }
        return results;
    }

    public static void testResults (String[] testResultsDtl) {
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
    //TO DO: Enhance the program to take input as multiple metrics
    //for this print program is already enhanced to have linkedlist
    public static void create(LinkedList<String> resultlist) {
        Iterator<TestResult> iterator = testresults.iterator();
        while (iterator.hasNext()) {
            TestResult result = iterator.next();
            result.create(resultlist);;
        }
    }
}
