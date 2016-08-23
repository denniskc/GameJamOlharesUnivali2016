import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Dennis Kerr Coelho - PalmSoft
 *
 */

public class Sprite extends Objeto{
    
    public Image Charset = null;
    
    float X,Y;
    int VelX,VelY;
    int FrameTime;
    long Tempo;
    int Anim;
    int Character;
    int Frame;
    int charx,chary;
    boolean EmCharset;
    int Altura,Largura;
    int NFrames,NAnimations;
    
    int Tipo;
    
    int Life = 100;
    
    public Sprite(Image charset,int x,int y,int charac,boolean emcharset,int largura,int altura,int nframes,int nanimations){
        X = x;
        Y = y;
        
        VelX = 0;
        VelY = 0;
        
        FrameTime = 100;
        
        Tempo = 0;
        
        Anim = 0;
        
        Character = charac;
        
        charx = (Character%nframes)*(nframes*largura); 
        chary = (Character/nanimations)*(nanimations*altura);
        
        Frame = 0;
        
        Charset = charset;
        
        EmCharset = emcharset;
        
        Altura = altura;
        Largura = largura;
        
        NFrames = nframes; 
        NAnimations = nanimations;
        
        Tipo = 0;
        
    }

    public void DesenhaSe(Graphics2D dbg) { 
        if(EmCharset==true){
            dbg.drawImage(Charset,(int)X,(int)Y,(int)X+Largura,(int)Y+Altura,charx+(Frame*Largura),chary+(Anim*Altura),charx+(Frame*Largura)+Largura,chary+(Anim*Altura)+Altura,null);
        }else{
            dbg.drawImage(Charset,(int)X,(int)Y,null);            
        }
    }


    public void DesenhaSe(Graphics2D dbg,int Xmundo,int Ymundo){
        int posx = (int)X - Xmundo; 
        int posy = (int)Y - Ymundo;
        
        if(EmCharset==true){
        	dbg.drawImage(Charset,posx,posy,posx+Largura,posy+Altura,charx+(Frame*Largura),chary+(Anim*Altura),charx+(Frame*Largura)+Largura,chary+(Anim*Altura)+Altura,null);
        }else{
            dbg.drawImage(Charset,posx,posy,null);            
        }
    }    
    
    public void SimulaSe(long diftime) {
        Tempo += diftime;
        
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
        
        Frame = ((int)(Tempo/FrameTime))%NFrames;
        
    }

}
