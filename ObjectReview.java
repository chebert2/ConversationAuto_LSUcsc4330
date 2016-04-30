/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   ObjectReview   class:
 * this is a class that is used by the other important executing makeCriteria class.
 */
package conversationauto1;

// Database packages
import java.sql.*;


/**
 * Description.
 * 
 * This class makes a search to the database of Object word classifications.
 * It builds data objects containing details about the word's background.
 * 
 * Since it is made only to give output string on a passing/iterim basis, it is made static.
 * 
 */
public class ObjectReview {
    
    
    /** 
     * 
     *
     * // JDBC driver name and database URL
     * public static final String DB_URL_project = "jdbc:mysql://127.0.0.1:3306/mydb";
     * //static final String DB_URL_project = "jdbc:mysql://localhost:3306/homeDB";
     *
     * //  Database credentials
     * public static final String USER_project = "root";
     * public static final String PASS_project = "";
     * 
     **/

    
    // Assesses a basic core statement's verb components, returned as an array.     
    //  the term basic core refers to a statement with one individual composite-subject and predicate
    public static ObjectData objectAssessment(String rawOriginalWord, String lemmaInputWord, String posInputWord, int inputWordIndex) {
         boolean object_successFlag3 = false;
         boolean object_successFlag2 = false;
         boolean object_successFlag1 = false;
        
        // construct an ObjectData object to be modified for returning output.
        ObjectData objectSubject = new ObjectData();
        
        try {
            try {
                // create a ObjectData object for the input word.

                //first: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                // establish a connection medium
                Connection conn1 = DriverManager.getConnection(ConversationAuto1.DB_URL_project, ConversationAuto1.USER_project, ConversationAuto1.PASS_project);
                
                //STEP 4: Execute a query
                Statement stmt1 = conn1.createStatement();
                
                // start specifying basic initial data about object
                objectSubject.setPosForm(posInputWord);
                objectSubject.setOrigWord(rawOriginalWord);
                objectSubject.setlemma(lemmaInputWord);
                objectSubject.setWordIndex(inputWordIndex);

                /**
                 *  since we are looking for entries whereEver, 
                 * we begin looking at the furthest depth branches,
                 * and then gather info on hierarchical of the classification
                 * 
                 */
                
                // make a query for the highest meta level of the table objects_aspects_desc3
                String sqlObjects = "select cs2ID from objects_aspects_desc3 where Description = \"" + lemmaInputWord + "\"";
                ResultSet rso1 = stmt1.executeQuery(sqlObjects);
                if (rso1.next()) {
                    // set flags so no other possible contingent searches shall go.  That is, no searches will be made specially for meta level 3 down.
                    object_successFlag3 = true;
                    object_successFlag2 = true;
                    object_successFlag1 = true;

                    int object_3p_idTag = rso1.getInt("cs2ID");

                    // gather data (csID, Description, from one level lower in objects_aspects table
                    sqlObjects = "select csID, Description from objects_aspects_desc2 where cs2ID = " + object_3p_idTag;
                    rso1 = stmt1.executeQuery(sqlObjects);
                    if (rso1.next()) {
                        // data for determining type of object...
                        String object_2p_Description = rso1.getString("Description");
                        // id tag to get to lower level table
                        int object_2p_idTag = rso1.getInt("csID");
                        // gather data (m2Code, Description, from one level lower in objects_aspects table
                        sqlObjects = "select m2Code, Description from objects_aspects_desc1 where csID = " + object_2p_idTag;
                        rso1 = stmt1.executeQuery(sqlObjects);
                        if (rso1.next()) {
                            // data for determining type of object...
                            String object_1p_Description = rso1.getString("Description");
                            // id tag to get to lower level table
                            int object_1p_idTag = rso1.getInt("m2Code");
                            // gather data (mCode, Description), from one level lower in objects_aspects table
                            sqlObjects = "select mCode, Description from objects_aspects_desc0 where m2Code = " + object_1p_idTag;
                            rso1 = stmt1.executeQuery(sqlObjects);
                            if (rso1.next()) {
                                // data for determining type of object...
                                String object_0p_Description = rso1.getString("Description");
                                // id tag to get to lower level table
                                int object_0p_idTag = rso1.getInt("mCode");
                                // gather data (Description), from one level lower in objects_aspects table
                                sqlObjects = "select Description from objects_aspects where mCode = " + object_0p_idTag;
                                rso1 = stmt1.executeQuery(sqlObjects);
                                if (rso1.next()) {
                                    // data for determining type of object...
                                    String object_p_Description = rso1.getString("Description");
                                    // table heirarchy in a path sequence.
                                    String pathTableForObject = "/" + object_0p_idTag + "_" + object_p_Description + "/" + object_0p_Description + "_" + object_1p_idTag + "/" + object_1p_Description + "_" + object_2p_idTag + "/" + object_2p_Description + "_" + object_3p_idTag + "/" + lemmaInputWord;
                                    objectSubject.setPathOfForm(pathTableForObject);
                                    // add info on branch depth and classification
                                    objectSubject.setLevel4FormedWord(object_2p_Description);
                                    objectSubject.setLevel3FormedWord(object_1p_Description);
                                    objectSubject.setLevel2FormedWord(object_0p_Description);
                                    objectSubject.setLevel1FormedWord(object_p_Description);

                                }
                            }
                        }
                    }
                }
                // make a query for the 3rd highest meta level of the table objects_aspects_desc2
                if (!object_successFlag3) {
                    
                    // gather data (csID, Description), from meta level 3 in objects_aspects table
                    sqlObjects = "select csID from objects_aspects_desc2 where Description = \"" + lemmaInputWord + "\"";
                    rso1 = stmt1.executeQuery(sqlObjects);
                    if (rso1.next()) {
                        // set flags so no other possible contingent searches shall go.  That is, no searches will be made specially for meta level 2 down.
                        object_successFlag2 = true;
                        object_successFlag1 = true;
                        
                        // id tag to get to meta level 3 table
                        int object_2p_idTag = rso1.getInt("csID");

                        // gather data (m2Code, Description), from one level down in objects_aspects table
                        sqlObjects = "select m2Code, Description from objects_aspects_desc1 where csID = " + object_2p_idTag;
                        rso1 = stmt1.executeQuery(sqlObjects);
                        if (rso1.next()) {
                            // data for determining type of object...
                            String object_1p_Description = rso1.getString("Description");
                            // id tag to get to lower level table
                            int object_1p_idTag = rso1.getInt("m2Code");

                            // gather data (mCode, Description), from one level lower in objects_aspects table
                            sqlObjects = "select mCode, Description from objects_aspects_desc0 where m2Code = " + object_1p_idTag;
                            rso1 = stmt1.executeQuery(sqlObjects);
                            if (rso1.next()) {
                                // data for determining type of object...
                                String object_0p_Description = rso1.getString("Description");
                                // id tag to get to lower level table
                                int object_0p_idTag = rso1.getInt("mCode");

                                // gather data (Description), from one level lower in objects_aspects table
                                sqlObjects = "select Description from objects_aspects where mCode = " + object_0p_idTag;
                                rso1 = stmt1.executeQuery(sqlObjects);
                                if (rso1.next()) {
                                    // data for determining type of object...
                                    String object_p_Description = rso1.getString("Description");
                                    // table heirarchy in a path sequence.
                                    String pathTableForObject = "/" + object_0p_idTag + "_" + object_p_Description + "/" + object_0p_Description + "_" + object_1p_idTag + "/" + object_1p_Description + "_" + object_2p_idTag + "/" + lemmaInputWord;
                                    objectSubject.setPathOfForm(pathTableForObject);
                                    // add info on branch depth and classification
                                    objectSubject.setLevel3FormedWord(object_1p_Description);
                                    objectSubject.setLevel2FormedWord(object_0p_Description);
                                    objectSubject.setLevel1FormedWord(object_p_Description);
                                }
                            }
                        }

                    }
                }
                // make a query for the 2nd highest meta level of the table objects_aspects_desc1
                if (!object_successFlag2) {
                    
                    // gather data (m2Code) from meta level 2 down in objects_aspects table
                    sqlObjects = "select m2Code from objects_aspects_desc1 where Description = \"" + lemmaInputWord + "\"";
                    rso1 = stmt1.executeQuery(sqlObjects);

                    if (rso1.next()) {
                        // set flags so no other possible contingent searches shall go.  That is, no searches will be made specially for meta level 1 down.
                        object_successFlag1 = true;

                        // id tag to get to lower level table
                        int object_1p_idTag = rso1.getInt("m2Code");

                        // gather data (mCode, Description), from one level lower in objects_aspects table
                        sqlObjects = "select mCode, Description from objects_aspects_desc0 where m2Code = " + object_1p_idTag;
                        rso1 = stmt1.executeQuery(sqlObjects);
                        if (rso1.next()) {
                            // data for determining type of object...
                            String object_0p_Description = rso1.getString("Description");
                            // id tag to get to lower level table
                            int object_0p_idTag = rso1.getInt("mCode");

                            // gather data (Description), from one level lower in objects_aspects table
                            sqlObjects = "select Description from objects_aspects where mCode = " + object_0p_idTag;
                            rso1 = stmt1.executeQuery(sqlObjects);
                            if (rso1.next()) {
                                // data for determining type of object...
                                String object_p_Description = rso1.getString("Description");
                                // table heirarchy in a path sequence.
                                String pathTableForObject = "/" + object_0p_idTag + "_" + object_p_Description + "/" + object_0p_Description + "_" + object_1p_idTag + "/" + lemmaInputWord;
                                objectSubject.setPathOfForm(pathTableForObject);
                                // add info on branch depth and classification
                                objectSubject.setLevel2FormedWord(object_0p_Description);
                                objectSubject.setLevel1FormedWord(object_p_Description);
                            }
                        }

                    }

                }
                // make a query for the base meta level of the table objects_aspects_desc0
                if (!object_successFlag1) {

                    // gather data (mCode), from meta level 1 in objects_aspects table
                    sqlObjects = "select mCode from objects_aspects_desc0 where Description = \"" + lemmaInputWord + "\"";
                    rso1 = stmt1.executeQuery(sqlObjects);

                    if (rso1.next()) {

                        // id tag to get to lower level table
                        int object_0p_idTag = rso1.getInt("mCode");

                        // gather data (Description), from one level lower in objects_aspects table
                        sqlObjects = "select Description from objects_aspects where mCode = " + object_0p_idTag;
                        rso1 = stmt1.executeQuery(sqlObjects);
                        if (rso1.next()) {
                            // data for determining type of object...
                            String object_p_Description = rso1.getString("Description");
                            // table heirarchy in a path sequence.
                            String pathTableForObject = "/" + object_0p_idTag + "_" + object_p_Description + "/" + lemmaInputWord;
                            objectSubject.setPathOfForm(pathTableForObject);
                            // add info on branch depth and classification
                            objectSubject.setLevel1FormedWord(object_p_Description);
                        }
                    }

                }
                
                
                
                // given whichever depth level of classification it found, it gives the background of it
                // as a ObjectData here:
                
                return objectSubject;
                
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            }
        } catch (Exception mx) {
            //Handle errors for Class.forName
            mx.printStackTrace();
        }
        return objectSubject;
    }
}

