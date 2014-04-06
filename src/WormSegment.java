import java.awt.Graphics;

public class WormSegment {
  private Point p;
  private WormSegment next;

  public WormSegment( Point pp ) { 
    p = pp;
    next = null;
  }
  
  public WormSegment( int row, int col ) {
    p = new Point( row, col );
    next = null;
  }

  public Point getPoint() { return p; }
  public void setNext( WormSegment ws ) { next = ws; }
  public WormSegment getNext() { return next; }
  
  public int getRow() { return p.getRow();  } //return the row location of this WormSegment 
  public int getCol() { return p.getCol();  } //return the column location of this WormSegment 
  public void display( Graphics g, int x, int y, int width, int height ) {
    g.fillOval( x, y, width, height );
  }
}