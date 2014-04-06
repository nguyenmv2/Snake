
public class ScreenBuffer {
	//more static CONSTANTS for convenience	  
	private final static int WORM_SEGMENT = -1, WALL = -2;
	private int [][] screen;
	
	//The values in ScreenBuufer's screen array are either WORM_SEGMENT, WALL, 
	// or the index value where the point can be found in the freePool
	//  
	ScreenBuffer( int rows, int columns ) {  //THIS IS COMPLETE
		screen = new int[rows][columns]; 
		//a two dimensional array is a "long" one-dimensional array but the the two
		//  indices [row] [col] internally do the arithmetic to find the correct
		//  associated value in the "hidden" long array
		for( int i = 0; i < rows; i++ ) {
			screen[i][0] = WALL;
			screen[i][columns-1] = WALL;
		}
		for( int i = 0; i < columns; i++ ) { // corners will be marked twice!
			screen[0][i] = WALL;
			screen[rows-1][i] = WALL;
		}
	}

	public boolean isAWormSegment( Point p ) { 
		
		return screen[p.getRow()][p.getCol()] == WORM_SEGMENT;
	}
		//return whether the given point corresponds to a Worm Segment location
	

	public void makeWormSegment( Point p ) { 
		
		screen[p.getRow()][p.getCol()] = WORM_SEGMENT;
		//set the given point's value to be a Worm Segment location
	}

	public boolean isWall( Point p ) { 
		
		return screen[p.getRow()][p.getCol()] == WALL;
	}
		//return whether the given point corresponds to a Wall location
	

	public boolean isOccupied( Point p ) { 
		
		return isAWormSegment(p) || isWall(p);
	}
		//return whether this point corresponds to a Worm Segment or Wall location
	

	public int valueAt( Point p ) {
		
		return screen[p.getRow()][p.getCol()];
		//return the screen value at Point p } 
	}
	
	public void setValueAt( Point p, int n ) {
		
		screen[p.getRow()][p.getCol()] = n;
		//set the screen value at Point p to value n
	}

}
