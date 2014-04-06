import java.util.Random;
import java.util.ArrayList;


public class ManageWorm {
	//These are static CONSTANTS that can be accessed by ManageWorm."constant name"
	public final static int  LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	private Point munchieLocation; //location of "active" munchie
	private int score, growthVal, munchieVal;
	//ArrayList is Java's implementation of a Python list
	private ArrayList<Point> freePool; //manage the "free" locations on the screen for next munchie
	private WormBody body;
	private ScreenBuffer image;
	private int wormInitTailCol, wormInitHeadCol; //starting and stopping columns for the initial position of the worm 
	private Random rand; 

	ManageWorm( int rows, int cols ) {
		score = growthVal = 0;
		munchieLocation = null;
		body = new WormBody();
		wormInitTailCol = 5;
		wormInitHeadCol = 12;
		rand = new Random();
		freePool = new ArrayList<Point>( rows * cols );
		image = new ScreenBuffer( rows, cols );
		int rowLimit = rows - 1, colLimit = cols - 1;

		for( int i = 1; i < rowLimit; i++ ) // declare everything free
			for( int j = 1; j < colLimit; j++ )
				makeFree( new Point( i, j ) );

		int wormInitRow = rows / 2;    
		for( int i = wormInitTailCol; i <= wormInitHeadCol; i++ ) // initialize worm
			makeWormSegment( new Point(wormInitRow, i ) );

		munchieVal = getAMunchieValue();
		munchieLocation = (Point)freePool.get( getAnIdxValue( freePool.size() ) );
	}

	public WormBody getWormBody() { return body; }
	public int getMunchieValue() { return munchieVal; }
	public Point getMunchieLocation() { return munchieLocation; }
	public int getScore() { return score; }
	public int getAMunchieValue() { // return a value in the range 1..9
		return Math.abs( rand.nextInt(9) ) + 1;
	}
	public int getAnIdxValue( int max ) { // return a value in 0..(max-1)
		return Math.abs( rand.nextInt(max) );
	}

	public boolean move( int direction ) { // did the worm survive the move?
		WormSegment head = body.getHead();
		if ( direction == LEFT ){
			Point dir = new Point( head.getRow(), head.getCol() -1);
			return finalizeMove(dir);
		}else if( direction == RIGHT ){
			Point dir = new Point( head.getRow(), head.getCol() +1);
			return finalizeMove(dir);
		}else if( direction == UP ){
			Point dir = new Point( head.getRow() -1, head.getCol() );
			return finalizeMove(dir);
		}else{ 
			Point dir = new Point( head.getRow() +1, head.getCol() );
			return finalizeMove(dir);
		}
		
		//for each possible direction create a new Point that will represent
		// the new head of the worm, call finalizeMove with this Point, and
		// return what finalizeMove returns (pass finalize's return on up)
		
	}
  
	//WORK HORSE FOR EACH "move".  This needs to determine when the game is over 
	//(run into wall or worm body).  It needs to add a new worms segment (to the head),
	// continue to grow if applicable, determine whether the new head "eats"
	// a munchie and responds correspondingly, and manages the freePool in case the
	// tail needs to be removed from the worm.
	public boolean finalizeMove( Point p ) {
		
		if( image.isOccupied( p ) ) { return false; }
		
		makeWormSegment( p );
		if( p.equals(munchieLocation) ) { 
			
			score += munchieVal;
			growthVal += munchieVal;
			munchieVal = getAMunchieValue();
			munchieLocation = (Point)freePool.get( getAnIdxValue( freePool.size() ) );
		}
		if ( growthVal != 0 ){
			
			growthVal --; 
		}else{ 
			
			WormSegment temp = body.rmTail();
			freePool.add(temp.getPoint());
			image.setValueAt(temp.getPoint(), getAnIdxValue(freePool.size()));
		}
		return true;
		//complete this method.
	}

	private void makeFree( Point p ) {
		
		freePool.add(p);
		image.setValueAt(p, freePool.size()-1 );
		//add this Point to the "freePool" arraylist and its location 
		// to the ScreenBuffer's reference to this Point
	}
  
	private void makeWormSegment( Point p  ) {
		
		body.addToHead(new WormSegment(p));
		freePool.remove(p);
		image.makeWormSegment(p);
		
		
	// worm must grow from its head, and both the "freePool" and "image" states 
	//  MUST BE CAREFULLY UPDATED 

   }

}

