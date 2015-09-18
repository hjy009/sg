package fuzhu.sg;

import javax.swing.UIManager;

import fuzhu.idMaker.Make;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
	    try {
		      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		    new Make();
    }
}
