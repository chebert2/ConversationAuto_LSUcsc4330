/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description: of this  VerbData   class:
 *  this class is important for background storage of information on verbs
 */
package conversationauto1;

/**
 * Description.
 * 
 * stores specifics for a particular verb's content analyzed.
 * 
 */
public class VerbData {
    // this says where a word's index is in all communicating pieces of a sentence.
    private int wordSentenceIndex;
    // this is the beginning form found from the start of the inspected sentence
    private String origUnassimilatedWord;
    // this is a semantically compact word form of the beginning original word
    private String lemmaForm;
    
    // the base depth of the database classification of the verb.
    private String classifiedWord;
    // the first depth classification of the verb.
    private String SecondClassifiedWord;
    
    // this prints out a breakdown of the categories from the beginning base level
    //  to the level it reaches
    private String pathOfClassification;
    // says whether the verb may bring an indication something will take place in future
    private int futureActIndication;
    
    private String verbTense;
    // part of speech given by core nlp tools
    private String formOfGrammar;
    /**
     *Verb ActionDetails Type descript.
     * 
     * 0   nothing
     * 1  category:
     *    verb constructive addition.
     *
     * 2 category:
     *    verb representing or specifying on with a given
     *
     * 3 category:
     *    verb assumes acquisition of knowledge or results
     *
     * 4 category:
     *    verb phrase of transitioning
     *
     * 5 category:
     *    verb reflects some happenings/preparation
     *
     * 6 category:
     *    verb embarking on a venture
     *
     * 7 category:
     *    verb variance on a venture
     *
     * 8 category:
     *    verb physical development of deed/process
     *
     * 9 category:
     *    verb reach outcome or enlistment
     *
     * 10 category:
     *    verb linking descriptive instance
     *
     * 11 blank category __ 
     *     new redundant or  entrapped  element
     * 
     */
    private int ActionDetails_type_of_Verb;
    
    // This will say more general quality attributed to what the verb
    // might be carrying out.  Is it a moving verb, is it a outcome verb...
    // and so on.
    private String Classification;
    public VerbData(){
        wordSentenceIndex = 0;
        origUnassimilatedWord = null;
        lemmaForm = "null";
        classifiedWord = "null";
        SecondClassifiedWord = "null";
        pathOfClassification = "/null";
        futureActIndication = 0;
        verbTense = "null";
        formOfGrammar = "null";
        Classification = "null";
        ActionDetails_type_of_Verb = 0;
    }
    /**
     * 
     * @param wordIndex   word's integer value of index in sentence
     * @param origWord   initial String of verb as uninspected word in starting sentence
     * @param lemmaForm  lemma of the word
     * @param firstLevelFormWord  String of first level word hierarchical classification
     * @param pathOfForm String of path of classifications based order hierarchical elements
     * @param futureIndicationFlag 0 or 1 for verb not being indicative of the future or otherwise
     * @param posForm  String of part of speech of object phrase
     * @param classifyID String of general word affiliation for usage.
     */
    public VerbData(int wordIndex, String origWord, String lemmaWord, String firstLevelFormWord, String pathOfForm, int futureIndicationFlag, String posForm, String classifyID){
        this.wordSentenceIndex = wordIndex;
        this.origUnassimilatedWord = origWord;
        this.lemmaForm = lemmaWord;
        this.classifiedWord = firstLevelFormWord;
        this.pathOfClassification = pathOfForm;
        this.futureActIndication = futureIndicationFlag;
        this.formOfGrammar = posForm;
        this.Classification = classifyID;
    }
    /**
     * @return wordSentenceIndex String
     */
    public int getSentenceIndex(){
        return this.wordSentenceIndex;
    }
    /**
     * @return origUnassimilated String
     */
    public String getOrigWord(){
        return this.origUnassimilatedWord;
    }
    /**
     * 
     * @return lemmaForm String
     */
    public String getLemmaForm(){
        return this.lemmaForm;
    }
    
    /**
     * @return classifiedWord String
     */
    public String getfirstLevelFormedWord(){
        return this.classifiedWord;
    }
    /**
     * @return SecondClassifiedWord String
     */
    public String getSecondLevelFormedWord(){
        return this.SecondClassifiedWord;
    }
    /**
     * @return pathOfClassification String
     */
    public String getPathOfForm(){
        return this.pathOfClassification;
    }
    /**
     * @return futureActIndication int
     */
    public int getFutureActIndicaton(){
        return this.futureActIndication;
    }
    /**
     * 
     * @return verbTense String
     */
    public String getVerbTense(){
        return this.verbTense;
    }
    /**
     * @return formOfGrammar String
     */
    public String getPosForm(){
        return this.formOfGrammar;
    }
    /**
     * @return Classification String
     */
    public String getClassification(){
        return this.Classification;
    }
    /**
     * 
     * @return ActionDetails type of Verb  int
     */
    public int getActionDetails_type_of_verb(){
        return this.ActionDetails_type_of_Verb;
    }
    
     /**
     *
     * @return String of the verb's data gathered as a readable summary.
     */
    public String getAllInformation(){
        String comprehensiveString = this.wordSentenceIndex + " Orig: " + this.origUnassimilatedWord +  " Lemma: " + this.lemmaForm   +
              " level1_newForm: " +      this.classifiedWord + " level2_newForm: " + this.SecondClassifiedWord + "  pathIs: " + 
                this.pathOfClassification + " verb Tense: " + this.verbTense + " posIs: "  +  this.formOfGrammar  +
                " VerbClassifyType: " + this.Classification + " Verb ActionDetails_type: " + this.ActionDetails_type_of_Verb ;
        return comprehensiveString;
    }
    
    public void setWordIndex(int enteredIndex){
        this.wordSentenceIndex = enteredIndex;
    }
    
    public void setOrigWord(String enteredOrigWord){
        this.origUnassimilatedWord = enteredOrigWord;
    }
    
    public void setLemmaForm(String lemmaToBeSet){
        this.lemmaForm = lemmaToBeSet;
    }
    
    
    public void setLevel1FormedWord(String enteredLev1FormedWord){
        this.classifiedWord = enteredLev1FormedWord;
    }
    
    public void setLevel2FormedWord(String enteredLev2FormedWord){
        this.SecondClassifiedWord = enteredLev2FormedWord;
    }
    
    public void setPathOfForm(String enteredPathOfForm){
        this.pathOfClassification = enteredPathOfForm;
    }
    
    public void setFutureActInd(int enteredFutActInd){
        this.futureActIndication = enteredFutActInd;
    }
    
    public void setVerbTense(String enteredVerbTense){
        this.verbTense = enteredVerbTense;
    }
    
    public void setPosForm(String enteredPos){
        this.formOfGrammar = enteredPos;
    }
    
    public void setClassification(String enteredClassification){
        this.Classification = enteredClassification;
    }
    
    public void setActionDetails_Type_of_verb(int inputActionType_details){
        this.ActionDetails_type_of_Verb = inputActionType_details;
    }
    
    
    
}
