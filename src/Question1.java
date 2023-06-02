import java.io.File;
import java.util.Scanner;

public class Question1 {
    /*DO NOT ALTER GLOBAL VARS */
    private static char[] text = null;          //The string that you are searching in.

    private static char[] pattern = null;       //The string that you are searching for.

    /*Do not change this method*/
    public static void main(String[] args) {
        readFromFile("comparison.txt");
        System.out.println(BoyerMoore());
        System.out.println(KnuttMorrisPratt());
    }


    /*Your implementation
    * Uses the Boyer Moore algorithm to determine the starting index of the first occurence of pattern (global char[]) in text (global char[])
    * If pattern string can't be found in text string then the function returns -1.*/
    private static int BoyerMoore() {
        int textLength = text.length;
        int patternLength = pattern.length;

        //instantiate bad character array with the size of 256 with the 256 being the number of characters
        int badCharacterArray[] = new int[256];

        //initialise occurrences
        for (int i = 0; i < badCharacterArray.length; i++){
            badCharacterArray[i] = -1;
        }
        //save the value of a characters last occurrence
        for (int i = 0; i < patternLength; i++){
            badCharacterArray[(int) pattern[i]] = i;
        }

        int index = 0;
        //if index is greater than textLength - patternLength possibility of finding an alignment ceases to exist
        while(index <= (textLength - patternLength))
        {
            int j = patternLength-1;

            //keep subtracting j by one if character of pattern and text are the same
            while(j >= 0 && pattern[j] == text[index+j]) {
                j=j-1;
            }
            //if j is less than zero then it's a match return index
            if (j < 0)
            {
                return index;
            } else{
                index =index+ Math.max(1, j - badCharacterArray[text[index+j]]);
            }
        }
        return -1;
    }
    /*Your implementation
     * Uses the Knutt Morris Pratt algorithm to determine the starting index of the first occurence of pattern (global char[]) in text (global char[])
     * If pattern string can't be found in text string then the function returns -1.*/
    private static int KnuttMorrisPratt() {
        int patternLength = pattern.length;
        int textLength = text.length;

        // stores patterns longest prefix suffix values
        int longestPrefixSuffix[] = new int[patternLength];
        //j is index of pattern
        int j = 0;

        // previous longestprefixsuffix length
        int length = 0;
        int k = 1;
        longestPrefixSuffix[0] = 0;

        //stores longestPrefixSuffix[k] when k=1 to patternLength
        while (k < patternLength) {
            if (pattern[k] == pattern[length]) {
                length=length+1;
                longestPrefixSuffix[k] = length;
                k=k+1;
            }
            else
            {
                if (length != 0) {
                    length = longestPrefixSuffix[length - 1];
                }
                else
                {
                    longestPrefixSuffix[k] = length;
                    k=k+1;
                }
            }
        }
        //i is the text index
        int i = 0;
        while ((textLength - i) >= (patternLength - j)) {
            if (pattern[j] == text[i]) {
                j=j+1;i=i+1;
            }
            if (j == patternLength) {
                return (i - j);
            }

            else if (i < textLength && pattern[j] != text[i]) {
                if (j != 0) { j = longestPrefixSuffix[j - 1];}
                else {i = i + 1;}
            }
        }
        return -1;
    }

    /*Do not change this method. Reads text and pattern from text file (fileName) into appropriate global vars.*/
    private static void readFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            text = scanner.nextLine().trim().toCharArray();
            pattern = scanner.nextLine().trim().toCharArray();
        } catch (Exception e) {
            System.out.println("Could not load file");
        }
    }


}
