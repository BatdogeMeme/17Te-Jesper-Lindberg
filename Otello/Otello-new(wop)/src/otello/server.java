/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otello;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesper.lindberg3
 */
public class server {

    public static void main(String[] args) {
        
        int port = 5050;
        
        try {
            ServerSocket serverSocker = new ServerSocket(port);
            
            while(true){
                
                
                Socket clientSocket = serverSocker.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                
                ServerWorker worker = new ServerWorker(clientSocket);
                worker.start();
                
            }
            
        } catch (IOException ex) {
            System.err.println("Something happend..");
        }
        
    }
    
    
    
}
