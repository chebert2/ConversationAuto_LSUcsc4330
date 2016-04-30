/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   InfinitiveSect  class:
 * This class holds a minimal pack of a background info on an infinitive verb section
 */
package conversationauto1;
 


public class InfinitiveSect {

    private int startIndex_Of_toInf_Section;
    private int endIndex_Of_toInf_Section;
    private int Index_Of_primaryInfinitiveVerb;
    private String tense_Of_VerbPhraseHere;
    
    /**
     * A minimal constructor
     */
    public InfinitiveSect(){
        this.startIndex_Of_toInf_Section = 0;
        this.endIndex_Of_toInf_Section = 0;
        this.Index_Of_primaryInfinitiveVerb = 0;
        this.tense_Of_VerbPhraseHere = "null";
    }
    /**
     * @desc  a more detailed constructor of the InfinitiveSect()
     * 
     * @param startIndex_phrase_int  int  of the Start index of the infinitive phrase
     * @param endIndex_phrase_int  int  of the end index of the phrase
     * @param Index_of_verb_int  int of the index of the verb entry in the phrase
     * @param tense_of_phrase_input   String of the tense of the phrase
     */
    public InfinitiveSect(int startIndex_phrase_int, int endIndex_phrase_int, int Index_of_verb_int, String tense_of_phrase_input){
        this.startIndex_Of_toInf_Section = startIndex_phrase_int;
        this.endIndex_Of_toInf_Section = endIndex_phrase_int;
        this.Index_Of_primaryInfinitiveVerb = Index_of_verb_int;
        this.tense_Of_VerbPhraseHere = tense_of_phrase_input;
    }
    
    
    public int getStartIndex_of_phrase(){
        return this.startIndex_Of_toInf_Section;
    }
    
    public int get_the_EndIndex_of_phrase(){
        return this.endIndex_Of_toInf_Section;
    }
    
    public int get_Index_of_Verb_Of_Phrase(){
        return this.Index_Of_primaryInfinitiveVerb;
    }
    public String get_Tense_of_phrase(){
        return this.tense_Of_VerbPhraseHere;
    }
    
    public void setStartIndex_of_phrase(int inputStartIndexOfPhrase){
        this.startIndex_Of_toInf_Section = inputStartIndexOfPhrase;
    }
    public void setEndIndex_of_phrase(int inputEndIndexOfPhrase){
        this.endIndex_Of_toInf_Section = inputEndIndexOfPhrase;
    }
    public void setIndex_of_Verb_of_phrase(int inputVerbIndex){
        this.Index_Of_primaryInfinitiveVerb = inputVerbIndex;
    }
    public void setTense_of_Verb(String inputString_of_Tense){
        this.tense_Of_VerbPhraseHere = inputString_of_Tense;
    }
    // returns a string of "start index: 2 Verb element Index: 3 verb tense is: perfect end index: 3
    public String print_infinitive_String_info(){
        return "start index:" + this.startIndex_Of_toInf_Section + " Verb element Index:" + this.Index_Of_primaryInfinitiveVerb + " verb tense is: " + this.tense_Of_VerbPhraseHere+ " end index: " + this.endIndex_Of_toInf_Section ;
    }
    
    
    
    
}
