package com.SENG4430.PlainTextCredentials;
import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlainTextCredentialsList extends MetricsList {

    private final PlainTextCredentialsChk plainTextCredentialsChk;

    public PlainTextCredentialsList(String[] args) {
        plainTextCredentialsChk = new PlainTextCredentialsChk();
    }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) {
        plainTextCredentialsChk.PlainTextCredentialsChk(launcher);
    }


    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("\t\t},\n");
        jsonBuilder.append("\n");

        // Add the Plain Text Credentials object to the JSON string
        LinkedList<Map.Entry<String, List<String>>> plainTextCredentials = plainTextCredentialsChk.getClassPlainTextCredential();
        if (plainTextCredentials.isEmpty()) {
            jsonBuilder.append("Plain Text Credentials\": \"No plain text credentials found. Safe to proceed.\"\n");
        } else {
            jsonBuilder.append("Plain Text Credentials\": {\n");
            String className = null;
            for (Map.Entry<String, List<String>> entry : plainTextCredentials) {
                if (!entry.getKey().equals(className)) {
                    className = entry.getKey();
                    jsonBuilder.append("\t\t\"" + className + "\": {\n");
                }
                jsonBuilder.append("\t\t\t\"" + entry.getValue() + "\"");
                if (plainTextCredentials.indexOf(entry) != plainTextCredentials.size() - 1) {
                    jsonBuilder.append(",\n");
                }
            }
        }
        return jsonBuilder.toString();
    }

//String password = "test6";
}
