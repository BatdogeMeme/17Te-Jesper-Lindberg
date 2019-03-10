/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesper.lindberg3
 */
public class ServerWorker extends Thread{

    private final Socket clientSocket;
    
    public ServerWorker(Socket clientSocket) {
        
        this.clientSocket = clientSocket;
        
    }
    
    
    
    @Override
    public void run(){
        try{
           handleClientSocket(); 
        }catch(IOException e){
            
        } catch (InterruptedException ex) {
           
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException{
        
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        
        
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        
        while( (line = reader.readLine()) != null){
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            String msg = "You typed " + line + "\n";
            outputStream.write(msg.getBytes());
        }
        
        
        clientSocket.close();
    }
    
    
}
