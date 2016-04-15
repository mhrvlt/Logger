package com.main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Index {

	public static void main(String[] args)  throws IOException { 
		File folder = new File("C:/Users/MValotia/Downloads");
		for(final File file : folder.listFiles()){
			Scanner sc1 = new Scanner(file);
			while(sc1.hasNextLine()){  // loop to 
				Scanner sc2 = new Scanner(sc1.nextLine());
			}
			
		}

	}
	
	
	
	
	

}
