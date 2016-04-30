/**
 *  @author Colin Hebert   CSC4330
 *    email chebe46@lsu.edu
 *  preliminary Description:  of this   CircularBuffer   class:
 * This is a data structure I modified from www.java2s.com
 */
package conversationauto1;

/**
 * @desc Just a simple design of a buffer to hold a few strings with circular buffer update
 * @author demo www.java2s.com
 */

class CircularBuffer {
  private String data[];
  private int head;
  private int tail;
  private int length;
  private int currentCount;

  public CircularBuffer(Integer number) {
    data = new String[number];
    head = 0;
    tail = 0;
    length = number;
    currentCount = 0;
  }

  public void store(String value) {
      // this is the prime initial run of array slots.
      if(this.currentCount<this.length+1){
         this.currentCount++;
         this.data[tail++] = value;
         // count is 1 over the end, so start back at beginning
         if (this.tail == this.length) 
            this.tail = 0;
         
      }
      else{
         this.data[tail++] = value;
         if (this.tail == this.length) {
            this.tail = 0;
         }
      }
          
  }
/**
 * 
 * @return String array of a FirstIn Last Out Buffer of String values
 */
  public String[] read() {
      String[] returnString = new String[length];
      int increase0 = 0;
      if(this.currentCount <= this.length)
        for(int s = this.currentCount - 1; s>=0 ; s--)
            returnString[increase0++] = this.data[s];
      else{
        int markerStart = this.tail;
        int firstNewSection = this.length - markerStart;
        increase0 = 0;
        while(this.length - firstNewSection > 0){
            returnString[increase0++] = this.data[this.length - firstNewSection -1];
            firstNewSection++;   
        }
        int SecondNewSection = this.length;
        while( this.tail < SecondNewSection ){
            returnString[increase0++] = this.data[SecondNewSection - 1];
            SecondNewSection--;   
            
        }
      }
      return returnString;

        
    }
  /**
   * 
   * @return int length of circular String buffer
   */
  public int getLength(){
      return this.length;
  }

}
