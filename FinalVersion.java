//James' Project//
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


public class FinalVersion extends JPanel implements MouseListener, MouseMotionListener{
	
	Bird[] bird = new Bird[100];
    static int NoOfBirds = 0;
    final static int MAXBIRDS = 80;
    int mousex;
    int mousey;

    Predator[] predator = new Predator[5];
    static int NoOfPredators = 0;
    final static int MAXPREDATORS = 3;
    

	Flock[] flock = new Flock[50];
	static int NoOfFlocks = 0;
	final static int MAXFLOCKS = 40;
 	int birdx;
 	int birdy;
 	int flocknumber;
 	int flockXax, flockXbx, flockXay, flockXby, flockXx, flockXy, flockXsize, flockXxsize, flockXysize, flockX;
 	int showflockYN =0;

 	public FinalVersion() {

	addMouseListener(this);
    addMouseMotionListener(this);
	}

	public void joinFlock () {
	  	if (NoOfFlocks >= 1 ){
	  			for (int i = 0; i < NoOfBirds;i++) {
	  				for (int j = 0; j < NoOfFlocks; j++){
	  				if (bird[i].getBounds().intersects(flock[j].getBounds()) && !bird[i].inflock()) {
	  					bird[i].joinFlock(2, 2, flock[j].getX(), flock[j].getY(), j);
	  				}
				}
			}			
		}
	}

	public void setup() {
		String multiLineMsg[] = { "This app creates flocks of birds.","Create birds using the left hand mouse button","Create Predators with the Right hand mouse button","Predators destroy flocks!","Would you like the flocks to be visible?" };
    	JOptionPane pane = new JOptionPane();
    	pane.setMessage(multiLineMsg);
    	pane.setOptionType(JOptionPane.YES_NO_OPTION);
    	JDialog d = pane.createDialog(null, "Welcome");
    	d.setVisible(true);
    	int n = ((Integer)pane.getValue()).intValue();
    	if (n == JOptionPane.YES_OPTION){showflockYN = 1;}else{showflockYN = 0;}
	}

	public void birdCollide () {
		for (int i = 0; i < NoOfBirds - 1; i++) {
			if (!bird[i].inflock()){
				for (int j = i+1; j < NoOfBirds; j++) {
					if (!bird[j].inflock()){
						if (bird[i].getBounds().intersects(bird[j].getBounds())){
							birdy = bird[i].getY();
							birdx = bird[i].getX();
							flock[NoOfFlocks] = this.new Flock(this, birdx, birdy, 100);
							bird[i].joinFlock(2, 2, flock[NoOfFlocks].getX(), flock[NoOfFlocks].getY(), NoOfFlocks);
							bird[j].joinFlock(2, 2, flock[NoOfFlocks].getX(), flock[NoOfFlocks].getY(), NoOfFlocks);
							NoOfFlocks++;
						}
					}
				}

			}
		}
	}

	public void flockCollide() {
		if(NoOfFlocks>1){
			for (int i =0; i <NoOfFlocks -1; i++) {
				flockX=0;
				for (int j =i+1;j<NoOfFlocks;j++){
					if(NoOfFlocks>1){
		
						if (flock[i].getBounds().intersects(flock[j].getBounds())){
							if (flock[i].getX() <= flock[j].getX()){
								flockXx = flock[i].getX();
								flockXxsize = flock[j].getX()+flock[j].getSize()-flock[i].getX();
							}
							else {
								flockXx = flock[j].getX();
								flockXxsize = flock[i].getX()+flock[i].getSize()-flock[j].getX();
							}

							if (flock[i].getY() <= flock[j].getY()){
								flockXy = flock[i].getY();
								flockXysize = flock[j].getY()+flock[j].getSize()-flock[i].getY();
							}
							else {
								flockXy = flock[j].getY();
								flockXysize = flock[i].getY()+flock[i].getSize()-flock[j].getY();
							}
							flockXsize = flockXysize +15;
							if (flockXxsize >= flockXysize){flockXsize = flockXxsize+15;}


						
							flock[NoOfFlocks] = this.new Flock(this, flockXx, flockXy, flockXsize);
						
							for (int k = 0; k <NoOfBirds; k++) {
								if(bird[k].inflock()){
									if(bird[k].getFlock()<i){}
									if(bird[k].getFlock() == i) {bird[k].joinFlock(1, 1, flockXx, flockXy, NoOfFlocks-2);}
									if(bird[k].getFlock()>i && bird[k].getFlock() < j) {bird[k].flockXchange(1);}
									if(bird[k].getFlock() == j) {bird[k].joinFlock(1, 1, flockXx, flockXy, NoOfFlocks-2);}
									if(bird[k].getFlock()>j) {bird[k].flockXchange(2);}
								}
							}
						
							NoOfFlocks++;
							System.arraycopy(flock,i+1,flock,i,NoOfFlocks-i-1);
							NoOfFlocks--;
							System.arraycopy(flock,j,flock,j-1,NoOfFlocks-j);
							NoOfFlocks--;
							i--;
							break;
						}
					}
				}
			}
		}
	}


