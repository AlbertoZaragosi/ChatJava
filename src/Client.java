
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author neong
 */
public class Client {

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String username;
    
    public void closeAll(Socket s, BufferedReader br, BufferedWriter bw){
        //removeClient();
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

    public Client(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            closeAll(socket,br,bw);
        }

    }

}
