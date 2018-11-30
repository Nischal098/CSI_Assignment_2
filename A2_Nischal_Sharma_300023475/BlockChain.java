import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

public class BlockChain{
	
	ArrayList<Block> blockList;
	public BlockChain(ArrayList<Block> blockList){
		this.blockList = blockList;
	}
	
	public static void main(String[] args) {
		String inputFile, outputFile;
		String senderInput, receiverInput, amountInput;
		String repeatTransaction;
		boolean flag = true;
		
		//Take input from file
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the File Name to read: ");
		inputFile = scan.next();
		
		//Create BlockChain instance
		BlockChain blockListCollection = fromFile(inputFile + ".txt");
		
		//Check Validity
		if(!blockListCollection.validateBlockchain())
			System.out.println("Invalid Block Chain");
		else {
			System.out.println("\nMaking a new Transaction");
			//Start a loop for repeating transactions
			while(flag) {
				System.out.println("Enter the sender for Transaction: ");
				senderInput = scan.next();
				
				System.out.println("Enter the receiver for Transaction: ");
				receiverInput = scan.next();
				
				System.out.println("Enter the amount for Transaction: ");
				amountInput = scan.next();				
				
				//Balance Error Check
				if (blockListCollection.getBalance(senderInput)<Integer.parseInt(amountInput)) {
					System.out.println("Sender Balance is less than amount specified. Please try again");
					continue;
				}else {
					//Make New Transaction
					Transaction newTransaction = new Transaction(senderInput, receiverInput, amountInput);
					Block newBlockCreated = new Block(blockListCollection.getList().size(), newTransaction, 
							blockListCollection.getList().get(blockListCollection.getList().size()-1).getHash());
					blockListCollection.add(newBlockCreated);
					
					//Check for another transaction
					System.out.println("Do you want to make another transaction? Type yes or no");
					repeatTransaction = scan.next();
					if(repeatTransaction.equals("no")) {
						flag = false;
						break;
					}
					//Enter infinite loop till user types Yes or No
					else if(!repeatTransaction.equals("yes"))
						while(true) {
							System.out.println("Please try again. type yes or no");
							repeatTransaction = scan.next();
							
							if(repeatTransaction.equals("no")) {
								flag = false;
								break;	
							}
							else if(repeatTransaction.equals("yes")) {
								break;
							}							
						}					
				
				}
			}		
			
			//Write into File
			//Check if the added transaction is valid
			if(blockListCollection.validateBlockchain()) {
				System.out.println("Writing Into File");
				outputFile = inputFile + "_nshar082";
				
				//Write to file
				blockListCollection.toFile(outputFile + ".txt");
				System.out.println("Succesful!");
			}
			else
				System.out.println("Block Chain is Invalid after adding transactions");			
		}	
	}
	
	public static BlockChain fromFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader;
		ArrayList<String> textFileInputs = new ArrayList<String>();
		
		//Read from file using buffered Reader
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String info;
			while ((info = reader.readLine()) != null) {
				textFileInputs.add(info);
			}			
			reader.close();
			
		} catch (FileNotFoundException e) {
			//If file not found runtime closes
			System.out.println("File not Found. Restart the compiler");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error reading file");
			System.exit(1);
		}
		
		//Create a temporary Array list of blocks to add the blocks in
		ArrayList<Block> returningBlockList = new ArrayList<Block>();
		
		for(int i = 0; i<= textFileInputs.size()-1; i+= 7) {
			Transaction Transactiontemp = new Transaction(textFileInputs.get(i+2), textFileInputs.get(i+3), textFileInputs.get(i+4));	
			if(i == 0)
				returningBlockList.add(new Block(Integer.parseInt(textFileInputs.get(i)), Transactiontemp,
						textFileInputs.get(i+5), null, Long.parseLong(textFileInputs.get(i+1)), textFileInputs.get(i+6)));
			else			
				returningBlockList.add(new Block(Integer.parseInt(textFileInputs.get(i)), Transactiontemp, 
						textFileInputs.get(i+5), returningBlockList.get(returningBlockList.size()-1).getHash(), 
						Long.parseLong(textFileInputs.get(i+1)), textFileInputs.get(i+6)));				
		}
		return new BlockChain(returningBlockList);
	}
	
	public void toFile(String fileName) {
		//Write to file using Print Writer
		try {
			PrintWriter writer = new PrintWriter(fileName);
			
			for(int i = 0; i < getList().size(); i++) {
				writer.println(getList().get(i).getIndex());				
				writer.println(Long.toString(getList().get(i).getTimestamp().getTime()));
				writer.println(getList().get(i).getTransaction().getSender());
				writer.println(getList().get(i).getTransaction().getReceiver());
				writer.println(getList().get(i).getTransaction().getAmount());
				writer.println(getList().get(i).getNonce());
				writer.println(getList().get(i).getHash());
			}	
			
			writer.close();			
		} catch (IOException e) {
			System.out.println("Error writing file");
		}
	}
	
	//getter for Array List
	public ArrayList<Block> getList() {
		return blockList;
	}
	
	//Validated the block chain
	@SuppressWarnings("static-access")
	public boolean validateBlockchain() {
		Sha1 checkHash = new Sha1();
		
		//Check if the index corresponds with the current array list
		for(int i = 0; i < getList().size(); i++) {			
			if(getList().get(i).getIndex() != i) 
				return false;
		}
		
		//check Sha-1 encrpytion value you get for the current block compared with the next block's previous hash
		for(int i = 0; i < getList().size()-1; i++) {		
			try {
				if (!checkHash.hash(getList().get(i).toString()).equals(getList().get(i+1).getPreviousHash())) 
					return false;
			} catch (UnsupportedEncodingException e) {
				System.out.println("Cannot Encode Block");
			}		
		}		
		
		//Get a list of usernames
		ArrayList<String> listOfPeople = new ArrayList<String>();
		for(int i = 0; i < getList().size(); i++) {
			listOfPeople.add(getList().get(i).getTransaction().getSender());
			listOfPeople.add(getList().get(i).getTransaction().getReceiver());
		}
		
		//Remove duplicates using Linked Hash Sets
		Set<String> withoutDuplicates = new LinkedHashSet<String>(listOfPeople);
		listOfPeople.clear();
		listOfPeople.addAll(withoutDuplicates);
		
		
		//Check if balace of all the users goes to negative or not
		for(int i = 0; i < listOfPeople.size(); i++) {
			if (getBalance(listOfPeople.get(i)) < 0)
				return false;
		}
		
		return true;
	}	
	
	//Check the balance of any user with their username
	public int getBalance(String username) {
		int returnBalance = 0;
		//Bitcoin is the provider. So don't look at bitcoin balance
		if (username.equals("bitcoin"))
			return 0;
		else {
			for(int i = 0; i < getList().size(); i++) {
				if (getList().get(i).getTransaction().getSender().equals(username))
					returnBalance -= getList().get(i).getTransaction().getAmount();
				if (getList().get(i).getTransaction().getReceiver().equals(username))
					returnBalance += getList().get(i).getTransaction().getAmount();			
			}
			return returnBalance;
		}		
	}	
	
	//Adds the block in the Array list of Blocks
	public void add(Block block) {
		blockList.add(block);
	}
}
















































