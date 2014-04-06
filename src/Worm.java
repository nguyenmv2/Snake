import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

public class Worm extends JFrame implements KeyListener, ActionListener {

	private int numRows, numCols, colWidth, rowHeight;
	private ManageWorm worm;
	private WormBody body;
	private Timer timer;
	private JPanel canvas;
	private boolean wormAlive = true;
	private int direction = ManageWorm.RIGHT;

	//This implementation allows for the worm to be moved both in terms
	// of direction and a single "step"
	public void keyPressed( KeyEvent ke ) {
		if( ! wormAlive ) return;
		int kc = ke.getKeyCode();
		if( kc == KeyEvent.VK_UP ) { 
			wormAlive = worm.move( ManageWorm.UP ); 
			repaint(); 
		}
		else if( kc == KeyEvent.VK_DOWN ) {
			wormAlive = worm.move( ManageWorm.DOWN ); 
			repaint();
		}
		else if( kc == KeyEvent.VK_LEFT ) {
			wormAlive = worm.move( ManageWorm.LEFT );
			repaint();
		}
		else if( kc == KeyEvent.VK_RIGHT ) {
			wormAlive = worm.move( ManageWorm.RIGHT );
			repaint();
		}
	}
	public void keyReleased( KeyEvent ke ) { /* System.out.println( "Key released " );*/ }
	public void keyTyped( KeyEvent ke ) {}


	public Worm() {
		super("Worm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		numRows = 25;
		numCols = 70;
		colWidth = rowHeight = 10;
		
		setSize( (numCols + 2 ) * colWidth , (numRows + 4 ) * rowHeight );
		addKeyListener( this );
		canvas = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Worm.this.paintGame(g);
            }
        };
        
        java.awt.Container contents = getContentPane();
        contents.setLayout(null);
        contents.add(canvas);
        Insets insets = this.getInsets();
        canvas.setSize(this.getWidth() - insets.left - insets.right, this.getHeight()- insets.top - insets.bottom );
        canvas.setLocation(insets.left,insets.top);
        canvas.setBackground(Color.WHITE);
		
        worm = new ManageWorm( numRows, numCols );
		body = worm.getWormBody();
	}

	public void paintGame( Graphics g ) {
		if( ! wormAlive ) {
			g.drawString( "Game is over", 10, 20 );
			g.drawString( "You gained " + Integer.toString( worm.getScore() ), 10, 30 );
			return;
		}
		
		Color prev_color = g.getColor(); //remember the Color state of the Graphics object
		
		//draw blue borders
		g.setColor( Color.blue );
		g.fillRect( 0, 0, numCols * colWidth, rowHeight );
		g.fillRect( 0, ( numRows - 1 ) * rowHeight, numCols * colWidth, rowHeight );
		g.fillRect( 0, 0, colWidth,  numRows * rowHeight );
		g.fillRect( colWidth * ( numCols - 1 ), 0, colWidth, numRows * rowHeight );
		//draw worm
		g.setColor( Color.red );
		body.start();
		while ( body.moreElements() ){

			WormSegment p = body.nextElement();
			p.display(g, p.getCol()* colWidth, p.getRow()*rowHeight, 10 , 10 );		}
		//COMPLETE THIS PORTION OF THE METHOD USING THE WORMBODY'S ITERATOR TO DRAW THE
		// WORM ON THE SCREEN.
		
		
		//write the munchie value at the appropriate location
		Point p = worm.getMunchieLocation();
		g.setColor( Color.black );
		g.drawString( Integer.toString( worm.getMunchieValue() ), p.getCol()*colWidth, ( p.getRow() + 1 ) *rowHeight );
		//replace the original Color 
		g.setColor( prev_color );
		
		timer = new Timer(500, this);
		timer.start();
	}
	
	public void actionPerformed( ActionEvent ae ) {
		if( ae.getSource() == timer ) {
			wormAlive = worm.move( direction );
			if( !wormAlive && timer.isRunning() )
				timer.stop();			
			canvas.repaint();
		}
	}

	//Entry point into the (GUI) application
	public static void main( String [] argv) {
		Worm w =  new Worm();
		w.setVisible( true );
	}
}
