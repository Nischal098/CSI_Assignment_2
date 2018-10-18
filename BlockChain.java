import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BlockChain{
	//Repeat Set block nonce to <next random string of visible characters>
	 //Until ( SHA-1(block.toString()) starts with ‘00000’ in hexa )
	ArrayList blocks;
	public BlockChain(ArrayList text){
		blocks = text;
	}
	
	public static void main(String[] args) {
		//first prompt user for file name
		String inputFile;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the File Name to read: ");
		inputFile = scan.next();
		
		BlockChain returnRead = fromFile(inputFile);
		
	}
	
	public static BlockChain fromFile(String fileName) {
		//add into array list
		File file = new File("\\"+fileName+".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		
		return null;
	}
	
	public void toFile(String fileName) {
		
	}
}