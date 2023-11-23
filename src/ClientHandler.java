
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author neong
 */
public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList();
    
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            username = br.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + username + " Joined the chat");
        } catch (Exception e) {
            closeAll(socket, br, bw);
        }
    }

    @Override
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = br.readLine();
                broadcastMessage(message);

            } catch (Exception e) {
                closeAll(socket, br, bw);
                break;
            }
        }
    }

    public void broadcastMessage(String s) {
        for (ClientHandler c : clientHandlers) {
            try {
                if (!c.username.equals(this.username)) {
                    c.bw.write(s);//Writes to everyone
                    c.bw.newLine();//Tells the program that already finished writing
                    c.bw.flush();//Sends the messages
                }
            } catch (Exception e) {
                closeAll(socket, br, bw);
            }
        }

    }
    public void removeClient(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+username+" has left the chat");
    }
    
    public void closeAll(Socket s, BufferedReader br, BufferedWriter bw){
        removeClient();
        try {
            if (br !=null) {
                br.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
