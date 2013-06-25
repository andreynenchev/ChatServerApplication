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
    
    ClientConn(Socket client) {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void run() {
        String msg;
        try {
            while (!"TERMINATE".equals(msg = in.readLine()) && (msg != null) ) {
                System.out.println("Client Thread: ("+ Thread.currentThread().getName()+ ")MSG: " + msg);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
