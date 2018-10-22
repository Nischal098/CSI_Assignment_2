import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Random;

//Repeat Set block nonce to <next random string of visible characters>
//Until ( SHA-1(block.toString()) starts with ‘00000’ in hexa )

public class Block {
	private int index; // the index of the block in the list
	private Timestamp timestamp; // time at which transaction has been processed
	private Transaction transaction; // the transaction object
	private String nonce; // random string (for proof of work)
	private String previousHash; // (in first block, set to string of zeroes of size of complexity "00000")
	private String hash; // hash of the block (hash of string obtained from previous variables via toString() method)
	
	//Creating a new Block
	public Block(int index, Transaction transaction, String previousHash){		
		 this.index = index;
		 this.transaction = transaction;
		 
		 if(index == 0) 
			 this.previousHash = "00000";
		 else
			 this.previousHash = previousHash;	
		 
		 timestamp = new Timestamp(System.currentTimeMillis());
		 
		 computeHash();
	 }
	
	//Block you get from reading
	public Block(int index, Transaction transaction, String nonce, String previousHash, long timestamp, String hash){
		 this.index = index;
		 this.transaction = transaction;
		 this.nonce = nonce;
		 if(index == 0) 
			 this.previousHash = "00000";
		 else
			 this.previousHash = previousHash;
		 this.timestamp = new Timestamp(timestamp);
		 this.hash = hash;
	 }
	
	
	private void computeHash() {
		Random rand = new Random();
		String experimentalNounce = null;
		Sha1 encryptor = new Sha1();		
		
		
		for(int i = 0; i <= rand.nextInt(15)+4; i++) {
			char c = (char) (rand.nextInt(126) + 33);
			experimentalNounce += c;
		}		
		
	 }
	 
	public int getIndex() {
		 return index;
	}
	 
	public Timestamp getTimestamp() {
		 return timestamp;
	}
	 
	public Transaction getTransaction() {
		 return transaction;
	}
	 
	public String getNonce() {
		 return nonce;
	}
	 
	public String getPreviousHash() {
		 return previousHash;
	}
	 
	public String getHash() {
		 return hash;
	}
	 
	public String toString() {	 
		 return timestamp.toString() + ":" + transaction.toString() + "." + nonce+ previousHash;
	}	
}