/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   makeCriteria   class:
 * This class develops an algorithmic sequence of processes for interpreting
 * the input sentence and pursuing a response.
 */
package conversationauto1;

import java.sql.*;
import java.io.*;
import java.util.*;

//import edu.stanford.nlp.dcoref.CorefChain;
//import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
//import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
//import edu.stanford.nlp.semgraph.SemanticGraph;
//import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
//import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

/**
 * Description.
 * 
 * First and foremost, passive phrases are not explicitly  supported in most
 *  parts of verb investigations.
 * 
 * Variables are declared at class start.
 * In the runMake method, first the input sentence is given preprocessing
 * to find simplifications of multiword phrases that could be
 * more concise. The second step starts with using Stanford core NLP tools
 * to get a grammatical dependency tree, pos tagging, word/punc
 * indexing, and lemmatization.  All verb forms (whether descriptive
 * or being predicate) are found and classified. It then places the
 * sentence's primary subjects, with possible sub clauses.  Then
 * the main thing being carried out or requested is placed in
 * perspective.
 * 
 */



public class makeCriteria {
    
    /**
      *   // JDBC driver name and database URL
      * static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      * static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/mydb";
      * //static final String DB_URL = "jdbc:mysql://localhost:3306/homeDB";
      *
      * //  Database credentials
      * static final String USER = "root";
      * static final String PASS = "";
      **/ 
    private int numWordsSentence;
    
    private String[] actualWords = new String[45];
    
    // a list of lemmas for all words in the sentence
    private String[] lemmasSentence = new String[45];
    
    // a list of pos tags for all words in the sentence
    private String[] posSentence = new String[45];
    
    // array of string of sorted dependency items
    String[] newArrOut_Sorted;
    
    
    // declaring a Properties object for making a fitting resource tools pipeline
    private Properties props;
    
    private StanfordCoreNLP pipeline;
    
    private Annotation annotation;
    
    private List<CoreMap> sentences;
    
    private CoreMap mainsentence;
    
    // tree for dependency grammar structure
    private Tree tree;
    
    // verb informational data objects
    private VerbData[] element1;
    
    // infinitive verb information reference array
    private InfinitiveSect[] newInfinitives1;
    private InfinitiveSect[] newInfinitives2;
    
    // index to keep track of what addition should be put in the InfinitiveSect[] object array
    private int duplicateInfinit1Count = 0;
    private int duplicateInfinit2Count = 0;
    
    private int infinitives1Count = 0;
    private int infinitives2Count = 0;
    
    //private boolean subjectHasFollowedInfinitive_Yes = false;
    private boolean startProbingForSubject_toPredicate = false;
    
    private boolean infinitive1IsSubject_of_predicate = false;
    private boolean infinitive2IsSubject_of_predicate = false;
    
    // for first section
    private int[] thatMark_1 = new int[6];
    // check if head grammar tag is VB
    private boolean eventResultFlag0;
    
    private boolean actionResultFlag0;
    
    // store root verb Header
    private int verbRootItem = 0;
    
    private int[] centerVerb1 = new int[6];
    private int[] centerVerb2 = new int[6];
    
    
    // if a separate ", and"  clause starts with its own independent verb
    private boolean commaAndClause = false;
    
    // verbs gathered that contribute to main predicate
    // [note]: ordered from back end of predicate to front end
    // 
    //private int[] backtrackVerbItems;
    
    //store noun/descriptive item info
    private ObjectData[] objects1;
    
    private int[] centerSubject1;
    private int[] centerSubject2;

    private int numOfSubjectsPrimary_1;
    // will go up if their is a second half of sentence with totally independent 
    //  grammatically separate subject
    private int numOfSubjectsPrimary_2;
    
    
    
    // tells which level clause we are in   --  descriptive terms
    // section 1's count of terms added 
    private int totalClauseElementCount1 = 0;
    private int totalClauseElementCount2 = 0;
    // one adjective reservered for first section of sentence
    private int[] currentAdjClauseNumber = new int[6];
    
    private int clause0NumAdjectives = 1;

    // record section 1 and 2 subclause-subject indices with this
    private int clauseSubject_sect_count;
    
    private int[] directObjects_1;
    private int[] directObjects_2;
    
    private int numOfDirectObjects_1 = 0;
    private int numOfDirectObjects_2 = 0;
    
    private int[] indirectObjects_1;
    private int[] indirectObjects_2;
    
    private int numOfIndirectObjects_1 = 0;
    private int numOfIndirectObjects_2 = 0;


    private int numberOfObjectPhrases;
    // integer
        //this.numberOfObjectPhrases = 0;
 
    // this stores details of predicate describers
    private int[][] descriptiveAdjectivePhrases;
    
    // if there is no problem of an infinitive past main verb...'s capability  to be added in response output
    private boolean infinitive_afterPredicate_NoProblem_in_Response = false;
    // no noun/personal pronoun  after verb object section and before a new transitioner
    //private boolean atVerbObjectClose = false;
    //private boolean nounPresentAtVerbObjectClose = false;
    //private int[] flag_proximityOfObjects_andNextTo_Infinitives1 = new int[6];
    
    
    private String[] nonVerbObjects;
    
    private predicateData[] accessoryVerbElements1;
    private predicateData[] accessoryVerbElements2;

    private int commaCount = 0;
    private int[] commaStart = new int[8];
    private int[] commaEnd = new int[8];
    private boolean commaStartBool = false;
    // int array,  where first index is for the clause we are in
      //  for each clause entry, there is a beginning index stored as one value
      // and an end index stored as a value
    private int[][] clauseInfo_onRange1 = new int[8][2];
    private int[][] clauseInfo_onRange2 = new int[8][2];
    
    public String[] arrayOfOutput1 = new String[18];
    public String[] arrayOfOutput2 = new String[18];
    
