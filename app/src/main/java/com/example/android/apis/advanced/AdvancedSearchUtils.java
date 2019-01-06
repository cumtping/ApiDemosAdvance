package com.example.android.apis.advanced;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for code searching.
 */
public class AdvancedSearchUtils {
    /** Log tag. */
    private static final String TAG = AdvancedSearchUtils.class.getSimpleName();

    /**
     * Find files
     *
     * @param baseDirName    Folder name
     * @param targetFileName File name to search
     * @param retList        Result list
     */
    public static void findFiles(String baseDirName, String targetFileName, List<File> retList) {
        String tempName;
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            Log.e(TAG, "findFiles, wrong folder.");
        } else {
            String[] fileList = baseDir.list();
            for (int i = 0; i < fileList.length; i++) {
                File readFile = new File(baseDirName + "/" + fileList[i]);
                // Log.i(TAG, "Searching: " + readFile.getPath());
                if (!readFile.isDirectory()) {
                    tempName = readFile.getName();
                    if (wildcardMatch(targetFileName, tempName)) {
                        retList.add(readFile.getAbsoluteFile());
                    }
                } else if (readFile.isDirectory()) {
                    findFiles(baseDirName + "/" + fileList[i], targetFileName, retList);
                }
            }
        }
    }

    /**
     * Wild card match.
     *
     * @param pattern Match pattern
     * @param str     String to be matched
     *
     * @return true is match.
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                strIndex++;
                if (strIndex > strLength) {
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

    /**
     * Get file content.
     *
     * @param file File.
     *
     * @return File content.
     */
    public static String getFileContent(File file) {
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            temp = br.readLine();
            int lineNumber = 1;
            while (temp != null) {
                // Append line number and return line.
                sb.append(lineNumber++).append(" ").append(temp).append("\n");
                temp = br.readLine();
            }
        } catch (IOException e) {
            Log.e(TAG, "getFileContent, Exception.", e);
        }
        return sb.toString();
    }

    public static Map<Integer, String> getMethodInfo(String codeContent) {
        Map<Integer, String> methodMap = new HashMap<>();
        String[] lines = codeContent.split("\n");

        return methodMap;
    }
}
