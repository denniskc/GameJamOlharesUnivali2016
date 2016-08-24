import java.awt.Image;


public class NavePlayer extends Sprite {

	boolean AUP,ADOWN,ALEFT,ARIGHT,FIRE;
	int vel = 0;
	boolean Atingido = false;
	
	int TempoEntreTiros = 400;
	int Tiro = 0;
	int ProximoTiro = 0;
	
	int TimeImpac;
	
	
	public NavePlayer(Image charset, int x, int y, int charac, boolean emcharset,
			int largura, int altura, int nframes, int nanimations) {
		super(charset, x, y, charac, emcharset, largura, altura, nframes, nanimations);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void SimulaSe(long diftime) {
		// TODO Auto-generated method stub
		float oldx = X;
		float oldy = Y;
	
		ProximoTiro += diftime;
		
		TimeImpac+=diftime;
		
		super.SimulaSe(diftime);
		
		if(Atingido==false){
		    
			if(ALEFT){
				X += -((vel*diftime)/1000.0f);
				Anim = 3; 
			}
			if(ARIGHT){
				X += ((vel*diftime)/1000.0f);
				Anim = 1;
			}	
			if(AUP){
				Y += -((vel*diftime)/1000.0f);
				Anim = 0;
			}
			if(ADOWN){
				Y += ((vel*diftime)/1000.0f);
				Anim = 2;
			}

			
			if((X<0)||((X+Charset.getWidth(null))>640)){
				X = oldx;
			}	
			if((Y<0)||((Y+Charset.getHeight(null))>640)){
				Y = oldy;
			}
		}
		
        int xmundo = GamePanel.instance.MAPA.MapX;
        int ymundo = GamePanel.instance.MAPA.MapY;
		
		// Atira
		if(FIRE){
		    if(ProximoTiro>=TempoEntreTiros){
		        
		        GamePanel.instance.SomTiro.ParaSom();
		        GamePanel.instance.SomTiro.TocaSom();
		        
	            Sprite Tiro = new Sprite( GamePanel.instance.Tiros[1],xmundo+(int)X+10,ymundo+(int)Y-20,0,true,20,40,4,1);		    
	            Tiro.VelX = 0;
	            Tiro.VelY = -800;		    
	            GamePanel.instance.ListaTiros.add(Tiro);
	            
	            Sprite Tiro2 = new Sprite( GamePanel.instance.Tiros[1],xmundo+(int)X+80,ymundo+(int)Y-20,0,true,20,40,4,1);		    
	            Tiro2.VelX = 0;
	            Tiro2.VelY = -800;		    
	            GamePanel.instance.ListaTiros.add(Tiro2);
	            
	            TempoEntreTiros = 300;		        
		        
		        /*if( GamePanel.instance.PontosVida<50){
		            Sprite Tiro = new Sprite( GamePanel.instance.Tiros[0],(int)X+40,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -400;
		            Tiro.Tipo = 5;
		    
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            TempoEntreTiros = 400;
		        }else if( GamePanel.instance.PontosVida<100){
		            Sprite Tiro = new Sprite( GamePanel.instance.Tiros[0],(int)X+40,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -600;
		            Tiro.Tipo = 5;		    
		            
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            TempoEntreTiros = 300;
		        }else if( GamePanel.instance.PontosVida<200){
		            Sprite Tiro = new Sprite( GamePanel.instance.Tiros[1],(int)X+10,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -800;		    
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            Sprite Tiro2 = new Sprite( GamePanel.instance.Tiros[1],(int)X+80,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro2.VelX = 0;
		            Tiro2.VelY = -800;		    
		            GamePanel.instance.ListaTiros.add(Tiro2);
		            
		            TempoEntreTiros = 300;
		        }else if(GamePanel.instance.PontosVida<300){
		            Sprite Tiro = new Sprite(GamePanel.instance.Tiros[1],(int)X+10,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -800;		    
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            Sprite Tiro2 = new Sprite(GamePanel.instance.Tiros[1],(int)X+80,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro2.VelX = 0;
		            Tiro2.VelY = -800;		    
		            GamePanel.instance.ListaTiros.add(Tiro2);
		            
		            Sprite Tiro3 = new Sprite(GamePanel.instance.Tiros[1],(int)X+50,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro3.VelX = 0;
		            Tiro3.VelY = -800;		    
		            GamePanel.instance.ListaTiros.add(Tiro3);
		            
		            TempoEntreTiros = 300;
		        }else if(GamePanel.instance.PontosVida<500){
		            Sprite Tiro = new Sprite(GamePanel.instance.Tiros[2],(int)X+10,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            Sprite Tiro2 = new Sprite(GamePanel.instance.Tiros[2],(int)X+80,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro2.VelX = 0;
		            Tiro2.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro2);
		            
		            Sprite Tiro3 = new Sprite(GamePanel.instance.Tiros[2],(int)X+50,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro3.VelX = 0;
		            Tiro3.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro3);
		            
		            TempoEntreTiros = 200;
		        }else{
		            Sprite Tiro = new Sprite(GamePanel.instance.Tiros[2],(int)X+10,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro.VelX = 0;
		            Tiro.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro);
		            
		            Sprite Tiro2 = new Sprite(GamePanel.instance.Tiros[2],(int)X+80,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro2.VelX = 0;
		            Tiro2.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro2);
		            
		            Sprite Tiro3 = new Sprite(GamePanel.instance.Tiros[2],(int)X+50,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro3.VelX = 0;
		            Tiro3.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro3);
		            
		            Sprite Tiro4 = new Sprite(GamePanel.instance.Tiros[2],(int)X,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro4.VelX = -500;
		            Tiro4.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro4);
		            
		            Sprite Tiro5 = new Sprite(GamePanel.instance.Tiros[2],(int)X+90,(int)Y-20,0,true,20,40,4,1);		    
		            Tiro5.VelX = 500;
		            Tiro5.VelY = -1000;		    
		            GamePanel.instance.ListaTiros.add(Tiro5);		            
		            
		            TempoEntreTiros = 200;		            
		        }*/
		        
		        ProximoTiro = 0;  
		    }
		}
		
		if(Atingido==false){
		    if(TimeImpac>2000){
				for(int j  = 0; j < GamePanel.instance.ListaObjetos.size();j++){
				    Sprite enemy = ((Sprite)GamePanel.instance.ListaObjetos.get(j));
				    int xenemy = (int)((Sprite)GamePanel.instance.ListaObjetos.get(j)).X-xmundo;
				    int yenemy = (int)((Sprite)GamePanel.instance.ListaObjetos.get(j)).Y-ymundo;		    
				    if(((X+Largura)>xenemy)&&(X<(xenemy+enemy.Largura))&&((Y+Altura)>yenemy)&&((Y)<(yenemy+enemy.Altura))){
				    	GamePanel.instance.ListaObjetos.remove(j);
						Sprite explosao1 = new Sprite(GamePanel.instance.Explosoes[1],xenemy+xmundo,yenemy+ymundo,0,true,100,100,4,1); 
						explosao1.VelX = 0;
						explosao1.VelY = 0;
						explosao1.FrameTime = 100;				
						GamePanel.instance.ListaExplosoes.add(explosao1);
						Sprite explosao2 = new Sprite(GamePanel.instance.Explosoes[1],(int)X+xmundo,(int)Y+ymundo,0,true,100,100,4,1); 
						explosao2.VelX = 0;
						explosao2.VelY = 0;
						explosao2.FrameTime = 100;
						GamePanel.instance.ListaExplosoes.add(explosao2);		
						
						Atingido = true;
						TimeImpac = 0;
						
						GamePanel.instance.PontosVida = 0;
						
						GamePanel.instance.Vidas--;
						GamePanel.instance.SomExplosao.ParaSom();
						GamePanel.instance.SomExplosao.TocaSom();
						
						break;
				    }
				}
		    }
		    
		    
		}else{
		    if(TimeImpac>500){
		        if(GamePanel.instance.Vidas>=0){
		            Atingido = false;
		        }
		    }
		}
		
	}

}
