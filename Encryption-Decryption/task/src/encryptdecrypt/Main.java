package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String op = "enc";
        String s = "";
        int key = 0;
        String fileNameIn = "";
        String fileNameOut = "";
        String resultString;
        String sourceString;
        String alg = "shift"; // stage 6
        for (int i = 0; i < args.length; i++) {
            if ("-mode".equals(args[i])) {
                op = args[i + 1];
            }
            else if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            }
            else if ("-out".equals(args[i])) {
                fileNameOut = args[i + 1];
            }
            else if ("-in".equals(args[i])) {
                fileNameIn = args[i + 1];
            }
            else if ("-data".equals(args[i])) {
                s = args[i + 1];
            }
            else if ("-alg".equals(args[i])) { // stage 6
                alg = args[i + 1];
            }
        }
        sourceString = getSourceString(s, fileNameIn);
        resultString = processing(sourceString, key, op, alg);
        giveOutResult(fileNameOut, resultString);
    }

    private static String getSourceString(String s, String fileNameIn) {
        if (!"".equals(fileNameIn)) {
            File file = new File(fileNameIn);
            try (Scanner scan = new Scanner(file)) {
                return scan.nextLine();
            } catch (FileNotFoundException e) {
                System.out.printf("Error. An exception occurs %s", e.getMessage());
            }
        }
        return s;
    }

    private  static void giveOutResult(String fileNameOut, String result) {
        if (!"".equals(fileNameOut)) {
            File file = new File(fileNameOut);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(result);
            } catch (IOException e) {
                System.out.printf("Error. An exception occurs %s", e.getMessage());
            }
        } else {
            System.out.println(result);
        }
    }

    private static String processing(String s, int key, String operation, String alg) {
        if (operation.equals("enc") && alg.equals("unicode")) {
            return (encriptUnicode(s, key));
        } else if (operation.equals("dec") && alg.equals("unicode")) {
            return (decriptUnicode(s, key));
        } else if (operation.equals("dec") && alg.equals("shift")) {
            return (decriptShift(s, key));
        } else if (operation.equals("enc") && alg.equals("shift")) {
            return (encriptShift(s, key));
        } else {
            return "Error in operation mode argument or algorithm mode argument. It must be \"enc\" or \"dec\"";
        }
    }


    private static String decriptUnicode(String s, int key) {
        int l = s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
                int ca = c - key;
                c = (char) ca;
                sb.append(c);
        }
        return sb.toString();
    }

    private static String encriptUnicode(String s, int key) {
        int l = s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
                int ca = c + key;
                c = (char) ca;
                sb.append(c);
        }
        return sb.toString();
    }

    private static String encriptShift(String s, int key) {
        int l = s.length();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String aLPPHABET = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (alphabet.indexOf(c) != -1) {
                sb.append(alphabet.charAt((alphabet.indexOf(c) + key) % 26));
            } else if (aLPPHABET.indexOf(c) != -1) {
                sb.append(alphabet.charAt((alphabet.indexOf(c) + key) % 26));
            } else {
                sb.append(c);
            }
        }
         return sb.toString();
    }

    private static String decriptShift(String s, int key) {
        int l = s.length();
        String alphabet = "zyxwvutsrqponmlkjihgfedcba";
        String aLPPHABET = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (alphabet.indexOf(c) != -1) {
                sb.append(alphabet.charAt((alphabet.indexOf(c) + key) % 26));
            } else if (aLPPHABET.indexOf(c) != -1) {
                sb.append(alphabet.charAt((alphabet.indexOf(c) + key) % 26));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
