/*
 * Developer: Don Manula Ransika Udugahapattuwa
 * Student ID: C3410266
 * Program Name: PlainTextCredentialsChk.java
 * Description: Program Finds any plaintext credentials that can be a security vulnerability.
 * Outputs the method where the plaintext credentials are found, along with the credentials.
 * Claim: Covers the security of the code by defending the program against credentials theft
 * through plain text credentials vulnerability.
 * Protects from Unauthorized Access, Credential Theft, Compromised Version Control Systems,
 * Insider Threats, Lack of Accountability, Compliance Violation:PCI DSS, HIPAA
 */

package com.SENG4430.PlainTextCredentials;
import java.util.*;
import java.util.regex.*;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

public class PlainTextCredentialsChk {

    private final LinkedList<Map.Entry<String, List<String>>> classPlainTextCredentialsChk;

    public PlainTextCredentialsChk() {
        classPlainTextCredentialsChk = new LinkedList<>();
    }

    public void PlainTextCredentialsChk(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            List<String> creds = findCredentialsInClass(classObject);
            if (!creds.isEmpty()) {
                classPlainTextCredentialsChk.add(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), creds));
            }
        }
    }

    public LinkedList<Map.Entry<String, List<String>>> getClassPlainTextCredential() {
        return classPlainTextCredentialsChk;
    }

    public List<String> findCredentialsInClass(CtClass<?> classObject) {
        List<String> creds = new ArrayList<>();
        String sourceCode = classObject.toString();
        Matcher matcher = Pattern.compile("\\b((api|key|token|password|secret)_?(key|token|phrase|pwd)?|access_?(key|token|phrase)|security_?(key|token))\\b\\s*=\\s*[\"'][^\"']*[\"']").matcher(sourceCode);
        while (matcher.find()) {
            creds.add(matcher.group());
        }
        return creds;
    }
}


