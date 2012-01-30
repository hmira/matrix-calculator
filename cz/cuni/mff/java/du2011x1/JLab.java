package cz.cuni.mff.java.du2011x1;


import java.io.*;
//import java.util.*;

/** DU 1. 
  * 
  * @author Peter Hmira
  */
public class JLab {

	
  public static void main(String[] argv) throws IOException {
	 /* File inputFile = new File("input.txt");
	  InputStreamReader isr = null;
	  try {
		isr = new InputStreamReader( new FileInputStream(inputFile));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	  
	
	  /*
	   * Equation reader, parses input
	   * */
	  EquationReader er = new EquationReader();
	  try {
		er.Read(false);
		
		if (er.Value instanceof Matrix)
		  ((Matrix)er.Value).Print();
		else if (er.Value instanceof Double)
			System.out.printf("%.5f\n",er.Value);
	} catch (Exception e) {
		System.out.printf("CHYBA\n");
	}
	  
    /* VZOR: kod, ktery opisuje data ze standardniho vstupu na standardni vystup
     * a konci pokud narazi na konec vstupu.
     *
     *
     *  try {
     *    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
     *    int c;
     *    while ((c = input.read()) != -1) {
     *      System.out.print((char) c);
     *    }
     *  } catch (IOException ex) {
     *    System.err.println("Nastala IOException");
     *  }
     *
     */

  }
}



