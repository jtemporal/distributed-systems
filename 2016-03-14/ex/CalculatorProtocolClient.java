/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.*;

public class CalculatorProtocolClient extends Thread implements ServiceInterface{

    // declaring constant
    private final short TIMEOUT = 100;

    // declaring variables
    private ServiceUserInterface serviceUser;	// Service user reference
    private int currentState;                 	// Protocol entity current state

    private DatagramSocket datagram;            // Socket datagram
    private InetAddress remoteAddress;          // Remote address
    private int remotePortNumber;               // remote port number

    private RequestPDU pdu = null;  			// Request PDU

    /** Creates a new instance of UnicastProtocol */
    public CalculatorProtocolClient( int portNumber, String serverAddr, int serverPortNumber) {

        currentState = 0;
        remotePortNumber = serverPortNumber;

        try{
            remoteAddress = InetAddress.getByName(serverAddr);
            datagram = new DatagramSocket(portNumber);
        }catch(IOException se){
            System.err.println("Nao foi possivel inicializar protocolo: "+se);
            System.exit(0);
        }
    }

    public void setRef(ServiceUserInterface user){
        serviceUser = user;
    }

    public void add(int op1, int op2){
        DatagramPacket reqPacket;

        // create pdu
        try {
            pdu = new RequestPDU(0, op1, op2); // 0 for add as especified
        } catch (IllegalFormatException ife){
            System.err.println(ife);
            System.exit(0);
        }

        // create packet
        reqPacket = new DatagramPacket(pdu.getPDUData(),
                pdu.getPDUData().length, remoteAddress, remotePortNumber);

        // send packet
        try {
            datagram.send(reqPacket);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(0);
        }

        // update current state
        currentState = 1;
    }

    public void sub(int op1, int op2){
        DatagramPacket reqPacket;

        // create pdu
        try {
            pdu = new RequestPDU(1, op1, op2); // 0 for add as especificated
        } catch(IllegalFormatException ife){
            System.err.println(ife);
            System.exit(0);
        }

        // create packet
        reqPacket = new DatagramPacket(pdu.getPDUData(),
                pdu.getPDUData().length, remoteAddress, remotePortNumber);

        // send packet
        try {
            datagram.send(reqPacket);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(0);
        }

        // update current state
        currentState = 1;       
    }
    
    public void times(int op1, int op2){
        DatagramPacket reqPacket;

        // create pdu
        try {
            pdu = new RequestPDU(2, op1, op2); // 0 for add as especificated
        } catch(IllegalFormatException ife){
            System.err.println(ife);
            System.exit(0);
        }

        // create packet
        reqPacket = new DatagramPacket(pdu.getPDUData(),
                pdu.getPDUData().length, remoteAddress, remotePortNumber);

        // send packet
        try {
            datagram.send(reqPacket);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(0);
        }

        // update current state
        currentState = 1;
    }

    public void div(int op1, int op2){
        DatagramPacket reqPacket;

        // create pdu
        try {
            pdu = new RequestPDU(3, op1, op2); // 0 for add as especificated
        } catch(IllegalFormatException ife){
            System.err.println(ife);
            System.exit(0);
        }

        // create packet
        reqPacket = new DatagramPacket(pdu.getPDUData(),
                pdu.getPDUData().length, remoteAddress, remotePortNumber);

        // send packet
        try {
            datagram.send(reqPacket);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(0);
        }

        // update current state
        currentState = 1;
    }

    public void run(){
        DatagramPacket reqPacket = null;        // Datagram for sending
        DatagramPacket responsePacket = null;   // Datagram for receiving

        byte[] buf;                             // Buffer used to store data
        ResponsePDU respPdu = null;             // ResponsePDU

        while (true){ // check for incoming packets from network
             // Ckeck protocol current state
            switch (currentState){
                case 0:
					// sleep
                    try {
                        sleep(100);
                    } catch(InterruptedException ie) {
                        System.err.println(ie);
                    }
                    break;
                case 1:
                    buf = new byte[128];

                    // try receive Response PDU
                    try{
                        // set timer
                        datagram.setSoTimeout(TIMEOUT);
                        responsePacket = new DatagramPacket(buf, buf.length);
                        datagram.receive(responsePacket);

                        // extracts pdu
                        try {
                            respPdu = new ResponsePDU(responsePacket.getData());
                        } catch (IllegalFormatException ife) {
                            System.err.println(ife);
                            System.exit(0);
                        }

                        // check response
                        if (respPdu.getRespcode() == 1) { // success
                           serviceUser.result(respPdu.getResult());
                        } else { // fail
                            serviceUser.error();
                        }
 
                        // update state
                        currentState = 0;
                    } catch (SocketException se){
                            System.err.println("Could not set timeout");
                    } catch(SocketTimeoutException ste){
                        // timeout - retransmit
                        // create packet
                        reqPacket = new DatagramPacket(pdu.getPDUData(),
                            pdu.getPDUData().length, remoteAddress, remotePortNumber);

                        // send packet
                        try {
                            datagram.send(reqPacket);
                        } catch (IOException ioe) {
                            System.err.println(ioe);
                            System.exit(0);
                        }
                    } catch (IOException ioe){
                        System.err.println("Could not receive data: "+ioe);
                    }
                    break;
            } // switch
        } //while
    }
}
