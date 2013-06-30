package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Andrey
 */
class ClientConn implements Runnable {

    private BufferedReader in = null;
    private Socket socket;

    ClientConn(Socket client) {
        socket = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            ServerApplication.ServApp.printMsgOnScreen("IOException" + e);
            //System.err.println(e);
        }
    }

    public void run() {
        String msg;
        try {
                ServerApplication.ServApp.addClienToList(Thread.currentThread(),socket);
            } catch (IOException ex) {
                ServerApplication.ServApp.printMsgOnScreen("IOException" + ex);
                //Logger.getLogger(ServerConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        //ServerApplication.ServApp.putClientOnLine(Thread.currentThread().getName());
        try {
            while (!"TERMINATE".equals(msg = in.readLine()) && (msg != null)) {
                if (msg.equals("<<REFRESH>>")){
                    ServerApplication.ServApp.getOnLineClients(Thread.currentThread().getName());
                }
                else if (msg.startsWith("<<")){
                    if(ServerApplication.ServApp.CHECK(msg)){
                        ServerApplication.ServApp.printMsgOnScreen("TRUE");
                    }
                    else ServerApplication.ServApp.printMsgOnScreen("FALSE");
                    
                }
                else ServerApplication.ServApp.printMsgOnScreen("Client Thread: (" + Thread.currentThread().getName() + ")MSG: " + msg);
                //System.out.println("Client Thread: (" + Thread.currentThread().getName() + ")MSG: " + msg);
            }
            ServerApplication.ServApp.putClientOffLine(Thread.currentThread().getName());
        } catch (IOException e) {
            ServerApplication.ServApp.printMsgOnScreen("IOException" + e);
            ServerApplication.ServApp.remClientFromList(Thread.currentThread());
            ServerApplication.ServApp.putClientOffLine(Thread.currentThread().getName());
            //System.err.println(e);
        }
    }
}
