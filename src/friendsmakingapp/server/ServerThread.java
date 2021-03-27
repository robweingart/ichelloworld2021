package friendsmakingapp.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread{

    private Socket socket;

    private GameSession session;

    public ObjectOutputStream output;

    public String OutputString = "";

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void setSession(GameSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            output = new ObjectOutputStream(socket.getOutputStream());

            while (true){
                String message = input.readLine();
                session.addToChat(message);
            }

        } catch (Exception e){

        }

        //super.run();
    }
}
