
class WormBody {

  /* YOU MUST CAREFULLY DECIDE AND MAINTAIN HOW YOU WILL
     ASSOCIATE THE HEAD OF THE WORM WITH THE HEAD OF
     WORMBODY (SINGLY LINKED LIST) !!!!!
     This class also provides an iterator.
  */

  private WormSegment head, tail, iter;
  
  WormBody() {
	  head = tail = iter = null;
  }
  
  public void addToHead( WormSegment p ) {
	  
	  if ( empty() ){ head = tail = p;}	  
	  else{
		  
		  head.setNext(p);
		  head = p;
	  }
	// p becomes the new head OF THE WORM
  }

  public WormSegment rmTail(){ 
	  WormSegment temp = tail;
	  tail = temp.getNext();
	  return tail;
	//remove and return the tail OF THE WORM
  }

  public boolean empty() {
	  
	  return head == null; 
	//returns whether the worm body is empty; }
  }
  public WormSegment getHead() {
	  
	  start();
	  WormSegment temp = new WormSegment(new Point(0,0) );
	  while (this.moreElements()){
		  temp = nextElement();
	  }
	  return temp;
  } //returns the head of the WORM

  
  // The following three methods are iterator methods.
  // This allows for an external agent to run through
  // the data structure WITHOUT KNOWING ITS UNDERLYING
  // STRUCTURE
  
  public void start() { 
	  
	  iter = head;
	  //set iter to the "head" of  WormBody
  }
  
  public WormSegment nextElement() {
    WormSegment ws = iter;
    iter = iter.getNext();
    return ws;
  }
  
  public boolean moreElements() { return iter != null; }
  
}
