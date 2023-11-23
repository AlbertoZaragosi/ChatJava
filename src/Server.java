
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author neong
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    
    
    
    private ServerSocket server;

    public Server(ServerSocket server) {
        this.server = server;
    }
    
    
    
    public void StartServer() {
        while (!server.isClosed()) {
            try {
                //The Program won't continue until a person comes here
                Socket socket = server.accept();
                ClientHandler h = new ClientHandler(socket);
                
                Thread thread = new Thread(h);
                thread.start();
                
                
                
                
                

            } catch (IOException ex) {
                System.err.println("There was an error with the I/O (Inputs/Outputs)");
                ex.printStackTrace();
            }
        }

    }
    
    public void closerServer(){
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket ser = new ServerSocket(1234);
            Server s = new Server(ser);
            s.StartServer();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
