/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversationauto1;

/**
 *
 * @author ebeclaim3uhave
 */
public class RespectivePhraseVerb_regularVerbs {
     
    /**
     * 
     * @param centerSubject  input subject to be converted into response Reference version
     * @param initialVerbGiven  input verb to be converted into response Reference version
     * @param predicateData input background details on whole predicate being considered.
     * @param DirectObjectWithVerb input to tell if there is a direct object or not:  false indicates no direct object
     * @return string array  containing, 0th element the person of noun classification, 1st element the  referenceSpeechIdentifier_Subject, 2nd element is the referenceSpeech_linkingVerb
     */
    public static String[] getRespectiveSubject_andCommonTypeVerb(ObjectData centerSubject, VerbData initialVerbGiven, predicateData  predicateBackground_input, boolean DirectObjectWithVerb) {
        String returnElements[] = new String[3];
        
        //returnElements[0] = Integer.toString(personOfNoun);
        //        returnElements[1] = originalSubjectString;
        //        returnElements[2] = referenceSpeech_CommonTypeVerb;
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
        // assign null value
        int personOfNoun = 0;
        String originalSubjectString = centerSubject.getOrigWord();
        String objectFirstPOS = centerSubject.getPosForm();
        boolean objectIsPersonOrPersons = (centerSubject.getSecondLevelFormedWord().matches("individual")
                || originalSubjectString.matches("member") || centerSubject.getfirstLevelFormedWord().matches("people")
                || centerSubject.getfirstLevelFormedWord().matches("person")
                || centerSubject.getfirstLevelFormedWord().matches("stickler"));

        boolean objectIs_A_Place = centerSubject.getfirstLevelFormedWord().matches("location");

        boolean hasAuxillaryBeingElement = !predicateBackground_input.getAuxillaryVerb1().matches("null");
        boolean hasModalElement = !predicateBackground_input.getModalInfo().matches("null");
        String auxElement = predicateBackground_input.getAuxillaryVerb1();
        String referenceSpeechIdentifier_Subject = null;
        String referenceSpeech_CommonTypeVerb = null;
        
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
        
        String beingPresentLinkVerb = "null";
        String beingPastLinkVerb = "null";
        String reflexivePastVerb = "null";
        if (originalSubjectString.matches("I")) {
          referenceSpeechIdentifier_Subject = "you";
          personOfNoun = 2;
          beingPresentLinkVerb = "are";
          beingPastLinkVerb = "were";
          reflexivePastVerb = "have";
        }
        else if (originalSubjectString.toLowerCase().matches("you")) {
          referenceSpeechIdentifier_Subject = "I";
          personOfNoun = 1;
          beingPresentLinkVerb = "am";
          beingPastLinkVerb = "was";
          reflexivePastVerb = "have";
        }
        else if( originalSubjectString.matches("he") || originalSubjectString.matches("she")
                || (objectFirstPOS.matches("NN") && objectIsPersonOrPersons)) 
        {
            personOfNoun = 3;
            referenceSpeechIdentifier_Subject = "he";
            beingPresentLinkVerb = "is";
            beingPastLinkVerb = "was";
            reflexivePastVerb = "has";
        }
        else if (!objectIs_A_Place && (originalSubjectString.matches("it") || objectFirstPOS.matches("DT") || objectFirstPOS.matches("NN") || objectFirstPOS.matches("NNP"))) 
        {
            personOfNoun = 4;
            referenceSpeechIdentifier_Subject = "it";
            beingPresentLinkVerb = "is";
            beingPastLinkVerb = "was";
            reflexivePastVerb = "has";
        }
        
        else if (objectIs_A_Place && (objectFirstPOS.matches("NN") || objectFirstPOS.matches("NNP"))) 
        {
            personOfNoun = 5;
            referenceSpeechIdentifier_Subject = "that location";
            beingPresentLinkVerb = "is";
            beingPastLinkVerb = "was";
            reflexivePastVerb = "has";
        }
        else if(originalSubjectString.matches("we")) 
        {
            personOfNoun = 6;
            referenceSpeechIdentifier_Subject = "we";
            beingPresentLinkVerb = "are";
            beingPastLinkVerb = "were";
            reflexivePastVerb = "have";
        }
        // we already ruled out single persons.. we continue to use the same test condition to get plural items
        else if (objectIsPersonOrPersons || objectFirstPOS.matches("NNS") || objectFirstPOS.matches("DT") || objectFirstPOS.matches("NNPS")) {
            personOfNoun = 7;
            referenceSpeechIdentifier_Subject = "they";
            beingPresentLinkVerb = "are";
            beingPastLinkVerb = "were";
            reflexivePastVerb = "have";
        }
         // just say 
        else{
            personOfNoun = 4;
            referenceSpeechIdentifier_Subject = "it";
            beingPresentLinkVerb = "is";
            beingPastLinkVerb = "was";
            reflexivePastVerb = "has";
        }
           
       
   // should figure in adding you all too.     

   //else if
        
        // configure response based on  inverted subject "you"  and a generic verb doing action
        if (initialVerbGiven.getActionDetails_type_of_verb() == 0) {
            // there is a direct object
            if (DirectObjectWithVerb) {

                if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " " + initialVerbGiven.getOrigWord();
                } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + initialVerbGiven.getOrigWord();
                } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                    referenceSpeech_CommonTypeVerb = auxElement + " not " + initialVerbGiven.getOrigWord();
                } else if (hasAuxillaryBeingElement) {
                    referenceSpeech_CommonTypeVerb = auxElement + " " + initialVerbGiven.getOrigWord();
                } else {
                    referenceSpeech_CommonTypeVerb = initialVerbGiven.getOrigWord();
                }
            } else {
                    // give tense and person to ordinaryDoing_CommonVerb function
                // verb without dobj    but has modal and modal not
                if (hasModalElement && predicateBackground_input.getModalNot()) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, false);
                } // verb without dobj    but has modal
                else if (hasModalElement) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " " + RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, false);
                } // verb without dobj    just statement without any accessory
                else {
                    referenceSpeech_CommonTypeVerb = RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, false);
                }
            }

        } // constructive addition verb
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 1) {
            // there is a direct object
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " to of getting done";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to get done";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to get done";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "of getting done";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " of getting done";
                    } else {
                        referenceSpeech_CommonTypeVerb = " of getting done";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " gotten " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb +" gotten " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " gotten " + initialVerbGiven.getOrigWord();
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "undertake";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "undertake";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + "undertake";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will undertake";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not undertake";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "undertaking";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " undertaking";
                    } else {
                        referenceSpeech_CommonTypeVerb = "undertake";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be undertaking";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not been undertaking";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " been undertaking";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb +" undertaking";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get undertaken";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not undertaken";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " undertaken";
                    } else {
                        referenceSpeech_CommonTypeVerb = "undertook";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not undertaken";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " undertaken";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had undertaken";
                    }
                }

            } // if there is no direct object
            else {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "be taking place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to take place";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to take place";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "be not to take place";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "be to take place";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to take place";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not " + reflexivePastVerb +  " taken place";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb +" taken place";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " taken place";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be taking place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not take place";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not take place";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will take place";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will take place";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not take place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not take place";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " not taking place";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " taking place";
                    } else {
                        referenceSpeech_CommonTypeVerb = "take place";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be taking place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be taking place";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not taking place";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " taking place";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb +" taking place";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " taken place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +" done";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not attended";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " attended";
                    } else {
                        referenceSpeech_CommonTypeVerb = "took place";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " taken place";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " taken place";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not taken place";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " taken place";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had taken place";
                    }
                }

            }
        }// configure response based on  inverted subject "you"  and a generic verb doing action
        // Action type 2 : verb is representing or specifying on with a given
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 2) {
            // has direct object
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "amply show typification/regard (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to show typify/regard (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to show/typify/regard (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to show typifying/regard (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " gotten shown/typified/regarded";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " gotten shown/typified/regarded";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb +" gotten shown/typified/regarded";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " showing/typifying/regarding";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "show/typify/regard " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "showing/typifying/regarding";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + "showing/typifying/regarding";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will show/typify/regard " + initialVerbGiven.getOrigWord();
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not show/typify/regard " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "show/typify/regard (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not been showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb +" showing/typifying/regarding (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "showed/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " +  reflexivePastVerb +" shown/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " made shown/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not shown/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " shown/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had shown/typified/regarded (" + initialVerbGiven.getOrigWord() + ")";
                    }
                }

            } // does not have direct object 
            else {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "amply be indicated/represented";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to indicate/represent it";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to indicate/represent it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "amply be indicated/represented";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " amply be indicated/represented";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to indicate/represente it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb+ " indicated/represented it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " indicated/represented it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " indicated/represented it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not indicate/represent it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not indicate/represent it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not indicate/represent it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " indicate/represent it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will indicate/represent it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " indicating/representing it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not indicate/represent it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " indicating/representing it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " indicating/representing it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "indicate/represent it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " indicated/represented it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " indicated/represented it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not indicating/representing it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " indicating/representing it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "indicated/represented it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " indicated/represented it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not indicate/represent it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not indicated/represented it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " indicated/represented it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "indicated/represented it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " indicated/represented it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " indicated/represented it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + reflexivePastVerb + " indicated/represented it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + reflexivePastVerb + " indicated/represented it";
                    } else {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " indicated/represented it";
                    }
                }
            }
        } // Action type 3 : verb has acquisition of knowledge or results
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 3) {
            // there is a direct object
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "initially direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " initially direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " initially direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to direct (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + " direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + " direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will direct (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "direct (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " directing (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "directed (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not make directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directed (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had directed (" + initialVerbGiven.getOrigWord() + ")";
                    }
                }

            } // does not have direct object 
            else {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "initially direct it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to direct it";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to direct it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " initially direct it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " initially direct it";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to direct it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " directed it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " directed it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " directed it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directing it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + " direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + " direct (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will direct (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directing it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " directing it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directing it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "direct it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " directed it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be directing it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not directing it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directing it";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " directing it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not direct it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not directed it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directed it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "directed it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " directed it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not make directed it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not directed it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " directed it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had directed it";
                    }
                }
            }
        } // Action type 3 : verb has acquisition of knowledge or results
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 4) {
            // there is a direct object
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "get transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "do to transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " do to transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " get transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " get transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " effectively to transition (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" +  reflexivePastVerb + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + " transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + " transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will transition (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "transition (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " transitioning (" + initialVerbGiven.getOrigWord() + ")";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not make transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had transitioned (" + initialVerbGiven.getOrigWord() + ")";
                    }
                }

            } // does not have direct object 
            else {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "get transitioning with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "do to transition with it";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " get transitioning with it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " get transitioning with it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " get transitioning with";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " effectively to transition with it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " transitioned with it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " transitioned with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " transitioned with it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioning with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + " transition with it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "transition with it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " " + " transition with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will transition with it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioning with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition with it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " transition with it";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transition with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "transition with it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " transitioned with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not be transitioning with it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not transitioning with it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioning with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " transitioning with it";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not transition with it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not transition with it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transition with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "transitioned with it";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " transitioned with it";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not make transitioned with  it";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not transitioned with it";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " transitioned with it";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had transitioned with it";
                    }
                }
            }
        } // Action type 5 : verb reflects some happening/preparation
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 5) {
            // there is a direct object
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "sufficiently get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "sufficiently get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " sufficiently get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to sufficiently get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " getting (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb +" not gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = "got (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had gotten (" + initialVerbGiven.getOrigWord() + ") something to happen";
                    }
                }

            } // does not have direct object 
            else {
                    // give tense and person to ordinaryDoing_CommonVerb function
                // verb without dobj    but has modal and modal not
                if (hasModalElement && predicateBackground_input.getModalNot()) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, true);
                } // verb without dobj    but has modal
                else if (hasModalElement) {
                    referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " " + RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, true);
                } // verb without dobj    just statement without any accessory
                else {
                    referenceSpeech_CommonTypeVerb = RespectivePhraseVerb_linking.ordinaryDoing_CommonVerb_form(predicateBackground_input.getPrimaryVerb(), personOfNoun, true);
                }
            }
        } // Action type 5 : verb reflects some happening/preparation
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 6) {
            // there is a direct object
            if (DirectObjectWithVerb) {

                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "sufficiently getting employed " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to employ " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to employ " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "sufficiently getting employed " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " sufficiently getting employed " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to sufficiently employ " + initialVerbGiven.getOrigWord();
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " employed such by " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " employed such by " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " employed such by " + initialVerbGiven.getOrigWord();
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not employ such by " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not employ such by " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not employ such by " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will not employ such by " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = "will employ such by " + initialVerbGiven.getOrigWord();
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " employing such by " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not employ such by " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " employ such by " + initialVerbGiven.getOrigWord();
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " employ such by " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " employing in " + initialVerbGiven.getOrigWord();
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " employed such by " + initialVerbGiven.getOrigWord();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " employed such by " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not employing such by " + initialVerbGiven.getOrigWord();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " employing such by " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " employing " + initialVerbGiven.getOrigWord();
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " employed such with an " + initialVerbGiven.getLemmaForm();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not employ the " + initialVerbGiven.getLemmaForm();
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not employed the " + initialVerbGiven.getLemmaForm();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " employed it by having " + initialVerbGiven.getOrigWord();
                    } else {
                        referenceSpeech_CommonTypeVerb = "employed (" + initialVerbGiven.getOrigWord() + ")";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " "+ reflexivePastVerb + " not employed such with an " + initialVerbGiven.getLemmaForm();
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " employed such with an " + initialVerbGiven.getLemmaForm();
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not employed such with an " + initialVerbGiven.getLemmaForm();
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had employed such with an " + initialVerbGiven.getLemmaForm();
                    } else {
                        referenceSpeech_CommonTypeVerb = "had employed such with an " + initialVerbGiven.getLemmaForm();
                    }
                }

            } // does not have direct object 
            else {

                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "convincingly get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "convincingly get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + "convincingly get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to rowdily get (" + initialVerbGiven.getOrigWord() + ") an activity going";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " getting (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " " + reflexivePastVerb +  " not gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not get (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = "got (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had gotten (" + initialVerbGiven.getOrigWord() + ") an activity going on";
                    }
                }

            }
        } // Action type 7 : variance on a venture
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 7) {
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "caringly devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to devise (" + initialVerbGiven.getLemmaForm() + ") an engagement with";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to devise (" + initialVerbGiven.getLemmaForm() + ") an engagement with";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "caringly devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " caringly devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " devised (" + initialVerbGiven.getOrigWord() + ") an engagement with";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " devised (" + initialVerbGiven.getOrigWord() + ") an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " devised (" + initialVerbGiven.getOrigWord() + ") an engagement with";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = "devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " devising (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = "devise (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had devised (" + initialVerbGiven.getLemmaForm() + "ing) an engagement with";
                    }
                }

            } // does not have direct object 
            else {

                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "willfully review/tweak something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to review/tweak something";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to review/tweak something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "willfully review/tweak something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + "willfully review/tweak something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to review/tweak something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " reviewed/tweaked something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb+ " reviewed/tweaked something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " reviewed/tweaked something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not review/tweak something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not review/tweak something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not review/tweak something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will review/tweak something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will review/tweak something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " reviewing/tweaking something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not review/tweak something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + " reviewing/tweaking something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " reviewing/tweaking something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "review/tweak something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " reviewed/tweaked something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not review/tweak something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not reviewing/tweaking something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " reviewing/tweaking something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " reviewing/tweaking something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb  +  " reviewed/tweaked something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not review/tweak something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not reviewed/tweaked something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " reviewed/tweaked something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "reviewed/tweaked something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " reviewed/tweaked something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " reviewed/tweaked something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not reviewed/tweaked something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had reviewed/tweaked something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had reviewed/tweaked something";
                    }
                }

            }
        } // Action type 8 : physical development of action or process
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 8) {
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "freefully (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to (" + initialVerbGiven.getLemmaForm() + ") apply to / or get a procedure for";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to (" + initialVerbGiven.getLemmaForm() + ") apply to / or get a procedure for";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "freefully (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " freefully (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to freefully (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or getting a procedure for";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = "(" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) applied to / or gotten a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) applied to / or gotten a procedure for";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) applying to / or getting a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = "(" + initialVerbGiven.getLemmaForm() + "ing) apply to / or get a procedure for";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had (" + initialVerbGiven.getOrigWord() + ") applied to / or gotten a procedure for";
                    }
                }

            } // does not have direct object 
            else {

                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "freefully run method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to run method/procedure on something";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to run method/procedure on something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "freefully run method/procedure on something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + "freefully run method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to run method/procedure on something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + " ran a method/procedure on something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + " ran a method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + " ran a method/procedure on something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not run method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not run method/procedure on something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not run method/procedure on something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will run method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will run method/procedure on something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " running method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not run method/procedure on something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "running method/procedure on something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " running method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "run method/procedure on something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " ran method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not run method/procedure on something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " running method/procedure on something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " running method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " running method/procedure on something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " run method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not run method/procedure on something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not run method/procedure on something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " run method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "ran method/procedure on something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " ran method/procedure on something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " ran a method/procedure on something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not run a method/procedure on something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had not run a method/procedure on something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had run a method/procedure on something";
                    }
                }

            }
        } // Action type 9 : verb that reaches outcome of something or delivers further on implemented action 
        else if (initialVerbGiven.getActionDetails_type_of_verb() == 9) {
            if (DirectObjectWithVerb) {
                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "fluently (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of ";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "fluidly (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " fluidly (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to fluidly (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") reached an outcome of";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") reached an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will" + reflexivePastVerb + " (" + initialVerbGiven.getOrigWord() + ") reached an outcome of";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = "(" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + "not (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) reaching an outcome of";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  " (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not (" + initialVerbGiven.getLemmaForm() + "ing) reach an outcome of";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    } else {
                        referenceSpeech_CommonTypeVerb = "(" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome of";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb +  reflexivePastVerb + " (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had (" + initialVerbGiven.getLemmaForm() + "ing) reached an outcome";
                    }
                }

            } // does not have direct object 
            else {

                if (predicateBackground_input.getPrimaryVerb().matches("BlankTense")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "fluently reach something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + "to reach something";
                    } else if (hasModalElement && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " to reach something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "fluently reach something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " fluently reach something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPresentLinkVerb + " to reach something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future-perfect")) {
                    if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will" + " not " + reflexivePastVerb+ " reached something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " reached something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will " + reflexivePastVerb + " reached something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("future")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " reaching something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " reach something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "will not reach something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "will reach something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "will run method/procedure on something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("present")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " reaching something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " reach something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " not " + "reaching something";
                    } else if (!hasModalElement && hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " reaching something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "reach something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("imperfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + auxElement + " reached something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " reach something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = auxElement + " reached something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = auxElement + " reached something";
                    } else {
                        referenceSpeech_CommonTypeVerb = beingPastLinkVerb + " reaching something";
                    }
                } else if (predicateBackground_input.getPrimaryVerb().matches("perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " reached something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + reflexivePastVerb + " reached something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " not reached something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = reflexivePastVerb + " reached something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "reached something";
                    }

                } else if (predicateBackground_input.getPrimaryVerb().matches("plu-perfect")) {
                    if (hasModalElement && predicateBackground_input.getModalNot() && hasAuxillaryBeingElement && !predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " reached something";
                    } else if (hasModalElement && predicateBackground_input.getModalNot() && !hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = predicateBackground_input.getModalInfo() + " not " + reflexivePastVerb + " reached something";
                    } else if (hasAuxillaryBeingElement && predicateBackground_input.getRegVerbNot()) {
                        referenceSpeech_CommonTypeVerb = "had not reached something";
                    } else if (hasAuxillaryBeingElement) {
                        referenceSpeech_CommonTypeVerb = "had reached something";
                    } else {
                        referenceSpeech_CommonTypeVerb = "had reached something";
                    }
                }

            }
        }
     
    
       
        returnElements[0] = Integer.toString(personOfNoun);
        returnElements[1] = referenceSpeechIdentifier_Subject;
        returnElements[2] = referenceSpeech_CommonTypeVerb;
        return returnElements;
    }
}
