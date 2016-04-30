/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   VerbReview   class:
 * this is a class that is used by the other important executing makeCriteria class.
 */
package conversationauto1;


import java.util.*;
import edu.stanford.nlp.trees.*;

/**
 * Description.
 * 
 * This class interprets the meaning and syntatic structure of a verb phrase, 
 * most primarily a predicate.
 * 
 * 
 * Since it is made only to give output string on a passing/iterim basis, it is made static.
 * 
 */

public class VerbReview {



    
/**
 * @desc Assesses a basic core statement's verb components
 * @param VerbData[] main verbData array passed by executing class.
 * @param startIndexOfInvestigation  the earliest word's index for start reading terms
 * @param ListInput  ListIterator<TypedDependency> ,is an iterable list of grammar elements
 * @param inputVerbItemIndex the index of the root verb of sentence, just for background info
 * @param CloneListInput ListIterator<TypedDependency> , ,is an iterable list of grammar elements
 * @return String, and two boolean integers within the string, of accessories moves in representing scenario
 */
    public static String VerbAssessment(VerbData[] givenVerbs, int startIndexOfInvestigation, ListIterator<TypedDependency> ListInput, int inputVerbItemIndex, ListIterator<TypedDependency> CloneListInput) {

        // sub side part of look at infinitives reference, with possible future in grammar relations
        // flag for infinitive extension pertaining to future reference: i.e. i am going "to" start something
     boolean extensionInfinitiveHere = false;
     boolean extensionInfinitiveEncountered = false;
     // unimportant infinitive markers
     boolean extensionAuxWithInfinitive = false;


    // verb tense flags
    boolean tenseFuturePerf = false;
    boolean tenseFuture = false;
    boolean tensePresent = false;
    boolean tenseImperf = false;
    boolean tensePerfect = false;
    boolean tensePluperf = false;
    // this will be the String of whatever tense should be assigned for output in the 
      //   String codeInfoReturn   (returnString).
    String tenseYielded = "null";

    String accessoryBeAuxVerb = "null";
    // flags for past tense entailer
    boolean wasWereAuxEncountered = false;
    boolean hasHaveAuxEncountered = false;
    boolean hadAuxEncountered = false;
    boolean hadBeenVerbing = false;
    boolean wouldHaveAuxEncountered = false;
    boolean willHaveAuxEncountered = false;
    boolean wouldHaveBeenVerbing = false;
    boolean willHaveBeenVerbed = false;

    boolean wasAuxBeing = false;


    // flags for imperfect tense auxilaries
    
    boolean hasHaveBeenVerbing = false;

    // flags for present tense terms
    boolean potentialAux_PresentTense = false;
    boolean isAuxBeing = false;

    // flags for future tense auxilaries
    boolean willBeVerbing = false;
    boolean wouldBeVerbing = false;



    // code value for which section: "not" : modal [here] main act , or aux [here] main act
    // 
    // the first representation is: he did not do it.
    int notAfterModal = 0;
    int notAfterAux = 0;


    // thing to refer to which modal auxilary appeared.
    String modalInfo = "null";

        // return string holds first what modal there is and then the flag on negation for the modal
        // 
        // likewise after that first pair of information, ... a tense "StringcharacterSequence" and a "0 or 1" negation flag succeeding an auxilary verb. like: I am not going there.
        
        
        // indent a faulty initial return
        String codeInfoReturn = "tense 0";
        //... : example previous
        //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "tense" + " " + notAfterAux;     
        
        
        // for a minor side tracking
        // working with evaluating future tenses,  through a clone of ListIterator<TypedDependency>
         
          // first word starts at index 1.
        int firstCurrentIndex = 1;
        int intIndex2 = 0;
        
        byte governorDependencyParseRelation_Bit = 0;
        byte DependentDependencyParseRelation_Bit = 0;
        String firstCurrentString;
        // first check if verb is reg-verb "going" or linking verb
          // this section represents as either true or false: extensionInfinitiveHere
        if ( ( givenVerbs[inputVerbItemIndex].getOrigWord().toLowerCase().matches("is") || givenVerbs[inputVerbItemIndex].getOrigWord().toLowerCase().matches("are") || givenVerbs[inputVerbItemIndex].getOrigWord().toLowerCase().matches("am")) || givenVerbs[inputVerbItemIndex].getSecondLevelFormedWord().matches("going")) {
            while (CloneListInput.hasNext()) {
                   // the bulk of the first grammar entries are word by word from beginning
                       // but only the words up past the main root verb are to be look at
                       // so skip so and so many first grammar entries in list iterator.
                if (firstCurrentIndex <= inputVerbItemIndex) 
                    firstCurrentIndex++;
                
                else {
                    // a verb should not come before the middle half sentence (at going),    as : I am going .... to start
                    // break and end search if reg-verb comes up before a expanded "to" infinitive clause, indicating it is no longer future essentially
                    if(!extensionInfinitiveEncountered && givenVerbs[firstCurrentIndex] != null)
                        break;
                    
                    firstCurrentString = CloneListInput.next().toString();
                    String[] firstarrayIn = firstCurrentString.split(" ");
                    int f1lengthOfDepFragment = firstarrayIn[firstarrayIn.length - 1].length();
                    String f1rawSubjectSentence = firstarrayIn[firstarrayIn.length - 1].substring(0, f1lengthOfDepFragment - 1);
                    // extract int from the word String
                    String[] f1verbRootSections = f1rawSubjectSentence.split("-");
                    String f1subjectSentence = f1verbRootSections[0];
                    f1lengthOfDepFragment = f1verbRootSections.length;
                    firstCurrentIndex = Integer.parseInt(f1verbRootSections[f1lengthOfDepFragment - 1]);
                    // go to next item in givenVerbs, if the listiterator is not yet to the starting term;
                    if (firstCurrentIndex <= inputVerbItemIndex) 
                        continue;
                    
                    // find index and string of first govenor dependency grammar parsing item.
                    String[] f1DependencyLeftSideSections = firstarrayIn[0].split("-");
                    int lengthOfDepFragment = f1DependencyLeftSideSections.length;
                    // check if governor index of 0 might return a negative index value
                    int correctedLenOfStringFragment = f1DependencyLeftSideSections[lengthOfDepFragment - 1].length();
                    if ( (f1DependencyLeftSideSections[lengthOfDepFragment - 1].length() - 1) < 0)
                       break;
                    else
                        intIndex2 = Integer.parseInt(f1DependencyLeftSideSections[lengthOfDepFragment - 1].substring(0, correctedLenOfStringFragment -1)); 
                    // check if governor is verb   as extra application for use of conjunctions following later here.
                    if(givenVerbs[intIndex2] == null)
                         governorDependencyParseRelation_Bit = 0;
                    else
                         governorDependencyParseRelation_Bit = 1;
                    
                    // a heuristic variable used
                    int getFutureActIndication;
                    
                    // turn off detection for violating verb before future "to be/verb" infinitive phrase
                    if(firstCurrentString.contains("mark(") && f1subjectSentence.toLowerCase().matches("to") && governorDependencyParseRelation_Bit == 1 )
                        extensionInfinitiveEncountered = true;
                    
                    // check first if an auxilary verb comes in the infinitive phrase
                    if(firstCurrentString.contains("aux(") )
                        extensionAuxWithInfinitive = true;
                    
                       // check if is null  for if next proceeding: // infinitive phrase making...
                       
                       // if no verb data exists,   smudge out the results for the query with -1
                       if(givenVerbs[firstCurrentIndex] ==null)
                          getFutureActIndication = -1;
                       else
                          getFutureActIndication = givenVerbs[firstCurrentIndex].getFutureActIndicaton();
                    
                    // infinitive phrase making future indication    here is found!
                    if( firstCurrentString.contains("xcomp(") && getFutureActIndication == 1 ) {
                        extensionInfinitiveHere = true;
                        break;
                    }   
                    
                    // the index should not go to far after infinitive phrase we found, like: going ... to verb/start  ..end infinitive
                    
                    if(extensionInfinitiveEncountered && givenVerbs[firstCurrentIndex] != null)
                        break;
                    
                }

            }
        }   // end :future infinitive rundown   minor side tracking
        
        // a heuristic String variable that refers to whether last iteration had a modal or a reg/or/linking verb.
        
        CircularBuffer lastVerbReference_buffer = new CircularBuffer(5);

        int currentIndex = 0;
        while (ListInput.hasNext() && currentIndex < inputVerbItemIndex) {
                  
            // take in first sentence grammar relation
            String currentString = ListInput.next().toString();
            
            String[] arrayIn = currentString.split(" ");
            int lengthOfDepFragment = arrayIn[arrayIn.length - 1].length();
            String rawSubjectSentence = arrayIn[arrayIn.length - 1].substring(0, lengthOfDepFragment - 1);
            
            // extract int from the word String
            String[] verbRootSections = rawSubjectSentence.split("-");
            
            String subjectSentence = verbRootSections[0];
            
            lengthOfDepFragment = verbRootSections.length;
            
            currentIndex = Integer.parseInt(verbRootSections[lengthOfDepFragment - 1]);
            
            // Check whether there is a verb for the dependent element_ of dependency grammar triple item
            if(givenVerbs[currentIndex] == null)
                DependentDependencyParseRelation_Bit = 0;
            else
                DependentDependencyParseRelation_Bit = 1;
            
            
            // a heuristic to get the start index of the List
            if(currentIndex < startIndexOfInvestigation)
                continue;
            
            if (currentString.contains("aux(") ) {
                
            //+
                // an auxilary to a verb_ this is a modal term
                if(subjectSentence.toLowerCase().matches("should") ){
                    modalInfo = "should";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("will") ){
                    modalInfo = "will";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("shall") ){
                    modalInfo = "shall";
                    lastVerbReference_buffer.store("modal");
                }
            //+  
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("can") ){
                    // close to Future tense    as : "He can get it later.
                    tensePresent = true;
                    modalInfo = "can";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("cannot") ){
                    // close to Future tense    as : "He can get it later.
                    tensePresent = true;
                    modalInfo = "cannot";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("could") ){
                    modalInfo = "could";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("may") ){
                    modalInfo = "may";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("might") ){
                    modalInfo = "might";
                    lastVerbReference_buffer.store("modal");
                }
            //+
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("must") ){
                    modalInfo = "must";
                    lastVerbReference_buffer.store("modal");
                }
           // +    
                // an auxilary to a verb_ this is a modal term
                else if(subjectSentence.toLowerCase().matches("would") ){
                    modalInfo = "would";
                    lastVerbReference_buffer.store("modal");
                }
                
                // check about future phrase references -- will be doing ... or might be verbed or might verb  etc.
                else if(subjectSentence.toLowerCase().matches("be") && modalInfo.toLowerCase().matches("will")){
                    willBeVerbing = true;
                    lastVerbReference_buffer.store("verbal");
                }
                 
                // check for auxilary be alongside modal:could/would.  as in would be verbed...
                else if(  ( modalInfo.matches("could")|| modalInfo.matches("would") )  && subjectSentence.toLowerCase().matches("be")){
                    wouldBeVerbing = true;
                    lastVerbReference_buffer.store("verbal");
                }
                      // end future phrase section

            // main present tense verbs
                // check about present tense beginning phrase -- am verbing
                else if(subjectSentence.toLowerCase().matches("am") ){
                    // almost Present tense  _ with exception of :  "I am going to" and "I am to"
                    potentialAux_PresentTense = true;
                    accessoryBeAuxVerb = "am";
                    lastVerbReference_buffer.store("verbal");
                }
                // check about present tense beginning phrase -- are verbing
                else if(subjectSentence.toLowerCase().matches("are") ){
                    // almost Present tense  _ with exception of :  "I am going to" and "I am to"
                    potentialAux_PresentTense = true;
                    accessoryBeAuxVerb = "are";
                    lastVerbReference_buffer.store("verbal");
                }
                // check about present tense beginning phrase -- is verbing
                else if(subjectSentence.toLowerCase().matches("is") ){
                    // almost Present tense  _ with exception of :  "I am going to" and "I am to"
                    potentialAux_PresentTense = true;
                    accessoryBeAuxVerb = "is";
                    lastVerbReference_buffer.store("verbal");
                }
                // check about present tense beginning phrase -- do verbing
                else if(subjectSentence.toLowerCase().matches("do") ){
                    accessoryBeAuxVerb = "do";
                    // almost Future tense     as :  "They do get verbed later"
                    tensePresent = true;
                    lastVerbReference_buffer.store("verbal");
                }
                // check about present tense beginning phrase -- does verbing
                else if(subjectSentence.toLowerCase().matches("does") ){
                    accessoryBeAuxVerb = "does";
                    // almost Future tense    as : "He does get it later.
                    tensePresent = true;
                    lastVerbReference_buffer.store("verbal");
                }

                
               // check if a being auxilary follows a previous present tense auxilary
                else if(potentialAux_PresentTense && subjectSentence.toLowerCase().matches("being")){
                    // this is the category it is
                    isAuxBeing = true;
                    
                    tensePresent = true;
                }
                    // check about past phrase --  have verbed or have been verbing
               // check if a being auxilary follows a previuos past tense auxilary
                else if(wasWereAuxEncountered && subjectSentence.toLowerCase().matches("being")){
                    // this is the category it is
                    wasAuxBeing = true;
                    
                    tensePerfect = true;
                }
                
                
                
                // check about past phrase --  have verbed or have been verbing
                
                // check for auxilary have  , as in: will have ...verbed 
                else if( ( modalInfo.matches("will") || modalInfo.matches("shall") )   && subjectSentence.toLowerCase().matches("have") ){
                    willHaveAuxEncountered = true;
                    lastVerbReference_buffer.store("verbal");
                }
                
                // check about auxilary of been , as in: will have been...
                else if(  willHaveAuxEncountered && subjectSentence.toLowerCase().matches("been")){
                    willHaveBeenVerbed = true;
                    lastVerbReference_buffer.store("verbal");
                }
                
                // check for auxilary have  , as in: could, would have .../[been] -- /  verbed 
                else if( (modalInfo.matches("could") || modalInfo.matches("would") )  && subjectSentence.toLowerCase().matches("have") ){
                    wouldHaveAuxEncountered = true;
                    lastVerbReference_buffer.store("verbal");
                }
                // check about past progressive tense for phrase: would have been verbing
                else if(  wouldHaveAuxEncountered && subjectSentence.toLowerCase().matches("been")){
                    wouldHaveBeenVerbing = true;
                    lastVerbReference_buffer.store("verbal");
                }
                // check for auxilaries have or has
                else if(subjectSentence.toLowerCase().matches("have") || subjectSentence.toLowerCase().matches("has") ){
                    hasHaveAuxEncountered = true;
                    lastVerbReference_buffer.store("verbal");
                }
                
                // check for auxilary had
                else if(subjectSentence.toLowerCase().matches("had") ){
                    hadAuxEncountered = true;
                    lastVerbReference_buffer.store("verbal");
                }
                
                // check for total element has/have been
                else if(subjectSentence.toLowerCase().matches("been") && hasHaveAuxEncountered){
                    hasHaveBeenVerbing = true;
                    lastVerbReference_buffer.store("verbal");
                }
                
                // check for total element had been
                else if(subjectSentence.toLowerCase().matches("been") && hadAuxEncountered){
                    hadBeenVerbing = true;
                    lastVerbReference_buffer.store("verbal");
                }
                

                // check about imperfect tense auxileries
                if(subjectSentence.toLowerCase().matches("was") || subjectSentence.toLowerCase().matches("were") ){
                    if(subjectSentence.toLowerCase().matches("was"))
                       accessoryBeAuxVerb = "was";
                    else
                       accessoryBeAuxVerb = "";
                    wasWereAuxEncountered = true;
                    lastVerbReference_buffer.store("verbal");
                }

                

                
                
            }
            
