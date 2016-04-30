/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   textLumpPhrasePreprocessor  class:
 * This class, with the use of a couple other classes, changes given multi word
 * phrases into a single term.  It helps keep concepts compact.
 */
package conversationauto1;

//import java.io.FileWriter;
import java.io.*;
import java.util.*;

/**
 * Description:
 * this is an executing class (for the phrase object class entryPreprocessReplace). 
 * It makes an instance of entryPreprocessReplace object and feeds it a phrase String.
 * 
 * The purpose is to change given multi word phrases into a single term.
 * 
 */


public class textLumpPhrasePreprocessor {
    
    /**
     * 
     * @param inputString String of raw initial input sentence
     * @return String of simplified sentence after preprocessing.
     */
    
    public static String textLumpPhrasePreprocessor(String inputString) {
        
        entryPreprocessReplace newArrayOfCriteriaTransform = new entryPreprocessReplace();

        // example adding of a preprocess item to be transformed
        //newArrayOfCriteriaTransform.addEntry("entity//chained input");

        /**
         * read in data file that gives all recorded phrases for possible simplification
         */
        InputStream is = null;
        BufferedReader bfReader = null;
        try {
            is = new FileInputStream("inputEntry.txt");
            bfReader = new BufferedReader(new InputStreamReader(is));
            String temp = null;
            
            while ((temp = bfReader.readLine()) != null) 
                newArrayOfCriteriaTransform.addEntry(temp);
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        // deals with preliminary sentence, without any simplification,

        Iterator<Entry> preprocessItemCriteria = newArrayOfCriteriaTransform.getArrayList().iterator();
        //make new Entry for each preprocess replacement operation
        Entry iterimEntrySample = null;
        // sift through all entries of item phrases to be replaced with an output term of simplification.
        while (preprocessItemCriteria.hasNext()) {
            iterimEntrySample = preprocessItemCriteria.next();
            if (inputString.toLowerCase().contains(iterimEntrySample.getInputEntry())) {
                inputString = inputString.replace(iterimEntrySample.getInputEntry(), iterimEntrySample.getOutputEntry());
            }
        }

        return inputString;
    }
}
