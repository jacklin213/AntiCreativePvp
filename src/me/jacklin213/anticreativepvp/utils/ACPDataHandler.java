package me.jacklin213.anticreativepvp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import me.jacklin213.anticreativepvp.ACP;

public class ACPDataHandler {
	
	public static ACP plugin;
	
	private File flyDataFile;
	private File godDataFile;
	
	public ACPDataHandler(File file, File file2, ACP instance) {
		this.flyDataFile = file;
		this.godDataFile = file2;
		plugin = instance;
		
		if (!(flyDataFile.exists())){
			try {
				this.flyDataFile.createNewFile();
				plugin.MSG.logInfo("Generating flymode.data file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!(godDataFile.exists())){
			try {
				this.godDataFile.createNewFile();
				plugin.MSG.logInfo("Generating godmode.data file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadData(){
		loadGodData();
		loadFlyData();
	}
	
	public void saveData(){
		saveGodData();
		saveFlyData();
	}
	
	public void loadGodData(){
		try {
			//Processes the FileData to the BufferedReader
			DataInputStream input = new DataInputStream(new FileInputStream(this.godDataFile));
			//Processes the data into Eclipse
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			//Creates a String called line
			String line;
			
			//When the string line is equal to the lines read off the file and does not = 0, repeat method.
			while ((line = reader.readLine()) != null){
				//If the staffNames ArrayList doesnt contain the name
				if(plugin.godModeEnabled.contains(line) == false){
					//This adds the name
					plugin.godModeEnabled.add(line);
				}
			}
			
			reader.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveGodData(){
		try {
			//Filewriter to write to file
			FileWriter writer = new FileWriter(this.godDataFile);
			//BufferedWriter to process data to the FileWriter
			BufferedWriter out = new BufferedWriter(writer);
			
			//for loop to save all staff names to the file
			for (String player : plugin.godModeEnabled){
				out.write(player);
				out.newLine();
			}
			
			//closes the BufferedWriter and the FileWriter to prevent data loss
			out.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFlyData(){
		try {
			//Processes the FileData to the BufferedReader
			DataInputStream input = new DataInputStream(new FileInputStream(this.flyDataFile));
			//Processes the data into Eclipse
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			//Creates a String called line
			String line;
			
			//When the string line is equal to the lines read off the file and does not = 0, repeat method.
			while ((line = reader.readLine()) != null){
				//If the staffNames ArrayList doesnt contain the name
				if(plugin.flyModeEnabled.contains(line) == false){
					//This adds the name
					plugin.flyModeEnabled.add(line);
				}
			}
			
			reader.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveFlyData(){
		try {
			//Filewriter to write to file
			FileWriter writer = new FileWriter(this.flyDataFile);
			//BufferedWriter to process data to the FileWriter
			BufferedWriter out = new BufferedWriter(writer);
			
			//for loop to save all staff names to the file
			for (String player : plugin.flyModeEnabled){
				out.write(player);
				out.newLine();
			}
			
			//closes the BufferedWriter and the FileWriter to prevent data loss
			out.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFlyDataFile() {
		return flyDataFile;
	}
	
	public File getGodDataFile() {
		return godDataFile;
	}
	
}
