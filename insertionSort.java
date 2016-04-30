/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   insertionSort  class:
 * This class sorts the output of the first typed dependencies parse tree output.
 */
package conversationauto1;

/**
 *
 * @author ebeclaim3u
 */
public class insertionSort {
    
    public static String[] insertionSort(String[] strArr1) throws IllegalArgumentException {
        
        // index items
        int s1 = 0;
        int[] integerOfValues = new int[strArr1.length];
        while(s1 < strArr1.length){
            // precaution : we cannot sort empty values
            if(strArr1[s1].isEmpty())
                throw new IllegalArgumentException("Error: Input can not be broken.");
        
            String[] currentINarrayStringBroken = strArr1[s1].split(" ");
            
            
            // extract int from the word String // core label/ title
            String[] subjectTypedSections = currentINarrayStringBroken[currentINarrayStringBroken.length -1].split("-");
           
            
            int lengthOfDepFragment = subjectTypedSections.length;
            int numberFragment1 = subjectTypedSections[lengthOfDepFragment - 1].length();
            // sorting item we are interested in
            integerOfValues[s1] = Integer.parseInt(subjectTypedSections[lengthOfDepFragment -1].substring(0, numberFragment1 - 1 ) );
            s1++;
        }
        
        
        
        
        int n = strArr1.length;
        for (int k = 1; k < n; k++) {
            //  now sort  with algorithm
            int curr1 = integerOfValues[k];
            String currStr1 = strArr1[k];
            int j = k;
            while (j > 0 && integerOfValues[j - 1] > curr1) {
                strArr1[j] = strArr1[j - 1];
                integerOfValues[j] = integerOfValues[j - 1];
                j--;
            }

            strArr1[j] = currStr1;
            integerOfValues[j] = curr1;
            

        }
        
        return strArr1;
            
            
            
           
            
        
        }
    }
    

