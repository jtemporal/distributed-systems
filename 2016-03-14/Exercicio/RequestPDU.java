/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class RequestPDU {
    // Data declaration
    private String pduData;
    private String[] elements;
    private int opcode;
    private int op1;
    private int op2;

    /** Creates a new instance of RequestPDU */
    public RequestPDU(int opcode, int op1, int op2) throws IllegalFormatException{

        // check opcode
        if (opcode < 0 || opcode > 3){ // error
            throw new IllegalFormatException();
        } else{
            // create new pdu
            this.opcode = opcode;
            this.op1 = op1;
            this.op2 = op2;

            pduData = new String("RQTPDU " + opcode + " " + op1 + " " + op2);
        }
    }

    /** Creates a new instance of RequestPDU from an array of bytes */
    public RequestPDU(byte[] data) throws IllegalFormatException{

        pduData = new String(data);

        // parse PDU 
        elements = pduData.split(" "); 
        // our elemnts are separated with <" "> hence the split
        
		// check pdu format
        if (!elements[0].equals("RQTPDU")) {
            throw new IllegalFormatException();
        } else {
             try {
                opcode = Integer.parseInt(elements[1].trim());
                // trim will guarantee that extra <" "> are deleted 
                if (opcode < 0 || opcode > 3) { // opcode err
                    System.err.println("invalid code: " + opcode);  
                    throw new IllegalFormatException();
                } else { // op parse
                    op1 = Integer.parseInt(elements[2].trim());
                    op2 = Integer.parseInt(elements[3].trim());
                }
             } catch {
                System.err.println(nfe);
                throw new IllegalFormatException();
             }
        }
    }

    public int getOpcode(){
        return opcode;
    }

    public int getOp1(){
        return op1;
    }

    public int getOp2(){
        return op2;
    }

    public byte[] getPDUData(){
        return pduData.getBytes();
    }
}
