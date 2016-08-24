import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


/**
 * @author Dennis Kerr Coelho - PalmSoft
 *
 */

public class GamePanel extends JPanel implements Runnable
{
	
public static GamePanel instance = null;
	
private static final int PWIDTH = 640; // size of panel
private static final int PHEIGHT = 600;
private Thread animator; // for the animation
private boolean running = false; // stops the animation
private boolean gameOver = false; // for game termination

private Graphics2D dbg;
private Image dbImage = null;

int FPS,SFPS;
long SegundoAtual = 0;
long NovoSegundo = 0;

// Atributos do Jogo
ArrayList<Objeto> ListaObjetos; 
ArrayList<Objeto> ListaTiros;
ArrayList<Objeto> ListaExplosoes;
ArrayList<Objeto> ListaParticulas;

public BufferedImage Nave;
public BufferedImage Inimigos[];
public BufferedImage Tiros[];
public BufferedImage Explosoes[];
public BufferedImage Fundo;



NavePlayer Personagem;
TileMapJSON MAPA;

Font FonteJogo = new Font(null,Font.BOLD,24);

//float x,y;
//int vel;

boolean AUP,ADOWN,ALEFT,ARIGHT,FIRE;



int Difficult;

int Pontos = 0;
int PontosVida = 0;

int Vidas = 4;

Som SomFundo;
Som SomTiro;
Som SomExplosao;


public static Random rnd =  new Random();

Canvas gui = null;
public BufferStrategy strategy = null;

float PosWorldX;
float PosWorldY;
float desloc;



public GamePanel()
{
	instance = this;
	
	setBackground(Color.white); // white background
	setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	gui = new Canvas();
	gui.setSize(WIDTH, HEIGHT);
	setLayout(new BorderLayout());
	add(gui, BorderLayout.CENTER);
	
	
	setFocusable(true); 	// create game components
	requestFocus(); // JPanel now receives key events
	
	// Adiciona um Key Listner
	gui.addKeyListener( new KeyAdapter() {
	// listen for esc, q, end, ctrl-c
		public void keyPressed(KeyEvent e)
			{ 
				TratadorDeTecladoPressed(e);
			}
		public void keyReleased(KeyEvent e)
			{ 
				TratadorDeTecladoReleased(e);
			}		
	});
	
	// Adiciona um Mouse Listner	
	gui.addMouseListener( new MouseAdapter() {
		public void mousePressed(MouseEvent e)
		{ TratadorDoMousePressed(e); }
	});	

	AUP = false;
	ADOWN = false;
	ALEFT = false;	
	ARIGHT = false;
	
	Inimigos = new BufferedImage[3];	

	    Inimigos[0] = carregaImagem("inimigo01_100x100.png");
	    Inimigos[1] = carregaImagem("inimigo02_100x100.png");
	    Inimigos[2] = carregaImagem("inimigo03_100x100.png");  
	
	
	Tiros = new BufferedImage[3];	

	    Tiros[0] = carregaImagem("ataque04.png");
	    Tiros[1] = carregaImagem("ataque02.png");
	    Tiros[2] = carregaImagem("ataque03.png");
	    	
	
	Explosoes = new BufferedImage[4];	

	    Explosoes[0] = carregaImagem("explosao20x20.png");
	    Explosoes[1] = carregaImagem("explosao100x100.png");
	    Explosoes[2] = carregaImagem("explosao200x200.png");
	    Explosoes[3] = carregaImagem("explosao20x20B.png");	    
	
	

	    Nave = carregaImagem("nave100x100.png");
	    Fundo = carregaImagem("fundo.jpg");
		

	    BufferedImage tmp = carregaImagem("spaceship.png");
	    BufferedImage tileset = new BufferedImage(tmp.getWidth(null), tmp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    tileset.getGraphics().drawImage(tmp, 0, 0, null);
	    MAPA = new TileMapJSON(tileset, 20, 20);
	    MAPA.AbreMapa("mapaNave.json");

	
	Personagem = new NavePlayer(Nave,20,20,0,false,Nave.getWidth(null),Nave.getHeight(null),1,1);
	Personagem.FrameTime = 200;
	Personagem.X = 280;
	Personagem.Y = 400;
	Personagem.vel = 400;

	
	
	ListaObjetos   = new ArrayList<Objeto>();
	ListaTiros 	   = new ArrayList<Objeto>();
	ListaExplosoes = new ArrayList<Objeto>();	
	ListaParticulas = new ArrayList<Objeto>();
	
	desloc = 0;	
		
	PosWorldY = 0;
	
	SomFundo = new Som("Crisis.mid");
	SomTiro = new Som("Attack2.wav");
	SomExplosao = new Som("Explosion5.wav");
	
//	if (dbImage == null){ // create the buffer
//		dbImage = new BufferedImage(PWIDTH, PHEIGHT,BufferedImage.TYPE_INT_ARGB);
//		if (dbImage == null) {
//			System.out.println("dbImage is null");
//			return;
//		}else{
//			dbg = (Graphics2D)dbImage.getGraphics();
//		}
//	}
	
} // end of GamePanel()


private void TratadorDoMousePressed(MouseEvent e)
{
}


public void TratadorDeTecladoPressed(KeyEvent e){ 
	int keyCode = e.getKeyCode();
	
	if ((keyCode == KeyEvent.VK_ESCAPE) ||
		(keyCode == KeyEvent.VK_Q) ||
		(keyCode == KeyEvent.VK_END) ||
		((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
			running = false;
	}
	
	
	if (keyCode == KeyEvent.VK_LEFT){
		ALEFT = true;			
	}
	if (keyCode == KeyEvent.VK_RIGHT){
		ARIGHT = true;			
	}
	if (keyCode == KeyEvent.VK_UP){
		AUP = true;			
	}
	if (keyCode == KeyEvent.VK_DOWN){
		ADOWN = true;			
	}
	
	if (keyCode == KeyEvent.VK_SPACE){
	    FIRE = true;				
	}	
	
}

public void TratadorDeTecladoReleased(KeyEvent e){
	int keyCode = e.getKeyCode();
	
	if (keyCode == KeyEvent.VK_LEFT){
		ALEFT = false;			
	}
	if (keyCode == KeyEvent.VK_RIGHT){
		ARIGHT = false;			
	}
	if (keyCode == KeyEvent.VK_UP){
	    AUP = false;			
	}
	if (keyCode == KeyEvent.VK_DOWN){
	    ADOWN = false;			
	}
	if (keyCode == KeyEvent.VK_SPACE){
	    FIRE = false;		
	}	
}


public void addNotify()
{
	super.addNotify(); // creates the peer
	startGame(); // start the thread
}


private void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()


public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	DifTime = 0;
	TempoAnterior = System.currentTimeMillis();
	SegundoAtual = (long) (TempoAnterior/1000);
	
	SomFundo.TocaSomLoop();
	
	gui.createBufferStrategy(2);
	strategy = gui.getBufferStrategy();
	
	while(running) {
		
		dbg = (Graphics2D) strategy.getDrawGraphics();
		dbg.setClip(0, 0, PWIDTH, PHEIGHT);
		dbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gameUpdate(DifTime);
		gameRender();

		dbg.dispose();
		strategy.show();		
		
				
		try {
			Thread.sleep(1); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
		NovoSegundo = (long) (TempoAnterior/1000);
			
		if(NovoSegundo!=SegundoAtual){
			FPS = SFPS;
			SegundoAtual = NovoSegundo;
			SFPS = 1;
		}else{
			SFPS++;
		}
		
	
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()


private void gameUpdate(long DiffTime)
{ 
    Personagem.ADOWN = ADOWN;
    Personagem.ALEFT  = ALEFT;
    Personagem.ARIGHT = ARIGHT;
    Personagem.AUP = AUP;
    Personagem.FIRE = FIRE;

    Personagem.SimulaSe(DiffTime);
    
	Difficult =1+(Personagem.TimeImpac/5000);	
	

	
	PosWorldY += -((20*DiffTime)/1000.0f);
	desloc += ((20*DiffTime)/1000.0f);
	
	int mapy = ((MAPA.Altura*MAPA.tileH)-20*MAPA.tileH)+((int)(PosWorldY));
	MAPA.Posiciona(0, mapy);
	
	
	if(desloc >= 96){
		int postile = (MAPA.MapY/32)-2;
		if(postile>4){
			for(int py = postile; py > (postile - 3);py--){
				for(int px = 0; px < 20;px++){
					int t = MAPA.mapa2[py][px];
					//System.out.println(" "+px+" "+py+" "+t);
					if(t >0){
					    int inimigoescolhido = rnd.nextInt(3);
					    Sprite UmInimigo = new Sprite(Inimigos[inimigoescolhido],px*32-50,py*32-50,0,false,Inimigos[inimigoescolhido].getWidth(null),Inimigos[inimigoescolhido].getHeight(null),1,1);
					    UmInimigo.VelX = 0;//-50 + rnd.nextInt(100);
					    UmInimigo.VelY = 100;//50+rnd.nextInt(50*Difficult);
					    UmInimigo.Anim = rnd.nextInt(4);
					    UmInimigo.Life = 20 + rnd.nextInt(20*Difficult);
					    
					    ListaObjetos.add(UmInimigo);	
					}
				}
			}
		}
		
		desloc = desloc-96;
		
		for(int i = 0; i < 200;i++){
			ListaParticulas.add(new Particula(rnd.nextInt(PWIDTH), (int)PosWorldY-rnd.nextInt(1000),0,rnd.nextInt(500)));
		}
	}	
	// Simula Lista de Tiros
	for(int i  = 0; i < ListaTiros.size();i++){
	    if(((Sprite)ListaTiros.get(i)).Y < -60){
	        ListaTiros.remove(i);
	        i--;
	    }else{
	        ((Sprite)ListaTiros.get(i)).SimulaSe(DiffTime);
	        if(((Sprite)ListaTiros.get(i)).Tipo == 5){
				Sprite explosao = new Sprite(Explosoes[3],(int)((Sprite)ListaTiros.get(i)).X,(int)((Sprite)ListaTiros.get(i)).Y+40,0,true,20,20,8,1); 
				explosao.VelX = 0;
				explosao.VelY = 0;
				explosao.FrameTime = 80;
				ListaExplosoes.add(explosao);		            
	        }
	    }
	}
	
	// Simula Lista de Objetos
	for(int i  = 0; i < ListaObjetos.size();i++){
//	    if(((Sprite)ListaObjetos.get(i)).Y > 650){
//	        ListaObjetos.remove(i);
//	        i--;
//	    }else{
	        ((Sprite)ListaObjetos.get(i)).SimulaSe(DiffTime);
	   // }
	}
	
	for(int i  = 0; i < ListaExplosoes.size();i++){
	    Sprite explosao = ((Sprite)ListaExplosoes.get(i));
	    
	    explosao.SimulaSe(DiffTime);
	    
	    if((explosao.Y < -60)||(explosao.Tempo >= ((explosao.FrameTime*explosao.NFrames)))){
	        ListaExplosoes.remove(i);
	        i--;
	    }
	}	
	
	// Calcula Colisão Tiros
	for(int i  = 0; i < ListaTiros.size();i++){
	    Sprite otiro = ((Sprite)ListaTiros.get(i));
	    int xtiro = (int)((Sprite)ListaTiros.get(i)).X;
	    int ytiro = (int)((Sprite)ListaTiros.get(i)).Y;
	    //System.out.println(" xtiro "+xtiro+" ytiro "+ytiro);
	    
		for(int j  = 0; j < ListaObjetos.size();j++){
		    Sprite enemy = ((Sprite)ListaObjetos.get(j)); 
		    int xenemy = (int)((Sprite)ListaObjetos.get(j)).X;
		    int yenemy = (int)((Sprite)ListaObjetos.get(j)).Y;
		    if(((xtiro+otiro.Largura)>xenemy)&&(xtiro<(xenemy+enemy.Largura))&&((ytiro+otiro.Altura)>yenemy)&&((ytiro)<(yenemy+enemy.Altura))){
		        ListaTiros.remove(i);
		        i--;
				Sprite explosao = new Sprite(Explosoes[0],xtiro,ytiro,0,true,20,20,4,1); 
				explosao.VelX = 0;
				explosao.VelY = -100;
				explosao.FrameTime = 50;
				ListaExplosoes.add(explosao);	
				
				enemy.Life -= 25;
				
				if(enemy.Life <= 0){
				    ListaObjetos.remove(j);
					Sprite explosao2 = new Sprite(Explosoes[1],xenemy,yenemy,0,true,100,100,4,1); 
					explosao2.VelX = 0;
					explosao2.VelY = 0;
					explosao2.FrameTime = 100;
					ListaExplosoes.add(explosao2);
					
					Pontos+= 10;
					PontosVida+=10;
					SomExplosao.ParaSom();
					SomExplosao.TocaSom();
				}
				
		        break;
		    }
		}	    
	}
	
	// Simula Lista de Particulas
	for(int i  = 0; i < ListaParticulas.size();i++){
	    if(((Particula)ListaParticulas.get(i)).y > (PosWorldY+680)){
	    	ListaParticulas.remove(i);
	        i--;
	    }else{
	        ((Particula)ListaParticulas.get(i)).SimulaSe(DiffTime);
	    }
	}
	
	
}


private void gameRender()
// draw the current frame to an image buffer
{	
	// clear the background
	//dbg.setColor(Color.black);
	//dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
	//MAPA.DesenhaSe(dbg);
			// draw game elements
	int x = (int)(PosWorldX/2);
	int y = (int)(PosWorldY/2)+3000;
	dbg.drawImage(Fundo,-x,-y,null);
	//System.out.println(" x "+x+" y "+y+" "+ListaParticulas.size());
	
	MAPA.DesenhaSe(dbg);
		
	if(Vidas>=0){
		dbg.setColor(Color.WHITE);	
		dbg.setFont(FonteJogo);
		dbg.drawString("FPS: "+FPS+" "+ListaObjetos.size()+" "+ListaTiros.size()+" "+ListaExplosoes.size()+"   "+Pontos+"  "+PontosVida+"  "+Difficult+" "+Vidas, 20, 20);	
	
		dbg.setColor(Color.BLUE);	
		
		if(Personagem.Atingido==false){
		    Personagem.DesenhaSe(dbg);
		    if(Personagem.TimeImpac< 2000){
		        dbg.setColor(Color.CYAN);
		        dbg.drawOval((int)Personagem.X,(int)Personagem.Y,Personagem.Largura,Personagem.Altura);
		    }
		}
		
		for(int i  = 0; i < ListaObjetos.size();i++){
		    ((Sprite)ListaObjetos.get(i)).DesenhaSe(dbg,MAPA.MapX,MAPA.MapY);
		}
		for(int i  = 0; i < ListaTiros.size();i++){
		    ((Sprite)ListaTiros.get(i)).DesenhaSe(dbg,MAPA.MapX,MAPA.MapY);
		}	
		
		for(int i  = 0; i < ListaExplosoes.size();i++){
		    ((Sprite)ListaExplosoes.get(i)).DesenhaSe(dbg,MAPA.MapX,MAPA.MapY);
		}
		
		for(int i  = 0; i < ListaParticulas.size();i++){
		    ((Particula)ListaParticulas.get(i)).DesenhaSe(dbg,(int)PosWorldX,(int)PosWorldY);
		}
	}else{
		dbg.setColor(Color.WHITE);	
		dbg.setFont(FonteJogo);
		dbg.drawString("Fim de Jogo", 250, 310);	
		dbg.drawString("Você Fez: "+Pontos+" Pontos", 200, 340);		
	}
	
	
	
} // end of gameRender()


//public void paintComponent(Graphics g)
//{
//	super.paintComponent(g);
//	if (dbImage != null)
//		g.drawImage(dbImage, 0, 0, null);
//	
//}


public static void main(String args[])
{
	GamePanel ttPanel = new GamePanel();

  // create a JFrame to hold the timer test JPanel
  JFrame app = new JFrame("Swing Timer Test");
  app.getContentPane().add(ttPanel, BorderLayout.CENTER);
  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  app.pack();
  app.setResizable(false);  
  app.setVisible(true);
} // end of main()

public BufferedImage carregaImagem(String filename){
	
	try {
		BufferedImage imgtmp = ImageIO.read( getClass().getResource(filename) );
		BufferedImage imgfinal = new BufferedImage(imgtmp.getWidth(), imgtmp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		imgfinal.getGraphics().drawImage(imgtmp, 0, 0, null);
		return imgfinal;
	} catch (IOException e) {
		e.printStackTrace();
		return null;
	}
}


} // end of GamePanel class

