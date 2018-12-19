package Day01;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jesper.lindberg3
 */
import java.io.*;
public class Day01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String str = new String();
        int floor = 0;
        int baseFloor = 0;
        boolean first = true;
        
        try {
            FileReader file = new FileReader("src/day1.txt");
            BufferedReader br = new BufferedReader(file);
            str = br.readLine();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found...");
        }
        
        for(int i = 0; i < str.length();i++){
            if(str.charAt(i) == '('){
                floor++;
            }
            if(str.charAt(i) == ')'){
                floor--;
            }
            if(floor < 0 && first){
                baseFloor = i;
                first = false;
            }
        }
        System.out.println(baseFloor+1);
    }
    
    
    
}
