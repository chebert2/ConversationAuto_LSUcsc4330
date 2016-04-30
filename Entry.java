/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary  Description:  of this  Entry  class:
 * this class stores a simple object data entry for a preprocess filter of beginning text.
 */
package conversationauto1;
import java.util.*;

/**
 * Description.
 * 
 * stores input "determining" criteria for what multi word phrases can be condensed into a one word phrase.
 * and
 * stores output of the single word to be inserted instead.
 * 
 */

public class Entry {
    // the words phrase that needs/should ne shortened to something
    private String inputEntry;
    // the shortened phrase of this replacement activity.
    private String outputEntry;
    /**
     * 
     * @param firstInput  phrase that can be changed to something shorter
     * @param firstOutput  shortened term for output
     */
    public Entry(String firstInput, String firstOutput){
        inputEntry = firstInput;
        outputEntry = firstOutput;
    }
    /**
     * @Desc (for another class's use,) in printing all entries to a data text file for later use.
     * @return  String for documenting entry in text file
     */
     @Override
    public String toString(){
        return outputEntry +"/_/_" + inputEntry;
    }
    /**
     * @return String input element
     */
    public String getInputEntry(){
        return inputEntry;
    }
    /**
     * @return String output element
     */
    public String getOutputEntry(){
        return outputEntry;
    }
    
}
