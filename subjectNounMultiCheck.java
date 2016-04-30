/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversationauto1;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

/**
 *
 * @author ebeclaim3u
 */
public class subjectNounMultiCheck {
       
    // first two integers in int[] return are more noun subjects.
    // the 3rd and fourth are possible (non critical: not nouns exactly)pre-determiners (i.e. any, some, all, each) for the first two,
    // the last two are possible (non critical: not nouns exactly) determiners (i.e. this, those, many) for the first two,
    //    w/ each related one to one with the first two subjects as ordered from first. 
    public static int[] lookAdditionalSubjects(Tree treeInput, int primarySubjectIndex) {
        // return variable initialize
        int[] givenNounIndices = {0,0,0,0,0,0};
        
        int DTentryPossible1 = 0;
        int DTentryPossible2 = 0;
        
        
        // flag to start base noun phrase area of main subject
        boolean commonBranchOfSubjectArea = false;
        // if comma follows noun area, there may be 2 or more subjects extra
        boolean commaFollows_1st = false;
        // if comma follows noun area, there may be 3 or more subjects extra
        boolean commaFollows_2nd = false;
        // conjunction meaning two nouns or more present.
        boolean conjunctAndFollows = false;
        
        // boolean for NN
        boolean NNentryFollows = false;
        // boolean for PRP
        boolean PRPentryFollows = false;
        // boolean for DT
        boolean DTentryFollows = false;
        // boolean for PDT
        boolean PDTentryFollows = false;
        
// mark the main branch that holds the subject clauses
        // for instance...  this goes on in while loop with while(countOfPart3< ...
        // and refers to the one earlier loop branch level.
        boolean nounPhraseUseExpanse1 = false;
        // for instance...  this goes on in while loop with while(countOfPart4< ...
        // and refers to the one earlier loop branch level.
        boolean nounPhraseUseExpanse2 = false;
        boolean nounPhraseUseExpanse3 = false;
        boolean nounPhraseUseExpanse4 = false;
        boolean nounPhraseUseExpanse5 = false;
        boolean nounPhraseUseExpanse6 = false;
        // boolean nounPhraseUseExpanse7 = false;
        
        // check if more phrases exists in current phrase locale.
        boolean morePhrasesNPreasoning = false;
        
        
        
        // inspection flag at end of nounphrase section of a noun.
          boolean nounPhraseOnNow2 = false;
        
        boolean nounPhraseOnNow3 = false;
        boolean nounPhraseOnNow4 = false;
        boolean nounPhraseOnNow5 = false;
        boolean nounPhraseOnNow6 = false;
        boolean nounPhraseOnNow7 = false;
        // boolean nounPhraseOnNow8 = false;
        
        
        
        boolean fallOutOfLoops = false;
        
        boolean firstItemHasNoun = false;
        boolean secondItemHasNoun = false;
        
        boolean firstCommaNotFollowedByDirectNounPhrase = false;
        boolean bypassSecondComma = false;
        boolean adjustSecondNounPostponedFromNonNounSubClause = false;
        
        boolean runOnce_NonPhraseAfterComma = false;
        
        
        // retrieve first start child.
        Tree treeAlso0 = treeInput.firstChild();
        int numChildrenTreeAlso0 = treeAlso0.numChildren();
        int countOfPart1 = 0;
        while (countOfPart1 < numChildrenTreeAlso0){
            
            // items found... exit loops
            if(fallOutOfLoops)
                break;
            Tree tree1_childElement = treeAlso0.getChild(countOfPart1);
            
            int outPartChildComp1 = tree1_childElement.numChildren();
            // dealing with finding larger phrases if we have subject clause later on...
            // historic reference for earlier NP ascertainment
            //  reference to tree1_childElement.nodeString();

            int countOfPart2 = 0;
            while(countOfPart2 < outPartChildComp1) {
                
                // items found... exit loops
                if(fallOutOfLoops)
                    break;
                
                Tree tree2_childElement = tree1_childElement.getChild(countOfPart2);

                int outPartChildComp2 = tree2_childElement.numChildren();
                // dealing with finding larger phrases if we have subject clause later on...
                // historic reference for earlier NP ascertainment
                //  reference to tree2_childElement.nodeString();
                                
                    if(commonBranchOfSubjectArea && tree2_childElement.nodeString().matches("PDT")){
                        PDTentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow2 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree2_childElement.nodeString().matches("DT")){
                        DTentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow2 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree2_childElement.nodeString().matches("PRP")){
                        PRPentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow2 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree2_childElement.nodeString().contains("NN")){
                        NNentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow2 = true;
                    }
                    else if(commonBranchOfSubjectArea &&   !tree2_childElement.nodeString().contains("NP") && !tree2_childElement.nodeString().contains(",")  &&   commaFollows_1st && !commaFollows_2nd && !runOnce_NonPhraseAfterComma){
                        firstCommaNotFollowedByDirectNounPhrase = true;
                        runOnce_NonPhraseAfterComma = true;
                    }
                int countOfPart3 = 0;
                while (countOfPart3 < outPartChildComp2) {
                    // items found... exit loops
                    if(fallOutOfLoops)
                        break;
                    
                    if(firstCommaNotFollowedByDirectNounPhrase){
                           bypassSecondComma = true;
                           //turn off so this does happen anymore
                           firstCommaNotFollowedByDirectNounPhrase = false;
                           break;
                        }
                    
                    Tree tree3_childElement = tree2_childElement.getChild(countOfPart3);
                    
                    int outPartChildComp3 = tree3_childElement.numChildren();
                    
                    // dealing with finding larger phrases if we have subject clause later on...
                    // historic reference for earlier NP ascertainment
                    //  reference to tree3_childElement.nodeString();
                    // acquire verb index number.
                    int verbWordIndex4 = 0;
                    if (outPartChildComp3 == 0) {                        
                        if (tree3_childElement.nodeString().equals(",") && commonBranchOfSubjectArea) {
                            if (!commaFollows_1st) {
                                commaFollows_1st = true;
                            } else {
                                commaFollows_2nd = true;
                                
                            }
                        } else if (tree3_childElement.nodeString().equals("and") && commonBranchOfSubjectArea) {
                            conjunctAndFollows = true;
                            if(bypassSecondComma && commaFollows_2nd){
                                adjustSecondNounPostponedFromNonNounSubClause = true;
                                bypassSecondComma = false;
                            }
                        }

                        
                        String[] labelInt4 = tree3_childElement.label().toString().split("-");
                        int labelItemsLength4 = labelInt4.length;

                        if (labelItemsLength4 > 1) {
                            verbWordIndex4 = Integer.parseInt(labelInt4[labelItemsLength4 - 1]);
                        }

                        if (verbWordIndex4 == primarySubjectIndex) {
                            commonBranchOfSubjectArea = true;
                            // set course to end gathering subjects altogether once elements at one earlier outer level loop child elements run out
                            nounPhraseUseExpanse1 = true;
                            break;
                        }

                        if (PDTentryFollows) {
                            // add predeterminer index for 1st accompanier after 1st originator subject
                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  || adjustSecondNounPostponedFromNonNounSubClause  ) {
                                givenNounIndices[2] = verbWordIndex4;
                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                givenNounIndices[3] = verbWordIndex4;
                            }
                            PDTentryFollows = false;
                        } else if (DTentryFollows) {
                                // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                            //    for 1st accompanier after 1st originator subject
                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause  ) {
                                DTentryPossible1 = verbWordIndex4;
                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                DTentryPossible2 = verbWordIndex4;
                            }
                            DTentryFollows = false;
                        } else if (PRPentryFollows) {
                            // add pronoun subject index
                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause  ) {
                                givenNounIndices[0] = verbWordIndex4;
                                firstItemHasNoun = true;
                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                givenNounIndices[1] = verbWordIndex4;
                                secondItemHasNoun = true;
                            }
                            PRPentryFollows = false;
                        } else if (NNentryFollows) {
                            // add noun subject index
                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause   ) {
                                givenNounIndices[0] = verbWordIndex4;
                                firstItemHasNoun = true;
                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                givenNounIndices[1] = verbWordIndex4;
                                secondItemHasNoun = true;
                            }
                            NNentryFollows = false;
                        }
                        break;

                    }
                    
