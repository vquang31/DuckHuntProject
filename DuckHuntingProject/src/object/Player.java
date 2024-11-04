
package object;

/**
 *
 * @author vvqua
 */
public class Player {
    public int score  ;
    public int quantityBullet ;
    
    public Player(){
        score = 0;
        quantityBullet = 3;
    }
    
    public void fire(){
        quantityBullet -= 1;
    }
    
    public void reload(){
        quantityBullet = 3;
    }
}
