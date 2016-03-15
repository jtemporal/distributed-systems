/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class ResponsePDU {
    // Data declaration
    private String pduData;
    private String[] elements;
    private int respcode;
    private int value;

    /** Creates a new instance of RequestPDU */
    public ResponsePDU(int respcode, int valor) throws IllegalFormatException{

        // check opcode
        if (respcode < 0 || respcode > 1){ // error
            throw new IllegalFormatException();
        } else{
            // create pdu
            this.respcode = respcode;
            this.value = value;

            pduData = new String("RSPPDU " + respcode + " " + value);
        }
    }

    /** Creates a new instance of RequestPDU from an array of bytes */
    public ResponsePDU(byte[] data) throws IllegalFormatException{

        pduData = new String(data);

        // parse PDU code
        elements = pduData.split(" ");
        
		// check pdu format 
		if (!elements[0].equals("RSPPDU")) {
            throw new IllegalFormatException();
        } else {
            try {
               respcode = Integer.parseInt(elements[1].trim());
               if (respcode < 0 || respcode > 3) { // respcode error
                   System.err.println("invalid code: " + respcode);
                   throw new IllegalFormatException();
               } else { // value parse
                   value = Integer.parseInt(elements[2]);
               }
            } catch(NumberFormatException nfe) {
                System.err.println(nfe);
                throw new IllegalFormatException();
            }
        }
    }

    public int getRespcode(){
        return respcode;
    }

    public int getResult(){
        return value;
    }

    public byte[] getPDUData(){
        return pduData.getBytes();
    }
}
