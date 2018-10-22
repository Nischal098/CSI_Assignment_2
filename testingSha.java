import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;


public class testingSha {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Sha1 sha = new Sha1();
		long b = 1536150600000l;
		try {
			 Timestamp timestamp = new Timestamp(b);
			//return timestamp.toString() + ":" + transaction.toString() + "." + nonce+ previousHash;
			System.out.println(sha.hash(timestamp.toString()+":satoshi:lucia=25.ZI4b]Cg+g2Tr`fn4EB00000613d1aec0be473e97e50e2a9e9f9f3fd73c"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

}
