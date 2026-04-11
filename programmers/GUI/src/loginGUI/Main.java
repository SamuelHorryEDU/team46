/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package loginGUI;
import com.formdev.flatlaf.*;
import javax.swing.UIManager;
        

/**
 *
 * @author lapis
 */
public class Main {

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        loginGUI.main(null);
        
        System.out.println("Hello World!");
    }
}
