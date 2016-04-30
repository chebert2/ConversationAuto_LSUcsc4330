/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   ObjectData    class:
 *  this class is important for background storage of information on nouns with 
 *         some concern, objective, or descriptive term. 
 */
package conversationauto1;

/**
 * Description.
 * 
 * stores specifics for a particular adjective, particle, or noun's content.
 * 
 */
public class ObjectData {
    // this says where a word's index is in all communicating pieces of a sentence.
    private int wordSentenceIndex;
    // this is the beginning word form found from the start of the inspected sentence
    private String origUnassimilatedWord;
    // this is a semantically compact word form of the beginning original word
    private String lemmaOfWord;
    
    /**
     *  these are terms found after looking in the
     *  object data base classification, to find
     *  more unified and versatile considerations
     *  of the original word
     */
    // the base depth of the database classification of objects.
    private String classifiedWord;
    // the 1st depth of the database classification of objects... and so on.
    private String SecondClassifiedWord;
    private String ThirdClassifiedWord;
    private String FourthClassifiedWord;
        
    // this prints out a breakdown of the categories from the beginning base level
    //  to the level it reaches
    private String pathOfClassification;
    
    /**
     * these are more on language usage
     */
    // part of speech given by core nlp tools
    private String formOfGrammar;
    
    // This will say more general quality and characteristic trait of the object
    // such as if the word might be an environmental term or a part of some 
    //  carried outfit.
    private String Classification;
    
    public ObjectData(){
        wordSentenceIndex = 0;
        origUnassimilatedWord = null;
        lemmaOfWord = "null";
        classifiedWord = "null";
        SecondClassifiedWord = "null";
        pathOfClassification = "/null";
        formOfGrammar = "null";
        Classification = "null";
    }
    /**
     * 
     * @param wordIndex  initial integer index
     * @param origWord    initial String of word's unmodified form
     * @param lemmaOfWord initial String of lemma of word
     * @param firstLevelFormWord  String of first level placement classification
     * @param secondLevelFormWord String of second level placement classification
     * @param pathOfForm  String of path of classifications based order hierarchical elements
     * @param posForm String of part of speech of object phrase
     * @param classifyID String of general word affiliation for usage.
     */
    public ObjectData(int wordIndex, String origWord, String lemmaOfWord1, String firstLevelFormWord, String secondLevelFormWord, String pathOfForm, String posForm, String classifyID){
        this.wordSentenceIndex = wordIndex;
        this.origUnassimilatedWord = origWord;
        this.lemmaOfWord = lemmaOfWord1;
        this.classifiedWord = firstLevelFormWord;
        this.SecondClassifiedWord = secondLevelFormWord;
        this.pathOfClassification = pathOfForm;
        this.formOfGrammar = posForm;
        this.Classification = classifyID;
    }
    public int getSentenceIndex(){
        return this.wordSentenceIndex;
    }
    public String getOrigWord(){
        return this.origUnassimilatedWord;
    }
    
    public String getLemma(){
        return this.lemmaOfWord;
    }
    
    public String getfirstLevelFormedWord(){
        return this.classifiedWord;
    }
    public String getSecondLevelFormedWord(){
        return this.SecondClassifiedWord;
    }
    public String getThirdLevelFormedWord(){
        return this.ThirdClassifiedWord;
    }
    public String getFourthLevelFormedWord(){
        return this.FourthClassifiedWord;   
    }
    public String getPathOfForm(){
        return this.pathOfClassification;
    }
    public String getPosForm(){
        return this.formOfGrammar;
    }
    public String getClassification(){
        return this.Classification;
    }
    /**
     *
     * @return String of the object's data as a readable summary.
     */
    public String getAllInformation(){
        String comprehensiveString = this.wordSentenceIndex + " Orig: " + this.origUnassimilatedWord + " Lemma: " + this.lemmaOfWord +
              " level1_newForm: " +      this.classifiedWord + " level2_newForm: " + this.SecondClassifiedWord +  " level3_newForm: " + this.ThirdClassifiedWord + " level4_newForm: " + this.FourthClassifiedWord + "  pathIs: " + this.pathOfClassification  + " posIs: "  +  this.formOfGrammar  + " ObjectClassifyType: " + this.Classification ;
        return comprehensiveString;
    }
    
    public void setWordIndex(int enteredIndex){
        this.wordSentenceIndex = enteredIndex;
    }
    
    public void setOrigWord(String enteredOrigWord){
        this.origUnassimilatedWord = enteredOrigWord;
    }
    
    public void setlemma(String enteredLemma){
        this.lemmaOfWord = enteredLemma;
    }
    
    public void setLevel1FormedWord(String enteredLev1FormedWord){
        this.classifiedWord = enteredLev1FormedWord;
    }
    
    public void setLevel2FormedWord(String enteredLev2FormedWord){
        this.SecondClassifiedWord = enteredLev2FormedWord;
    }
    
    public void setLevel3FormedWord(String enteredLev3FormedWord){
        this.ThirdClassifiedWord = enteredLev3FormedWord;
    }
    
    public void setLevel4FormedWord(String enteredLev4FormedWord){
        this.FourthClassifiedWord = enteredLev4FormedWord;
    }
    
    public void setPathOfForm(String enteredPathOfForm){
        this.pathOfClassification = enteredPathOfForm;
    }
    
    public void setPosForm(String enteredPos){
        this.formOfGrammar = enteredPos;
    }
    
    public void setClassification(String enteredClassification){
        this.Classification = enteredClassification;
    }
    
    
    
    
    
}
