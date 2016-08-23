import java.awt.Graphics2D;

/**
 * @author Dennis Kerr Coelho - PalmSoft
 *
 */

public abstract class Objeto {
    
    abstract public void DesenhaSe(Graphics2D dbg,int Xmundo,int Ymundo);

    abstract public void SimulaSe(long diftime);    
    
}
