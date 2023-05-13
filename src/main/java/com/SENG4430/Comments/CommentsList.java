package com.SENG4430.Comments;
import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class CommentsList extends MetricsList {

    private final CommentsChk commentChk;

    public CommentsList(String[] args) {
        commentChk = new CommentsChk();
    }

    /**
     * Runs the analysis
     */
    @Override
    public void execute(Launcher launcher) {
        commentChk.commentChkLauncher(launcher);
    }


    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        //jsonBuilder.append("\t\t},\n");
        jsonBuilder.append("\n");

        // Add the Plain Text Credentials object to the JSON string
        LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> mapList = commentChk.getComments();
        if (mapList.isEmpty()) {
            jsonBuilder.append("Comment Listing\": \"No classes to traverse\"\n");
        } else {
            jsonBuilder.append("Comment Listing: \n");
            String className;
            for (Map.Entry<String, LinkedList<Map<String, Integer>>> entry : mapList) {
                className = entry.getKey();
                jsonBuilder.append("Class - \"").append(className).append("\": \n");
                int select = 0;
                for(Map<String, Integer> methodEntry: entry.getValue()){
                    Set<String> methods = methodEntry.keySet();
                    if(select==0){
                        jsonBuilder.append("\t Class Comments: (ClassName:Value) \n");
                        select++;
                    }else{
                        jsonBuilder.append("\n\t Method Comments: (MethodName:Value) \n");
                    }
                    int iter=0;
                    for(String method:methods){
                        jsonBuilder.append("\t\t").append(method).append(":").append(methodEntry.get(method)).append("\t");
                        iter++;
                        if(iter%5==0){
                            jsonBuilder.append("\n");
                        }
                    }
                    jsonBuilder.append("\n");
                }
                jsonBuilder.append("\n");
            }
        }
        return jsonBuilder.toString();
    }


}