/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 * Description:  of this    entryPreprocessReplace class:
 * 
 * this makes an ArrayList of Entry objects for holding String input and output
 * entries of multi word phrases and their related condensation to a single term
 * output.
 */
package conversationauto1;
import java.io.FileWriter;
import java.io.*;
import java.util.*;

/**
 * Description:
 * this makes an ArrayList of Entry objects for holding String input and output
 * entries of multi word phrases and their related condensation to a single term
 * output.
 */

public class entryPreprocessReplace {
    
    private ArrayList<Entry> givenCriteriaList;
    
    // a temporary String variable for getting the input and output.
    private String[] SplitParseRawEntry;
    private Entry exploreIfDuplicate;
    // a temporay variable to examine if an entry
    // being added to givenCriteriaList
    // might be a duplicate.
    private String newExplItem;
    
    // a constructor
    public entryPreprocessReplace(){
        givenCriteriaList = new ArrayList();
    }
    
    /**
     * @Desc Description: Add a entry representing a related input:multiword phrase ,
     *  and appropriate output: single word.
     * @param compositeInputLine String of input muliwordPhrase input and single phrase output criteria 
     */
    public void addEntry(String compositeInputLine){
        // an object iterator to flip through this arraylist
        Iterator testIterator = givenCriteriaList.iterator();
        
        boolean duplicatePresent = false;
        // split this method's input parameter where  the object Entry's two
        // elements are between the "/_/_"
        SplitParseRawEntry = compositeInputLine.split("/_/_");
        // a simple precaution to check if there are two elements in the temporary
        // String array.
        if(SplitParseRawEntry.length > 1){
            exploreIfDuplicate = new Entry(SplitParseRawEntry[1], SplitParseRawEntry[0]);
            while (testIterator.hasNext()) {
                //check if duplicate exists
                if(testIterator.hasNext()){
                   newExplItem = testIterator.next().toString();
                   if(newExplItem.equals(exploreIfDuplicate.toString())){
                       System.out.println("problem: entry already exists!");
                    duplicatePresent = true;
                    break;
                   }
                }
            }
            if(!duplicatePresent)
                givenCriteriaList.add(new Entry(SplitParseRawEntry[1], SplitParseRawEntry[0]));
            
        }
        else
            System.out.println("error input:  only one term");
    }
    /**
     * @desc output all of the arrayList Entry items to a file with their string representations
     *      for later use of the program.
     * @throws IOException 
     */
    public void outputAllEntriesToFile() throws IOException{
        Iterator firstIterator = givenCriteriaList.iterator();
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("textPreprocessEntries.txt")));
        while (firstIterator.hasNext()) {
            
            pw.println(firstIterator.next().toString());
            
            
        }
        pw.close();
    }
    
    public ArrayList<Entry> getArrayList(){
        return this.givenCriteriaList;
    }
    
    
}
