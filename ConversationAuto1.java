/**
 * @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 * preliminary Description:   of this   ConversationAuto1   class:
 * This is the main implementation of a conversation AI program.  It
 * pertains mostly to chummy interchanges.
 * 
 * The main thing that starts piecing together dialogue (using stanford NLP core set of tools)
 * is the important executing class makeCriteria.  A simple input String is taken with a
 * makeCriteria object to start building a response to be return as output in this main class.
 */
package conversationauto1;


public class ConversationAuto1 {
    
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    static final String DB_URL_project = "jdbc:mysql://localhost:3306/homeDB";

    //  Database credentials
    public static final String USER_project = "root";
    public static final String PASS_project = "";
    public static void main(String[] args) {
   
        
       // constructor of the running evaluator object class makeCriteria 
      //  makeCriteria make8 = new makeCriteria();
      //  make8.runMake("Others and I prepared people at home of people to venture there today with relating.");
        
      
         String[] stringA = new String[18];
        
        stringA[0] = "A member prepared people to do stuff which make nice results.";
        
        stringA[1] = "I went somewhere there.";
        
        stringA[2] = "He had stored part.";
        stringA[3] = "He is concerned.";
        stringA[4] = "I views it that something got stuff.";
        stringA[5] = "He may work.";
        stringA[6] = "He got energy for things, something that helps;";
        stringA[7] = "The man is worker that offers ideas.";
        stringA[8] = "Others and I prepared people at home of people.";
        stringA[9] = "the boy was trying to get the center.";
        stringA[10] = "The members are going a sign.";
        stringA[11] = "a claim is concluding.";
        stringA[12] = "I brought all the items to here";
        stringA[13] = "the red items were heavy to lift there now";
        stringA[14] = "a thought and implication were beginning to settle.";
        stringA[15] = "He got anything.";
        stringA[16] = "It might do good.";
        stringA[17] = "Attention is given today .";
 
        
        for(int i = 0; i< 18; i++){
            makeCriteria make10 = new makeCriteria();
            make10.runMake(stringA[i]);
            
           System.out.println(stringA[i]);
        }
   
    
    }
    
}
