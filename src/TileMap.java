import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

/**
 * @author Dennis Kerr Coelho - PalmSoft
 *
 */
public class TileMap {
    
    public Image TileSet = null;
    
    int MapX;
    int MapY;
    int NTileX,NTileY;
    int Largura = 40;
    int Altura = 50;
    int TilePLinhaTileset;
    int TamTelaX;
    int TamTelaY;
    
    int POSMAPX;
    
    int [][]mapa;
   		
    public TileMap(Image tileset,int temtelaX,int tamtelaY){
        TamTelaX = temtelaX;
        TamTelaY = tamtelaY;        
        NTileX = temtelaX/16;
        NTileY = tamtelaY/16;
        TileSet = tileset;
        MapX = 0;
        MapY = 0;        
        TilePLinhaTileset = TileSet.getWidth(null) >>4;
        
        NTileY = 1000;
        NTileX = 40;
        mapa = new int[NTileY][NTileX];
        
        Largura = 40;
        Altura = 2000;        
        
        for(int j = 0; j < 1000;j++){
            for(int i = 0; i < 40;i++){
                mapa[j][i] = GamePanel.rnd.nextInt(10);
            }            
        }    
    }
        
    public void DesenhaSe(Graphics2D dbg){
    	int offx = MapX&0x0f; 
    	int offy = MapY&0x0f;
    	
    	int MapXFundo = MapX/8;
    	int MapYFundo = MapY/8;
    	
    	int offxlf = MapXFundo%16; 
    	int offylf = MapYFundo%16;
    	
    	int somax,somay;
    	if(offx>0){
    		somax = 1;
    	}else{
    		somax = 0;
    	}
    	if(offy>0){
    		somay = 1;
    	}else{
    		somay = 0;
    	}
    	
    	int somaxlf,somaylf;
    	if(offxlf>0){
    		somaxlf = 1;
    	}else{
    		somaxlf = 0;
    	}
    	if(offylf>0){
    		somaylf = 1;
    	}else{
    		somaylf = 0;
    	}    	
    	int py = 0;
    	int px = 0;
        for(int j = 0; j < NTileY + somaylf; j++){     
        	py = j+(MapYFundo>>4);
        	if(py>=NTileY){
        		py = py - NTileY;
        	}
            for(int i = 0; i < NTileX + somaxlf; i++){
            	px = i+(MapXFundo>>4);
            	int tile = mapa[py][px];
                int tilex = (tile%TilePLinhaTileset)*16;
                int tiley = (tile/TilePLinhaTileset)*16;
                
                dbg.drawImage(TileSet,(i*16)-offxlf,(j*16)-offylf,(i*16)+16-offxlf,(j*16)+16-offylf,tilex,tiley,tilex+16,tiley+16,null);
            }
        }    
    }
    
    public void Posiciona(int x,int y){
    	int X = x/16;
    	int Y = y/16;
    	
        if(X < 0){
            MapX = 0;
        }else if(X >= (Largura-NTileX)){
            MapX = ((Largura-NTileX))*16;
        }else{
            MapX = x;
            //System.out.println(MapX);
        }
        
        
        if(Y < 0){
        	//.out.println(" Y "+Y);
        	MapY = (NTileY+(Y%NTileY))*16;
        	//System.out.println(" MapY - "+MapY);
        }        

    }
}
