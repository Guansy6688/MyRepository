import java.awt.Canvas;
import java.awt.Color;

public class Ring {
	int x1, y1, x2, y2,x0,y0;// x1,y1:upleft coordinate; x2,y2:width; x0,y0:origin
	Color color;

	public Ring(int x0, int y0, int x2, int y2) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x0-(x2/2);
		this.y1 = y0-(y2/2);
		this.x2 = x2;
		this.y2 = y2;
	}

	void draw(Canvas c, Color color) {
		this.color=color;
		c.getGraphics().setColor(color);
		c.getGraphics().drawOval(x1, y1, x2, y2);
	}
	
	void remove(Canvas c) {
		int x,y,width,height;
		x=this.x1;
		y=this.y1;
		width=this.x2+1;
		height=this.y2+1;
		c.getGraphics().clearRect(x, y, width, height);
		
	}
	
	
	
}