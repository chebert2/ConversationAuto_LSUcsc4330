/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   predicateData  class:
 * This class holds a miniature set of a single verb sections's phrase elements.
 */
package conversationauto1;


public class predicateData {
    private String modalInfo;
    private String beingAuxillary1;
    // only really need 2nd being auxillary for passive verbs
    private String beingAuxillary2;
    boolean modalNot;
    boolean auxillaryVerbNot;
    // this is the tense of the primary main verb
    private String mainVerb;
    private String transitivity;
    
    public predicateData(){
        this.modalInfo = "null";
        this.beingAuxillary1 = "null";
        this.beingAuxillary2 = "null";
        this.modalNot = false;
        this.auxillaryVerbNot = false;
        this.mainVerb = "null";
        this.transitivity = "null";
    }
    
    public predicateData(String modalInfoInput, String beingAuxillary1Input, String beingAuxillary2Input, boolean modalNotInput, boolean auxillaryVerbNotInput, String mainVerbInput, String transitivityInput){
        this.modalInfo = modalInfoInput;
        this.beingAuxillary1 = beingAuxillary1Input;
        this.beingAuxillary2 = beingAuxillary2Input;
        this.modalNot = modalNotInput;
        this.auxillaryVerbNot = auxillaryVerbNotInput;
        this.mainVerb = mainVerbInput;
        this.transitivity = transitivityInput;
    }
    
    public String printPredicateData(){
        String modalNot1 = null;
        String verbNot1 = null;
        if(this.modalNot)
            modalNot1 = "not";
        if(this.auxillaryVerbNot)
            verbNot1 = "not";
        String returnStringData = "modal:" +this.modalInfo+ " modal_Not: " + this.modalNot +  "  auxillary1: " + this.beingAuxillary1 + "  auxillary2: " + this.beingAuxillary2  +  " verb_Not: " + this.auxillaryVerbNot  + " primary verb: " + this.mainVerb
                 + " transitivity: " + this.transitivity;
        return returnStringData;
    }
    
    public String getModalInfo(){
        return this.modalInfo;
    }
    public String getAuxillaryVerb1(){
        return this.beingAuxillary1;
    }
    public String getAuxillaryVerb2(){
        return this.beingAuxillary2;
    }
    public boolean getModalNot(){
        return this.modalNot;
    }
    public boolean getRegVerbNot(){
        return this.auxillaryVerbNot;
    }
    public String getPrimaryVerb(){
        return this.mainVerb;
    }
    public String getTransitivityOfVerb(){
        return this.transitivity;
    }
    
    public void setModalInfo(String modalInfoInput1){
        this.modalInfo = modalInfoInput1;
    }
    public void setAuxillaryVerb1(String auxillaryVerbInput1){
          this.beingAuxillary1 = auxillaryVerbInput1;
    }
    public void setAuxillaryVerb2(String auxillaryVerbInput2){
        this.beingAuxillary2 = auxillaryVerbInput2;
    }
    public void setModalNot(boolean setModalNot){
        this.modalNot = setModalNot;
    }
    public void setAuxVerbNot(boolean setAuxVerbNot){
        this.auxillaryVerbNot = setAuxVerbNot;
    }
    public void setPrimaryVerb(String primaryVerbInput){
        this.mainVerb = primaryVerbInput;
    }
    public void setTransitivity(String transitivity1){
        this.transitivity  = transitivity1;
    }
}
