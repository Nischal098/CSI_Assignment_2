public class Transaction {
	private String sender;
	private String receiver;
	private int amount;
	
	//Constructor for the transaction
	public Transaction(String sender, String receiver, String amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = Integer.parseInt(amount);
	}
	
	//Getter for sender
	public String getSender() {
		return sender;
	}
	
	//Getter for receiver
	public String getReceiver() {
		return receiver;
	}
	
	//Getter for amount
	public int getAmount() {
		return amount;
	}
	
	//toString Method
	public String toString() {
		return sender + ":" + receiver + "=" + amount;
	}
}