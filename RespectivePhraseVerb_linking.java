/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversationauto1;

/**
 *
 * @author ebeclaim3u
 */
public class RespectivePhraseVerb_linking {
    
    /**
     * 
     * @param centerSubject  input subject to be converted into response Reference version
     * @param initialVerbGiven input verb to be converted into response Reference version
     * @return string array  containing, 0th element the person of noun classification, 1st element the  referenceSpeechIdentifier_Subject, 2nd element is the referenceSpeech_linkingVerb
     */
    public static String[] getRespectiveSubject_andLinkingVerb(ObjectData centerSubject, VerbData initialVerbGiven) {
 
        String returnElements[] = new String[3];
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

        String referenceSpeechIdentifier_Subject = null;
        String referenceSpeech_linkingVerb = null;
        if (originalSubjectString.matches("I")) {
            personOfNoun = 1;
            referenceSpeechIdentifier_Subject = "you";
            // configure linking verb predicate based on  inverted subject "you"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "is to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "are";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "were being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "were";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        } else if (originalSubjectString.matches("you")) {
            personOfNoun = 2;
            referenceSpeechIdentifier_Subject = "I";
            // configure linking verb predicate based on  inverted subject "I"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "am to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "am";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "was being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "was";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        } else if (originalSubjectString.matches("he") || originalSubjectString.matches("she")
                || (objectFirstPOS.matches("NN") && objectIsPersonOrPersons)) {
            personOfNoun = 3;
            referenceSpeechIdentifier_Subject = "he";
            // configure linking verb predicate based on  inverted subject "he"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "is to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "is";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "was being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "was";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }

        } else if (!objectIs_A_Place && (originalSubjectString.matches("it") || objectFirstPOS.matches("DT") || objectFirstPOS.matches("NN") || objectFirstPOS.matches("NNP"))) {
            personOfNoun = 4;
            referenceSpeechIdentifier_Subject = "it";
            // configure linking verb predicate based on  inverted subject "it"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "is to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "is";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "was being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "was";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        } else if (objectIs_A_Place && (objectFirstPOS.matches("NN") || objectFirstPOS.matches("NNP"))) {
            personOfNoun = 5;
            referenceSpeechIdentifier_Subject = "that location";
            // configure linking verb predicate based on  inverted subject "that location/place"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "is to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "is";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "was being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "was";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        } else if (originalSubjectString.matches("we")) {
            personOfNoun = 6;
            referenceSpeechIdentifier_Subject = "we";
            // configure linking verb predicate based on subject "we"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "are to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "are";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "were being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "were";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        } // we already ruled out single persons.. we continue to use the same test condition to get plural items
        else if (objectIsPersonOrPersons || objectFirstPOS.matches("NNS") || objectFirstPOS.matches("DT") || objectFirstPOS.matches("NNPS")) {
            personOfNoun = 7;
            referenceSpeechIdentifier_Subject = "they";
            // configure linking verb predicate based on subject "they"
            if (initialVerbGiven.getfirstLevelFormedWord().toLowerCase().matches("be")) {
                if (initialVerbGiven.getVerbTense().matches("BlankTense")) {
                    referenceSpeech_linkingVerb = "are to be [blank]";
                } else if (initialVerbGiven.getVerbTense().matches("future-perfect")) {
                    referenceSpeech_linkingVerb = "will have been";
                } else if (initialVerbGiven.getVerbTense().matches("future")) {
                    referenceSpeech_linkingVerb = "will be";
                } else if (initialVerbGiven.getVerbTense().matches("present")) {
                    referenceSpeech_linkingVerb = "are";
                } else if (initialVerbGiven.getVerbTense().matches("imperfect")) {
                    referenceSpeech_linkingVerb = "were being";
                } else if (initialVerbGiven.getVerbTense().matches("perfect")) {
                    referenceSpeech_linkingVerb = "were";
                } else if (initialVerbGiven.getVerbTense().matches("plu-perfect")) {
                    referenceSpeech_linkingVerb = "had been";
                }
            }
        }
        
        returnElements[0] = Integer.toString(personOfNoun);
        returnElements[1] = referenceSpeechIdentifier_Subject;
        returnElements[2] = referenceSpeech_linkingVerb;
        return returnElements;
        
    }
    
    /**
     * @desc  just returns forms of do with object term it
     * @param tenseInput the tense of the verb given
     * @param speechPersonNum 1st singular so on  with regard to int table
     * @param it_or_that  specifies which output to get : false  it or true that
     * @return the verb phrase of doing
     */
    public static String ordinaryDoing_CommonVerb_form(String tenseInput, int speechPersonNum, boolean it_or_that){
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
        
        
        String returnPhrase = "null";
        if (speechPersonNum == 1 ) {
            if (tenseInput.matches("BlankTense")) {
                returnPhrase = "am to do it";
            } else if (tenseInput.matches("future-perfect")) {
                returnPhrase = "will have done it";
            } else if (tenseInput.matches("future")) {
                returnPhrase = "will do it";
            } else if (tenseInput.matches("present")) {
                returnPhrase = "do it";
            } else if (tenseInput.matches("imperfect")) {
                returnPhrase = "was doing it";
            } else if (tenseInput.matches("perfect")) {
                returnPhrase = "did it";
            } else if (tenseInput.matches("plu-perfect")) {
                returnPhrase = "had done it";
            }
        }
        // person is "you"
        else if (speechPersonNum == 2 || speechPersonNum == 6 || speechPersonNum == 7 ) {
            if (tenseInput.matches("BlankTense")) {
                returnPhrase = "are to do it";
            } else if (tenseInput.matches("future-perfect")) {
                returnPhrase = "will have done it";
            } else if (tenseInput.matches("future")) {
                returnPhrase = "will do it";
            } else if (tenseInput.matches("present")) {
                returnPhrase = "do it";
            } else if (tenseInput.matches("imperfect")) {
                returnPhrase = "were doing it";
            } else if (tenseInput.matches("perfect")) {
                returnPhrase = "did it";
            } else if (tenseInput.matches("plu-perfect")) {
                returnPhrase = "had done it";
            }
        }
        // person is "he / she / it / some thing"
        else if (speechPersonNum == 3 || speechPersonNum == 4 || speechPersonNum == 5) {
            if (tenseInput.matches("BlankTense")) {
                //referenceSpeech_CommonTypeVerb = "is to be [blank]";
                returnPhrase = "is to do it";
            } else if (tenseInput.matches("future-perfect")) {
                returnPhrase = "will have done it";
            } else if (tenseInput.matches("future")) {
                returnPhrase = "will do it";
            } else if (tenseInput.matches("present")) {
                returnPhrase = "does it";
            } else if (tenseInput.matches("imperfect")) {
                returnPhrase = "was doing it";
            } else if (tenseInput.matches("perfect")) {
                returnPhrase = "did it";
            } else if (tenseInput.matches("plu-perfect")) {
                returnPhrase = "had done it";
            }
        }
        
        return returnPhrase;
        
    }
    
    
    
}
