
public class Point {  // represents a block/location on the screen/image
  private int row, col;

  public Point( int x, int y ) {
    row = x;
    col = y;
  }

  public void setRow( int x ) { row = x; } 
  public int getRow() { return row; } 
  public void setCol( int x ) { col = x; } 
  public int getCol() { return col; } 
  
  public boolean equals( Point p ) {
	  
	  return getRow() == p.getRow()  && getCol() == p.getCol();
	  
    //return whether Point p is the same as this Point 
  }
}