	public void predatorCollide() {
		if ((NoOfFlocks > 0) && NoOfPredators > 0) {
			for(int i=0; i <NoOfPredators;i++){
				for(int j=0;j<NoOfFlocks;j++){
					if (predator[i].getBounds().intersects(flock[j].getBounds())){
							for (int k = 0; k <NoOfBirds; k++) {
								if(bird[k].inflock()){
									if(bird[k].getFlock()<j){}
									if(bird[k].getFlock() == j) {bird[k].leaveFlock();}
									if(bird[k].getFlock()>j) {bird[k].flockXchange(1);}
								}
							}
						System.arraycopy(flock,j+1,flock,j,NoOfFlocks-j-1);
						NoOfFlocks--;
					}
				}
			}
		}

	}


	public void mousePressed(MouseEvent event) {
    if(event.getButton() == MouseEvent.BUTTON1)
     {
    	mousex = (int) event.getPoint().getX();
    	mousey = (int) event.getPoint().getY();
    	bird[NoOfBirds] = this.new Bird(this, mousex, mousey);
    	NoOfBirds++;}
	else if(event.getButton() == MouseEvent.BUTTON3)
	{
		mousex = (int) event.getPoint().getX();
    	mousey = (int) event.getPoint().getY();
    	predator[NoOfPredators] = this.new Predator(this, mousex, mousey);
    	NoOfPredators++;}	
	}
	

	public void mouseClicked(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}	
		

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < NoOfBirds;i++) {
		g2d.setPaint(new Color(bird[i].getRed(), bird[i].getGreen(), bird[i].getBlue(), 255));
        bird[i].paint(g2d);
        }
        for (int i = 0; i < NoOfPredators;i++) {
		g2d.setPaint(new Color(255, 0, 0, 150));
        predator[i].paint(g2d);
        }
        if(showflockYN==1){
        	for (int i = 0; i < NoOfFlocks;i++) {
	  		g2d.setPaint(new Color(0, 255, 0, 50));
      		flock[i].paint(g2d);
      		}
      	}
        

    }


	public static void main(String[] args) throws InterruptedException {
	
		JFrame frame = new JFrame("Wind");
		FinalVersion game = new FinalVersion();
		frame.add(game);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(1920, 1020);
		// frame.setBackground(Color.BLACK);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setup();
		while (true) {
		
			game.joinFlock();
			game.birdCollide();
			game.flockCollide();
			game.predatorCollide();

			for (int i = 0; i <NoOfBirds;i++) {
				if (game.bird[i].inflock())
				{
					game.flocknumber = game.bird[i].getFlock();
					game.bird[i].move(game.flock[game.flocknumber].getX(), game.flock[game.flocknumber].getY(), game.flock[game.flocknumber].getSize(), game.flock[game.flocknumber].getXv(), game.flock[game.flocknumber].getYv());
				}
				else {
					game.bird[i].move();	
				}
			}
			for (int i = 0; i <NoOfPredators;i++) {
				game.predator[i].move();
			}
			for (int i = 0; i <NoOfFlocks;i++) {
				game.flock[i].move();
			}
			game.repaint();
			Thread.sleep(10);
		}
	}
