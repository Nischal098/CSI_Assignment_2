import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BlockChain{
	//Repeat Set block nonce to <next random string of visible characters>
	 //Until ( SHA-1(block.toString()) starts with ‘00000’ in hexa )
	
	ArrayList<Block> arrayList;
	public BlockChain(ArrayList<Block> fileBlocks){
		arrayList = fileBlocks;
	}
	
	public static void main(String[] args) {
		String inputFile;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the File Name to read: ");
		inputFile = scan.next();
		
		BlockChain returnRead = fromFile(inputFile + ".txt");
	}
	
	public static BlockChain fromFile(String fileName) {
		//add into array list
		File file = new File(fileName);
		BufferedReader reader;
		ArrayList<String> textFileInputs = new ArrayList<String>();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String info;
			while ((info = reader.readLine()) != null) {
				textFileInputs.add(info);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		
		for(int i = 0; i<= textFileInputs.size()-1; i+= 7) {
			//System.out.println(textFileInputs.get(i));
			
		}
		return null;
	}
	
	public void toFile(String fileName) {
		
	}
}