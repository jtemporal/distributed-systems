public class Teste {
    
    public static void main(String args[]) {
        RequestPDU req1, req2, req3;
        ResponsePDU res;
        
        try {
            req = new ResponsePDU(1,10,20);

            // write request info
            System.out.println(req1.getOpcode());
            System.out.println(req1.getOp1());
            System.out.println(req1.getOp2());

            System.out.println(); // line jump
            
            req2 = new RequestPDU(req1.getPDUData());
            // write request info
            System.out.println(req2.getOpcode());
            System.out.println(req2.getOp1());
            System.out.println(req2.getOp2());
            
            // should give an error
            req3 = new RequestPDU(new String("ABC 1 10 20").getBytes());
            // write request info
            System.out.println(req3.getOpcode());
            System.out.println(req3.getOp1());
            System.out.println(req3.getOp2());
            
            // missing response test

        } catch(IllegalFormatException ife) {
            System.err.println("Format error");
        }
    } // main

} // class
