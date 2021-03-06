package server.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Andrey
 */
public class ServerConn implements Runnable {
    private ServerSocket server;
    private Socket client = null;
    
    
    public ServerConn(ServerSocket server) {
        this.server = server;
        
    }

    @Override
    public void run() {
        while(true) {
            try {
                client = this.server.accept();
            } catch (IOException e) {
                ServerApplication.ServApp.printMsgOnScreen("Accept failed."+"\r\n"+e);
                //System.exit(1);
            }
            Thread t = new Thread(new ClientConn(client));
            t.start();
            
            
            
        }
    }
    
}
