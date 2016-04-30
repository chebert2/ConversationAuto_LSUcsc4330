/**
 * @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 * 
 * 
 * 
 * 
**/
package conversationauto1;

import edu.stanford.nlp.trees.*;
import java.util.*;

/**
 * Description:
 * This sifts through after the starting predicate and sees if an infinitive 
 * phrase is direct and simple enough to quote in response
 * 
 * It returns false if there is a additional verb phrase (not divided by
 * a comma though)  continuing 
 * after an infinitive in the location  ( a comma is a whole new section to be
 * reviewed from the makeCriteria class
 * 
 * It flips through the structural grammar tree POS grouping
 */

public class checkInfinitiveClause_level_Intricacy {
    
    public static Boolean checkInfinitiveDetails(Tree tree){
        boolean returnValue_search = false;
        

      boolean[] Encounter_Level_Infinitive = new boolean[15];

      for(int i = 1; i<15; i++){
          Encounter_Level_Infinitive[i] = false;
         
      }

      boolean VerbPhraseStarted = false;
      boolean toMark_infinitive_found = false;
      boolean startLookingForWords_counting = false;
      
      byte counterWordsAfter_to_mark = 0;
      
      
      
      int numChildren0 = tree.numChildren();
      int count0 = 0;
      
      while (count0 < numChildren0) {
          Tree tree_one = tree.getChild(count0);
          
          String tree_one_label = tree_one.nodeString();
          
          if(tree_one_label.contains("VP"))
              VerbPhraseStarted = true;
          
          
          
          int numChildren1 = tree_one.numChildren();
    
          int count1 = 0;
          while (count1 < numChildren1) {
              Tree tree_two = tree_one.getChild(count1);
          
              String tree_two_label = tree_two.nodeString();

              if(tree_two_label.contains("VP"))
                  VerbPhraseStarted = true;
          
               if(VerbPhraseStarted)
                   if(tree_two_label.contains("TO")){
                       Encounter_Level_Infinitive[2] = true;
                       startLookingForWords_counting = true;
                   }
               
               int numChildren2 = tree_two.numChildren();
               
               if(startLookingForWords_counting && numChildren2 == 0){
                   counterWordsAfter_to_mark++;
               }
                 if(tree_two_label.contains("WDT") && counterWordsAfter_to_mark >2){
                       returnValue_search = true;
                       break;
                 } 
                 if(tree_two_label.contains("VB") && counterWordsAfter_to_mark >3){
                       returnValue_search = true;
                       break;
                 }
                 if(startLookingForWords_counting && tree_two_label.matches(",") )  {
                       returnValue_search = false;
                       break;
                 }
               
              int count2 = 0;
              while (count2 < numChildren2) {
                  Tree tree_three = tree_two.getChild(count2);
          
                  String tree_three_label = tree_three.nodeString();

                  if(tree_three_label.contains("VP"))
                     VerbPhraseStarted = true;
                  
                   if(VerbPhraseStarted)
                       if(tree_three_label.contains("TO")){
                           Encounter_Level_Infinitive[3] = true;
                           startLookingForWords_counting = true;
                       }
                   
                   int numChildren3 = tree_three.numChildren();
                   
                   if(startLookingForWords_counting && numChildren3 == 0){
                       counterWordsAfter_to_mark++;
                     }
                   
                   if(tree_three_label.contains("WDT") && counterWordsAfter_to_mark >2){
                       returnValue_search = true;
                       break;
                   } 
                    if(tree_three_label.contains("VB") && counterWordsAfter_to_mark >3){
                       returnValue_search = true;
                       break;
                   }
                   if (startLookingForWords_counting && tree_three_label.matches(",")) {
                       returnValue_search = false;
                       break;
                   }
                  
                  
                  int count3 = 0;
                  while (count3 < numChildren3) {
                      Tree tree_four = tree_three.getChild(count3);
          
                      String tree_four_label = tree_four.nodeString();
                     
                      if(tree_four_label.contains("VP"))
                         VerbPhraseStarted = true;
                      
                       if(VerbPhraseStarted)
                           if(tree_four_label.contains("TO")){
                               Encounter_Level_Infinitive[4] = true;
                               startLookingForWords_counting = true;
                           }
                      int numChildren4 = tree_four.numChildren();
                     
                     
                      if(startLookingForWords_counting && numChildren4 == 0){
                          counterWordsAfter_to_mark++;
                        }
                   
                      if (tree_four_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                          returnValue_search = true;
                          break;
                      }
                      if (tree_four_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                          returnValue_search = true;
                          break;
                      }
                      if (startLookingForWords_counting && tree_four_label.matches(",")) {
                          returnValue_search = false;
                          break;
                      }
                       
                      
                      int count4 = 0;
                      while (count4 < numChildren4) {
                          Tree tree_five = tree_four.getChild(count4);
          
                          String tree_five_label = tree_five.nodeString();

                          if(tree_five_label.contains("VP"))
                              VerbPhraseStarted = true;
                          
                           if(VerbPhraseStarted)
                              if(tree_five_label.contains("TO")){
                                  Encounter_Level_Infinitive[5] = true;
                                  startLookingForWords_counting = true;
                              }
                           
                           int numChildren5 = tree_five.numChildren();
                           
                            if(startLookingForWords_counting && numChildren5 == 0){
                               counterWordsAfter_to_mark++;
                             }
                
                          if (tree_five_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                              returnValue_search = true;
                              break;
                          }
                          if (tree_five_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                              returnValue_search = true;
                              break;
                          }
                          if (startLookingForWords_counting && tree_five_label.matches(",")) {
                              returnValue_search = false;
                              break;
                          }
                          
                          
                          int count5 = 0;
                          while (count5 < numChildren5) {
                              Tree tree_six = tree_five.getChild(count5);
          
                              String tree_six_label = tree_six.nodeString();

                              if(tree_six_label.contains("VP"))
                                 VerbPhraseStarted = true;
                              
                               if(VerbPhraseStarted)
                                  if(tree_six_label.contains("TO")){
                                     Encounter_Level_Infinitive[6] = true;
                                     startLookingForWords_counting = true;
                                  }
                               
                               int numChildren6 = tree_six.numChildren();
                               
                               if(startLookingForWords_counting && numChildren6 == 0){
                                    counterWordsAfter_to_mark++;
                                 }
                   
                              if (tree_six_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                  returnValue_search = true;
                                  break;
                              }
                              if (tree_six_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                  returnValue_search = true;
                                  break;
                              }
                              if (startLookingForWords_counting && tree_six_label.matches(",")) {
                                  returnValue_search = false;
                                  break;
                              }
                              
                              int count6 = 0;
                              while (count6 < numChildren6) {
                                  Tree tree_seven = tree_six.getChild(count6);
          
                                  String tree_seven_label = tree_seven.nodeString();

                                  if(tree_seven_label.contains("VP"))
                                      VerbPhraseStarted = true;
                                  
                                   if(VerbPhraseStarted)
                                       if(tree_seven_label.contains("TO")){
                                           Encounter_Level_Infinitive[7] = true;
                                           startLookingForWords_counting = true;
                                       }
                                  
                                   int numChildren7 = tree_seven.numChildren();
                                   
                                     if(startLookingForWords_counting && numChildren7 == 0){
                                         counterWordsAfter_to_mark++;
                                      }
                                  if (tree_seven_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                      returnValue_search = true;
                                      break;
                                  }
                                  if (tree_seven_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                      returnValue_search = true;
                                      break;
                                  }
                                  if (startLookingForWords_counting && tree_seven_label.matches(",")) {
                                      returnValue_search = false;
                                      break;
                                  }
                                  
                                  int count7 = 0;
                                  while (count7 < numChildren7) {
                                      Tree tree_eight = tree_seven.getChild(count7);
          
                                      String tree_eight_label = tree_eight.nodeString();

                                      if(tree_eight_label.contains("VP"))
                                          VerbPhraseStarted = true;
                                      
                                        if(VerbPhraseStarted)
                                            if(tree_eight_label.contains("TO")){
                                                Encounter_Level_Infinitive[8] = true;
                                                startLookingForWords_counting = true;
                                            }
                                     
                                        int numChildren8 = tree_eight.numChildren();
                                        
                                        if(startLookingForWords_counting && numChildren8 == 0){
                                           counterWordsAfter_to_mark++;
                                        }
                                      if (tree_eight_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                          returnValue_search = true;
                                          break;
                                      }
                                      if (tree_eight_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                          returnValue_search = true;
                                          break;
                                      }
                                      if (startLookingForWords_counting && tree_eight_label.matches(",")) {
                                          returnValue_search = false;
                                          break;
                                      } 
                                      int count8 = 0;
                                      while (count8 < numChildren8) {
                                          Tree tree_nine = tree_eight.getChild(count8);
          
                                          String tree_nine_label = tree_nine.nodeString();

                                          if(tree_nine_label.contains("VP"))
                                              VerbPhraseStarted = true;
                                          
                                           if(VerbPhraseStarted)
                                               if(tree_nine_label.contains("TO")){
                                                   Encounter_Level_Infinitive[9] = true;
                                                   startLookingForWords_counting = true;
                                               }
                                          
                                           int numChildren9 = tree_nine.numChildren();
                                           
                                           if(startLookingForWords_counting && numChildren9 == 0){
                                               counterWordsAfter_to_mark++;
                                            }
                                          if (tree_nine_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                              returnValue_search = true;
                                              break;
                                          }
                                          if (tree_nine_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                              returnValue_search = true;
                                              break;
                                          }
                                          if (startLookingForWords_counting && tree_nine_label.matches(",")) {
                                              returnValue_search = false;
                                              break;
                                          }
                                          
                                          int count9 = 0;
                                          while (count9 < numChildren9) {
                                              Tree tree_ten = tree_nine.getChild(count9);
          
                                              String tree_ten_label = tree_ten.nodeString();

                                              if(tree_ten_label.contains("VP"))
                                                 VerbPhraseStarted = true;
                                              
                                               if(VerbPhraseStarted)
                                                   if(tree_ten_label.contains("TO")){
                                                       Encounter_Level_Infinitive[10] = true;
                                                       startLookingForWords_counting = true;
                                                   }
                                              
                                               int numChildren10 = tree_ten.numChildren();
                                            
                                              if(startLookingForWords_counting && numChildren10 == 0){
                                                   counterWordsAfter_to_mark++;
                                                }
                                              if (tree_ten_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                  returnValue_search = true;
                                                  break;
                                              }
                                              if (tree_ten_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                  returnValue_search = true;
                                                  break;
                                              }
                                              if (startLookingForWords_counting && tree_ten_label.matches(",")) {
                                                  returnValue_search = false;
                                                  break;
                                              }
                                            
                                              int count10 = 0;
                                              while (count10 < numChildren10) {
                                                  Tree tree_eleven = tree_ten.getChild(count10);
          
                                                  String tree_eleven_label = tree_eleven.nodeString();

                                                  if(tree_eleven_label.contains("VP"))
                                                      VerbPhraseStarted = true;
                                                  
                                                  if (VerbPhraseStarted) {
                                                      if (tree_eleven_label.contains("TO")) {
                                                          Encounter_Level_Infinitive[11] = true;
                                                          startLookingForWords_counting = true;
                                                      }
                                                  }
                                                        int numChildren11 = tree_eleven.numChildren();
                                                        
                                                  if (startLookingForWords_counting && numChildren11 == 0) {
                                                      counterWordsAfter_to_mark++;
                                                  }
                                                  if (tree_eleven_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                      returnValue_search = true;
                                                      break;
                                                  }
                                                     
                                                  if (tree_eleven_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                      returnValue_search = true;
                                                      break;
                                                  }
                                                  if (startLookingForWords_counting && tree_eleven_label.matches(",")) {
                                                      returnValue_search = false;
                                                      break;
                                                  } 
                                                  
                                                  int count11 = 0;
                                                  while (count11 < numChildren11) {
                                                      Tree tree_twelve = tree_eleven.getChild(count11);
          
                                                      String tree_twelve_label = tree_twelve.nodeString();
                                                      
                                                      if (tree_twelve_label.contains("VP")) {
                                                          VerbPhraseStarted = true;
                                                      }

                                                        if (VerbPhraseStarted) {
                                                            if (tree_twelve_label.contains("TO")) {
                                                               Encounter_Level_Infinitive[12] = true;
                                                               startLookingForWords_counting = true;
                                                            }
                                                        }
                                                      
                                                      int numChildren12 = tree_twelve.numChildren();
                                                     
                                                         if (startLookingForWords_counting && numChildren12 == 0) {
                                                             counterWordsAfter_to_mark++;
                                                         }
                                                      if (tree_twelve_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                          returnValue_search = true;
                                                          break;
                                                      }
                                                      if (tree_twelve_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                          returnValue_search = true;
                                                          break;
                                                      }
                                                      if (startLookingForWords_counting && tree_twelve_label.matches(",")) {
                                                          returnValue_search = false;
                                                          break;
                                                      } 
                                                      
                                                      int count12 = 0;
                                                      while (count12 < numChildren12) {
                                                          Tree tree_thirteen = tree_twelve.getChild(count12);
          
                                                          String tree_thirteen_label = tree_thirteen.nodeString();
                                                            
                                                          if (tree_thirteen_label.contains("VP")) {
                                                              VerbPhraseStarted = true;
                                                          }
                                                            if (VerbPhraseStarted) {
                                                                if (tree_thirteen_label.contains("TO")) {
                                                                   Encounter_Level_Infinitive[13] = true;
                                                                   startLookingForWords_counting = true;
                                                                }
                                                            }
                                                          
                                                            int numChildren13 = tree_thirteen.numChildren();
                                                          
                                                            if (startLookingForWords_counting && numChildren13 == 0) {
                                                                counterWordsAfter_to_mark++;
                                                            }
                                                          if (tree_thirteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                              returnValue_search = true;
                                                              break;
                                                          }
                                                          if (tree_thirteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                              returnValue_search = true;
                                                              break;
                                                          }
                                                          if (startLookingForWords_counting && tree_thirteen_label.matches(",")) {
                                                              returnValue_search = false;
                                                              break;
                                                          }
                                                           
                                                          
                                                          int count13 = 0;
                                                          while (count13 < numChildren13) {
                                                              Tree tree_fourteen = tree_thirteen.getChild(count13);
            
                                                              String tree_fourteen_label = tree_fourteen.nodeString();
                                                              
                                                              if (tree_fourteen_label.contains("VP")) {
                                                                  VerbPhraseStarted = true;
                                                              }
                                                                if (VerbPhraseStarted) {
                                                                    if (tree_fourteen_label.contains("TO")) {
                                                                        Encounter_Level_Infinitive[14] = true;
                                                                        startLookingForWords_counting = true;
                                                                    }
                                                                }
                                                              
                                                                 int numChildren14 = tree_fourteen.numChildren();
                                                     
                                                              if (startLookingForWords_counting && numChildren14 == 0) {
                                                                  counterWordsAfter_to_mark++;
                                                              }
                                                              if(tree_fourteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                  returnValue_search = true;
                                                                  break;
                                                              }
                                                              if (tree_fourteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                  returnValue_search = true;
                                                                  break;
                                                              }
                                                              if (startLookingForWords_counting && tree_fourteen_label.matches(",")) {
                                                                  returnValue_search = false;
                                                                  break;
                                                              }
                                                              int count14 = 0;
                                                              while(count14 < numChildren14){
                                                                Tree tree_fifteen = tree_fourteen.getChild(count14);
            
                                                                String tree_fifteen_label = tree_fifteen.nodeString();
                                                          
                                                                  if (tree_fifteen_label.contains("VP")) {
                                                                      VerbPhraseStarted = true;
                                                                  }
                                                                  if (VerbPhraseStarted) {
                                                                      if (tree_fifteen_label.contains("TO")) {
                                                                          Encounter_Level_Infinitive[15] = true;
                                                                          startLookingForWords_counting = true;
                                                                      }
                                                                  }
                                                                  int numChildren15 = tree_fifteen.numChildren();

                                                                  if (startLookingForWords_counting && numChildren15 == 0) {
                                                                      counterWordsAfter_to_mark++;
                                                                  }

                                                                  if (tree_fifteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                      returnValue_search = true;
                                                                      break;
                                                                  }
                                                                  if (tree_fifteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                      returnValue_search = true;
                                                                      break;
                                                                  }
                                                                  if (startLookingForWords_counting && tree_fifteen_label.matches(",")) {
                                                                      returnValue_search = false;
                                                                      break;
                                                                  }
                                                                  int count15 = 0;
                                                                  while (count15 < numChildren15) {
                                                                        Tree tree_sixteen = tree_fifteen.getChild(count15);
            
                                                                        String tree_sixteen_label = tree_sixteen.nodeString();
                                                                        
                                                                      if (tree_sixteen_label.contains("VP")) {
                                                                          VerbPhraseStarted = true;
                                                                      }
                                                                        
                                                                        if (VerbPhraseStarted) {
                                                                            if (tree_sixteen_label.contains("TO")) {
                                                                                Encounter_Level_Infinitive[16] = true;
                                                                                startLookingForWords_counting = true;
                                                                           }
                                                                        }

                                                                        int numChildren16 = tree_sixteen.numChildren();
  
                                                                        if (startLookingForWords_counting && numChildren16 == 0) {
                                                                          counterWordsAfter_to_mark++;
                                                                        }

                                                                      if (tree_sixteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                          returnValue_search = true;
                                                                          break;
                                                                      }
                                                                        if (tree_sixteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                            returnValue_search = true;
                                                                            break;
                                                                        }
                                                                        if (startLookingForWords_counting && tree_sixteen_label.matches(",")) {
                                                                            returnValue_search = false;
                                                                            break;
                                                                        }
                                                                        int count16 = 0;
                                                                        while (count16 < numChildren16) {
                                                                            Tree tree_seventeen = tree_sixteen.getChild(count16);
            
                                                                            String tree_seventeen_label = tree_seventeen.nodeString();
  
                                                                               if (tree_seventeen_label.contains("VP")) {
                                                                                VerbPhraseStarted = true;
                                                                            }
                                                                            
                                                                            if (VerbPhraseStarted) {
                                                                                if (tree_seventeen_label.contains("TO")) {
                                                                                    Encounter_Level_Infinitive[17] = true;
                                                                                    startLookingForWords_counting = true;
                                                                                }
                                                                            }

                                                                            int numChildren17 = tree_seventeen.numChildren();
                                                                            
                                                                            if (startLookingForWords_counting && numChildren17 == 0) {
                                                                               counterWordsAfter_to_mark++;
                                                                            }

                                                                            if (tree_seventeen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                                returnValue_search = true;
                                                                                break;
                                                                            }
                                                                            if (tree_seventeen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                                returnValue_search = true;
                                                                                break;
                                                                            }
                                                                            if (startLookingForWords_counting && tree_seventeen_label.matches(",")) {
                                                                               returnValue_search = false;
                                                                                break;
                                                                             }
                                                                             int count17 = 0;
                                                                             while (count17 < numChildren17) {
                                                                                  Tree tree_eightteen = tree_seventeen.getChild(count17);
            
                                                                                 String tree_eightteen_label = tree_eightteen.nodeString();

                                                                                 if (tree_eightteen_label.contains("VP")) {
                                                                                     VerbPhraseStarted = true;
                                                                                 }
                                                                                 if (VerbPhraseStarted) {
                                                                                     if (tree_eightteen_label.contains("TO")) {
                                                                                         Encounter_Level_Infinitive[18] = true;
                                                                                         startLookingForWords_counting = true;
                                                                                     }
                                                                                 }
                                                                                 
                                                                                 int numChildren18 = tree_eightteen.numChildren();
                                                                                 
                                                                                 if (startLookingForWords_counting && numChildren18 == 0) {
                                                                                     counterWordsAfter_to_mark++;
                                                                                 }

                                                                                 if (tree_eightteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                                     returnValue_search = true;
                                                                                     break;
                                                                                 }
                                                                                 if (tree_eightteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                                     returnValue_search = true;
                                                                                     break;
                                                                                 }
                                                                                 if (startLookingForWords_counting && tree_eightteen_label.matches(",")) {
                                                                                     returnValue_search = false;
                                                                                     break;
                                                                                 }
                                                                                 int count18 = 0;
                                                                                 while (count18 < numChildren18) {
                                                                                      Tree tree_nineteen = tree_eightteen.getChild(count18);

                                                                                     String tree_nineteen_label = tree_nineteen.nodeString();

                                                                                     int numChildren19 = tree_nineteen.numChildren();

                                                                                     if (startLookingForWords_counting && numChildren19 == 0) {
                                                                                         counterWordsAfter_to_mark++;
                                                                                     }
                                                                                     
                                                                                     if (tree_nineteen_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                                         returnValue_search = true;
                                                                                         break;
                                                                                     } 
                                                                                     if (tree_nineteen_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                                         returnValue_search = true;
                                                                                         break;
                                                                                     }
                                                                                     if (startLookingForWords_counting && tree_nineteen_label.matches(",")) {
                                                                                         returnValue_search = false;
                                                                                         break;
                                                                                     }
                                                                                     int count19 = 0;
                                                                                     while (count19 < numChildren19) {

                                                                                         Tree tree_twenty = tree_nineteen.getChild(count19);

                                                                                         String tree_twenty_label = tree_twenty.nodeString();

                                                                                         int numChildren20 = tree_twenty.numChildren();

                                                                                         if (startLookingForWords_counting && numChildren20 == 0) {
                                                                                             counterWordsAfter_to_mark++;
                                                                                         }

                                                                                         if (tree_twenty_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                                             returnValue_search = true;
                                                                                             break;
                                                                                         }
                                                                                         if (tree_twenty_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                                             returnValue_search = true;
                                                                                             break;
                                                                                         }
                                                                                         if (startLookingForWords_counting && tree_twenty_label.matches(",")) {
                                                                                             returnValue_search = false;
                                                                                             break;
                                                                                         }
                                                                                         int count20 = 0;
                                                                                         while (count20 < numChildren20) {
                                                                                             Tree tree_twentyone = tree_twenty.getChild(count20);

                                                                                             String tree_twentyone_label = tree_twentyone.nodeString();

                                                                                             if (tree_twentyone_label.contains("WDT") && counterWordsAfter_to_mark > 2) {
                                                                                                 returnValue_search = true;
                                                                                                 break;
                                                                                             }
                                                                                             if (tree_twentyone_label.contains("VB") && counterWordsAfter_to_mark > 3) {
                                                                                                 returnValue_search = true;
                                                                                                 break;
                                                                                             }
                                                                                             if (startLookingForWords_counting && tree_twentyone_label.matches(",")) {
                                                                                                 returnValue_search = false;
                                                                                                 break;
                                                                                             }
                                                                                         
                                                                                             count20++;
                                                                                         }
                                                                                         
                                                                                         count19++;
                                                                                     }
                                                                                    
                                                                                    count18++;
                                                                                 }
                                                                            
                                                                                 count17++;
                                                                            
                                                                              }
                                                                             count16++;
                                                                        }
  
                                                                      if (Encounter_Level_Infinitive[17]) {
                                                                          startLookingForWords_counting = false;
                                                                          counterWordsAfter_to_mark = 0;
                                                                      }
                                                                        count15++;
                                                                         
                                                                  }
                                                                  if (Encounter_Level_Infinitive[16]) {
                                                                      startLookingForWords_counting = false;
                                                                      counterWordsAfter_to_mark = 0;
                                                                  }
                                                                  count14++;
                                                              }
                                                              
                                                              if (Encounter_Level_Infinitive[15]) {
                                                                  startLookingForWords_counting = false;
                                                                  counterWordsAfter_to_mark = 0;
                                                              }
                                                              count13++;

                                                          }
                                                          if (Encounter_Level_Infinitive[14]) {
                                                              startLookingForWords_counting = false;
                                                              counterWordsAfter_to_mark = 0;
                                                          }
                                                        count12++;
                                                      }
                                                      if (Encounter_Level_Infinitive[13]) {
                                                          startLookingForWords_counting = false;
                                                          counterWordsAfter_to_mark = 0;
                                                      }
                                                      
                                                    count11++;  
                                                  }
                                                  if (Encounter_Level_Infinitive[12]) {
                                                      startLookingForWords_counting = false;
                                                      counterWordsAfter_to_mark = 0;
                                                  }
                                                  
                                                count10++;  
                                              }
                                             if (Encounter_Level_Infinitive[11]) {
                                              startLookingForWords_counting = false;
                                              counterWordsAfter_to_mark = 0;
                                              }
                                              
                                            count9++;  
                                          }
                                          if (Encounter_Level_Infinitive[10]) {
                                              startLookingForWords_counting = false;
                                              counterWordsAfter_to_mark = 0;
                                          }
         
                                        count8++;
                                      }
                                      if (Encounter_Level_Infinitive[9]) {
                                          startLookingForWords_counting = false;
                                          counterWordsAfter_to_mark = 0;
                                      }
                                    count7++;
                                  }
                                  
                                  if (Encounter_Level_Infinitive[8]) {
                                      startLookingForWords_counting = false;
                                      counterWordsAfter_to_mark = 0;
                                  }
           
                                count6++;

                              }
                              
                              if (Encounter_Level_Infinitive[7]) {
                                  startLookingForWords_counting = false;
                                  counterWordsAfter_to_mark = 0;
                              }
          
                              
                            count5++;  
                          }
                          
                          if (Encounter_Level_Infinitive[6]) {
                              startLookingForWords_counting = false;
                              counterWordsAfter_to_mark = 0;
                          }
     
                        count4++;
                      }
                      
                      if (Encounter_Level_Infinitive[5]) {
                          startLookingForWords_counting = false;
                          counterWordsAfter_to_mark = 0;
                      }
                    count3++;
                  }
                  if (Encounter_Level_Infinitive[4]) {
                      startLookingForWords_counting = false;
                      counterWordsAfter_to_mark = 0;
                  }
                count2++;
              }
           
              if (Encounter_Level_Infinitive[3]) {
                  startLookingForWords_counting = false;
                  counterWordsAfter_to_mark = 0;
              }
          
            count1++;
          }
          if(Encounter_Level_Infinitive[2]){
              startLookingForWords_counting = false;
              counterWordsAfter_to_mark = 0;
          }
          
          VerbPhraseStarted = false;
        count0++;
      }
        
   
        return returnValue_search;
    }
    
    
    public static int[] check_linking_verb_when_No_root_VB_Classifcation(Tree tree){
        
        // root is first,    boolean result second  of whether root is adject (true) 1 or false 0
        int[] returnResults = new int[2];
        
        returnResults[0] = 0;
        returnResults[1] = 0;
        
        boolean returnValue_linking_class_result = false;

                // background dependency parsing
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        //use the tree graph made earlier ...(up several pages)
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
        
        Iterator itrBlank0 = tdl.iterator();
        
        
        
        boolean copulativeInSentence = false;
        boolean rootStringAcquired = false;
        // the return criteria
        boolean rootIsNotVerb = false;
  
        String currentString;
        String rootString = "null";

        boolean startTaperingOut = false;
        int countTo_start_taperingOutOfLine = 3;        
        
        // look for root and if there is a linking verb copulative or not first.
          // iterator will be continued later down in code.... too...
        while (itrBlank0.hasNext()) {
            
            if(countTo_start_taperingOutOfLine == 0)
                break;
            
            currentString = itrBlank0.next().toString();

            String[] arrayIn = currentString.split(" ");
            int lengthOfDepFragment = arrayIn[arrayIn.length - 1].length();
            String subjectSentence = arrayIn[arrayIn.length - 1];
            // extract int from the word String // core label/ title
            String[] subjectTypedSections1 = subjectSentence.split("-");
            String currentTokenString = subjectTypedSections1[0];
            
            
            lengthOfDepFragment = subjectTypedSections1.length;
            int numberFragment1 = subjectTypedSections1[lengthOfDepFragment - 1].length();
            int intIndex1 = 0;
            try {
                    intIndex1 = Integer.parseInt(subjectTypedSections1[lengthOfDepFragment - 1].substring(0, numberFragment1 - 1));
                    
               } catch (NumberFormatException e) {
                   
               }
           
            
            if (currentString.startsWith("nsubj(")) {
                continue;
            }
            
            
            else if(currentString.contains("root(")){
                   
                   rootStringAcquired = true;
                   returnResults[0] = intIndex1;
                   rootString = currentTokenString;
                   startTaperingOut = true;
                   continue;
             }
                
               // check if a punctuation mark is encountered
            if(currentString.contains("cop(") ){
                
                copulativeInSentence = true;
                break;
                
            }
            
              if(startTaperingOut){
                  countTo_start_taperingOutOfLine--;
                  continue;
              }
        
        }
        
        // if copulative is in sentence, // quit and return true;
        if(copulativeInSentence)
           rootIsNotVerb = true;
        

      boolean VerbPhraseStarted = false;
      

      boolean gotRootEntry_in_Tree = false;
      
      String currentPOSLabel = "null";
      
      String labelWordentry = "null";
      int numChildren0 = tree.numChildren();
      int count0 = 0;
      
      while (count0 < numChildren0) {
          Tree tree_one = tree.getChild(count0);
          
          int numChildren1 = tree_one.numChildren();
          
          String currentPOSLabel0 = tree_one.nodeString();
          
          int count1 = 0;
          while (count1 < numChildren1) {
              Tree tree_two = tree_one.getChild(count1);
          
               int numChildren2 = tree_two.numChildren();
               // the word label   from the pos entry last loop above
              String currentPOSLabel1 = tree_two.nodeString();

              if(numChildren2 == 0)
                  if(currentPOSLabel1.matches(rootString))
                      gotRootEntry_in_Tree = true;
              
             if(gotRootEntry_in_Tree){
                 if(!currentPOSLabel0.contains("VB")){
                     rootIsNotVerb = true;
                     break;
                 }
             } 
             
            
               
              int count2 = 0;
              while (count2 < numChildren2) {
                  Tree tree_three = tree_two.getChild(count2);
          
                  int numChildren3 = tree_three.numChildren();
                  
                  String currentPOSLabel2 = tree_three.nodeString();

                  if (numChildren3 == 0) {
                      if (currentPOSLabel2.matches(rootString)) {
                          gotRootEntry_in_Tree = true;
                      }
                  }

                  if (gotRootEntry_in_Tree) {
                      if (!currentPOSLabel1.contains("VB")) {
                          rootIsNotVerb = true;
                          break;
                      }
                  }

                  
                  
                  int count3 = 0;
                  while (count3 < numChildren3) {
                      Tree tree_four = tree_three.getChild(count3);
          
                      int numChildren4 = tree_four.numChildren();
                      
                      String currentPOSLabel3 = tree_four.nodeString();

                      if (numChildren4 == 0) {
                          if (currentPOSLabel3.matches(rootString)) {
                              gotRootEntry_in_Tree = true;
                          }
                      }

                      if (gotRootEntry_in_Tree) {
                          if (!currentPOSLabel2.contains("VB")) {
                              rootIsNotVerb = true;
                              break;
                          }
                      }

                      
                      int count4 = 0;
                      while (count4 < numChildren4) {
                          Tree tree_five = tree_four.getChild(count4);
          
                           int numChildren5 = tree_five.numChildren();
                           
                          String currentPOSLabel4 = tree_five.nodeString();

                           if (numChildren5 == 0) {
                              if (currentPOSLabel4.matches(rootString)) {
                                  gotRootEntry_in_Tree = true;
                              }
                          }

                          if (gotRootEntry_in_Tree) {
                              if (!currentPOSLabel3.contains("VB")) {
                                  rootIsNotVerb = true;
                                  break;
                              }
                          }

                          
                          
                          int count5 = 0;
                          while (count5 < numChildren5) {
                              Tree tree_six = tree_five.getChild(count5);
          
                              int numChildren6 = tree_six.numChildren();
                              
                              String currentPOSLabel5 = tree_six.nodeString();

                              if (numChildren6 == 0) {
                                  if (currentPOSLabel5.matches(rootString)) {
                                      gotRootEntry_in_Tree = true;
                                  }
                              }

                              if (gotRootEntry_in_Tree) {
                                  if (!currentPOSLabel4.contains("VB")) {
                                      rootIsNotVerb = true;
                                      break;
                                  }
                              }
                              
                              
                              int count6 = 0;
                              while (count6 < numChildren6) {
                                  Tree tree_seven = tree_six.getChild(count6);
          
                                   int numChildren7 = tree_seven.numChildren();
                                   
                                  String currentPOSLabel6 = tree_seven.nodeString();

                                  if (numChildren7 == 0) {
                                      if (currentPOSLabel6.matches(rootString)) {
                                          gotRootEntry_in_Tree = true;
                                      }
                                  }

                                  if (gotRootEntry_in_Tree) {
                                      if (!currentPOSLabel5.contains("VB")) {
                                          rootIsNotVerb = true;
                                          break;
                                      }
                                  }
                                 
                                  
                                  int count7 = 0;
                                  while (count7 < numChildren7) {
                                      Tree tree_eight = tree_seven.getChild(count7);
          
                                        int numChildren8 = tree_eight.numChildren();
                                        
                                        String currentPOSLabel7 = tree_eight.nodeString();

                                     if (numChildren8 == 0) {
                                          if (currentPOSLabel7.matches(rootString)) {
                                              gotRootEntry_in_Tree = true;
                                          }
                                      }

                                      if (gotRootEntry_in_Tree) {
                                          if (!currentPOSLabel6.contains("VB")) {
                                              rootIsNotVerb = true;
                                              break;
                                          }
                                      }
                                      
                                      int count8 = 0;
                                      while (count8 < numChildren8) {
                                          Tree tree_nine = tree_eight.getChild(count8);
          
                                          int numChildren9 = tree_nine.numChildren();
                                          
                                          String currentPOSLabel8 = tree_nine.nodeString();

                                          if (numChildren9 == 0) {
                                              if (currentPOSLabel8.matches(rootString)) {
                                                  gotRootEntry_in_Tree = true;
                                              }
                                          }

                                          if (gotRootEntry_in_Tree) {
                                              if (!currentPOSLabel7.contains("VB")) {
                                                  rootIsNotVerb = true;
                                                  break;
                                              }
                                          }
                                          
                                          
                                            break;
                                              
                                              
                                             
                                          
                                         
         
                                        
                                      }
                                  
                                    count7++;
                                  }
                                  
                            
           
                                count6++;

                              }
                              
                           
                              
                            count5++;  
                          }
                          
                       
     
                        count4++;
                      }
                      
                    count3++;
                  }
                
                count2++;
              }
           
             
          
            count1++;
          }
    
          
          
        count0++;
    }
      
        if(rootIsNotVerb)
           returnResults[1] = 1;
        
        return returnResults;
        
    }
    
}
