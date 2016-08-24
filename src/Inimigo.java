import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Inimigo extends Sprite {
	int vel = 0;
	int pathpoint = 0;
	
	float initx = 0;
	float inity = 0;
	
	GamePath path = null;
	
	public Inimigo(BufferedImage charset, int x, int y, int charac, boolean emcharset, int largura, int altura, int nframes,
			int nanimations) {
		super(charset, x, y, charac, emcharset, largura, altura, nframes, nanimations);
		initx = x;
		inity = y;
		
        Altura = charset.getHeight();
        Largura = charset.getWidth();
	}
	
	@Override
	public void SimulaSe(long diftime) {
        Tempo += diftime;
        
        if(path==null){

        }else{
        	float xp = path.xpoints[pathpoint]+initx;
        	float yp = path.ypoints[pathpoint]+inity;
        	
        	float dx = xp - X;
        	float dy = yp - Y;
        	
        	float dist = dx*dx+dy*dy;
        
        	if(dist < 25){
        		pathpoint++;
        		if(pathpoint>=path.npoints){
        			if(path.ciclico){
        				pathpoint = 0;
        			}else{
        				pathpoint = 0;
        				initx = X;
        				inity = Y;
        			}
        		}
            	xp = path.xpoints[pathpoint]+initx;
            	yp = path.ypoints[pathpoint]+inity;
            	
            	dx = xp - X;
            	dy = yp - Y;
        	}
        	
        	double ang = Math.atan2(dy, dx);
        	VelX = (float)Math.cos(ang)*vel;
        	VelY = (float)Math.sin(ang)*vel;
        }
        
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
                
        Frame = ((int)(Tempo/FrameTime))%NFrames;
        
	}

}