            else if (currentString.contains("neg(") ) {
                
                // skim through most recent auxilary/verb or modal from order most recent starting at zero.
                for(String e1 : lastVerbReference_buffer.read()){
                    if(e1 != null){
                        if(e1.matches("modal")){
                            notAfterModal = 1;
                            break;
                        }
                        else if(e1.matches("verbal")){
                            notAfterAux = 1;
                            break;
                        }
                        else
                            System.out.println("neither verb or modal entry preceding not: here");
                            
                     }
                    else
                        continue;
                 }
                
            }
            else if (currentString.contains("cop(")){
                
                // caps off future phrase: noun should/must/might   be   a adjective
                if(!hasHaveAuxEncountered && ( modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                    tenseFuture = true;
                // caps off future linking verb phrase:  could/would be - [without reg verb] - adjective
                else if( ( modalInfo.matches("could") || modalInfo.matches("would") ) && !wouldBeVerbing && !wouldHaveBeenVerbing)
                    tenseFuture = true;
                // caps off future linking verb phrase:  will/shall be - [without reg verb] - adjective
                else if( ( modalInfo.matches("will") || modalInfo.matches("shall") )  && !willBeVerbing && !willHaveBeenVerbed)
                    tenseFuture = true;
                // caps off future perfect linking verb phrase: will/shall have been adjective
                else if(willHaveAuxEncountered && !willHaveBeenVerbed)
                    tenseFuturePerf = true;
                // caps off present linking verb phrase in expanded being form : i.e. I am being/ he is being/ they are being.
                else if( potentialAux_PresentTense && subjectSentence.toLowerCase().matches("being") )
                    tensePresent = true;
                // caps off present linking verb phrase: i.e.  he is/am/are good   
                else if( subjectSentence.toLowerCase().matches("is") || subjectSentence.toLowerCase().matches("are") || subjectSentence.toLowerCase().matches("am")  )
                    tensePresent = true;
                // caps off perfect phrase: noun should/must/might [and could/would]  have  been   a adjective
                else if(hasHaveAuxEncountered && ( wouldHaveBeenVerbing || modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                    tensePerfect = true;
                // caps off imperfect linking verb phrase in being expanded form: e.g. was being adjective // or//  has been adjective
                else if( (wasWereAuxEncountered || hasHaveAuxEncountered ) && modalInfo.matches("null"))
                    tenseImperf = true;
                // caps off perfect linking verb phrase
                else if(subjectSentence.toLowerCase().matches("was") || subjectSentence.toLowerCase().matches("were"))
                    tensePerfect = true;
                // caps off pluperfect linking verb phrase
                else if(hadAuxEncountered)
                    tensePluperf = true;
                
                
                
            }
            else if(currentString.contains("root(")){                
               
              // future tense root words  
              if(subjectSentence.toLowerCase().matches("should") || subjectSentence.toLowerCase().matches("will") || subjectSentence.toLowerCase().matches("shall") || subjectSentence.toLowerCase().matches("may") || subjectSentence.toLowerCase().matches("might") || subjectSentence.toLowerCase().matches("must") ||  subjectSentence.toLowerCase().matches("would")  )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // look into main verb with all previous auxilary information
            
              // will or shall verb  ;  and also   will be// shall be.
              else if( (modalInfo.matches("will") ||modalInfo.matches("shall") ) )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              // would be verbing/verbed
              else if(wouldBeVerbing &&  (givenVerbs[currentIndex].getPosForm().matches("VBG") || givenVerbs[currentIndex].getPosForm().matches("VBD") )  )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // sentence: noun could/would/should/may/must verb it  /perhaps: then or [sometime]
              else if( (!hasHaveAuxEncountered)  && ( modalInfo.matches("could") || modalInfo.matches("would")  ||  modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // sentence: noun should/must have verbed it    [implied:..at a time...]
              else if( (hasHaveAuxEncountered)  && ( modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;

              // noun is/am/are  to [start verb]
              else if(( givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("is") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("are") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("am") ) && extensionInfinitiveHere)
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // going to verb or (maybe: (be verbed)  -- instead )
              else if(potentialAux_PresentTense && extensionInfinitiveHere)
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // noun is/am/are verbing  [not going]  : just present.
                /**
                 * extra note: the interpreted stand of this code is that any future phrase with these verbs
                 *    should contain "going" as:  He is going to a meeting tonight
                 *   end note:
                 **/
              else if(potentialAux_PresentTense && !extensionInfinitiveHere && givenVerbs[currentIndex].getPosForm().matches("VBG"))
                  tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "present" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // this is not needed< since we can parse from pos tagging
              // is/am/are  present  
              //else if( givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("am") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("is") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("are"))
                  //tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "present" + " " + notAfterAux + accessoryBeAuxVerb;
              
              
              // will have been verbed : passive
              // or
              // will have been verbing
              else if(willHaveBeenVerbed && (givenVerbs[currentIndex].getPosForm().matches("VBG") || givenVerbs[currentIndex].getPosForm().matches("VBD") ) )
                  tenseFuturePerf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // will have verbed
              else if(willHaveAuxEncountered && !willHaveBeenVerbed && givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tenseFuturePerf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // was verbed _perfect : passive
              else if(wasWereAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // was verbing ...imperfect active
              else if(wasWereAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBG") )
                 tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // could/would have been verbed : passive
              else if(wouldHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
             // could/would have been verbing
              else if(wouldHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBG")  )
                  tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // could/would have verbed
              else if(wouldHaveAuxEncountered  && !wouldHaveBeenVerbing  && givenVerbs[currentIndex].getPosForm().matches("VBD") )
                 tensePerfect = true;
                 //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // has/have been verbed  : passive
              else if(hasHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBD") ){
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
              // has/have been verbing
              else if(hasHaveBeenVerbing &&  givenVerbs[currentIndex].getPosForm().matches("VBG")  ){
                  tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
            
              // has/have verbed  perfect tense
              else if(hasHaveAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") && !hasHaveBeenVerbing ) {
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
                        
              
              // had been verbed :  passive 
              // or
              // had been stopping : active
              else if(hadBeenVerbing && ( givenVerbs[currentIndex].getPosForm().matches("VBG") ||givenVerbs[currentIndex].getPosForm().matches("VBD") ) )
                  tensePluperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "plu-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
            
            
              // had verbed  plu-perfect tense
              else if(hadAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") &&  !hadBeenVerbing ) {
                  tensePluperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "plu-perfect" + " " + notAfterAux + accessoryBeAuxVerb;

                  }
             // past tense root words
              else if( givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
             // present tense root words
              else if( givenVerbs[currentIndex].getPosForm().contains("VB")   )
                  tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;     
            

            }
            
            if(  ( currentString.contains("ccomp(" ) || currentString.contains("acl:relcl(") || currentString.contains("conj:and(") ||  currentString.contains("conj:but(")  ) && DependentDependencyParseRelation_Bit == 1 ){
              
                // the relative clause value occasionally contains a linking nomnitive that is not to be applied
                //if( currentString.contains("acl:relcl(")  )
                 //   if(givenVerbs[currentIndex] == null)
                  //      continue;
                    
                    
            
            // future tense root words  
              if(subjectSentence.toLowerCase().matches("should") || subjectSentence.toLowerCase().matches("will") || subjectSentence.toLowerCase().matches("shall") || subjectSentence.toLowerCase().matches("may") || subjectSentence.toLowerCase().matches("might") || subjectSentence.toLowerCase().matches("must") ||  subjectSentence.toLowerCase().matches("would")  )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              
            // noun is/am/are  to [start verb]
              else if( ( givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("is") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("are") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("am") ) && extensionInfinitiveHere)
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // look into main verb with all previous auxilary information
            
              // will or shall verb   ;  and also: will be or shall be
                   //  also  caps off future linking verb phrase:  will/shall be - [without reg verb] - adjective
              else if( (modalInfo.matches("will") ||modalInfo.matches("shall") ) )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              // would be verbing
              else if(wouldBeVerbing &&  (givenVerbs[currentIndex].getPosForm().matches("VBG") || givenVerbs[currentIndex].getPosForm().matches("VBD") )  )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // sentence: noun could/would/should/may/must verb it  /perhaps: then or [sometime]
                         // also caps off future phrase: noun should/must/might   be   a adjective
              else if( (!hasHaveAuxEncountered)  && ( modalInfo.matches("could") || modalInfo.matches("would")  ||  modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // sentence: noun should/must have verbed it    [implied:..at a time...]
              else if( (hasHaveAuxEncountered)  && ( modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must") ) )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // going to verb or (maybe: (be verbed)  -- instead )
              else if(potentialAux_PresentTense && extensionInfinitiveHere)
                  tenseFuture = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // noun is/am/are verbing  [not going]  : just present.
                /**
                 * extra note: the interpreted stand of this code is that any future phrase with these verbs
                 *    should contain "going" as:  He is going to a meeting tonight
                 *   end note:
                 **/
              else if(potentialAux_PresentTense && !extensionInfinitiveHere && givenVerbs[currentIndex].getPosForm().matches("VBG"))
                  tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "present" + " " + notAfterAux + accessoryBeAuxVerb;
              
              // this is not needed< since we can parse from pos tagging
              // is/am/are  present  
              //else if( givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("am") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("is") || givenVerbs[currentIndex].getOrigWord().toLowerCase().matches("are"))
                  //tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "present" + " " + notAfterAux + accessoryBeAuxVerb;
              
              
              // will have been verbed : passive
              // or
              // will have been verbing  
                   // also  caps off future perfect linking verb phrase: will/shall have been adjective
              else if(willHaveBeenVerbed && (givenVerbs[currentIndex].getPosForm().matches("VBG") || givenVerbs[currentIndex].getPosForm().matches("VBD") ) )
                  tenseFuturePerf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // will have verbed
              else if(willHaveAuxEncountered && !willHaveBeenVerbed && givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tenseFuturePerf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "future-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // was verbed _perfect : passive
              else if(wasWereAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // was verbing ...imperfect active
                    // also  caps off imperfect linking verb phrase:   was being adjective
              else if(wasWereAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBG") )
                 tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // could/would have been verbed : passive
              else if(wouldHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
             // could/would have been verbing
                 // also caps off perfect phrase: noun would have  been   a adjective
              else if(wouldHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBG")  )
                  tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
              // could/would have verbed
              else if(wouldHaveAuxEncountered  && givenVerbs[currentIndex].getPosForm().matches("VBD") )
                 tensePerfect = true;
                 //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
            
              // has/have been verbed  : passive
              else if(hasHaveBeenVerbing && givenVerbs[currentIndex].getPosForm().matches("VBD") ){
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
              // has/have been verbing
              else if(hasHaveBeenVerbing &&  givenVerbs[currentIndex].getPosForm().matches("VBG")  ){
                  tenseImperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "imperfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
            
              // has/have verbed  perfect tense
                  // also  caps off imperfect linking verb phrase: has been adjective
              else if(hasHaveAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") ) {
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
                  }
                        
              
              // had been verbed :  passive 
              // or
              // had been stopping : active
              else if(hadBeenVerbing && ( givenVerbs[currentIndex].getPosForm().matches("VBG") ||givenVerbs[currentIndex].getPosForm().matches("VBD") ) )
                  tensePluperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "plu-perfect" + " " + notAfterAux + accessoryBeAuxVerb;
            
            
              // had verbed  plu-perfect tense
                 // caps off perfect linking verb phrase
              else if(hadAuxEncountered && givenVerbs[currentIndex].getPosForm().matches("VBD") ) {
                  tensePluperf = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "plu-perfect" + " " + notAfterAux + accessoryBeAuxVerb;

                  }
              // caps off future phrase: noun should/must/might   be   a adjective
              else if (!hasHaveAuxEncountered && (modalInfo.matches("should") || modalInfo.matches("may") || modalInfo.matches("might") || modalInfo.matches("must")))
                  tenseFuture = true;
              
              // caps off future linking verb phrase:  could/would be - [without reg verb] - adjective
              else if ((modalInfo.matches("could") || modalInfo.matches("would")) && !wouldBeVerbing && !wouldHaveBeenVerbing)
                  tenseFuture = true;
              
              // caps off future linking verb phrase:  will/shall be - [without reg verb] - adjective
              else if ((modalInfo.matches("will") || modalInfo.matches("shall")) && !willBeVerbing && !willHaveBeenVerbed)
                  tenseFuture = true;
              
             // past tense root words
              else if( givenVerbs[currentIndex].getPosForm().matches("VBD")  )
                  tensePerfect = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;
             // present tense root words
              else if( givenVerbs[currentIndex].getPosForm().contains("VB")   )
                  tensePresent = true;
                  //codeInfoReturn = modalInfo + " " + notAfterModal +" " + "perfect" + " " + notAfterAux + accessoryBeAuxVerb;     
                  
            }
            
        }

        
        if(tenseFuturePerf)
           tenseYielded = "future-perfect";
        else if(tenseFuture)
            tenseYielded = "future";
        else if(tensePresent)
            tenseYielded = "present" ;
        else if(tenseImperf)
            tenseYielded = "imperfect";
        else if(tensePerfect)
            tenseYielded = "perfect" ;
        else if(tensePluperf)
            tenseYielded = "plu-perfect";
        else
            tenseYielded = "BlankTense";
        
        if(accessoryBeAuxVerb.matches("null"))
            codeInfoReturn = modalInfo + " " + notAfterModal +" " + tenseYielded + " " + notAfterAux;    
        else
            codeInfoReturn = modalInfo + " " + notAfterModal +" " + tenseYielded + " " + notAfterAux + " " + accessoryBeAuxVerb ;    
        
        // turn listIterators back to beginning
        while(ListInput.hasPrevious())
            ListInput.previous();
        while(CloneListInput.hasPrevious())
            CloneListInput.previous();
        
        // all reversed now!
        
        
        return codeInfoReturn;
    }
   /**
    * @param commaStart int array of indices of comma starts
    * @param commaEnd int array of indices comma ends
    * @param wordsOfSentence String array of all words in sentence
    * @param PreviousRegionEnd int for the beginning range of clause
    * @param NextSequenceItem_beginning  int for the end range of clause
    * @param givenValueinput int for the value being checked if it is in the range of beginning and end
    * @return boolean result of true or false
    */
    public static boolean CheckNounItem_in_ClauseRange(int[] commaStart, int[] commaEnd, String[] wordsOfSentence, int PreviousRegionEnd, int NextSequenceItem_beginning, int givenValueinput){
        boolean returnItemBool = false;
        //dummy variable
        int ee1 = 0;
        if(givenValueinput <= NextSequenceItem_beginning && givenValueinput >= PreviousRegionEnd)
            returnItemBool = true;
        else
            returnItemBool = false;
        
        boolean commaIsStartingInPertainingArea = false;
        boolean commaEndsInPertainingArea = false;
        int commaStartInvestigated = 0;
        
        if(returnItemBool){
            if(commaEnd[0] == 0)
                ee1 = 0;
            else{
                // run through all non zero values in comma arrays
                
                // note: commaStart is going to be 0 or an even number
                for(int c1 = 0; c1< commaStart.length && c1< commaEnd.length; c1++){
                    if (c1 % 2 == 0) {
                        if (commaStart[c1] < NextSequenceItem_beginning && commaStart[c1] > PreviousRegionEnd) {
                            commaIsStartingInPertainingArea = true;
                            commaStartInvestigated = commaStart[c1];
                        } 
                        if(commaStart[c1] == 0)
                            break;
                    }
                    else{
                        // now check if it ends before the next start
                        if (commaIsStartingInPertainingArea) {
                            if (wordsOfSentence[commaStartInvestigated + 1].matches("that") || (commaEnd[c1] < NextSequenceItem_beginning && commaEnd[c1] >= commaStartInvestigated)) {
                                commaEndsInPertainingArea = true;
                            }
                        }
                        if(commaEnd[c1] == 0)
                            break;
                    }
                }
                if (commaIsStartingInPertainingArea && !commaEndsInPertainingArea) 
                  returnItemBool = false;
                else
                  returnItemBool = true;  

                                    
            }
        }
        return returnItemBool;
            
    }
       /**
    * 
    * @desc just checks if an item is in a range of the sentence
    * @param PreviousRegionEnd int for the beginning range of clause
    * @param NextSequenceItem_beginning  int for the end range of clause
    * @param givenValueinput int for the value being checked if it is in the range of beginning and end
    * @return boolean result of true or false
    */
    public static boolean CheckItem_in_ClauseRange(int PreviousRegionEnd, int NextSequenceItem_beginning, int givenValueinput){
        
       
        if(givenValueinput <= NextSequenceItem_beginning && givenValueinput >= PreviousRegionEnd)
            return true;
        else
            return false;
      
        
            
    }

   
}