    // used for parsing integers from string.
    boolean parseable = false;
    // index increment for output of section one reponse words output
    // used at very last part of code here
    public int counter_addingElementsArrayOut1 = 0;

    
    // a plain constructor
    public makeCriteria(){
        numWordsSentence = 0;
    }
    /**
     * @desc  this does all the processes at hand to parse things being talked
     *      about.
     * @param inputStatement String of the user's statement to start talking.
     */ 
    public void runMake(String inputStatement){
    
        // run the preproessor to condense multiword phrases that could be more concise.
        
        String inputStatement2 = textLumpPhrasePreprocessor.textLumpPhrasePreprocessor(inputStatement);
        
        System.out.println( "Initial Sentence to be proc: " + inputStatement2);
        
        
        // Create a CoreNLP pipeline. To build the default pipeline, you can just use:
        //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // Here's a more complex setup example:
        //   Properties props = new Properties();
        //   props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse");
        //   props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
        //   props.put("ner.applyNumericClassifiers", "false");
        //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // Add in sentiment
        this.props = new Properties();
        this.props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
        
        
        ArrayList<String> arrayStringList = new ArrayList();
        // start getting parse information
        
   
        String[] bigArrayOfString1 = new String[40];
        int line1 = 0;
        boolean StartReading = false;
        String line1Str;
        try {
            File num1File = new File("temp_file.txt");
             // first extract text from System.out relayed through a printstream to a file and back into an arrayList
            PrintStream output1 = new PrintStream(num1File);
            System.setOut( output1);  
            
            this.pipeline = new StanfordCoreNLP(this.props);

            // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
            this.annotation = new Annotation(inputStatement2);

            // run all the selected Annotators on this text
            this.pipeline.annotate(this.annotation);

            // this prints out the results of sentence analysis to file(s) in good formats
            this.pipeline.prettyPrint(this.annotation, System.out);
            
            
            FileReader inputParseFileAgain = new FileReader(num1File);
            BufferedReader buffread1 = new BufferedReader(inputParseFileAgain);

            while ((line1Str = buffread1.readLine()) != null) {

                if (!StartReading) {
                    // start reading from blank line ...right after grammar figure tree breakdown
                    if (line1Str.matches("")) {
                        StartReading = true;
                    }
                } else {
                    // break at end of the parse given
                    if (line1Str.matches("")) {
                        break;
                    }
                    bigArrayOfString1[line1] = line1Str;
                    line1++;
                }

            }
            // copy items from read array to new smaller array.
            String[] smallerArrayOfString1 = new String[line1];
            int b1 = 0;
            while (b1 < line1) {
                smallerArrayOfString1[b1] = bigArrayOfString1[b1];
                b1++;
            }
            // sort values with static insertion sort method
            this.newArrOut_Sorted = insertionSort.insertionSort(smallerArrayOfString1);
      
     System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
 
    //Iterator itr1 = array1.iterator();

     
      inputParseFileAgain.close();

     } catch (IOException e) {
      e.printStackTrace();
     }
        
        // print what we got sorted ready    of dependency parse trios
        int i = 0;
        while (i < this.newArrOut_Sorted.length) {
            arrayStringList.add(this.newArrOut_Sorted[i]);
            System.out.println(this.newArrOut_Sorted[i]);
            i++;
        }
        
        int stringOf_Number_ArraySplit_Word_Length = 0;
        int stringLengthOf_NumberInToken = 0;
        int tokenLength;
        String[] arrayOfTokenSplit;
        // An Annotation is a Map with Class keys for the linguistic analysis types.
        // You can get and use the various analyses individually.
        // For instance, this gets the parse tree of the first sentence in the text.
        // List<CoreMap> sentences  = annot...
        this.sentences = this.annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : this.sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                
                    // this is the actual token from the sentence
                       // we need to find the length of the numbers element to remove it from the word token of letters itself.
                
                       // find length token
                    tokenLength = token.toString().length();
                
                    arrayOfTokenSplit = token.toString().split("-");
                    stringOf_Number_ArraySplit_Word_Length = arrayOfTokenSplit.length;
                    stringLengthOf_NumberInToken = arrayOfTokenSplit[stringOf_Number_ArraySplit_Word_Length - 1].length();
                    // token in our String array of all tokens.
                    this.actualWords[token.index()] = token.toString().substring(0, tokenLength - stringLengthOf_NumberInToken - 1);
                    
                
                // this is the lemma of the token
                this.lemmasSentence[token.index()] = token.lemma();
                // this is the POS tag of the token
                this.posSentence[token.index()] = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // System.out.println("part of speech is " + pos);
                
                this.numWordsSentence++;
            }
        }
        boolean runOnce = false;
        
        // CoreMap mainsentence = sentences...
        this.mainsentence = this.sentences.get(0);
        // Tree tree for dependency grammar structure
        this.tree = this.mainsentence.get(TreeCoreAnnotations.TreeAnnotation.class);
        
        
        
        Tree DuplicateForVerbCheck = this.tree.deepCopy();
        
        int[] nounAdj_as_root_With_no_other_verb = checkInfinitiveClause_level_Intricacy.check_linking_verb_when_No_root_VB_Classifcation(DuplicateForVerbCheck);
        
        // an important modification of pos of root if it is an adjective when it should really be a verb.
        if(nounAdj_as_root_With_no_other_verb[1] == 1)
            this.posSentence[nounAdj_as_root_With_no_other_verb[0]] = "VB";
        
        // verb informational data objects
        // VerbData[] element1 = new Verb....
        this.element1 = new VerbData[this.numWordsSentence + 1];
        
        // extract verb components
        for(int e = 1; e<this.numWordsSentence; e++){
            if(this.posSentence[e].contains("VB")){
                this.element1[e] = new VerbData();
                // set initializing variables for false concerning
                // ... later conclusion on verb catagory type.
                this.eventResultFlag0 = false;
                this.actionResultFlag0 = false;
            try{
                try {
                    
                    // create a verbData object for the verb labeled.

                    //first: Register JDBC driver
                    Class.forName(ConversationAuto1.JDBC_DRIVER);

                    //STEP 3: Open a connection
                    if(!runOnce){
                        System.out.println("Connecting to a selected database...");
                        
                        System.out.println("Connected database successfully...");

                        //STEP 4: Execute a query
                        System.out.println("Creating statement...");
                        
                        runOnce = true;
                    }
                    //STEP 3: Open a connection
                    Connection conn = DriverManager.getConnection(ConversationAuto1.DB_URL_project, ConversationAuto1.USER_project, ConversationAuto1.PASS_project);
                    //STEP 4: Execute a query
                    Statement stmt = conn.createStatement();

                    this.element1[e] = new VerbData();
                    // set original word for verb in object VerbData
                    this.element1[e].setOrigWord(this.actualWords[e]);
                    // set lemma form of the nlp breakdown classification.
                    this.element1[e].setLemmaForm(this.lemmasSentence[e]);
                    // set pos tag for verb in object VerbData
                    this.element1[e].setPosForm(this.posSentence[e]);
                    // set sentence's word index on entry of verb.
                    this.element1[e].setWordIndex(e);

                    String sql = "select e2_id, futActInd, Action_type from event3_sub where entry = \"" + this.lemmasSentence[e] + "\"";
                    ResultSet rsv = stmt.executeQuery(sql);
                    if (rsv.next()) {
                        int event2_idTag = rsv.getInt("e2_id");
                        int event3_futActIndTag = rsv.getInt("futActInd");
                        int event3_ActionType = rsv.getInt("Action_type");
                        this.eventResultFlag0 = true;
                        // set classification
                        this.element1[e].setClassification("Event");
                        this.element1[e].setFutureActInd(event3_futActIndTag);
                        // this record  will help how to describe what is happening with regard to the verb  so a response can be fit properly to the occurence thus
                        this.element1[e].setActionDetails_Type_of_verb(event3_ActionType);
                        
                        sql = "select e_id, entry from event2_sub where e2_id = " + event2_idTag;
                        rsv = stmt.executeQuery(sql);
                        if (rsv.next()) {
                            // give path to event2 number id, and following event3 entry, etc
                            String event2_0 = rsv.getString("entry");
                            int event1_idTag = rsv.getInt("e_id");
                            // set 2nd level of heirarchy of formed word for verb in object VerbData
                            this.element1[e].setLevel2FormedWord(event2_0);
                            sql = "select entry from event1 where e_id = " + event1_idTag;
                            rsv = stmt.executeQuery(sql);
                            if (rsv.next()) {
                                String event1_0 = rsv.getString("entry");
                                // set 1st level of heirarchy of formed word for verb in object VerbData
                                this.element1[e].setLevel1FormedWord(event1_0);
                                // set path info of word formation
                                this.element1[e].setPathOfForm("/" + event1_idTag + "_" + event1_0 + "/" + event2_0 + "_" + event2_idTag + "/" + this.lemmasSentence[e]);
                            }
                        }
                    }

                    // check in other circumstances, that an event exists in event2_sub table, (given no result in event3_sub table above
                    if (!this.eventResultFlag0) {
                        sql = "select e_id, futActInd, Action_type from event2_sub where entry = \"" + this.lemmasSentence[e] + "\"";
                        rsv = stmt.executeQuery(sql);
                        if (rsv.next()) {
                            this.eventResultFlag0 = true;
                            // find element id in event2 table
                            int event1_idTag = rsv.getInt("e_id");
                            int event2_futActIndTag = rsv.getInt("futActInd");
                            int event2_ActionType = rsv.getInt("Action_type");
                            // set classification
                            this.element1[e].setClassification("Event");
                            this.element1[e].setFutureActInd(event2_futActIndTag);
                            // this record  will help how to describe what is happening with regard to the verb  so a response can be fit properly to the occurence thus
                            this.element1[e].setActionDetails_Type_of_verb(event2_ActionType);

                            sql = "select entry from event1 where e_id = " + event1_idTag;
                            rsv = stmt.executeQuery(sql);
                            if (rsv.next()) {
                                String event1_0 = rsv.getString("entry");
                                // set 1st level of heirarchy of formed word for verb in object VerbData
                                this.element1[e].setLevel1FormedWord(event1_0);
                                // give path to event1 number id
                                this.element1[e].setPathOfForm("/" + event1_idTag + "_" + event1_0 + "/" + this.lemmasSentence[e]);
                            }
                        }
                    }
                    // look into if action exists in acts_proc1 table
                    sql = "select nextID, futActInd, Action_type from acts_proc1 where Description = \"" + this.lemmasSentence[e] + "\"";
                    rsv = stmt.executeQuery(sql);
                    if (rsv.next()) {
                        int acts_proc0_idTag = rsv.getInt("nextID");
                        int acts_proc1_futActIndTag = rsv.getInt("futActInd");
                        int acts_proc1_ActionType = rsv.getInt("Action_type");
                        this.actionResultFlag0 = true;

                        // set whether it is action,... or both an event and an action
                        if (this.eventResultFlag0) {
                            this.element1[e].setClassification("Event and Action");
                        } else {
                            this.element1[e].setClassification("Action");
                            // this record  will help how to describe what is happening with regard to the verb  so a response can be fit properly to the occurence thus
                            this.element1[e].setActionDetails_Type_of_verb(acts_proc1_ActionType);
                        }
                        this.element1[e].setFutureActInd(acts_proc1_futActIndTag);
                        
                        
                        sql = "select pID, Description from acts_proc0 where nextID = " + acts_proc0_idTag;
                        rsv = stmt.executeQuery(sql);
                        if (rsv.next()) {
                            // give path to acts_proc1  proc_id, and following Description, etc
                            String acts_proc0 = rsv.getString("Description");
                            int acts_proc_idTag = rsv.getInt("pID");
                            // set 2nd level of heirarchy of formed word for verb in object VerbData
                            this.element1[e].setLevel2FormedWord(acts_proc0);
                            sql = "select Description from acts_proc where pID = " + acts_proc_idTag;
                            rsv = stmt.executeQuery(sql);
                            if (rsv.next()) {
                                String acts_proc = rsv.getString("Description");
                                // set 1st level of heirarchy of formed word for verb in object VerbData
                                this.element1[e].setLevel1FormedWord(acts_proc);
                                // set path info of word formation
                                this.element1[e].setPathOfForm("/" + acts_proc_idTag + "_" + acts_proc + "/" + acts_proc0 + "_" + acts_proc0_idTag + "/" + this.lemmasSentence[e]);
                            }
                        }
                    }
                    // check in other circumstances, that an action exists in acts_proc0 table (but not in acts_proc1)
                    if (!this.actionResultFlag0) {
                        sql = "select pID, futActInd, Action_type from acts_proc0 where Description = \"" + this.lemmasSentence[e] + "\"";
                        rsv = stmt.executeQuery(sql);
                        if (rsv.next()) {
                            
                            int acts_proc0_ActionType = rsv.getInt("Action_type");
                            
                            this.actionResultFlag0 = true;
                            // set whether it is action,... or both an event and an action
                            if (this.eventResultFlag0) {
                                this.element1[e].setClassification("Event and Action");
                            } else {
                                this.element1[e].setClassification("Action");
                                // this record  will help how to describe what is happening with regard to the verb  so a response can be fit properly to the occurence thus
                                this.element1[e].setActionDetails_Type_of_verb(acts_proc0_ActionType);
                            }

                            // find action proceeding_id in acts_proc table
                            int acts_proc_idTag = rsv.getInt("pID");
                            
                            
                            int acts_proc0_futActIndTag = rsv.getInt("futActInd");
                            
                            
                            this.element1[e].setFutureActInd(acts_proc0_futActIndTag);
                            // this record  will help how to describe what is happening with regard to the verb  so a response can be fit properly to the occurence thus
                            this.element1[e].setActionDetails_Type_of_verb(acts_proc0_ActionType);
                            
                            // look for Description in acts_proc table
                            sql = "select Description from acts_proc where pID = " + acts_proc_idTag;
                            rsv = stmt.executeQuery(sql);
                            if (rsv.next()) {
                                String acts_proc = rsv.getString("Description");
                                // set 1st level of heirarchy of formed word for verb in object VerbData
                                this.element1[e].setLevel1FormedWord(acts_proc);
                                // give path to event1 number id
                                this.element1[e].setPathOfForm("/" + acts_proc_idTag + "_" + acts_proc + "/" + this.lemmasSentence[e]);
                                // if it was also an event, just clear previous highest degree word form i.e. level 2
                                if (this.eventResultFlag0) {
                                    this.element1[e].setLevel2FormedWord("null");
                                }

                            }
                        }
                    }
                    if (!this.actionResultFlag0 && !this.eventResultFlag0) {
                        this.element1[e].setLevel1FormedWord("Unknown");
                    }

                    rsv.close();

                                } catch (SQLException se) {
                    //Handle errors for JDBC
                    se.printStackTrace();
                }
                
            } catch (Exception mx) {
            //Handle errors for Class.forName
            mx.printStackTrace();
            }
            }
        }
        
         /** this is an array of descriptive adjectives,
          * 
          * note: the first 6 rows of x are for the first half of sentence,
          * 
          *  the xth row represents the index of the adjective term word
          * 
          *  the yth column index 0 represents the clause index for the subject or direct/indirect object section extent          
          *  the yth column index 1 represents the index of the descriptive term
          *  the yth column index 2 represents the index of the word modified         
          * 
          * ordered as such,  
          * [subj1] + clause1, +clause2 [verbSect1] + [obj]clause3, + [obj]clause4 [verbSect2] + [obj]clause5 + [obj]clause6, 
          * 
          *     [subj/obj]clause7, + [obj]clause8  are for subordinate clause objects in first part of sentence
          * 
          * [subj2] + clause9, +clause10 [verbSect3] + [obj]clause11, + [obj]clause12 [verbSect4] + [obj]clause13 + [obj]clause14,
          * 
          *     [subj/obj]clause15, + [obj]clause16  are for subordinate clause objects  in secondHalf sentence
          * 
          * from the way this is, there are up to 4 levels of clauses 
          * the clause number goes back to zero with a new root subject after a comma and an and conjunction
          * 
          * an independent subclause will have the code number : 10
          * 
          */
         
         descriptiveAdjectivePhrases = new int[12][3];
         // initialize all adjective phrase terms as null -1 value
         for(int e1 = 0; e1 < 12; e1++){
             descriptiveAdjectivePhrases[e1][0] = -1;
         }
         
         // initialize clause beginning range and end range
         for(int r0 = 0; r0<8; r0++){
             this.clauseInfo_onRange1[r0][0] = 0;
             this.clauseInfo_onRange1[r0][1] = 0;
             
             this.clauseInfo_onRange2[r0][0] = 0;
             this.clauseInfo_onRange2[r0][1] = 0;
         }
         
         
         
        int adjectiveLocationIndex = 0;
        boolean adjectiveSeen = false;
        //noun and descriptive clauses...
        
        //store noun/descriptive item info
        // ObjectData objects1 = new ...
        this.objects1 = new ObjectData[this.numWordsSentence + 1];
        

        
        // int[]
        this.centerSubject1 = new int[6];
        this.centerSubject2 = new int[6];
        
        // integers of subject Items in each half of two independent declarative clauses
        this.numOfSubjectsPrimary_1 = 0;
        this.numOfSubjectsPrimary_2 = 0;
        
        // marks clause section index beginning
        
        // int[]  this.declarativeSectionNotify = new int[6];
        
        
        
        // background dependency parsing
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        //use the tree graph made earlier ...(up several pages)
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
           
       
        
        Iterator itr0 = arrayStringList.iterator();
        // mark beginning of first info clause range index's first
        this.clauseInfo_onRange1[0][0] = 1;
        
        
        runOnce = false;
        String currentString;
        boolean punctuationMark = false;
        boolean to_MarkEncountered = false;
        this.newInfinitives1 = new InfinitiveSect[6];
        // reference to adjective modifier's subject index
        int subjectTerm_Dependent_Near = 0;
        
        // look for nsubj( first one,  which will be our centersubject[0]
          // iterator will be continued later down in code.... too...
        while (itr0.hasNext()) {
            currentString = itr0.next().toString();

            String[] arrayIn = currentString.split(" ");
            int lengthOfDepFragment = arrayIn[arrayIn.length - 1].length();
            String subjectSentence = arrayIn[arrayIn.length - 1];
            // extract int from the word String // core label/ title
            String[] subjectTypedSections1 = subjectSentence.split("-");
            lengthOfDepFragment = subjectTypedSections1.length;
            int numberFragment1 = subjectTypedSections1[lengthOfDepFragment - 1].length();
            int intIndex1 = 0;
               try {
                    intIndex1 = Integer.parseInt(subjectTypedSections1[lengthOfDepFragment - 1].substring(0, numberFragment1 - 1));
                    this.parseable = true;
               } catch (NumberFormatException e) {
                   this.parseable = false;
               }


            String termParseSentence = arrayIn[0];
            // extract int from the governor parse label
            String[] subjectTypedSections2 = termParseSentence.split("-");
            int lengthOfDepFragment2 = subjectTypedSections2.length;
            int numberFragment2 = subjectTypedSections2[lengthOfDepFragment2 - 1].length();
            int intIndex2 = 0; 
                try {
                    intIndex2 = Integer.parseInt(subjectTypedSections2[lengthOfDepFragment2 - 1].substring(0,  numberFragment2 - 1 ));
                    this.parseable = true;
                } catch (NumberFormatException e) {
                   this.parseable = false;
                }
            
            //String[] subjectTypedSections3 = subjectTypedSections2[0].split("(");
            //int lengthOfDepFragment3 = subjectTypedSections3.length;
            //String dependentTerm = subjectTypedSections3[lengthOfDepFragment3 - 1 ];
            
            // check if a punctuation mark is encountered
            if(currentString.contains("punct(") && subjectTypedSections1[0].matches(",")){
                
                if (!punctuationMark) {
                    commaStart[0] = intIndex1;
                    // mark beginning of subclause number 1
                    this.clauseInfo_onRange1[6][0] = intIndex1;
                    // up the counter on subClause in first section
                    clauseSubject_sect_count++;
                }
                else{
                    commaEnd[1] = intIndex1;
                    // mark end of subclause number 1
                    this.clauseInfo_onRange1[6][1] = intIndex1;
                }
                punctuationMark = !punctuationMark;
            }
            
            // seek index of the word that is sentence subject:
            else if (currentString.startsWith("nsubj(") && this.numOfSubjectsPrimary_1 < 1 && !runOnce) {
                this.centerSubject1[0] = intIndex1;
                // store reference nsubject might have to a root adjective term's index
                subjectTerm_Dependent_Near = intIndex2;                          
                this.numOfSubjectsPrimary_1++;
                
                
             //assign object to objectData array of objects
              this.objects1[intIndex1] = new ObjectData();
              this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
              // set original word for verb in object VerbData
              this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
              // set lemma form of the nlp breakdown classification.
              this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
              // set pos tag for verb in object VerbData
              this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);    
                
                
                
                
                
                
                
                if(adjectiveSeen){
                    
                    this.descriptiveAdjectivePhrases[1][0] = 0;
                    this.descriptiveAdjectivePhrases[1][1] = adjectiveLocationIndex;
                    this.descriptiveAdjectivePhrases[1][2] = this.centerSubject1[0];
                    // record this as the first adjective if it occurs
                    
                    adjectiveSeen = false;
                }
                
                //if(itr0.hasNext())
                //    if(itr0.next().toString().startsWith("cc("))
                //lookNowAdditionalAndItem = true;
                
                this.clauseInfo_onRange1[0][0] = intIndex1;
                
                runOnce = true;
                
                
            }
            // seek adjective modifying main subject
            else if (currentString.startsWith("amod(") && !punctuationMark ){
                adjectiveLocationIndex = intIndex1;
                adjectiveSeen = true;
                
            }
            // seek the index of a linking verb, that should essentially be root verb.
            else if(currentString.contains("cop(") && !punctuationMark){
                // extract int from the word String
                this.verbRootItem = intIndex1;
                this.centerVerb1[0] =intIndex1;
                
                // mark end of first info clause range
                this.clauseInfo_onRange1[0][1] = intIndex1;
                
            }
                    
            else if(currentString.contains("root(")){
                if(element1[intIndex1] != null){
                // extract int from the word String
                   this.verbRootItem = intIndex1;
                   this.centerVerb1[0] = intIndex1;
                   // mark end of first info clause range
                   this.clauseInfo_onRange1[0][1] = intIndex1;
                   
                }
                else{
                  
                    if (this.posSentence[intIndex1].contains("DT") || this.posSentence[intIndex1].matches("WDT") || this.posSentence[intIndex1].contains("WP") || this.posSentence[intIndex1].contains("NN") || this.posSentence[intIndex1].matches("PRP") || this.posSentence[intIndex1].contains("PP")) {
                        
                            
                            this.descriptiveAdjectivePhrases[1][0] = 1;
                            this.descriptiveAdjectivePhrases[1][1] = intIndex1;
                            this.descriptiveAdjectivePhrases[1][2] = this.centerSubject1[0];
                            
                            //record if this is the second descriptive item _ a noun this time
                            this.clause0NumAdjectives++;
                            //assign object to objectData array of objects
                            this.objects1[intIndex1] = new ObjectData();
                            this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                            // set original word for verb in object VerbData
                            this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                            // set lemma form of the nlp breakdown classification.
                            this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                            // set pos tag for verb in object VerbData
                            this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                        
                 
                        break;
                    }
                   
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = 1;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = subjectTerm_Dependent_Near;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = this.centerSubject1[0];
                    this.totalClauseElementCount1++;
                    //record if this is the second adjective
                    this.clause0NumAdjectives++;
                  
                    
                }
                
                break;
                
            } 
            else{
                // check if govenor entry of the dependent grammar is a verb or not
                int predicate_verbPresentAtIndexItem_2ndPart;
                if (this.element1[intIndex2] != null) {
                    predicate_verbPresentAtIndexItem_2ndPart = 1;
                } else {
                    predicate_verbPresentAtIndexItem_2ndPart = 0;
                }

                // check for To infinitive phrase start
                if (currentString.contains("mark(") && subjectTypedSections1[0].toLowerCase().matches("to") && predicate_verbPresentAtIndexItem_2ndPart == 1) {
                    to_MarkEncountered = true;
                    this.newInfinitives1[0] = new InfinitiveSect();
                    this.newInfinitives1[0].setStartIndex_of_phrase(intIndex2);
                    this.newInfinitives1[0].setIndex_of_Verb_of_phrase(intIndex2);
                    this.newInfinitives1[0].setTense_of_Verb("present");
                    this.newInfinitives1[0].setEndIndex_of_phrase(intIndex2);
                    
                    
                    this.infinitives1Count++;
                    
                }
                //check for infinitive phrase elements
                
                // look if an auxillary follows to infinitive start
                if (to_MarkEncountered && currentString.contains("aux(")){
                    if(subjectTypedSections1[0].toLowerCase().matches("have")){
                        
                        this.newInfinitives1[0].setTense_of_Verb("perfect");
                        this.newInfinitives1[0].setStartIndex_of_phrase(intIndex1);
                        
                    }
                    else{
                        this.newInfinitives1[0].setTense_of_Verb("present");
                        this.newInfinitives1[0].setStartIndex_of_phrase(intIndex1);
                    }
                    to_MarkEncountered = false;
                    
                }
                // finish and turn off flag
                else if(to_MarkEncountered && element1[intIndex1] != null)
                      to_MarkEncountered = false;
                
                    
                //if(lookNowAdditionalAndItem && currentString.startsWith("CC")) {
            }
            

        }
        // an iterator for later use on evaluating verb predicate background
        ListIterator<TypedDependency> listItr0 = tdl.listIterator();
        ListIterator<TypedDependency> CloneListInput = tdl.listIterator();
        
        
        if(this.centerSubject1[0] == 0 )
            System.out.println("problem,... no noun subject phrase present!  __ excluding passive noun probably");                
            
        
     // need to add original word info  besides lemmasSentence
        // look up subject from mysql data base
        if (this.centerSubject1[0] != 0) {
            // create a new ObjectData for the subject element
            this.objects1[this.centerSubject1[0]] = new ObjectData();
            this.objects1[this.centerSubject1[0]] = ObjectReview.objectAssessment( this.actualWords[this.centerSubject1[0]], this.lemmasSentence[this.centerSubject1[0]], this.posSentence[this.centerSubject1[0]], this.centerSubject1[0]);
            // set original word for verb in object VerbData
            this.objects1[this.centerSubject1[0]].setOrigWord(this.actualWords[this.centerSubject1[0]]);
            // set lemma form of the nlp breakdown classification.
            this.objects1[this.centerSubject1[0]].setlemma(this.lemmasSentence[this.centerSubject1[0]]);
            // set pos tag for verb in object VerbData
            this.objects1[this.centerSubject1[0]].setPosForm(this.posSentence[this.centerSubject1[0]]);
            
        }
        
        // try and discover if there is a second subject to sentence
        int[] otherSubjectsWithBeginning = subjectNounMultiCheck.lookAdditionalSubjects(this.tree, this.centerSubject1[0]);
        
        // set up additional subjects as located in centerSubjects and objects1 ObjectData
        // first look if there is one extra subject
        if (otherSubjectsWithBeginning[0] != 0) {
            this.centerSubject1[1] = otherSubjectsWithBeginning[0];
            this.numOfSubjectsPrimary_1++;
            this.objects1[this.centerSubject1[1]] = new ObjectData();
            this.objects1[this.centerSubject1[1]] = ObjectReview.objectAssessment(this.actualWords[this.centerSubject1[1]], this.lemmasSentence[this.centerSubject1[1]], this.posSentence[this.centerSubject1[1]], this.centerSubject1[1]);
            // set original word for verb in object VerbData
            this.objects1[this.centerSubject1[1]].setOrigWord(this.actualWords[this.centerSubject1[1]]);
            // set lemma form of the nlp breakdown classification.
            this.objects1[this.centerSubject1[1]].setlemma(this.lemmasSentence[this.centerSubject1[1]]);
            // set pos tag for verb in object VerbData
            this.objects1[this.centerSubject1[1]].setPosForm(this.posSentence[this.centerSubject1[1]]);
        }
        // also then look if there is one more following extra subject
        if(otherSubjectsWithBeginning[1] != 0){
            this.centerSubject1[2] = otherSubjectsWithBeginning[1];
            this.numOfSubjectsPrimary_1++;
            this.objects1[this.centerSubject1[2]] = new ObjectData();
            this.objects1[this.centerSubject1[2]] = ObjectReview.objectAssessment(this.actualWords[this.centerSubject1[2]], this.lemmasSentence[this.centerSubject1[2]], this.posSentence[this.centerSubject1[2]], this.centerSubject1[2]);
            // set original word for verb in object VerbData
            this.objects1[this.centerSubject1[2]].setOrigWord(this.actualWords[this.centerSubject1[2]]);
            // set lemma form of the nlp breakdown classification.
            this.objects1[this.centerSubject1[2]].setlemma(this.lemmasSentence[this.centerSubject1[2]]);
            // set pos tag for verb in object VerbData
            this.objects1[this.centerSubject1[2]].setPosForm(this.posSentence[this.centerSubject1[2]]);
        }
        // things as subject    Readout
        //if(this.centerSubject1[0] != 0)
           //System.out.println(this.objects1[this.centerSubject1[0]].getAllInformation() + "subj0");
        if(this.centerSubject1[1] != 0)
           System.out.println(this.objects1[this.centerSubject1[1]].getAllInformation() + "subj1");
        if(this.centerSubject1[2] != 0)
           System.out.println(this.objects1[this.centerSubject1[2]].getAllInformation() + "subj2");
        
        
        
        
        
        String oneTake = VerbReview.VerbAssessment(this.element1, this.centerSubject1[0], listItr0, this.verbRootItem, CloneListInput);
        

        
        // these cover each individual full verb phrase
    /**
     * predicateData is an object containing the following items:
     *
     * private String modalInfo;
     * private String beingAuxillary1;
     * private String beingAuxillary2;
     * boolean modalNot;
     * boolean auxillaryVerbNot;
     * private String mainVerb;
     * private String transitivity;
     * 
     */
         this.accessoryVerbElements1 = new predicateData[8];
         this.accessoryVerbElements2 = new predicateData[8];
       
         this.newInfinitives2 = new InfinitiveSect[6];
         /**
          * the following variable will break down the variable results of
          * oneTake
          * ____VerbReview =
          * modalInfo + " " + notAfterModal +" " + tenseYielded + " " + notAfterAux; 
          * 
          * the first object of predicateData  will be for the first root verb
          *   hence: index 0
          */
         boolean hasBeingAuxillaryInResultsArray = false;
         String[] verbAssessmentResults = oneTake.split(" ");
         
         if(verbAssessmentResults.length == 5)
             hasBeingAuxillaryInResultsArray = true;
         
         else
             // this one is missing the beingAuxillary
             hasBeingAuxillaryInResultsArray = false;
     // first verb from start  holistic look  
         this.accessoryVerbElements1[0] = new predicateData();
         // assign the modal to the predicate phrase object of the sentences root main verb
         this.accessoryVerbElements1[0].setModalInfo(verbAssessmentResults[0]);
         // assign the tense of the main verb to the predicate verb object
         this.accessoryVerbElements1[0].setPrimaryVerb(verbAssessmentResults[2]);
         try {
             // document if there is a "not" with the modal in the predicate phrase
             if (Integer.parseInt(verbAssessmentResults[1]) == 1) 
                 this.accessoryVerbElements1[0].setModalNot(true);
             else 
                 this.accessoryVerbElements1[0].setModalNot(false);
             

             // document if there is a "not" with the verb component itself
             if (Integer.parseInt(verbAssessmentResults[3]) == 1) 
                 this.accessoryVerbElements1[0].setAuxVerbNot(true);
              else 
                 this.accessoryVerbElements1[0].setAuxVerbNot(false);
             
             
             this.parseable = true;
             
            } catch (NumberFormatException e) {
                this.parseable = false;
            }
         
             
         // document "was" "were" "is" "are" or  "am"  as an auxillary being verb
         if(hasBeingAuxillaryInResultsArray)
             this.accessoryVerbElements1[0].setAuxillaryVerb1(verbAssessmentResults[4]);
         
         // reset old runOnce;
         runOnce = false;
         
       // do a quick screen of nonVerb words.
       this.nonVerbObjects = new String[this.numWordsSentence + 1];
       for(int tOne = 1; tOne <= this.numWordsSentence; tOne++){
           
           if(this.element1[tOne] == null)
               this.nonVerbObjects[tOne] = this.posSentence[tOne];
           
               }




        // this will be a variable for any additional verb holistic examination
          // it will be recycled to if need be   with next new verb addition
        String twoTake;

