import java.awt.Color;
import java.awt.Graphics2D;

public class Particula extends Objeto{
	float x,y;
	int vx,vy;
	
	Color cor = new Color(255,255,255,170);
	
	public Particula(int x,int y,int vx,int vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg,int Xmundo,int Ymundo) {
		dbg.setColor(cor);
		dbg.fillRect((int)(x-Xmundo),(int)(y-Ymundo), 2, 2);
		//System.out.println(" px "+(x-Xmundo)+" py "+(y-Ymundo));
	}

	@Override
	public void SimulaSe(long diftime) {
		// TODO Auto-generated method stub
		x+=vx*diftime/1000.0f;
		y+=vy*diftime/1000.0f;
	}

}
