/*
 * Created on 05/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * @author Palm Soft
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Som {
    AudioClip clip;
    public Som(String name){
        clip = Applet.newAudioClip(getClass().getResource(name));        
    }
    
    public void TocaSom(){
        clip.play();        
    }
    public void TocaSomLoop(){
        clip.loop();        
    }
    
    public void ParaSom(){
        clip.stop();        
    }    
}