public class UFO {
	int x;
 	int y;
 	int xv;
 	int yv;
 	int size;
 	int flockNo;

	public FinalVersion game;
	
	public UFO() {
	}
 	
	
	public int getXv() {
		return xv;
	}

	public int getYv() {
		return yv;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public int getFlock() {
		return flockNo;
	}
 	
 	public void move () {
		if(x <= 0) {
			xv = -xv;
			x = 1;}
		if(x >= (game.getWidth() - size)){
			xv = - xv;
			x = game.getWidth() - size - 1;}
		if(y >= (game.getHeight() - size)) {
			yv = -yv;
			y = game.getHeight() - size - 1;}
		if(y <= 0) {
			yv = -yv;
			y = 1;}

		x = x + xv;
		y = y + yv;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, size, size); 
	}
	
	
}


 

 public class Bird extends UFO{
 	boolean inFlock = false;

public Bird(FinalVersion game, int mousex, int mousey) {
	this.game = game;
	x = mousex;
	y = mousey;
	xv = -5 + (int) (Math.random() * 10);
	yv = -5 + (int) (Math.random() * 10);
	size = 15;
		} 	

	public void move (int flockx, int flocky, int flockSize, int flockxv, int flockyv) {
		x = x + xv + flockxv;
		y = y + yv + flockyv;
		if(x <= flockx ) {
			xv = -xv;
			x = flockx + 1;}
		if(x >= (flockx + flockSize - size)){
			xv = - xv;
			x =flockx + flockSize - size-1;}
		if(y >= (flocky + flockSize - size)) {
			yv = -yv;
			y =flocky + flockSize - size-1;}
		if(y <= flocky ) {
			yv = -yv;
			y = flocky + 1;}
		}

	public void joinFlock (int flockxv, int flockyv, int flockx, int flocky, int flocknumber) {
		inFlock = true;
		xv = (xv/flockxv);
		yv = (yv/flockyv);
		flockNo = flocknumber;
		}

	public boolean inflock() {
		return inFlock;
	}

	public void flockXchange (int change){
		flockNo = flockNo - change;
	}

	public void leaveFlock (){
		inFlock = false;
		xv = (xv*2);
		yv = (yv*2);
		if(xv<2){xv = -5 + (int) (Math.random() * 10);}
		if(yv<2){yv = -5 + (int) (Math.random() * 10);}
	}

	public int getRed() {
    	int r = x / 6;
    	if (r > 255) r = 255;
    	if (r <0 ) r=0;
    	return r;

    }

    public int getGreen() {
    	 int g = 0 ;
    	 if (xv <0) g = xv * -40;
    	 if (xv >0) g = xv * 40;
    	return g;
    }

    public int getBlue() {
    	int b = y / 4;
    	if (b > 255) b = 255;
    	if (b <0 ) b=0;
    	return b;
    }


    public int getHue(){
    	int h = 100;
    	if (inFlock) h=255;
    	return h;
    }
	public void paint(Graphics2D g) {
		g.fillOval(x, y, size, size);
		}
	}
	



public class Flock extends UFO {
	
		public Flock(FinalVersion game, int birdx, int birdy, int startsize) {
		this.game = game;
		x = birdx ;
		y = birdy ;
		xv = -5 + (int) (Math.random() * 10);
		yv = -5 + (int) (Math.random() * 10);
		size = startsize;
	}


	public void paint(Graphics2D g) {
	g.fillRect(x, y, size, size);
	}
}

public class Predator extends UFO{

		public Predator(FinalVersion game, int mousex, int mousey) {
		this.game = game;
		x = mousex;
		y = mousey;
		xv = -5 + (int) (Math.random() * 10);
		yv = -5 + (int) (Math.random() * 10);
		size = 50;
		}

		public void paint(Graphics2D g) {
		g.fillOval(x, y, size, size);
		}
}
}