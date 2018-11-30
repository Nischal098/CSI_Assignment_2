import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//Repeat Set block nonce to <next random string of visible characters>
//Until ( SHA-1(block.toString()) starts with ‘00000’ in hexa )

public class Block {
	private int index; // the index of the block in the list
	private Timestamp timestamp; // time at which transaction has been processed
	private Transaction transaction; // the transaction object
	private String nonce; // random string (for proof of work)
	private String previousHash; // (in first block, set to string of zeroes of size of complexity "00000")
	private String hash; // hash of the block (hash of string obtained from previous variables via toString() method)
	
	//Constructor for creating a new Block
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
	
	//Constructor for block you get from reading
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
	
	
	//Computes the hash and nonce for the new block created
	@SuppressWarnings("static-access")
	private void computeHash() {
		Random rand = new Random();
		String experimentalHash = null;
		Sha1 encryptor = new Sha1();	
		long trialsCounter = 0l;
		
		//infinite loop. only stops when nonce value is found
		while(true) {
			trialsCounter++;
			nonce = "";
			//takes a random string of length 5 to 10
			for(int i = 0; i <= rand.nextInt(5)+10; i++) {
				char c = (char) (ThreadLocalRandom.current().nextInt(33,127));
				nonce += c;
			}	
			try {
				//Loops to find the required hash value
				experimentalHash = encryptor.hash(toString());
				if (experimentalHash.substring(0,5).equals("00000")) {
					hash = experimentalHash;
					System.out.println("Number of Trials: " + trialsCounter);
					break;
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported Encoding Block");
			}			
		}			
	 }
	 
	//getter for index
	public int getIndex() {
		 return index;
	}
	 
	//getter for timeStamp
	public Timestamp getTimestamp() {
		 return timestamp;
	}
	 
	//getter for getTransaction
	public Transaction getTransaction() {
		 return transaction;
	}
	
	//getter for getNonce
	public String getNonce() {
		 return nonce;
	}
	 
	//getter for getPreviousHash
	public String getPreviousHash() {
		 return previousHash;
	}
	 
	//getter for getHash
	public String getHash() {
		 return hash;
	}
	 
	//To String Method
	public String toString() {	 
		 return timestamp.toString() + ":" + transaction.toString() + "." + nonce+ previousHash;
	}	
}