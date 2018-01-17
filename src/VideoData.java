
import java.io.Serializable;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp-
 */
public class VideoData extends Data implements Serializable {
    
    boolean like;

    public VideoData(int request,boolean like) {
        this.like = like;
        this.setRequest(request);
    }
    
    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
    
    
}