//  note: need to do clauses here and adverb elements!
 
        // now continue iterator from the root just examined
         // look into verbal items after root verb item..  i.e.  a closely following (or conjunctioned) verbal phrase and sub clause
         
         // an iterator for later use on evaluating verb predicate background
         
        ListIterator<TypedDependency> DoubleListItr0 = tdl.listIterator();
        ListIterator<TypedDependency> DoubleCloneListInput = tdl.listIterator();
             
   
        
         boolean runOnce1 = false;
         boolean runOnce2 = false;
         boolean punctuationCommaEncountered = false;
         
         boolean AndClausComma = false;
         //  //pre__boolean WithoutAnd_SubjectClause = false;
         
         
         //boolean for whether the and deals as a pairing with the previous verb or the previous object.
         boolean AndWithObject = false;
        
         
         
         // this will be the dependent grammatical dependency item's index
         // dummy null value that has no importance
         int intIndex1 = 0;
         // this will be the governor grammatical dependency item's index
         
         // dummy null value that has no importance
         int intIndex2 =0;
         
         /** 
           *variable: NounandOrThatFirst
           * which entity came first, "and" or "that" before verb 
           * essentially represents expanded boolean that_MarkEncountered = 16 types;
           *  int = -1 for : nn  verb   _  *
           *      =  0 for :     verb   and   _  *
           *      =  1 for :     verb   and   verb   _  *
           *      =  2 for :     verb  _that  verb   *
           *   
           *      =  3 for : nn  verb  nsubj  _  *
           *      =  4 for :     verb  nsubj   verb  _  *
           *      =  5 for :     verb  nsubj  _that  verb
           *     
           *      =  6 for : nn  verb  nsubj   verb    and  _ *
           * 
           *      =  7 for : nn  verb  nsubj   verb    and  _that  verb
           * 
           *      =  8 for : nn  verb  nsubj   verb  _that   verb           
           * 
           *      =  9 for : nn  verb   and   _that  verb   _ * 
           *      = 10 for :     verb   and    verb  _that  verb
           *
           *      = 11 for : nn  verb  _that   verb    and  _ *
           *      = 12 for :     verb  _that   verb    and  _that  verb
           * 
           *      = 13 for : nn  verb   and   nsubj  verb   _ *
           *      = 14 for :     verb   and   nsubj  verb   _that verb
           *
           *      = 15 for : nn  verb   and    verb  nsubj  _ *           
           *      = 16 for :     verb   and    verb  nsubj   verb  _ *
           *      = 17 for :     verb   and    verb  nsubj  _that  verb             
           *      = 18 for :     verb   and    verb  nsubj   verb  that  *verb
           *
           * //   = 19 omitted
           * //   = 20 omitted
           * //   = 21 omitted
           *
           *            
           * //important about "that"
           * 
           * remark: that can be replaced with that/IN, or a preposition term equivalent like whether/what/whereby/of 
           * 
           * remark: nsubj can be replaced by "nomnitive that"
          **/
    
         int NounandOrThatFirst = -1;
         boolean LastTokenElementWasLinkingVerb = false;
         String posNextTokenFrom_here = "null";
         byte predicate_verbPresentAtIndexItem = 0;
         byte predicate_verbPresentAtIndexItem_2ndPart = 0;
         boolean Predicate_NotFlag = false;
         //boolean andConjunct1 = false;
         
         boolean StartExaminingVerbPhraseHere = false;
         int verbPhraseStart = 0;
         // the second verb id index
         int conjVerbRoot = 0;
         
         boolean and_That_Which_Phrase_VerbGotten = false;
         boolean currentAnd_VerbPhrase_ShouldNotSearch = false;
         
         byte centerVerb1Count = 1;
         byte centerVerb2Count = 0;
         
         byte duplicateCenterVerb1Count = 1;
         byte duplicateCenterVerb2Count = 0;
         
         boolean subsectionActive = false;
         
         String mostCurrentPOS = "null";
         
         boolean verbalConj_And_Present = false;
         
         boolean that_MarkEncountered = false;
         to_MarkEncountered = false;
         int numberOfItemsToSkipInfinitivePhrase = 0;
         
         int lastSubjectForCurrentClause = 0;
         
         String previousWordAnd = "null";
         boolean not_A_SubClauseNow_becauseAnd = false;
         
         boolean newSubjectPresentCurrently = false;
         int infinitiveVerb_index_as_subject_of_predicate = 0;
         
         directObjects_1 = new int[8];
         directObjects_2 = new int[8];
         
         indirectObjects_1 = new int[8];
         indirectObjects_2 = new int[8];
         
         
        while (itr0.hasNext()) {
            currentString = itr0.next().toString();

            String[] arrayIn = currentString.split(" ");
            int lengthOfDepFragment = arrayIn[arrayIn.length - 1].length();
            // extract int from the word String // core label/ title
            String[] subjectTypedSections = arrayIn[arrayIn.length -1].split("-");
            String termParseSentence = subjectTypedSections[0];
            not_A_SubClauseNow_becauseAnd = false;
            lengthOfDepFragment = subjectTypedSections.length;
            int numberFragment1 = subjectTypedSections[lengthOfDepFragment - 1].length();

               try {
                   intIndex1 = Integer.parseInt(subjectTypedSections[lengthOfDepFragment - 1].substring(0, numberFragment1 - 1 ) );
                   this.parseable = true;
                 } catch (NumberFormatException e) {
                    this.parseable = false;
                 }
            
             
            
            // Check whether there is a verb at this dependency grammar item
            if(element1[intIndex1] == null)
                predicate_verbPresentAtIndexItem = 0;
            else
                predicate_verbPresentAtIndexItem = 1;
            
            // find index and string of first govenor dependency grammar parsing item.
            String[] subjectTypedSections2 = arrayIn[0].split("-");
            int lengthOfDepFragment2 = subjectTypedSections2.length;
            // check if governor index of 0 might return a negative index value
            int correctedLenOfStringFragment2 = subjectTypedSections2[lengthOfDepFragment2 - 1].length();
                try {
                    intIndex2 = Integer.parseInt(subjectTypedSections2[lengthOfDepFragment2 - 1].substring(0, correctedLenOfStringFragment2 -1));
                    this.parseable = true;
                } catch (NumberFormatException e) {
                    this.parseable = false;
                }
            
            // check if governor is verb   as extra application for use of conjunctions following later here.
            if(this.element1[intIndex2] == null)
               predicate_verbPresentAtIndexItem_2ndPart = 0;
            else
                predicate_verbPresentAtIndexItem_2ndPart = 1;
            
            // find from index if item is a noun:  noun check heuristic
            mostCurrentPOS = posSentence[intIndex1];
            
            // check if new independent sentence section has begun last iteration with ", and"
            if(previousWordAnd.matches("yes")){
                previousWordAnd = "null";
                not_A_SubClauseNow_becauseAnd = true;
             }
            
            // assign nomnitive linking verb object  to adjectives array
            if(LastTokenElementWasLinkingVerb){
                // set back to false
                LastTokenElementWasLinkingVerb = false;
                
                 if (this.numOfSubjectsPrimary_2 == 0) {
                    
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = centerVerb1Count;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = lastSubjectForCurrentClause;
                        
              // this will tell us the count of the adjective in progression  of verb
                        this.currentAdjClauseNumber[centerVerb1Count] = intIndex1;
                        this.totalClauseElementCount1++;
                 } else {
                    
                    
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][0] = centerVerb2Count;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][2] = lastSubjectForCurrentClause;
                 
                 this.totalClauseElementCount2++;
                 }
                 
                 

                 
                  //assign object to objectData array of objects
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                
             }
            
            
            
     // start: punctuation dealings here : about index of progressive phrases
            
             // in case of clause comma,  check if we have one here
             // , noting a separate clause;
            if(currentString.contains("punct(") && termParseSentence.matches(",") && !punctuationCommaEncountered ){
                
                if(!commaStartBool)
                    commaStart[commaCount] = intIndex1;
                else
                    commaEnd[commaCount] = intIndex1;
                commaCount++;
                commaStartBool = !commaStartBool;
                punctuationCommaEncountered = true;
                if(StartExaminingVerbPhraseHere && !and_That_Which_Phrase_VerbGotten)
                    currentAnd_VerbPhrase_ShouldNotSearch = true;
                // go straight to next index's label in iterator.
                continue;
                
            }
                
            // the event that an ", and"  happens with the comma from last parse dependency item
            else if (punctuationCommaEncountered && currentString.contains("cc(") && termParseSentence.toLowerCase().matches("and") ) {
               
                
                // check if governor term of conjunction parse tag is verb or not.
                if(predicate_verbPresentAtIndexItem_2ndPart == 1)
                      verbalConj_And_Present = true;
                else
                      verbalConj_And_Present = false;
                
               //note that a new independent progression of the sentence has begun
                AndClausComma = true;
                // mark for next iteration
                previousWordAnd = "yes";
              
                
                
                // check for : nn verb _ *  
                if(NounandOrThatFirst == -1 ){
                    // assign case of nn verb and _*
                    NounandOrThatFirst = 0;
                }
                // check for : nn verb that verb _ *  
                if(NounandOrThatFirst == 2 ){
                    // assign case of nn verb that verb and _*
                    NounandOrThatFirst = 11;
                }
                // check for : nn verb nsubjj verb _ * 
                if(NounandOrThatFirst == 4 ){
                    NounandOrThatFirst = 6;
                }
                
                // as preliminary procedure, set start of new conjunctioned independ verb clause
                verbPhraseStart = intIndex1;
                // go straight to next index's label in iterator.
                continue;
            } 
            
         // clause items   
            // after AndClausComma is true, "this can be run once, and only once "; 
               // otherwise the next following decision if  would be followed     and only handled through comma protocal rules.
            else if (punctuationCommaEncountered && currentString.contains("nsubj(") && AndClausComma && !that_MarkEncountered && !runOnce2) {
                
            // classes general flag noter  that a separate section of the sentence takes place   i.e. I went there  ,__ and a person told me something.
                this.commaAndClause = true;
                
                verbPhraseStart = intIndex1;
                
                if(verbalConj_And_Present){
                  // set new subsentence centerSubject2 spot;
                  this.centerSubject2[0] = intIndex1;               
                  this.numOfSubjectsPrimary_2++;
                
                  this.clauseInfo_onRange2[0][0] = intIndex1;
                  this.clauseSubject_sect_count = 0;
                }
                
                StartExaminingVerbPhraseHere = true;
                
                
                // run only once by activating flag runonce2
                runOnce2 = true;
                
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                
                newSubjectPresentCurrently = true;
                
                lastSubjectForCurrentClause = intIndex1;
                
                // go straight to next index's label in iterator.
                continue;
                    } 
            
            // the event that an ", nsubj"  happens [but without any __ "and"  proceeding it] with the comma from last parse dependency item
            else if ( currentString.contains("nsubj(") && !that_MarkEncountered && !AndClausComma && !not_A_SubClauseNow_becauseAnd) {
                
                
                verbPhraseStart = intIndex1;
              
             
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);   
                
                
                if (punctuationCommaEncountered) {
                    subsectionActive = true;

                    // record sub clause subject   for either sect 1 or sect2
                    if (this.numOfSubjectsPrimary_2 == 0) {
                        if (clauseSubject_sect_count == 0) // first subsection in sect 1
                        {
                            this.clauseInfo_onRange1[6][0] = intIndex1;
                        } else {
                            this.clauseInfo_onRange1[7][0] = intIndex1;
                        }
                    } else {
                        // first subsection in sect 2
                        if (clauseSubject_sect_count == 0) {
                            this.clauseInfo_onRange2[6][0] = intIndex1;
                        } else {
                            this.clauseInfo_onRange2[7][0] = intIndex1;
                        }
                    }
                }
                else {
                    // record sub clause subject   for either sect 1 or sect2
                    if (this.numOfSubjectsPrimary_2 == 0) {
                        // first section of sentence
                        this.clauseInfo_onRange1[centerVerb1Count][0] = intIndex1;
                    } else {
                        // second section of sentence
                        this.clauseInfo_onRange2[centerVerb2Count][0] = intIndex1;
                    }
                }
                
                
                    StartExaminingVerbPhraseHere = true;
                     // check for : nn verb _ *
                     if(NounandOrThatFirst == -1)
                         NounandOrThatFirst = 3;
                     // check for : nn verb and _ *
                     else if(NounandOrThatFirst == 0)
                         NounandOrThatFirst = 13;
                     // check for : nn verb and verb _*
                     else if(NounandOrThatFirst == 1)
                         NounandOrThatFirst = 16;
               
                newSubjectPresentCurrently = true;
                
                
                if ((mostCurrentPOS.matches("WDT") || mostCurrentPOS.contains("WP") || mostCurrentPOS.matches("DT") || mostCurrentPOS.contains("NN") || mostCurrentPOS.matches("PRP") || mostCurrentPOS.contains("PP"))) 
                    // start evaluating if and,/that,/, or which PHrase has a main verb.
                    and_That_Which_Phrase_VerbGotten = false;

                
                lastSubjectForCurrentClause = intIndex1;
                
                // go straight to next index's label in iterator.
                continue;
                
           }
         // end clause items
            
            // Conclude comma clause with last , noting end of separate clause;
            else if(currentString.contains("punct(") && termParseSentence.matches(",") && punctuationCommaEncountered ){
                punctuationCommaEncountered = false;
                
                verbPhraseStart = intIndex1;
                
                if(StartExaminingVerbPhraseHere && !and_That_Which_Phrase_VerbGotten)
                    currentAnd_VerbPhrase_ShouldNotSearch = true;
                
          // needs work to interpret later  sub clause organization
                subsectionActive = false;
                // go straight to next index's label in iterator.
                continue;
            }
            
   // !! needs work             // maybe do a verb assessment here
   
   
   
   // end of : punctuation dealings here : about index of progressive phrases
               
          // brief conjunction encountered here    [meaning __"and"   without any comma involved]
            // seek index of the word that is sentence subject:
            else if (currentString.contains("cc(") && termParseSentence.toLowerCase().matches("and") ) {
               
                                // check if governor term of conjunction parse tag is verb or not.
                if (predicate_verbPresentAtIndexItem_2ndPart == 1) {
                    
                    verbPhraseStart = intIndex1;
                    
                    verbalConj_And_Present = true;
                    // check for : nn verb _ *
                    if (NounandOrThatFirst == -1) {
                        // assign case of nn verb and _*
                        NounandOrThatFirst = 0;
                    }
                    // check for : nn verb that verb _ *  
                    if (NounandOrThatFirst == 2) {
                        // assign case of nn verb that verb and _*
                        NounandOrThatFirst = 11;
                    }
                    // check for : nn verb nsubjj verb _ * 
                    if (NounandOrThatFirst == 4) {
                        NounandOrThatFirst = 6;
                    }
                    
                    StartExaminingVerbPhraseHere = true;
                    
                } else
                      verbalConj_And_Present = false;
             
                
                // go straight to next index's label in iterator.
                continue;
               
            }

            else if (to_MarkEncountered && ( currentString.contains("aux(") || ( currentString.contains("neg(") && predicate_verbPresentAtIndexItem_2ndPart == 1) )  ) {
                // there is still one primary element left, and maybe more than one.
                numberOfItemsToSkipInfinitivePhrase = 1;
                
                if (this.numOfSubjectsPrimary_2 == 0) {
                    if (termParseSentence.toLowerCase().matches("have")) {
                        this.newInfinitives1[this.duplicateInfinit1Count] = new InfinitiveSect();
                        this.newInfinitives1[this.duplicateInfinit1Count].setTense_of_Verb("perfect");
                        this.newInfinitives1[this.duplicateInfinit1Count].setStartIndex_of_phrase(intIndex1);

                    } else {
                        this.newInfinitives1[this.duplicateInfinit1Count] = new InfinitiveSect();
                        this.newInfinitives1[this.duplicateInfinit1Count].setTense_of_Verb("present");
                        this.newInfinitives1[this.duplicateInfinit1Count].setStartIndex_of_phrase(intIndex1);
                    }

                }
                else {
                    if (termParseSentence.toLowerCase().matches("have")) {
                        this.newInfinitives2[this.duplicateInfinit2Count] = new InfinitiveSect();
                        this.newInfinitives2[this.duplicateInfinit2Count].setTense_of_Verb("perfect");
                        this.newInfinitives2[this.duplicateInfinit2Count].setStartIndex_of_phrase(intIndex1);

                    } else {
                        this.newInfinitives2[this.duplicateInfinit2Count] = new InfinitiveSect();
                        this.newInfinitives2[this.duplicateInfinit2Count].setTense_of_Verb("present");
                        this.newInfinitives2[this.duplicateInfinit2Count].setStartIndex_of_phrase(intIndex1);
                    }

                }
                    
                continue;
            }
            // skip over primary verb of infinitive phrase in and/that clause
            else if (to_MarkEncountered && predicate_verbPresentAtIndexItem_2ndPart == 1  && numberOfItemsToSkipInfinitivePhrase > 0) {
                to_MarkEncountered = false;
                numberOfItemsToSkipInfinitivePhrase--;
                // set infinitive term as subject here
                if(!newSubjectPresentCurrently)
                   lastSubjectForCurrentClause = intIndex1;
                // set a checker for subject noun being there in this area
                this.startProbingForSubject_toPredicate = true;
                // set this up in case it is needed when no noun can be a subject
                infinitiveVerb_index_as_subject_of_predicate = intIndex1;
                continue;
            }
            // a slightly closer beginning to verb phrase with root verb:  the boolean information given to verbPhraseStart
             //  will be passed on to the VerbAssessment that finds tense and auxilary components of the verb clause.
            //
            // note that verbs in an infinitive are not of interest to the verbAssessment pursued.
            else if (!runOnce1 && !to_MarkEncountered && ( currentString.contains("aux(") || ( currentString.contains("neg(") && predicate_verbPresentAtIndexItem_2ndPart == 1) )  ) {
                verbPhraseStart = intIndex1;
                runOnce1 = true;
            }
          // !nounStartedWithoutThat
            // this is root element only if the dependency labeled root is not a verb but an adjective
            // andConjuct
            else if (StartExaminingVerbPhraseHere && ( currentString.contains("cop(")  ||  ( currentString.contains("acl:relcl(") && predicate_verbPresentAtIndexItem == 1  ) || ( currentString.contains("ccomp(") && predicate_verbPresentAtIndexItem == 1  ) ) ){
                // note index of local main / root  verb
                conjVerbRoot = intIndex1;
                
                // turn off independent subclause
                if(subsectionActive){
                    
                    // increase clause section number here
                    this.clauseSubject_sect_count++;
                    
                    // record sub clause subject   for either sect 1 or sect2
                    if (this.numOfSubjectsPrimary_2 == 0) {
                        if (clauseSubject_sect_count == 0) // first subsection in sect 1
                        {
                            this.clauseInfo_onRange1[6][0] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 5
                            duplicateCenterVerb1Count = 5;
                        } else {
                            this.clauseInfo_onRange1[7][0] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 6
                            duplicateCenterVerb1Count = 6;
                        }
                    } else {
                        // first subsection in sect 2
                        if (clauseSubject_sect_count == 0) {
                            this.clauseInfo_onRange2[6][0] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 5
                            duplicateCenterVerb2Count = 5;
                        } else {
                            this.clauseInfo_onRange2[7][0] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 6
                            duplicateCenterVerb2Count = 6;
                        }
                    }
                    
                }
                
                
                // case that verb is part of first verb section of declarative sentence.
                if(this.numOfSubjectsPrimary_2 == 0 && !subsectionActive){
                    duplicateCenterVerb1Count = centerVerb1Count;
                     
                    // check if "nn verb and _*" condition holds
                    if (NounandOrThatFirst == 0) {
                        
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // essentially it is now "nn verb  and verb _*"
                        NounandOrThatFirst = 1;
                    }
                    
                    // check if "verb _that verb _*" condition holds
                    else if (NounandOrThatFirst == 2) {
                        // essentially it is now the same"nn verb _that verb _*"  no change
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // keep nounVariable where it is

                    } 
                    // check if "nn verb nsubj _*" condition holds
                    else if (NounandOrThatFirst == 3) {
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // essentially it is now "nn verb nsubj verb _*"
                        NounandOrThatFirst = 4;

                    }  
                    // check if "verb  nsubj  _that  verb"  condition holds
                    else if(NounandOrThatFirst == 5){
                        // essentially it is now the same "verb  nsubj  _that  verb"  no change_ 
                          //from here:no more branching to be here though
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                        }
                    // check if "nn verb nsubj verb and _ *" condition holds
                    else if (NounandOrThatFirst == 6) {
                        // essentially it is now  "nn verb nsubj verb and verbs " no more branching to be here though
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    }
                    // check if  "nn  verb  nsubj   verb  and  _that  *verb "      holds
                    else if (NounandOrThatFirst == 7) {
                        // essentially it is now  the same "nn  verb  nsubj   verb  and  _that  *verb " no change_ 
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    } 
                    // check if  "nn  verb  nsubj   verb  _that   verb"  holds
                    else if (NounandOrThatFirst == 8) {
                        // essentially it is the same.
                        this.centerVerb1[centerVerb1Count] = intIndex1;

                    } 
                    // check if  "nn  verb  and _that   verb _ *"  holds
                    else if (NounandOrThatFirst == 9) {
                        // essentially it is the same.
                        this.centerVerb1[centerVerb1Count] = intIndex1;

                    }
                    // check if  "nn  verb  _that verb and _ *"  holds
                    else if (NounandOrThatFirst == 11) {
                        // essentially it is now   "nn  verb  _that verb and verb*"
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  "nn verb  _that  verb 2 and  _that  verb"    condition holds
                    else if(NounandOrThatFirst == 12){
                        // essentially it is now   "nn verb  _that   verb    and  _that  verb"
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and nsubj  verb   _ *"  condition holds
                    else if(NounandOrThatFirst == 13){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb   _ *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and  nsubj  verb _that verb *"  condition holds
                    else if(NounandOrThatFirst == 14){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb _that verb *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj   verb  _ *
                    else if(NounandOrThatFirst == 16){
                        // essentially it is now  the same "nn  verb   and  verb nsubj  verb *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj _that  verb  _ *"
                    else if(NounandOrThatFirst == 17){
                        // essentially it is now  the same " nn verb   and  verb  nsubj _that  verb  _ *"  no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                       // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    
                    
                    if(this.startProbingForSubject_toPredicate)
                        if(!newSubjectPresentCurrently){
                            clauseInfo_onRange1[centerVerb1Count][0] = infinitiveVerb_index_as_subject_of_predicate;
                            // mark end of clause
                            clauseInfo_onRange1[centerVerb1Count][1] = intIndex1;
                            infinitive1IsSubject_of_predicate = true;
                            infinitiveVerb_index_as_subject_of_predicate = 0;
                        }
                    
                    
                     // increase centerVerb1Count
                      centerVerb1Count++; 
                }
                // case that verb is part of second verb section of declarative sentence.
                else if (!subsectionActive){
                    duplicateCenterVerb2Count = centerVerb2Count;
                    // check if "nn verb and _*" condition holds
                    if (NounandOrThatFirst == 0) {
                        
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // essentially it is now "nn verb  and verb _*"
                        NounandOrThatFirst = 1;
                    }
                    
                    // check if "verb _that verb _*" condition holds
                    else if (NounandOrThatFirst == 2) {
                        // essentially it is now the same"nn verb _that verb _*"  no change
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // keep nounVariable where it is

                    } 
                    // check if "nn verb nsubj _*" condition holds
                    else if (NounandOrThatFirst == 3) {
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // essentially it is now "nn verb nsubj verb _*"
                        NounandOrThatFirst = 4;

                    }  
                    // check if "verb  nsubj  _that  verb"  condition holds
                    else if(NounandOrThatFirst == 5){
                        // essentially it is now the same "verb  nsubj  _that  verb"  no change_ 
                          //from here:no more branching to be here though
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                        }
                    // check if "nn verb nsubj verb and _ *" condition holds
                    else if (NounandOrThatFirst == 6) {
                        // essentially it is now  "nn verb nsubj verb and verbs " no more branching to be here though
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    }
                    // check if  "nn  verb  nsubj   verb  and  _that  *verb "      holds
                    else if (NounandOrThatFirst == 7) {
                        // essentially it is now  the same "nn  verb  nsubj   verb  and  _that  *verb " no change_ 
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    } 
                    // check if  "nn  verb  nsubj   verb  _that   verb"  holds
                    else if (NounandOrThatFirst == 8) {
                        // essentially it is the same.
                        this.centerVerb2[centerVerb2Count] = intIndex1;

                    } 
                    // check if  "nn  verb  and _that   verb _ *"  holds
                    else if (NounandOrThatFirst == 9) {
                        // essentially it is the same.
                        this.centerVerb2[centerVerb2Count] = intIndex1;

                    }
                    // check if  "nn  verb  _that verb and _ *"  holds
                    else if (NounandOrThatFirst == 11) {
                        // essentially it is now   "nn  verb  _that verb and verb*"
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  "nn verb  _that  verb 2 and  _that  verb"    condition holds
                    else if(NounandOrThatFirst == 12){
                        // essentially it is now   "nn verb  _that   verb    and  _that  verb"
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and nsubj  verb   _ *"  condition holds
                    else if(NounandOrThatFirst == 13){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb   _ *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and  nsubj  verb _that verb *"  condition holds
                    else if(NounandOrThatFirst == 14){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb _that verb *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj   verb  _ *
                    else if(NounandOrThatFirst == 16){
                        // essentially it is now  the same "nn  verb   and  verb nsubj  verb *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj _that  verb  _ *"
                    else if(NounandOrThatFirst == 17){
                        // essentially it is now  the same " nn verb   and  verb  nsubj _that  verb  _ *"  no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    
                    if(this.startProbingForSubject_toPredicate)
                        if(!newSubjectPresentCurrently){
                            clauseInfo_onRange2[centerVerb2Count][0] = infinitiveVerb_index_as_subject_of_predicate;
                            // mark end of clause
                            clauseInfo_onRange2[centerVerb2Count][1] = intIndex1;
                            
                            infinitiveVerb_index_as_subject_of_predicate = 0;
                        }
                    
                    
                    // increase centerVerb2Count
                    centerVerb2Count++;
                 } 
                
                                
                
                // find out tense and auxilary info on verbs of root centerVerb2.
             // to call the VerbAssessment, the last main verb index must be assured to be an actual verb 
                twoTake = VerbReview.VerbAssessment(this.element1, verbPhraseStart, DoubleListItr0, intIndex1, DoubleCloneListInput);
                
               
               // break the output from VerbAssessment (which informs us of conditions and tense of the verbs)
               //   up into segments of terms
               verbAssessmentResults = twoTake.split(" ");
               
                if (verbAssessmentResults.length == 5)
                    hasBeingAuxillaryInResultsArray = true;
                else // this one is missing the beingAuxillary
                
                    hasBeingAuxillaryInResultsArray = false;
                
               //accessoryVerbElements1
               if(this.numOfSubjectsPrimary_2 == 0){
                   // construct a new predicate data object
                   this.accessoryVerbElements1[duplicateCenterVerb1Count] = new predicateData();
                   // set modal info to accessoryVerbElements predicateData array
                   this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalInfo(verbAssessmentResults[0]);
                   
                   // set modal not info to accessoryVerbElements
                   int individVerbInt1 = 0;
                   int individVerbInt2 = 0;
                       try {
                           individVerbInt1 =Integer.parseInt(verbAssessmentResults[1]);
                           individVerbInt2 = Integer.parseInt(verbAssessmentResults[3]);
                           this.parseable = true;
                         } catch (NumberFormatException e) {
                           this.parseable = false;
                       }
                   if(individVerbInt1 == 1)  
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalNot(true);
                   else
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalNot(false);
                     
                   // set primary verb information to accessoryVerbElements
                   this.accessoryVerbElements1[duplicateCenterVerb1Count].setPrimaryVerb(verbAssessmentResults[2]);
                   
                   // set auxillary/Verb not info to accessoryVerbElements
                   if(individVerbInt2 == 1)  
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setAuxVerbNot(true);
                   else
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setAuxVerbNot(false);
                
                   // document "was" "were" "is" "are" or  "am"  as an auxillary being verb
                   if(hasBeingAuxillaryInResultsArray)
                     this.accessoryVerbElements1[0].setAuxillaryVerb1(verbAssessmentResults[4]);
                   
                   //duplicateCenterVerb1Count++;
                   
                   
                   
               }
               // condition that we are on the second section of the sentence,  from " and, "
               else {
                   this.accessoryVerbElements2[duplicateCenterVerb2Count] = new predicateData();
                   // set modal info to accessoryVerbElements predicateData array
                   this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalInfo(verbAssessmentResults[0]);
                   // set modal not info to accessoryVerbElements
                   int individVerbInt1 = 0;
                   int individVerbInt2 = 0;
                       try {
                           individVerbInt1 =Integer.parseInt(verbAssessmentResults[1]);
                           individVerbInt2 = Integer.parseInt(verbAssessmentResults[3]);
                           this.parseable = true;
                        } catch (NumberFormatException e) {
                          this.parseable = false;
                        }
                   if(individVerbInt1 == 1)  
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalNot(true);
                   else
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalNot(false);
                     
                   // set primary verb information to accessoryVerbElements
                   this.accessoryVerbElements2[duplicateCenterVerb2Count].setPrimaryVerb(verbAssessmentResults[2]);
                   
                   // set auxillary/Verb not info to accessoryVerbElements
                   if(individVerbInt2 == 1)  
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setAuxVerbNot(true);
                   else
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setAuxVerbNot(false);
                   
                   // document "was" "were" "is" "are" or  "am"  as an auxillary being verb
                   if(hasBeingAuxillaryInResultsArray)
                     this.accessoryVerbElements1[duplicateCenterVerb2Count].setAuxillaryVerb1(verbAssessmentResults[4]);
                   
                   //duplicateCenterVerb2Count++;
               }
                        // this overall flag for subdependent comma sub clauses
                   subsectionActive = false;
                   
               // check if linking verb might involve a noun
            if(this.numWordsSentence <= intIndex1 + 1 && element1[intIndex1].getfirstLevelFormedWord().matches("be")){
               posNextTokenFrom_here = this.posSentence[intIndex1+ 1];
            } else{
               posNextTokenFrom_here = "null" ;
            }
                
            // a linking verb refers to a noun, not an adjective
            if(posNextTokenFrom_here.contains("NN") || posNextTokenFrom_here.matches("PRP") || posNextTokenFrom_here.contains("PP")){
                LastTokenElementWasLinkingVerb = true;
            }
               
                StartExaminingVerbPhraseHere = false;
                that_MarkEncountered = false;
                
                AndClausComma = false;
                
                newSubjectPresentCurrently = false;
                
                this.startProbingForSubject_toPredicate = false;
                
                and_That_Which_Phrase_VerbGotten = true;
                currentAnd_VerbPhrase_ShouldNotSearch = false;
                
                
                runOnce1 = false;
                
                // go straight to next index's label in iterator.
                continue;
            }
          // !nounStartedWithoutThat   
          
         //  Normal Verbs         Not referring with an linking verb's adjective as root
            else if ( StartExaminingVerbPhraseHere && (  currentString.contains("conj:and(")  && predicate_verbPresentAtIndexItem == 1  ) ){
                
                conjVerbRoot = intIndex1;
                
                // turn off independent subclause
                if(subsectionActive){
                    
                    // increase clause section number here
                    this.clauseSubject_sect_count++;
                    
                    // record sub clause subject   for either sect 1 or sect2
                    if (this.numOfSubjectsPrimary_2 == 0) {
                        if (clauseSubject_sect_count == 0) // first subsection in sect 1
                        {
                            this.clauseInfo_onRange1[6][1] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 5
                            duplicateCenterVerb1Count = 5;
                        } else {
                            this.clauseInfo_onRange1[7][1] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 6
                            duplicateCenterVerb1Count = 6;
                        }
                    } else {
                        // first subsection in sect 2
                        if (clauseSubject_sect_count == 0) {
                            this.clauseInfo_onRange2[6][1] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 5
                            duplicateCenterVerb2Count = 5;
                        } else {
                            this.clauseInfo_onRange2[7][1] = intIndex1;
                            // record down verb info for predicate holistic evaluation   verb count for sub clause is coded 6
                            duplicateCenterVerb2Count = 6;
                        }
                    }
                    
                }
                
                
                // case that verb is part of first verb section of declarative sentence.
                if(this.numOfSubjectsPrimary_2 == 0){
                    // make a copy of the centerVerb1 count variable
                    duplicateCenterVerb1Count = centerVerb1Count;
                    // check if "nn verb and _*" condition holds
                    if (NounandOrThatFirst == 0) {
                        
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // essentially it is now "nn verb  and verb _*"
                        NounandOrThatFirst = 1;
                    }
                    
                    // check if "verb _that verb _*" condition holds
                    else if (NounandOrThatFirst == 2) {
                        // essentially it is now the same"nn verb _that verb _*"  no change
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // keep nounVariable where it is

                    } 
                    // check if "nn verb nsubj _*" condition holds
                    else if (NounandOrThatFirst == 3) {
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // essentially it is now "nn verb nsubj verb _*"
                        NounandOrThatFirst = 4;

                    }  
                    // check if "verb  nsubj  _that  verb"  condition holds
                    else if(NounandOrThatFirst == 5){
                        // essentially it is now the same "verb  nsubj  _that  verb"  no change_ 
                          //from here:no more branching to be here though
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                        }
                    // check if "nn verb nsubj verb and _ *" condition holds
                    else if (NounandOrThatFirst == 6) {
                        // essentially it is now  "nn verb nsubj verb and verbs " no more branching to be here though
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    }
                    // check if  "nn  verb  nsubj   verb  and  _that  *verb "      holds
                    else if (NounandOrThatFirst == 7) {
                        // essentially it is now  the same "nn  verb  nsubj   verb  and  _that  *verb " no change_ 
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    } 
                    // check if  "nn  verb  nsubj   verb  _that   verb"  holds
                    else if (NounandOrThatFirst == 8) {
                        // essentially it is the same.
                        this.centerVerb1[centerVerb1Count] = intIndex1;

                    } 
                    // check if  "nn  verb  and _that   verb _ *"  holds
                    else if (NounandOrThatFirst == 9) {
                        // essentially it is the same.
                        this.centerVerb1[centerVerb1Count] = intIndex1;

                    }
                    // check if  "nn  verb  _that verb and _ *"  holds
                    else if (NounandOrThatFirst == 11) {
                        // essentially it is now   "nn  verb  _that verb and verb*"
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  "nn verb  _that  verb 2 and  _that  verb"    condition holds
                    else if(NounandOrThatFirst == 12){
                        // essentially it is now   "nn verb  _that   verb    and  _that  verb"
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and nsubj  verb   _ *"  condition holds
                    else if(NounandOrThatFirst == 13){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb   _ *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and  nsubj  verb _that verb *"  condition holds
                    else if(NounandOrThatFirst == 14){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb _that verb *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj   verb  _ *
                    else if(NounandOrThatFirst == 16){
                        // essentially it is now  the same "nn  verb   and  verb nsubj  verb *"   no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj _that  verb  _ *"
                    else if(NounandOrThatFirst == 17){
                        // essentially it is now  the same " nn verb   and  verb  nsubj _that  verb  _ *"  no change_
                        this.centerVerb1[centerVerb1Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    
                    if(this.startProbingForSubject_toPredicate)
                        if(!newSubjectPresentCurrently){
                            clauseInfo_onRange1[centerVerb1Count][0] = infinitiveVerb_index_as_subject_of_predicate;
                            // mark end of clause
                            clauseInfo_onRange1[centerVerb1Count][1] = intIndex1;
                            
                            infinitiveVerb_index_as_subject_of_predicate = 0;
                        }
                    
                    
                    // increase centerVerb1Count
                    centerVerb1Count++;
                    
                    
                    
                }
                // case that verb is part of first verb section of declarative sentence.
                else {
                    duplicateCenterVerb2Count = centerVerb2Count;
                    
                    // check if "nn verb and _*" condition holds
                    if (NounandOrThatFirst == 0) {
                        
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // essentially it is now "nn verb  and verb _*"
                        NounandOrThatFirst = 1;
                    }
                    
                    // check if "verb _that verb _*" condition holds
                    else if (NounandOrThatFirst == 2) {
                        // essentially it is now the same"nn verb _that verb _*"  no change
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // keep nounVariable where it is

                    } 
                    // check if "nn verb nsubj _*" condition holds
                    else if (NounandOrThatFirst == 3) {
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // essentially it is now "nn verb nsubj verb _*"
                        NounandOrThatFirst = 4;

                    }  
                    // check if "verb  nsubj  _that  verb"  condition holds
                    else if(NounandOrThatFirst == 5){
                        // essentially it is now the same "verb  nsubj  _that  verb"  no change_ 
                          //from here:no more branching to be here though
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                        }
                    // check if "nn verb nsubj verb and _ *" condition holds
                    else if (NounandOrThatFirst == 6) {
                        // essentially it is now  "nn verb nsubj verb and verbs " no more branching to be here though
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    }
                    // check if  "nn  verb  nsubj   verb  and  _that  *verb "      holds
                    else if (NounandOrThatFirst == 7) {
                        // essentially it is now  the same "nn  verb  nsubj   verb  and  _that  *verb " no change_ 
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                        // Add verb to data,  but no modification of nounVar since no more numbers for this depth level

                    } 
                    // check if  "nn  verb  nsubj   verb  _that   verb"  holds
                    else if (NounandOrThatFirst == 8) {
                        // essentially it is the same.
                        this.centerVerb2[centerVerb2Count] = intIndex1;

                    } 
                    // check if  "nn  verb  and _that   verb _ *"  holds
                    else if (NounandOrThatFirst == 9) {
                        // essentially it is the same.
                        this.centerVerb2[centerVerb2Count] = intIndex1;

                    }
                    // check if  "nn  verb  _that verb and _ *"  holds
                    else if (NounandOrThatFirst == 11) {
                        // essentially it is now   "nn  verb  _that verb and verb*"
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    
                    // check if  "nn verb  _that  verb 2 and  _that  verb"    condition holds
                    else if(NounandOrThatFirst == 12){
                        // essentially it is now   "nn verb  _that   verb    and  _that  verb"
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and nsubj  verb   _ *"  condition holds
                    else if(NounandOrThatFirst == 13){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb   _ *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if "nn  verb   and  nsubj  verb _that verb *"  condition holds
                    else if(NounandOrThatFirst == 14){
                        // essentially it is now  the same "nn  verb   and   nsubj  verb _that verb *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj   verb  _ *
                    else if(NounandOrThatFirst == 16){
                        // essentially it is now  the same "nn  verb   and  verb nsubj  verb *"   no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    // check if  " nn verb   and  verb  nsubj _that  verb  _ *"
                    else if(NounandOrThatFirst == 17){
                        // essentially it is now  the same " nn verb   and  verb  nsubj _that  verb  _ *"  no change_
                        this.centerVerb2[centerVerb2Count] = intIndex1;
                    // Add verb to data,  but no modification of nounVar since no more numbers for this depth level
                    }
                    
                    
                    if(this.startProbingForSubject_toPredicate)
                        if(!newSubjectPresentCurrently){
                            clauseInfo_onRange2[centerVerb2Count][0] = infinitiveVerb_index_as_subject_of_predicate;
                            // mark end of clause
                            clauseInfo_onRange2[centerVerb2Count][1] = intIndex1;
                            
                            infinitiveVerb_index_as_subject_of_predicate = 0;
                        }
                    
                    // increase centerVerb2Count
                    centerVerb2Count++;
                    
                 }
                
               
               // find out tense and auxilary info on verbs of root centerVerb2.
         // to call the VerbAssessment, the last main verb index must be assured to be an actual verb
                twoTake = VerbReview.VerbAssessment(this.element1, verbPhraseStart, DoubleListItr0, intIndex1, DoubleCloneListInput);
                
               
               // break the output from VerbAssessment (which informs us of conditions and tense of the verbs)
               //   up into segments of terms
               verbAssessmentResults = twoTake.split(" ");
               
                if (verbAssessmentResults.length == 5)
                    hasBeingAuxillaryInResultsArray = true;
                else // this one is missing the beingAuxillary
                
                    hasBeingAuxillaryInResultsArray = false;
               
               //accessoryVerbElements1
               if(this.numOfSubjectsPrimary_2 == 0){
                   // construct a new predicate data object
                   this.accessoryVerbElements1[duplicateCenterVerb1Count] = new predicateData();
                   // set modal info to accessoryVerbElements predicateData array
                   this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalInfo(verbAssessmentResults[0]);
                   // set modal not info to accessoryVerbElements
                   int individVerbInt1 = 0;
                   int individVerbInt2 = 0;
                       try {
                           individVerbInt1 =Integer.parseInt(verbAssessmentResults[1]);
                           individVerbInt2 = Integer.parseInt(verbAssessmentResults[3]);
                           this.parseable = true;
                       } catch (NumberFormatException e) {
                           this.parseable = false;
                       }
                   if(individVerbInt1 == 1)  
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalNot(true);
                   else
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setModalNot(false);
                     
                   // set primary verb information to accessoryVerbElements
                   this.accessoryVerbElements1[duplicateCenterVerb1Count].setPrimaryVerb(verbAssessmentResults[2]);
                   
                   // set auxillary/Verb not info to accessoryVerbElements
                   if(individVerbInt2 == 1)  
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setAuxVerbNot(true);
                   else
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setAuxVerbNot(false);
                   
                   // document "was" "were" "is" "are" or  "am"  as an auxillary being verb
                   if(hasBeingAuxillaryInResultsArray)
                     this.accessoryVerbElements1[duplicateCenterVerb1Count].setAuxillaryVerb1(verbAssessmentResults[4]);
                   
               }
               // condition that we are on the second section of the sentence,  from " and, "
               else {
                   //construct a new predicateData object
                   this.accessoryVerbElements2[duplicateCenterVerb2Count] = new predicateData();
                   // set modal info to accessoryVerbElements predicateData array
                   this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalInfo(verbAssessmentResults[0]);
                   // set modal not info to accessoryVerbElements
                   int individVerbInt1 = 0;
                   int individVerbInt2 = 0;
                       try {
                           individVerbInt1 =Integer.parseInt(verbAssessmentResults[1]);
                           individVerbInt2 = Integer.parseInt(verbAssessmentResults[3]);
                           this.parseable = true;
                       } catch (NumberFormatException e) {
                           this.parseable = false;
                       }
                   if(individVerbInt1 == 1)  
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalNot(true);
                   else
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setModalNot(false);
                     
                   // set primary verb information to accessoryVerbElements
                   this.accessoryVerbElements2[duplicateCenterVerb2Count].setPrimaryVerb(verbAssessmentResults[2]);
                   
                   // set auxillary/Verb not info to accessoryVerbElements
                   if(individVerbInt2 == 1)  
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setAuxVerbNot(true);
                   else
                     this.accessoryVerbElements2[duplicateCenterVerb2Count].setAuxVerbNot(false);
                   
                   // document "was" "were" "is" "are" or  "am"  as an auxillary being verb
                   if(hasBeingAuxillaryInResultsArray)
                     this.accessoryVerbElements1[duplicateCenterVerb2Count].setAuxillaryVerb1(verbAssessmentResults[4]);
                   
               }
               
               subsectionActive = false;
               
               
               
            // check if linking verb might involve a noun
            if(this.numWordsSentence <= intIndex1 + 1 && element1[intIndex1].getfirstLevelFormedWord().matches("be")
                    && !this.posSentence[intIndex1+ 1].contains("VB")){
               posNextTokenFrom_here = this.posSentence[intIndex1+ 1];
            } else{
               posNextTokenFrom_here = "null" ;
            }
                
            // a linking verb refers to a noun, not an adjective
            if(posNextTokenFrom_here.contains("NN") || posNextTokenFrom_here.matches("PRP") || posNextTokenFrom_here.contains("PP")){
                LastTokenElementWasLinkingVerb = true;
            }
               
               
               StartExaminingVerbPhraseHere = false;
               that_MarkEncountered = false;
               
                AndClausComma = false;
                
                newSubjectPresentCurrently = false;
                this.startProbingForSubject_toPredicate = false;
                
                and_That_Which_Phrase_VerbGotten = true;
                currentAnd_VerbPhrase_ShouldNotSearch = false;
                
                
                runOnce1 = false;
                
                // go straight to next index's label in iterator.
                continue;
            }
            
            else if(currentString.contains("mark(") && termParseSentence.toLowerCase().matches("that")){
                // consider  __if(centerVerb2 != 0)

                that_MarkEncountered = true;
                if(this.numOfSubjectsPrimary_2 == 0)
                  this.thatMark_1[centerVerb1Count] = intIndex1;
                
                //else   // not considering second 
                
                
                // check for : nn verb _ *
                if(NounandOrThatFirst == -1)
                    NounandOrThatFirst = 2;
                // check for : nn verb and _ *
                else if(NounandOrThatFirst == 0)
                    NounandOrThatFirst = 9;
                // check for : nn verb nsubj verb _ *
                else if(NounandOrThatFirst == 4)
                    NounandOrThatFirst = 8;
                // check for : nn verb nsubj verb and _ *
                else if(NounandOrThatFirst == 6)
                    NounandOrThatFirst = 7;
                // check for : nn verb _that verb and _ *
                else if(NounandOrThatFirst == 11)
                    NounandOrThatFirst = 12;
                // check for : nn verb and nsubj verb _ *
                else if(NounandOrThatFirst == 13)
                    NounandOrThatFirst = 14;
                // check for : nn verb and verb nsubj _ *
                else if(NounandOrThatFirst == 15)
                    NounandOrThatFirst = 17;
                
                
                and_That_Which_Phrase_VerbGotten = false;
                
                
                // go straight to next index's label in iterator.
                continue;
                 
                
            }
            // main section_ linking verb's "adjective" element here
            else if ( StartExaminingVerbPhraseHere && (  currentString.contains("conj:and(")  && predicate_verbPresentAtIndexItem == 0  ) && (mostCurrentPOS.contains("JJ") || mostCurrentPOS.contains("PDT") || mostCurrentPOS.contains("VB") ) ) {
                if (this.numOfSubjectsPrimary_2 == 0) {
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = centerVerb1Count;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = intIndex1;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = lastSubjectForCurrentClause;
                    this.totalClauseElementCount1++;
// this will tell us the count of the adjective in progression  of verb
                        this.currentAdjClauseNumber[centerVerb1Count] = intIndex1;                    
                } else {
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][0] = centerVerb2Count;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][1] = intIndex1;
                    this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][2] = lastSubjectForCurrentClause;
                    this.totalClauseElementCount2++;
                }
                
            }
                // sub section_ linking verb's "adjective" element here
            else if ( subsectionActive && StartExaminingVerbPhraseHere && (  currentString.contains("acl:relcl(")  && predicate_verbPresentAtIndexItem == 0  ) && (mostCurrentPOS.contains("JJ") || mostCurrentPOS.contains("PDT") || mostCurrentPOS.contains("VB")) ) {
                if (this.numOfSubjectsPrimary_2 == 0) {
                    if (clauseSubject_sect_count == 0) {
                        // first sub-clause of adjectives   code 11
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = 11;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = lastSubjectForCurrentClause;
// this will tell us the count of the adjective in progression  of verb
                        this.currentAdjClauseNumber[centerVerb1Count] = intIndex1;                        
                    } else {
                        // this will tell us the count of the adjective in progression  of verb
                     //probably should have double int array
                        this.currentAdjClauseNumber[centerVerb1Count] = intIndex1;
                        // first sub-clause of adjectives   code 12
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = 12;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = lastSubjectForCurrentClause;
                    }
                    this.totalClauseElementCount1++;
                } else {
                    if (clauseSubject_sect_count == 0) {
                        // first sub-clause of adjectives   code 11
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][0] = 11;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][2] = lastSubjectForCurrentClause;
                    } else {
                        // first sub-clause of adjectives   code 12
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][0] = 12;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][2] = lastSubjectForCurrentClause;
                    }
                    this.totalClauseElementCount2++;
                }
                
            }
            
            else if(currentString.contains("mark(")  && termParseSentence.toLowerCase().matches("to") && predicate_verbPresentAtIndexItem_2ndPart == 1){
                to_MarkEncountered = true;
                numberOfItemsToSkipInfinitivePhrase = 1;
                
                if (this.numOfSubjectsPrimary_2 == 0) {
                    this.duplicateInfinit1Count = this.infinitives1Count;
                    this.newInfinitives1[this.infinitives1Count] = new InfinitiveSect();
                    this.newInfinitives1[this.infinitives1Count].setStartIndex_of_phrase(intIndex2);
                    this.newInfinitives1[this.infinitives1Count].setTense_of_Verb("present");
                    this.newInfinitives1[this.infinitives1Count].setIndex_of_Verb_of_phrase(intIndex2);
                    this.newInfinitives1[this.infinitives1Count].setEndIndex_of_phrase(intIndex2);
                    this.infinitives1Count++;
                    
                }
                else {
                    this.duplicateInfinit2Count = this.infinitives2Count;
                    this.newInfinitives2[this.infinitives2Count] = new InfinitiveSect();
                    this.newInfinitives2[this.infinitives2Count].setStartIndex_of_phrase(intIndex2);
                    this.newInfinitives2[this.infinitives2Count].setTense_of_Verb("present");
                    this.newInfinitives2[this.infinitives2Count].setIndex_of_Verb_of_phrase(intIndex2);
                    this.newInfinitives2[this.infinitives2Count].setEndIndex_of_phrase(intIndex2);
                    this.infinitives2Count++;
                }
                continue;
                
            }
            

            
            // given mark detailer of "that " encountered
            else if(currentString.contains("nsubj(")  && that_MarkEncountered){
                verbPhraseStart = intIndex1;
                StartExaminingVerbPhraseHere = true;
             
                
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                
                // record sub clause subject   for either sect 1 or sect2
                if(this.numOfSubjectsPrimary_2 == 0){
                    // first section of sentence
                    this.clauseInfo_onRange1[centerVerb1Count][0] = intIndex1;
                }
                else{
                    // second section of sentence
                    this.clauseInfo_onRange2[centerVerb2Count][0] = intIndex1;
                }
                
                //if(this.startProbingForSubject_toPredicate)
                //    this.subjectHasFollowedInfinitive_Yes = true;
                
                newSubjectPresentCurrently = true;
                
                lastSubjectForCurrentClause = intIndex1;
                continue;
            }
            
            
   
       // look for adjective modifier
            else if(currentString.contains("amod(")){
            
                if (this.numOfSubjectsPrimary_2 == 0) {
                    
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][0] = centerVerb1Count;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount1][2] = lastSubjectForCurrentClause;
                   this.totalClauseElementCount1++;
                 } else {
                    
                    
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][0] = centerVerb2Count;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][1] = intIndex1;
                        this.descriptiveAdjectivePhrases[this.totalClauseElementCount2 + 6][2] = lastSubjectForCurrentClause;
                   this.totalClauseElementCount2++;
                 }
            
            }
            // look for direct Objects
            else if(currentString.contains("dobj(")){
                if(this.numOfSubjectsPrimary_2 == 0) {
                    this.directObjects_1[this.numOfDirectObjects_1++] = intIndex1;      
                }
                else {
                    this.directObjects_2[this.numOfDirectObjects_2++] = intIndex1; 
                }
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                continue;
            }
            
            // look for indirect Objects
            else if(currentString.contains("iobj(")){
                if(this.numOfSubjectsPrimary_2 == 0) {
                    this.indirectObjects_1[this.numOfIndirectObjects_1++] = intIndex1;
                }
                
                else {
                    this.indirectObjects_2[this.numOfIndirectObjects_2++] = intIndex1;
                }
                this.objects1[intIndex1] = new ObjectData();
                this.objects1[intIndex1] = ObjectReview.objectAssessment(this.actualWords[intIndex1], this.lemmasSentence[intIndex1], this.posSentence[intIndex1], intIndex1);
                // set original word for verb in object VerbData
                this.objects1[intIndex1].setOrigWord(this.actualWords[intIndex1]);
                // set lemma form of the nlp breakdown classification.
                this.objects1[intIndex1].setlemma(this.lemmasSentence[intIndex1]);
                // set pos tag for verb in object VerbData
                this.objects1[intIndex1].setPosForm(this.posSentence[intIndex1]);
                continue;
            }
            
        }
        
        
           // screen for problems of response   with intricate issues of an infinitive phrase after main verb gets going
            // 
            // though not implemented,  infinitives could easily be inserted after main verb and object items
            infinitive_afterPredicate_NoProblem_in_Response = checkInfinitiveClause_level_Intricacy.checkInfinitiveDetails(this.tree);

        
        
        
        int arrayOfOut1NumCount = 0;
        // initialize output array
        for(int ss1=0; ss1<18 ; ss1++)
           arrayOfOutput1[ss1] = new String();
        int arrayOfOut2NumCount = 0;
        // initialize output array
        for(int ss2=0; ss2<18 ; ss2++)
           arrayOfOutput2[ss2] = new String();
     
        // document which clauses have a linking verb present
        //int[] linkingVerbClauseHere1 = new int[6];
        // this counter will be used for the previous int array,  and the following two string arrays
        //int ctLinkingVerbClauseHere1 = 0;
       /**
         * person of noun is 1 for first singular 
         *                   2 for second singular
         *                   3 for third singular concerning human person
         *                   4     third singular concerning nonhuman being/thing
         *                   5     third singular concerning nonhuman place
         *                   6 for first plural
         *                   7 for second and third person plural
         *                   
         **/
        int[] personOfNoun = new int[6];
        // store linking verb response referenceSpeech terms  of subject and predicate
        //String[] referenceSpeech_Subject1 = new String[6];
        //String[] referenceSpeech_LinkingVerb1 = new String[6];
     
        
        // for sect1  assign tenses to our main array of important verbs , to  objects verbData
        int verbCount_IterateThroughPredicates = 0;
        while(verbCount_IterateThroughPredicates < centerVerb1Count){
           element1[this.centerVerb1[verbCount_IterateThroughPredicates]].setVerbTense(this.accessoryVerbElements1[verbCount_IterateThroughPredicates].getPrimaryVerb());
           // check if it is a linking verb term.
           //linkingVerbClauseHere1[ctLinkingVerbClauseHere1++] = this.centerVerb1[verbCount_IterateThroughPredicates];
           
           verbCount_IterateThroughPredicates++;
        }
        
       
        // for sect1  assign tenses to subclause verbs , to  objects verbData
        if (clauseSubject_sect_count == 2 && this.clauseInfo_onRange2[7][1] != 0){
            element1[this.clauseInfo_onRange1[7][1]].setVerbTense(this.accessoryVerbElements1[6].getPrimaryVerb());
        }
        else if(clauseSubject_sect_count == 1 && this.clauseInfo_onRange2[6][1] != 0){
            element1[this.clauseInfo_onRange1[6][1]].setVerbTense(this.accessoryVerbElements1[5].getPrimaryVerb());
        }
       
        // reset counter for linking verb response   of section 1
        //ctLinkingVerbClauseHere1 = 0;
        
        
        
           /**
            * person of noun is 1 for first singular 
            *                   2 for second singular
            *                   3 for third singular concerning human person
            *                   4     third singular concerning nonhuman being/thing
            *                   5     third singular concerning nonhuman place
            *                   6 for first plural
            *                   7 for second and third person plural
            *                   
            **/
 
        int verbCount_IterateThroughPredicates_sect2 = 0;
            // for sect2  assign tenses to our main array of important verbs , to  objects verbData
            verbCount_IterateThroughPredicates_sect2 = 0;
            while (verbCount_IterateThroughPredicates_sect2 < centerVerb2Count) {
                element1[this.centerVerb2[verbCount_IterateThroughPredicates_sect2]].setVerbTense(this.accessoryVerbElements2[verbCount_IterateThroughPredicates_sect2].getPrimaryVerb());
                
                // check if it is a linking verb term.
                //linkingVerbClauseHere2[ctLinkingVerbClauseHere2++] = this.centerVerb2[verbCount_IterateThroughPredicates_sect2];
                
                verbCount_IterateThroughPredicates_sect2++;
            }
            // reset counter for linking verb response   of section 1
            //ctLinkingVerbClauseHere2 = 0;
        
        // for sect1  assign tenses to subclause verbs , to  objects verbData
        if (clauseSubject_sect_count == 2 && this.clauseInfo_onRange2[7][1] != 0 ){
            element1[this.clauseInfo_onRange2[7][1]].setVerbTense(this.accessoryVerbElements2[6].getPrimaryVerb());
        }
        else if(clauseSubject_sect_count == 1  && this.clauseInfo_onRange2[6][1] != 0){
            element1[this.clauseInfo_onRange2[6][1]].setVerbTense(this.accessoryVerbElements2[5].getPrimaryVerb());
        }
        

        
        // try and discover if there is a second subject to sentence's second section
        int[] otherSubjectsWithBeginning2 = new int[6];
        if(this. numOfSubjectsPrimary_2 != 0)
            otherSubjectsWithBeginning2 = subjectNounMultiCheck.lookAdditionalSubjects(this.tree, this.centerSubject2[0]);
        
        
        // set up additional subjects as located in centerSubjects2 and objects1 ObjectData
        // first look if there is one extra subject
        if (otherSubjectsWithBeginning2[0] != 0) {
            this.centerSubject2[1] = otherSubjectsWithBeginning2[0];
            this.numOfSubjectsPrimary_2++;
            this.objects1[this.centerSubject2[1]] = new ObjectData();
            this.objects1[this.centerSubject2[1]] = ObjectReview.objectAssessment(this.actualWords[this.centerSubject2[1]], this.lemmasSentence[this.centerSubject2[1]], this.posSentence[this.centerSubject2[1]], this.centerSubject2[1]);
            // set original word for verb in object VerbData
            this.objects1[this.centerSubject2[1]].setOrigWord(this.actualWords[this.centerSubject2[1]]);
            // set lemma form of the nlp breakdown classification.
            this.objects1[this.centerSubject2[1]].setlemma(this.lemmasSentence[this.centerSubject2[1]]);
            // set pos tag for verb in object VerbData
            this.objects1[this.centerSubject2[1]].setPosForm(this.posSentence[this.centerSubject2[1]]);
        }
        // also then look if there is one more following extra subject
        if(otherSubjectsWithBeginning2[1] != 0){
            this.centerSubject2[2] = otherSubjectsWithBeginning2[1];
            this.numOfSubjectsPrimary_2++;
            this.objects1[this.centerSubject2[2]] = new ObjectData();
            this.objects1[this.centerSubject2[2]] = ObjectReview.objectAssessment(this.actualWords[this.centerSubject2[2]], this.lemmasSentence[this.centerSubject2[2]], this.posSentence[this.centerSubject2[2]], this.centerSubject2[2]);
            // set original word for verb in object VerbData
            this.objects1[this.centerSubject2[2]].setOrigWord(this.actualWords[this.centerSubject2[2]]);
            // set lemma form of the nlp breakdown classification.
            this.objects1[this.centerSubject2[2]].setlemma(this.lemmasSentence[this.centerSubject2[2]]);
            // set pos tag for verb in object VerbData
            this.objects1[this.centerSubject2[2]].setPosForm(this.posSentence[this.centerSubject2[2]]);
        }
        
  

      boolean noSubjectFromStart = false;
        
      int currentSubject1Count = 0;
      int directObject1Count = 0;
      int indexOfApplicableObjectDescription_to_currentSubject = 0;

      boolean DirectObjectIsNotDescriptive = false;
      boolean doNotUseDirectObject = false;
      boolean thereIsADirObject = false;
      boolean linkingVerbClauseHere1 = false;
      boolean infinitiveSubject = false;
      boolean runOnce_also = false;
      boolean doNotRunBoth = false;
      
      String[] newReferenceToClauseSubject_and_Verb = new String[3];
      String[] newReferenceToClauseSubject_and_Verb2 = new String[3];
      //newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_regularVerbs.getRespectiveSubject_andCommonTypeVerb(objects1[this.directObjects_1[0]], element1[this.centerVerb1[1]], this.accessoryVerbElements1[1], thereIsADirObject);
      
      //personOfNoun[currentSubject1Count] = Integer.parseInt(newReferenceToClauseSubject_and_Verb[0]);
      String objectSubject = "";
      String objectSubject_POS = "";
      
      if(this.centerSubject1[0] == 0)
         noSubjectFromStart = true;
                 
      while(currentSubject1Count < centerVerb1Count && !noSubjectFromStart){
      //ctLinkingVerbClauseHere1
          
          
      // for clause 0    // these are the subjects in the clause presently at
          if(!runOnce){
             // get the special first subject item
              objectSubject = objects1[this.centerSubject1[0]].getOrigWord().toLowerCase();
              objectSubject_POS = objects1[this.centerSubject1[0]].getPosForm();
              
              if(element1[this.centerVerb1[0]].getfirstLevelFormedWord().matches("be"))
                  linkingVerbClauseHere1 = true;
              runOnce = true;
              if(currentSubject1Count == 0){
                  if(directObject1Count >= this.numOfDirectObjects_1){
                       doNotUseDirectObject = true;
                 }
                 else{
                      doNotUseDirectObject = false;
                      
                      if(VerbReview.CheckItem_in_ClauseRange(this.clauseInfo_onRange1[0][1], this.clauseInfo_onRange1[1][0] , this.directObjects_1[directObject1Count]))
                            // dobj found
                               thereIsADirObject = true;
                      else
                             thereIsADirObject = false;
                               
                      if (objects1[this.directObjects_1[directObject1Count]].getPosForm().contains("DT"))
                            DirectObjectIsNotDescriptive = true;
                            
                      
                      else  
                            DirectObjectIsNotDescriptive = false;
                            
                      
                 }
                  // print the first details of section 1 
        //only if it is linking phrase without infinitive on right side
                 if(linkingVerbClauseHere1 ){
                     // check if the adjective is there and refers to subject  or is itself root noun from starting linking phrase
                     if (this.descriptiveAdjectivePhrases[1][2] == this.centerSubject1[0] || this.descriptiveAdjectivePhrases[1][2] == 0) {
                         newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_linking.getRespectiveSubject_andLinkingVerb(objects1[this.centerSubject1[0]], element1[this.centerVerb1[0]]);
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[1][1]];
                         runOnce_also = true;

                     }
                 }
                 // run the follow if the previous did not  
                 //this is also for the first phrase if it is not linking...
                 
                 // this clause has a direct object
                 else if(thereIsADirObject ){
                     
                     newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_regularVerbs.getRespectiveSubject_andCommonTypeVerb(objects1[this.centerSubject1[0]], element1[this.centerVerb1[0]], this.accessoryVerbElements1[0], true);
                     
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                     
                     // look for adjective that relates to direct object     _ if there is one
                     for(int ii = 0; ii < this.totalClauseElementCount1 ; ii++){
                         if(this.directObjects_1[directObject1Count] == this.descriptiveAdjectivePhrases[ii][2]){
                             this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[ii][2]];
                             break;      
                        }
                     }
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.directObjects_1[directObject1Count]];
                     
                }
                 // does not have a direct object
                 else{
                     newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_regularVerbs.getRespectiveSubject_andCommonTypeVerb(objects1[this.centerSubject1[0]], element1[this.centerVerb1[0]], this.accessoryVerbElements1[0], false);
                     
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                     
                 }
              }
              
          }
          
          else{
              
                 // add a that to sentence if there is one  __ will not print a subject that though
                  // insert that
                  if(this.thatMark_1[currentSubject1Count] != 0 && this.thatMark_1[currentSubject1Count] != this.clauseInfo_onRange1[currentSubject1Count][0])
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = "that"; 
              
              
          
                    // check if subject is a noun and not an infinitive verb element... because that is not handled as of yet
                     if(objects1[this.clauseInfo_onRange1[currentSubject1Count][0]] != null){
                         
                     }
                     else {
                         // this means that it is not an infinitive
                         infinitiveSubject = false;
                        
                         break;
                     }
              
                
                 if(directObject1Count >= this.numOfDirectObjects_1){
                       doNotUseDirectObject = true;
                 }
                 else{
                      doNotUseDirectObject = false;
                      
                      if(VerbReview.CheckItem_in_ClauseRange(this.clauseInfo_onRange1[currentSubject1Count-1][1], this.clauseInfo_onRange1[currentSubject1Count][0] , this.directObjects_1[directObject1Count]))
                            // dobj found
                               thereIsADirObject = true;
                      else
                             thereIsADirObject = false;
                               
                      if (objects1[this.directObjects_1[directObject1Count]].getPosForm().contains("DT"))
                            DirectObjectIsNotDescriptive = true;
                            
                      
                      else  
                            DirectObjectIsNotDescriptive = false;
                            
                 
              
                 
              /** do a check if subject refers to a more specific item/, or direct object
                *remember: an independent subject with ",and" starts a complete new second half of the sentence
                * 
                *     check if a pastphrase direct object is in border range with the current nominal subject
                *      the first argument in the function in the condition statement
                *      is the past clause end,  the next argument is current clause beginning      
                * 
                * if you look at the functions arguments in RespectivePhraseVerb_linking.getRespectiveSubject_andLinkingVerb
                * a direct object index is used for the verb
               **/
              
           if (!doNotUseDirectObject && !DirectObjectIsNotDescriptive) {
              if (VerbReview.CheckNounItem_in_ClauseRange(commaStart, commaEnd, this.actualWords, this.clauseInfo_onRange1[currentSubject1Count - 1][1], this.clauseInfo_onRange1[currentSubject1Count][0], this.directObjects_1[directObject1Count])) {
                  // thereIsADirObject is just a placeholder test
                  if(thereIsADirObject && element1[this.centerVerb1[currentSubject1Count]].getfirstLevelFormedWord().toLowerCase().matches("be")){
                      newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_linking.getRespectiveSubject_andLinkingVerb(objects1[this.directObjects_1[directObject1Count]], element1[this.centerVerb1[currentSubject1Count]]);
                      try {
                          personOfNoun[currentSubject1Count] = Integer.parseInt(newReferenceToClauseSubject_and_Verb[0]);
                        this.parseable = true;
                      } catch (NumberFormatException e) {
                          this.parseable = false;
                      }
                      // example output of what was assigned _ System.out.println("first one: " +personOfNoun[currentSubject1Count]);
                      this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                      this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                      
                     // look for adjective that relates to direct object     _ if there is one
                     for(int f = 0; f < this.totalClauseElementCount1 ; f++){
                         // see if the governor item of the adjective refers to the direct object       And add the item adjective
                         if(this.directObjects_1[directObject1Count] == this.descriptiveAdjectivePhrases[f][2]){
                             this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[f][2]];
                             break;      
                        }
                     }
                       // add direct object to output array now.
                     this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.directObjects_1[directObject1Count]];
                     
                  }
                  
                  else {
                    // thereIsADirObject is just a placeholder test
                     if (thereIsADirObject) {
                         // look up regular verb phrase
                         newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_regularVerbs.getRespectiveSubject_andCommonTypeVerb(objects1[this.directObjects_1[directObject1Count]], element1[this.centerVerb1[currentSubject1Count]], this.accessoryVerbElements1[currentSubject1Count], thereIsADirObject);
                         try {
                             personOfNoun[currentSubject1Count] = Integer.parseInt(newReferenceToClauseSubject_and_Verb[0]);
                             this.parseable = true;
                         } catch (NumberFormatException e) {
                             this.parseable = false;
                         }
                        // example output of what was assigned _ System.out.println("next one: " +personOfNoun[currentSubject1Count]);
                        this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                        this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                        
                        // look for adjective that relates to direct object     _ if there is one
                        for(int f = 0; f < this.totalClauseElementCount1 ; f++){
                           // see if the governor item of the adjective refers to the direct object       And add the item adjective
                           if(this.directObjects_1[directObject1Count] == this.descriptiveAdjectivePhrases[f][2]){
                               this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[f][2]];
                               break;      
                          }
                       }
                          // add direct object to output array now.
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.directObjects_1[directObject1Count]];
                           }
                     
                       }
                    }
                
                  
                  // a linking verb part without detailed or present direct object
                  if(element1[this.centerVerb1[currentSubject1Count]].getfirstLevelFormedWord().toLowerCase().matches("be")){
                      newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_linking.getRespectiveSubject_andLinkingVerb(objects1[this.clauseInfo_onRange1[currentSubject1Count][0]], element1[this.centerVerb1[currentSubject1Count]]);
                      try {
                          personOfNoun[currentSubject1Count] = Integer.parseInt(newReferenceToClauseSubject_and_Verb[0]);
                          this.parseable = true;
                      } catch (NumberFormatException e) {
                          this.parseable = false;
                      }
                      // example output of what was assigned _ System.out.println("first one: " +personOfNoun[currentSubject1Count]);
                      this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                      this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                      // check if there is a adjective  // assuming that any linking verb has a noun at the right
                      if(this.descriptiveAdjectivePhrases[currentSubject1Count][1] != 0 &&  this.descriptiveAdjectivePhrases[currentSubject1Count][1] != -1)
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[currentSubject1Count][1]];
                      else
                         this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = "noAdjective";
                  }
                  // a regular verb section   Not a being/linking verb
                  else {
                        newReferenceToClauseSubject_and_Verb = RespectivePhraseVerb_regularVerbs.getRespectiveSubject_andCommonTypeVerb(objects1[this.clauseInfo_onRange1[currentSubject1Count][0]], element1[this.centerVerb1[currentSubject1Count]], this.accessoryVerbElements1[currentSubject1Count], false);
                           try {
                             personOfNoun[currentSubject1Count] = Integer.parseInt(newReferenceToClauseSubject_and_Verb[0]);
                             this.parseable = true;
                          } catch (NumberFormatException e) {
                             this.parseable = false;
                         }
                        // example output of what was assigned _ System.out.println("next one: " +personOfNoun[currentSubject1Count]);
                        this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[1];
                        this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = newReferenceToClauseSubject_and_Verb[2];
                        
                        // look for adjective that relates to direct object     _ if there is one
                        for(int f = 0; f < this.totalClauseElementCount1 ; f++){
                           // see if the governor item of the adjective refers to the direct object       And add the item adjective
                           if(this.directObjects_1[directObject1Count] == this.descriptiveAdjectivePhrases[f][2]){
                               this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.descriptiveAdjectivePhrases[f][2]];
                               break;      
                           }
                        }
                           // add direct object to output array now.
                          this.arrayOfOutput1[this.counter_addingElementsArrayOut1++] = this.actualWords[this.directObjects_1[directObject1Count]];
                     }
                }
                 
           }
           
      }
          directObject1Count++;
          

      
          currentSubject1Count++;
  }

      System.out.println("begin output");
     for(int iOut = 0; iOut <this.counter_addingElementsArrayOut1  ; iOut++ ){
        System.out.print(this.arrayOfOutput1[iOut] + " ");
     }
      System.out.println();
      System.out.println(" end of output _ Section 1 of sentence..");
        
    }
    /**
     * @desc simply prints info on all objects considered and attempted to classify
     */
    public void printInfoObjects( ) {
        
        for(int ab0 = 1; ab0 < this.numWordsSentence; ab0++) {
            if(this.objects1[ab0] != null)
               System.out.println(this.objects1[ab0].getAllInformation() );
        }
        
    }
    /**
     * @desc  simply prints info on all verbs accessed and attempted to classify
     */
    public void printInfoVerbs ( ) {
        for(int ab1 = 1; ab1 < this.numWordsSentence; ab1++){
            if(this.element1[ab1] != null)
                System.out.println(this.element1[ab1].getAllInformation());
        }
    }
    
}
