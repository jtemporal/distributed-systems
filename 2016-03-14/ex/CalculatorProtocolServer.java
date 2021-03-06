/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.*;

public class CalculatorProtocolServer extends Thread{

    // declaring variables
    private DatagramSocket datagram;            // Socket datagram

    /** Creates a new instance of CalculatorProtocolServer */
    public CalculatorProtocolServer( int portNumber) {
        try{
            datagram = new DatagramSocket(portNumber);
        }catch(IOException se){
            System.err.println("Not possible to initialize protocol: "+se);
            System.exit(0);
        }
    }

    public void run(){
        DatagramPacket reqPacket = null;        // Datagram for sending
        DatagramPacket responsePacket = null;   // Datagram for receiving

        byte[] buf;                             // Buffer used to store data
        ResponsePDU respPdu = null;             // ResponsePDU
        RequestPDU reqPdu = null;               // RequestPDU
        int op1, op2, result = 0, respCode = 0;

        while (true){ // check for incoming packets from network

            buf = new byte[128];

			// try receive request PDU
            try{ 
                reqPacket = new DatagramPacket(buf, buf.length);
                datagram.receive(reqPacket);

                // extracts pdu
                try {
                    RequestPDU reqPdu = new RequestPDU(reqPacket.getData());
                } catch(IllegalFormatException ife) {
                    System.err.println(ife);
                    System.exit(0);
                }
                op1 = reqPdu.getOp1();
                op2 = reqPdu.getOp2();

                // extracts info
                
                
                // check request
                switch(reqPdu.getOpcode()){
                    case 0: // calculate add
                        result = op1 + op2;
                        respCode = 1;
                        break;
                    case 1: // calculate sub
                        result = op1 - op2;
                        respCode = 1;
                        break;
                    case 2: // calculate times
                        result = op1 * op2;
                        respCode = 1;
                        break;
                    case 3: // calculate div
                        if ( op2 != 0) {
                            result = op1/op2;
                            respCode = 1;
                        } else {
                            result = 0;
                            respCode = 0;
                        }
                        break;
                }

                // create response PDU
                try { 
                    RequestPDU reqPdu = new ResponsePDU(respCode, result);
                } catch(IllegalFormatException ife) {
                    System.err.println(ife);
                    System.exit(0);
                }

                // create response packet
                responsePacket = new DatagramPacket(respPdu.getPDUData(), 
                        respPdu.getPDUData().length, reqPacket, getAdress(),
                        respPdu.getPort());

                // send response packet
                try {
                    datagram.send(responsePacket);
                } catch(IOException ioe) {
                    System.err.println("Coud not send result");
                }
            } catch (IOException ioe){
                System.err.println("Could not receive data: "+ioe);
            }
        } //while
    }
}
