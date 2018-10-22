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
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the File Name to read: ");
		inputFile = scan.next();
		
		BlockChain blockListCollection = fromFile(inputFile + ".txt");
		
		if(!blockListCollection.validateBlockchain())
			System.out.println("Invalid Block Chain");
		else {
			System.out.println("\nMaking a new Transaction");
			while(flag) {
				System.out.println("Enter the sender for Transaction: ");
				senderInput = scan.next();
				
				System.out.println("Enter the receiver for Transaction: ");
				receiverInput = scan.next();
				
				System.out.println("Enter the amount for Transaction: ");
				amountInput = scan.next();				
				
				if (blockListCollection.getBalance(senderInput)<Integer.parseInt(amountInput)) {
					System.out.println("Sender Balance is less than amount specified. Please try again");
					continue;
				}else {
					Transaction newTransaction = new Transaction(senderInput, receiverInput, amountInput);
					Block newBlockCreated = new Block(blockListCollection.getList().size(), newTransaction, 
							blockListCollection.getList().get(blockListCollection.getList().size()-1).getHash());
					blockListCollection.add(newBlockCreated);
					
					System.out.println("Do you want to make another transaction? Type yes or no");
					repeatTransaction = scan.next();
					if(repeatTransaction.equals("no")) {
						flag = false;
						break;
					}
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
			System.out.println("Writing Into File");
			outputFile = inputFile + "_nshar082";
			
			blockListCollection.toFile(outputFile + ".txt");
			System.out.println("Succesful!");
		}	
	}
	
	public static BlockChain fromFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader;
		ArrayList<String> textFileInputs = new ArrayList<String>();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String info;
			while ((info = reader.readLine()) != null) {
				textFileInputs.add(info);
			}			
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		
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
	
	public ArrayList<Block> getList() {
		return blockList;
	}
	
	@SuppressWarnings("static-access")
	public boolean validateBlockchain() {
		Sha1 checkHash = new Sha1();
		
		for(int i = 0; i < getList().size(); i++) {			
			if(getList().get(i).getIndex() != i) 
				return false;
		}
		
		for(int i = 0; i < getList().size()-1; i++) {		
			try {
				if (!checkHash.hash(getList().get(i).toString()).equals(getList().get(i+1).getPreviousHash())) 
					return false;
			} catch (UnsupportedEncodingException e) {
				System.out.println("Cannot Encode Block");
			}		
		}		
		
		ArrayList<String> listOfPeople = new ArrayList<String>();
		for(int i = 0; i < getList().size(); i++) {
			listOfPeople.add(getList().get(i).getTransaction().getSender());
			listOfPeople.add(getList().get(i).getTransaction().getReceiver());
		}
		
		//Remove duplicates using Linked Hash Sets
		Set<String> withoutDuplicates = new LinkedHashSet<String>(listOfPeople);
		listOfPeople.clear();
		listOfPeople.addAll(withoutDuplicates);
		
		
		for(int i = 0; i < listOfPeople.size(); i++) {
			if (getBalance(listOfPeople.get(i)) < 0)
				return false;
		}
		
		return true;
	}	
	
	public int getBalance(String username) {
		int returnBalance = 0;
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
	
	public void add(Block block) {
		blockList.add(block);
	}
}
















































