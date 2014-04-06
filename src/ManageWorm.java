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
		if (direction == UP){
			Point n = new Point(head.getRow() - 1, head.getCol());
			return finalizeMove(n);
		}
		else if (direction == DOWN){
			Point n = new Point(head.getRow() + 1, head.getCol());
			return finalizeMove(n);
		}
		else if (direction == LEFT){
			Point n = new Point(head.getRow(), head.getCol()-1);
			return finalizeMove(n);
		}
		else {
			Point n = new Point(head.getRow(), head.getCol() + 1);
			return finalizeMove(n);
		}		
	}
	public boolean finalizeMove( Point p ) {
		if( image.isOccupied( p ) ) return false;
		makeWormSegment( p );
		if ( p.equals(munchieLocation)){
			score += munchieVal;
			growthVal = munchieVal;
			munchieVal = getAMunchieValue();
			munchieLocation = (Point)freePool.get( getAnIdxValue( freePool.size() ) );
		}
		if (growthVal != 0){
			growthVal--;			
		}
		else {
			WormSegment t = body.rmTail();
			freePool.add(t.getPoint());
			image.setValueAt(t.getPoint(), getAnIdxValue(freePool.size()));
		}
		return true;
	}

	private void makeFree( Point p ) {
		freePool.add(p);
		image.setValueAt(p, getAnIdxValue(freePool.size()) );
	}
  
	private void makeWormSegment( Point p  ) { 
		body.addToHead(new WormSegment(p)) ;
		freePool.remove(p);
		image.setValueAt(p, -1);
   }

}