                    else if(commonBranchOfSubjectArea && tree3_childElement.nodeString().matches("PDT")){
                        PDTentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow3 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree3_childElement.nodeString().matches("DT")){
                        DTentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow3 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree3_childElement.nodeString().matches("PRP")){
                        PRPentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow3 = true;
                    }
                    else if(commonBranchOfSubjectArea && tree3_childElement.nodeString().contains("NN")){
                        NNentryFollows = true;
                        // noun section noted
                        nounPhraseOnNow3 = true;
                    }
                    else if(commonBranchOfSubjectArea && !tree3_childElement.nodeString().contains("NP") && !tree3_childElement.nodeString().contains(",") && commaFollows_1st && !commaFollows_2nd && !runOnce_NonPhraseAfterComma ){
                        firstCommaNotFollowedByDirectNounPhrase = true;
                        runOnce_NonPhraseAfterComma = true;
                    }
                        
                    
                    
                    int countOfPart4 = 0;
                    while (countOfPart4 < outPartChildComp3) {
                        
                        // items found... exit loops
                        if(fallOutOfLoops)
                           break;
                        
                        if(firstCommaNotFollowedByDirectNounPhrase){
                           bypassSecondComma = true;
                           // turn off now so it doesn't happen anymore
                           firstCommaNotFollowedByDirectNounPhrase = false;
                           break;
                        }
            
                        Tree tree4_childElement = tree3_childElement.getChild(countOfPart4);
                        
                        int outPartChildComp4 = tree4_childElement.numChildren();
                        
                                            
                        // dealing with finding larger phrases if we have subject clause later on...
                        // historic reference for earlier NP ascertainment
                        //  reference to tree4_childElement.nodeString();
                        
                        // acquire verb index number.
                        int verbWordIndex5 = 0;
                        if (outPartChildComp4 == 0) {
                            if (tree4_childElement.nodeString().equals(",") && commonBranchOfSubjectArea) {
                                if (!commaFollows_1st) {
                                    commaFollows_1st = true;
                                } else {
                                    commaFollows_2nd = true;
                                   
                                }
                            } else if (tree4_childElement.nodeString().equals("and") && commonBranchOfSubjectArea) {
                                conjunctAndFollows = true;
                                if(bypassSecondComma && commaFollows_2nd){
                                    adjustSecondNounPostponedFromNonNounSubClause = true;
                                    bypassSecondComma = false;
                                }
                            }


                            String[] labelInt5 = tree4_childElement.label().toString().split("-");
                            int labelItemsLength5 = labelInt5.length;

                            if (labelItemsLength5 > 1) {
                                verbWordIndex5 = Integer.parseInt(labelInt5[labelItemsLength5 - 1]);
                            }

                            if (verbWordIndex5 == primarySubjectIndex) {
                                commonBranchOfSubjectArea = true;
                                // set course to end gathering subjects altogether once elements at one earlier outer level loop child elements run out
                                nounPhraseUseExpanse2 = true;
                                break;
                            }
                            
                            if(PDTentryFollows){
                                // add predeterminer index for 1st accompanier after 1st originator subject
                                if( (conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause )
                                    givenNounIndices[2] = verbWordIndex5;
                                else if( commaFollows_2nd && conjunctAndFollows  )
                                    givenNounIndices[3] = verbWordIndex5;
                                PDTentryFollows = false;
                            }
                            else if(DTentryFollows){
                                // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                                //    for 1st accompanier after 1st originator subject
                                if( (conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause  )
                                     DTentryPossible1 = verbWordIndex5;
                                else if( commaFollows_2nd && conjunctAndFollows  )
                                     DTentryPossible2 = verbWordIndex5;
                                DTentryFollows = false;
                            }
                            else if(PRPentryFollows){
                                // add pronoun subject index
                                
                                if( (conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  || adjustSecondNounPostponedFromNonNounSubClause  ){
                                    givenNounIndices[0] = verbWordIndex5;
                                    firstItemHasNoun = true;
                                    
                                }
                                else if( commaFollows_2nd && conjunctAndFollows  ){
                                    givenNounIndices[1] = verbWordIndex5;
                                    secondItemHasNoun = true;
                                }
                                PRPentryFollows = false;
                            }
                            else if(NNentryFollows){
                                // add noun subject index
                                if( (conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  || adjustSecondNounPostponedFromNonNounSubClause   ){
                                    givenNounIndices[0] = verbWordIndex5;
                                    
                                    firstItemHasNoun = true;
                                }
                                else if( commaFollows_2nd && conjunctAndFollows  ){
                                    givenNounIndices[1] = verbWordIndex5;
                                    secondItemHasNoun = true;
                                }
                                NNentryFollows = false;
                            }
                            
                            break;

                        }
                                            
                        else if (commonBranchOfSubjectArea && tree4_childElement.nodeString().matches("PDT")) {
                            PDTentryFollows = true;
                            // noun section noted
                            nounPhraseOnNow4 = true;
                        } else if (commonBranchOfSubjectArea && tree4_childElement.nodeString().matches("DT")) {
                            DTentryFollows = true;
                            // noun section noted
                            nounPhraseOnNow4 = true;
                        } else if (commonBranchOfSubjectArea && tree4_childElement.nodeString().matches("PRP")) {
                            PRPentryFollows = true;
                            // noun section noted
                            nounPhraseOnNow4 = true;
                        } else if (commonBranchOfSubjectArea && tree4_childElement.nodeString().contains("NN")) {
                            NNentryFollows = true;
                            // noun section noted
                            nounPhraseOnNow4 = true;
                        }
                        else if(commonBranchOfSubjectArea && !tree4_childElement.nodeString().contains("NP") && !tree4_childElement.nodeString().contains(",") && commaFollows_1st && !commaFollows_2nd  && !runOnce_NonPhraseAfterComma){
                            firstCommaNotFollowedByDirectNounPhrase = true;
                            runOnce_NonPhraseAfterComma = true;
                        }

                        int countOfPart5 = 0;
                        while (countOfPart5 < outPartChildComp4) {
                            
                            // items found... exit loops
                            if(fallOutOfLoops)
                                break;
                            
                            if (firstCommaNotFollowedByDirectNounPhrase) {
                                bypassSecondComma = true;
                                // turn off now so it doesnt happen anymore
                                firstCommaNotFollowedByDirectNounPhrase = false;
                                break;
                            }
                            
                            Tree tree5_childElement = tree4_childElement.getChild(countOfPart5);

                            int outPartChildComp5 = tree5_childElement.numChildren();
                            
                                                                        
                            // dealing with finding larger phrases if we have subject clause later on...
                            // historic reference for earlier NP ascertainment
                            //  reference to tree5_childElement.nodeString();

                            
                            // acquire verb index number.
                            int verbWordIndex6 = 0;
                            if (outPartChildComp5 == 0) {
                                if (tree5_childElement.nodeString().equals(",") && commonBranchOfSubjectArea) {
                                    if (!commaFollows_1st) {
                                        commaFollows_1st = true;
                                    } else {
                                        commaFollows_2nd = true;
                                    }
                                } else if (tree5_childElement.nodeString().equals("and") && commonBranchOfSubjectArea) {
                                    conjunctAndFollows = true;
                                    if(bypassSecondComma && commaFollows_2nd){
                                        adjustSecondNounPostponedFromNonNounSubClause = true;
                                        bypassSecondComma = false;
                                    }
                                }

                                
                                String[] labelInt6 = tree5_childElement.label().toString().split("-");
                                int labelItemsLength6 = labelInt6.length;

                                if (labelItemsLength6 > 1) {
                                    verbWordIndex6 = Integer.parseInt(labelInt6[labelItemsLength6 - 1]);
                                }

                                if (verbWordIndex6 == primarySubjectIndex) {
                                    commonBranchOfSubjectArea = true;
                                    // set course to end gathering subjects altogether once elements at one earlier outer level loop child elements run out
                                    nounPhraseUseExpanse3 = true;
                                    break;
                                }
                                if (PDTentryFollows) {
                                    // add predeterminer index for 1st accompanier after 1st originator subject
                                    if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  || adjustSecondNounPostponedFromNonNounSubClause ) {
                                        givenNounIndices[2] = verbWordIndex6;
                                    } else if (commaFollows_2nd && conjunctAndFollows) {
                                        givenNounIndices[3] = verbWordIndex6;
                                    }
                                    PDTentryFollows = false;
                                } else if (DTentryFollows) {
                                // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                                    //    for 1st accompanier after 1st originator subject
                                    if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                        DTentryPossible1 = verbWordIndex6;
                                    } else if (commaFollows_2nd && conjunctAndFollows) {
                                        DTentryPossible2 = verbWordIndex6;
                                    }
                                    DTentryFollows = false;
                                } else if (PRPentryFollows) {
                                    // add pronoun subject index
                                    if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                        givenNounIndices[0] = verbWordIndex6;
                                        firstItemHasNoun = true;
                                    } else if (commaFollows_2nd && conjunctAndFollows) {
                                        givenNounIndices[1] = verbWordIndex6;
                                        secondItemHasNoun = true;
                                    }
                                    PRPentryFollows = false;
                                } else if (NNentryFollows) {
                                    // add noun subject index
                                    if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                        givenNounIndices[0] = verbWordIndex6;
                                        firstItemHasNoun = true;
                                    } else if (commaFollows_2nd && conjunctAndFollows) {
                                        givenNounIndices[1] = verbWordIndex6;
                                        secondItemHasNoun = true;
                                    }
                                    NNentryFollows = false;
                                }
                                break;

                            } else if (commonBranchOfSubjectArea && tree5_childElement.nodeString().matches("PDT")) {
                                PDTentryFollows = true;
                                // noun section noted
                                nounPhraseOnNow5 = true;
                            } else if (commonBranchOfSubjectArea && tree5_childElement.nodeString().matches("DT")) {
                                DTentryFollows = true;
                                 // noun section noted
                                nounPhraseOnNow5 = true;
                            } else if (commonBranchOfSubjectArea && tree5_childElement.nodeString().matches("PRP")) {
                                PRPentryFollows = true;
                                // noun section noted
                                nounPhraseOnNow5 = true;
                            } else if (commonBranchOfSubjectArea && tree5_childElement.nodeString().contains("NN")) {
                                NNentryFollows = true;
                                // noun section noted
                                nounPhraseOnNow5 = true;
                            }
                            else if(commonBranchOfSubjectArea && !tree5_childElement.nodeString().contains("NP") && !tree5_childElement.nodeString().contains(",") && commaFollows_1st && !commaFollows_2nd  && ! runOnce_NonPhraseAfterComma){
                                firstCommaNotFollowedByDirectNounPhrase = true;
                                runOnce_NonPhraseAfterComma = true;
                            }
   

                            int countOfPart6 = 0;
                            while (countOfPart6 < outPartChildComp5) {
                                
                                // items found... exit loops
                                if(fallOutOfLoops)
                                    break;
                                
                                if (firstCommaNotFollowedByDirectNounPhrase) {
                                    bypassSecondComma = true;
                                    // turn off now so it does not happen anymore
                                    firstCommaNotFollowedByDirectNounPhrase = false;
                                    break;
                                }
                                
                                Tree tree6_childElement = tree5_childElement.getChild(countOfPart6);

                                int outPartChildComp6 = tree6_childElement.numChildren();
                                
                                                                              
                                // dealing with finding larger phrases if we have subject clause later on...
                                // historic reference for earlier NP ascertainment
                                //  reference to tree6_childElement.nodeString();
                                
                                // acquire verb index number.
                                int verbWordIndex7 = 0;
                                if (outPartChildComp6 == 0) {

                                    if (tree6_childElement.nodeString().equals(",") && commonBranchOfSubjectArea) {
                                        if (!commaFollows_1st) {
                                            commaFollows_1st = true;
                                        } else {
                                            commaFollows_2nd = true;
                                        }
                                    } else if (tree6_childElement.nodeString().equals("and") && commonBranchOfSubjectArea) {
                                        conjunctAndFollows = true;
                                        if(bypassSecondComma && commaFollows_2nd){
                                            adjustSecondNounPostponedFromNonNounSubClause = true;
                                            bypassSecondComma = false;
                                        }
                                    }

                                    
                                    String[] labelInt7 = tree6_childElement.label().toString().split("-");
                                    int labelItemsLength7 = labelInt7.length;

                                    if (labelItemsLength7 > 1) {
                                        verbWordIndex7 = Integer.parseInt(labelInt7[labelItemsLength7 - 1]);
                                    }

                                    if (verbWordIndex7 == primarySubjectIndex) {
                                        commonBranchOfSubjectArea = true;
                                        // set course to end gathering subjects altogether once elements at one earlier outer level loop child elements run out
                                        nounPhraseUseExpanse4 = true;
                                        break;
                                    }
                                    if (PDTentryFollows) {
                                        // add predeterminer index for 1st accompanier after 1st originator subject
                                        if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                            givenNounIndices[2] = verbWordIndex7;
                                        } else if (commaFollows_2nd && conjunctAndFollows) {
                                            givenNounIndices[3] = verbWordIndex7;
                                        }
                                        PDTentryFollows = false;
                                    } else if (DTentryFollows) {
                                // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                                        //    for 1st accompanier after 1st originator subject
                                        if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                            DTentryPossible1 = verbWordIndex7;
                                        } else if (commaFollows_2nd && conjunctAndFollows) {
                                            DTentryPossible2 = verbWordIndex7;
                                        }
                                        DTentryFollows = false;
                                    } else if (PRPentryFollows) {
                                        // add pronoun subject index
                                        if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd)  ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                            givenNounIndices[0] = verbWordIndex7;
                                            firstItemHasNoun = true;
                                        } else if (commaFollows_2nd && conjunctAndFollows) {
                                            givenNounIndices[1] = verbWordIndex7;
                                            secondItemHasNoun = true;
                                        }
                                        PRPentryFollows = false;
                                    } else if (NNentryFollows) {
                                        // add noun subject index
                                        if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                            givenNounIndices[0] = verbWordIndex7;
                                            firstItemHasNoun = true;
                                        } else if (commaFollows_2nd && conjunctAndFollows) {
                                            givenNounIndices[1] = verbWordIndex7;
                                            secondItemHasNoun = true;
                                        }
                                        NNentryFollows = false;
                                    }
                                    break;

                                } else if (commonBranchOfSubjectArea && tree6_childElement.nodeString().matches("PDT")) {
                                    PDTentryFollows = true;
                                    // noun section noted
                                    nounPhraseOnNow6 = true;
                                } else if (commonBranchOfSubjectArea && tree6_childElement.nodeString().matches("DT")) {
                                    DTentryFollows = true;
                                    // noun section noted
                                    nounPhraseOnNow6 = true;
                                } else if (commonBranchOfSubjectArea && tree6_childElement.nodeString().matches("PRP")) {
                                    PRPentryFollows = true;
                                    // noun section noted
                                    nounPhraseOnNow6 = true;
                                } else if (commonBranchOfSubjectArea && tree6_childElement.nodeString().contains("NN")) {
                                    NNentryFollows = true;
                                    // noun section noted
                                    nounPhraseOnNow6 = true;
                                }
                                else if(commonBranchOfSubjectArea && !tree6_childElement.nodeString().contains("NP") && !tree6_childElement.nodeString().contains(",")  && commaFollows_1st && !commaFollows_2nd && !runOnce_NonPhraseAfterComma){
                                    firstCommaNotFollowedByDirectNounPhrase = true;
                                    runOnce_NonPhraseAfterComma = true;
                                }
                                
                                int countOfPart7 = 0;
                                while (countOfPart7 < outPartChildComp6) {
                                    
                                    
                                    if (firstCommaNotFollowedByDirectNounPhrase) {
                                        bypassSecondComma = true;
                                        // turn off now so it does not happen anymore
                                        firstCommaNotFollowedByDirectNounPhrase = false;
                                        break;
                                    }
                                    
                                    Tree tree7_childElement = tree6_childElement.getChild(countOfPart7);

                                    int outPartChildComp7 = tree7_childElement.numChildren();
                                    // acquire verb index number.
                                    int verbWordIndex8 = 0;
                                    if (outPartChildComp7 == 0) {

                                        if (tree7_childElement.nodeString().equals(",") && commonBranchOfSubjectArea) {
                                            if (!commaFollows_1st) {
                                                commaFollows_1st = true;
                                            } else {
                                                commaFollows_2nd = true;
                                            }
                                        } else if (tree7_childElement.nodeString().equals("and") && commonBranchOfSubjectArea) {
                                            conjunctAndFollows = true;
                                            if(bypassSecondComma && commaFollows_2nd){
                                                adjustSecondNounPostponedFromNonNounSubClause = true;
                                                bypassSecondComma = false;
                                            }
                                        }

                                        
                                        String[] labelInt8 = tree7_childElement.label().toString().split("-");
                                        int labelItemsLength8 = labelInt8.length;

                                        if (labelItemsLength8 > 1) {
                                            verbWordIndex8 = Integer.parseInt(labelInt8[labelItemsLength8 - 1]);
                                        }

                                        if (verbWordIndex8 == primarySubjectIndex) {
                                            commonBranchOfSubjectArea = true;
                                            // set course to end gathering subjects altogether once elements at one earlier outer level loop child elements run out
                                            nounPhraseUseExpanse5 = true;
                                            break;
                                        }
                                        if (PDTentryFollows) {
                                            // add predeterminer index for 1st accompanier after 1st originator subject
                                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause ) {
                                                givenNounIndices[2] = verbWordIndex8;
                                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                                givenNounIndices[3] = verbWordIndex8;
                                            }
                                            PDTentryFollows = false;
                                        } else if (DTentryFollows) {
                                            // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                                            //    for 1st accompanier after 1st originator subject
                                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) ||  adjustSecondNounPostponedFromNonNounSubClause ) {
                                                DTentryPossible1 = verbWordIndex8;
                                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                                DTentryPossible2 = verbWordIndex8;
                                            }
                                            DTentryFollows = false;
                                        } else if (PRPentryFollows) {
                                            // add pronoun subject index
                                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause ) {
                                                givenNounIndices[0] = verbWordIndex8;
                                                firstItemHasNoun = true;
                                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                                givenNounIndices[1] = verbWordIndex8;
                                                secondItemHasNoun = true;
                                            }
                                            PRPentryFollows = false;
                                        } else if (NNentryFollows) {
                                            // add noun subject index
                                            if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause ) {
                                                givenNounIndices[0] = verbWordIndex8;
                                                firstItemHasNoun = true;
                                            } else if (commaFollows_2nd && conjunctAndFollows) {
                                                givenNounIndices[1] = verbWordIndex8;
                                                secondItemHasNoun = true;
                                            }
                                            NNentryFollows = false;
                                        }
                                        break;

                                    } else if (commonBranchOfSubjectArea && tree7_childElement.nodeString().matches("PDT")) {
                                        PDTentryFollows = true;
                                        // noun section noted
                                        nounPhraseOnNow7 = true;
                                    } else if (commonBranchOfSubjectArea && tree7_childElement.nodeString().matches("DT")) {
                                        DTentryFollows = true;
                                        // noun section noted
                                        nounPhraseOnNow7 = true;
                                    } else if (commonBranchOfSubjectArea && tree7_childElement.nodeString().matches("PRP")) {
                                        PRPentryFollows = true;
                                        // noun section noted
                                        nounPhraseOnNow7 = true;
                                    } else if (commonBranchOfSubjectArea && tree7_childElement.nodeString().contains("NN")) {
                                        NNentryFollows = true;
                                        // noun section noted
                                        nounPhraseOnNow7 = true;
                                    }
                                    else if(commonBranchOfSubjectArea && !tree7_childElement.nodeString().contains("NP")  &&  !tree7_childElement.nodeString().contains(",")  && commaFollows_1st && !commaFollows_2nd  && ! runOnce_NonPhraseAfterComma){
                                        firstCommaNotFollowedByDirectNounPhrase = true;
                                        runOnce_NonPhraseAfterComma = true;
                                    }
                                    
                                    int countOfPart8 = 0;
                                    while (countOfPart8 < outPartChildComp7) {
                                        
                         
                                        if (firstCommaNotFollowedByDirectNounPhrase) {
                                            bypassSecondComma = true;
                                            // turn off now so it does not happen anymore
                                            firstCommaNotFollowedByDirectNounPhrase = false;
                                            break;
                                        }
                                        
                                        
                                        Tree tree8_childElement = tree7_childElement.getChild(countOfPart8);

                                        int outPartChildComp8 = tree8_childElement.numChildren();
                                        // acquire verb index number.
                                        int verbWordIndex9 = 0;
                                        if (outPartChildComp8 == 0) {
                                            String[] labelInt9 = tree8_childElement.label().toString().split("-");
                                            int labelItemsLength9 = labelInt9.length;

                                            if (labelItemsLength9 > 1) {
                                                verbWordIndex9 = Integer.parseInt(labelInt9[labelItemsLength9 - 1]);
                                            }

                                            if (verbWordIndex9 == primarySubjectIndex) {
                                                commonBranchOfSubjectArea = true;
                                                // set course to end gathering subjects altogher once elements at one earlier outer level loop child elements run out
                                                nounPhraseUseExpanse6 = true;
                                                break;
                                            }
                                            if (PDTentryFollows) {
                                                // add predeterminer index for 1st accompanier after 1st originator subject
                                                if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause ) {
                                                    givenNounIndices[2] = verbWordIndex9;
                                                } else if (commaFollows_2nd && conjunctAndFollows) {
                                                    givenNounIndices[3] = verbWordIndex9;
                                                }
                                                PDTentryFollows = false;
                                            } else if (DTentryFollows) {
                                // reserve determiner_ later to be added as noun index  _ or as accompanying determiner
                                                //    for 1st accompanier after 1st originator subject
                                                if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause  ) {
                                                    DTentryPossible1 = verbWordIndex9;
                                                } else if (commaFollows_2nd && conjunctAndFollows) {
                                                    DTentryPossible2 = verbWordIndex9;
                                                }
                                                DTentryFollows = false;
                                            } else if (PRPentryFollows) {
                                                // add pronoun subject index
                                                if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause ) {
                                                    givenNounIndices[0] = verbWordIndex9;
                                                    firstItemHasNoun = true;
                                                } else if (commaFollows_2nd && conjunctAndFollows) {
                                                    givenNounIndices[1] = verbWordIndex9;
                                                    secondItemHasNoun = true;
                                                }
                                                PRPentryFollows = false;
                                            } else if (NNentryFollows) {
                                                // add noun subject index
                                                if ((conjunctAndFollows && !commaFollows_1st) || (commaFollows_1st && !commaFollows_2nd) || adjustSecondNounPostponedFromNonNounSubClause  ) {
                                                    givenNounIndices[0] = verbWordIndex9;
                                                    firstItemHasNoun = true;
                                                } else if (commaFollows_2nd && conjunctAndFollows) {
                                                    givenNounIndices[1] = verbWordIndex9;
                                                    secondItemHasNoun = true;
                                                }
                                                NNentryFollows = false;
                                            }
                                            break;

                                        }
                                        
                                        countOfPart8++;
                                        
                                    }

                                    
                                    countOfPart7++;

                                    if (countOfPart7 == outPartChildComp6 && nounPhraseOnNow7) {
                                        if (!firstItemHasNoun && DTentryPossible1 != 0) {
                                            // add predeterminer index for 1st accompanier after 1st originator subject
                                            givenNounIndices[0] = DTentryPossible1;

                                        } else if (!secondItemHasNoun && DTentryPossible2 != 0) {
                                            givenNounIndices[1] = DTentryPossible2;
                                        } else if (firstItemHasNoun && DTentryPossible1 != 0) {
                                            // add predeterminer index for 1st accompanier after 1st originator subject
                                            givenNounIndices[5] = DTentryPossible1;
                                        } else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                            givenNounIndices[6] = DTentryPossible2;
                                        }
                                    }
                                    
                                }
                                
                                countOfPart6++;
                                if (tree5_childElement.nodeString().equals("NP")) {
                                    morePhrasesNPreasoning = true;
                                }

                                if (nounPhraseUseExpanse6 && !morePhrasesNPreasoning) {
                                    fallOutOfLoops = true;
                                } else if (nounPhraseUseExpanse6 && morePhrasesNPreasoning && countOfPart6 == outPartChildComp5) {
                                    fallOutOfLoops = true;
                                }

                                if (countOfPart6 == outPartChildComp5 && nounPhraseOnNow6) {
                                    if (!firstItemHasNoun && DTentryPossible1 != 0) {
                                        // add predeterminer index for 1st accompanier after 1st originator subject
                                        givenNounIndices[0] = DTentryPossible1;

                                    } else if (!secondItemHasNoun && DTentryPossible2 != 0) {
                                        givenNounIndices[1] = DTentryPossible2;
                                    } else if (firstItemHasNoun && DTentryPossible1 != 0) {
                                        // add predeterminer index for 1st accompanier after 1st originator subject
                                        givenNounIndices[5] = DTentryPossible1;
                                    } else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                        givenNounIndices[6] = DTentryPossible2;
                                    }
                                }
                                
                            }

                            countOfPart5++;
                            
                            if (tree4_childElement.nodeString().equals("NP")) {
                                morePhrasesNPreasoning = true;
                            }

                            if (nounPhraseUseExpanse5 && !morePhrasesNPreasoning) {
                                fallOutOfLoops = true;
                            } else if (nounPhraseUseExpanse5 && morePhrasesNPreasoning && countOfPart5 == outPartChildComp4) {
                                fallOutOfLoops = true;
                            }
                            
                         
                            if (countOfPart5 == outPartChildComp4 && nounPhraseOnNow5) {
                                if (!firstItemHasNoun && DTentryPossible1 != 0) {
                                    // add predeterminer index for 1st accompanier after 1st originator subject
                                    givenNounIndices[0] = DTentryPossible1;

                                } else if (!secondItemHasNoun && DTentryPossible2 != 0) {
                                    givenNounIndices[1] = DTentryPossible2;
                                } else if (firstItemHasNoun && DTentryPossible1 != 0) {
                                    // add predeterminer index for 1st accompanier after 1st originator subject
                                    givenNounIndices[5] = DTentryPossible1;
                                } else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                    givenNounIndices[6] = DTentryPossible2;
                                }
                            }
   
                        }
                        
                        
                        countOfPart4++;
                        
                        if (tree3_childElement.nodeString().equals("NP")) {
                            morePhrasesNPreasoning = true;
                        }

                        if (nounPhraseUseExpanse4 && !morePhrasesNPreasoning) {
                            fallOutOfLoops = true;
                        } else if (nounPhraseUseExpanse4 && morePhrasesNPreasoning && countOfPart4 == outPartChildComp3) {
                            fallOutOfLoops = true;
                        }
                        
                        
                        if (countOfPart4 == outPartChildComp3 && nounPhraseOnNow4) {
                            if (!firstItemHasNoun && DTentryPossible1 != 0) {
                                // add predeterminer index for 1st accompanier after 1st originator subject
                                givenNounIndices[0] = DTentryPossible1;

                            } else if (!secondItemHasNoun && DTentryPossible2 != 0) {
                                givenNounIndices[1] = DTentryPossible2;
                            } else if (firstItemHasNoun && DTentryPossible1 != 0) {
                                // add predeterminer index for 1st accompanier after 1st originator subject
                                givenNounIndices[5] = DTentryPossible1;
                            } else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                givenNounIndices[6] = DTentryPossible2;
                            }
                        }
                        
                    }
                    
                    countOfPart3++;
                    if (tree2_childElement.nodeString().equals("NP")) {
                        morePhrasesNPreasoning = true;
                    }

                    if (nounPhraseUseExpanse3 && !morePhrasesNPreasoning) {
                        fallOutOfLoops = true;
                    } else if (nounPhraseUseExpanse3 && morePhrasesNPreasoning && countOfPart3 == outPartChildComp2) {
                        fallOutOfLoops = true;
                    }

                    if(countOfPart3 == outPartChildComp2  && nounPhraseOnNow3){
                        if (!firstItemHasNoun && DTentryPossible1 != 0) {
                            // add predeterminer index for 1st accompanier after 1st originator subject
                            givenNounIndices[0] = DTentryPossible1;
                            
                        }
                        else if(!secondItemHasNoun && DTentryPossible2 != 0){
                            givenNounIndices[1] = DTentryPossible2;
                        }
                        else if (firstItemHasNoun && DTentryPossible1 != 0) {
                            // add predeterminer index for 1st accompanier after 1st originator subject
                            givenNounIndices[5] = DTentryPossible1;
                        }
                        else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                givenNounIndices[6] = DTentryPossible2;
                            }
                        }
                    
                }
                
                
                
                countOfPart2++;
                if(tree1_childElement.nodeString().equals("NP"))
                    morePhrasesNPreasoning = true;
                
                if(nounPhraseUseExpanse2 && !morePhrasesNPreasoning)
                    fallOutOfLoops = true;
                else if(nounPhraseUseExpanse2 && morePhrasesNPreasoning && countOfPart2 == outPartChildComp1 )
                    fallOutOfLoops = true;
                
                
                if(countOfPart2 == outPartChildComp1  && nounPhraseOnNow2){
                        if (!firstItemHasNoun && DTentryPossible1 != 0) {
                            // add predeterminer index for 1st accompanier after 1st originator subject
                            givenNounIndices[0] = DTentryPossible1;
                            
                        }
                        else if(!secondItemHasNoun && DTentryPossible2 != 0){
                            givenNounIndices[1] = DTentryPossible2;
                        }
                        else if (firstItemHasNoun && DTentryPossible1 != 0) {
                            // add predeterminer index for 1st accompanier after 1st originator subject
                            givenNounIndices[5] = DTentryPossible1;
                        }
                        else if (secondItemHasNoun && DTentryPossible2 != 0) {
                                givenNounIndices[6] = DTentryPossible2;
                            }
                        }
                
            }
            
         if(nounPhraseUseExpanse1)
             fallOutOfLoops = true;
         
         countOfPart1++;
         
        }
        
        return givenNounIndices;
    }
    
}
