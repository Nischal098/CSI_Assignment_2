public class Block {
 private int index; // the index of the block in the list
 private java.sql.Timestamp timestamp; // time at which transaction
 // has been processed
 private Transaction transaction; // the transaction object
 private String nonce; // random string (for proof of work)
 private String previousHash; // previous hash (set to "" in first block)
 //(in first block, set to string of zeroes of size of complexity "00000")
 private String hash; // hash of the block (hash of string obtained
//from previous variables via toString() method)
//...
 public String toString() {
 return timestamp.toString() + ":" + transaction.toString()
 + "." + nonce+ previousHash;
}
}