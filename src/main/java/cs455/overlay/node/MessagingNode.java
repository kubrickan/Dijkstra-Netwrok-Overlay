package cs455.overlay.node;

import cs455.overlay.transport.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MessagingNode implements Node{

    //TODO DISABLE DEBUG TOGGLE
    private boolean debug = true;

    //Registry's network information
    private String  REGISTRY_HOST;
    private Integer REGISTRY_PORT;

    //MessengerNode's network information
    private String  NODE_HOST;
    private Integer NODE_PORT;

    //CONSTRUCTOR
    private MessagingNode(String REGHOST, int REGPORT){
        this.REGISTRY_HOST = REGHOST;
        this.REGISTRY_PORT = REGPORT;

        //Initializes the TCPServerThread
        try {
            //acquire available tcp port
            this.NODE_HOST = InetAddress.getLocalHost().getHostName();
            this.NODE_PORT = Node.acquirePORT();

            //create/initialize server thread
            Thread newServerThread = new Thread(new TCPServerThread(NODE_PORT, this));
            newServerThread.start();

            if(debug) {
                System.out.println("INITIALIZED MESSAGING NODE");
                System.out.println("NODE_HOST: " + NODE_HOST);
                System.out.println("NODE_PORT: " + NODE_PORT);
                System.out.println();
            }

            //USER COMMAND INPUT
            while(true){
                String in;
                Scanner scanner = new Scanner(System.in);
                in = scanner.nextLine();
                switch (in) {
                    case "print-shortest-path":
                        System.out.println("tat");
                        break;
                    case "exit-overlay":
                        System.out.println("tat");
                        break;
                    default:
                        System.out.println("command not recognized");
                        break;
                }
                scanner.close();
            }

        } catch (IOException e){
            System.out.println("Registry::failed_starting_server_thread:: " + e);
            System.exit(1);
        }
    }

    //First Arg  = registry's Host address
    //Second Arg = registry's port number
    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("INCORRECT ARGUMENTS FOR MESSENGER NODE");
            return;
        }
        //Reroutes arguments to MessagingNode's Constructor
        new MessagingNode(args[0], Integer.parseInt(args[1]));
    }


    //getters
    public String getAddr() { return this.NODE_HOST; }
    public int    getPort() { return this.NODE_PORT; }
    public String getRegAddr() { return this.REGISTRY_HOST; }
    public int    getRegPort() { return this.REGISTRY_PORT; }
}
