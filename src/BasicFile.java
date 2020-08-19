import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BasicFile {
	
	private File f;
	static String path;

	
	public void openFile(JFileChooser choose)
	{
		int status = choose.showOpenDialog(null); //opens the JFileChooser window
		
		try
		{
			if(status == JFileChooser.APPROVE_OPTION) 
			
			f = choose.getSelectedFile();
			
			else throw new IOException();

			if(!f.exists())
			{
				throw new FileNotFoundException();
			}
			
			display(f.getName(), "File has been chosen", JOptionPane.INFORMATION_MESSAGE);
			path = f.getPath();
			
		}
		catch (FileNotFoundException e)
		{
			display("File not found ...", e.toString(), JOptionPane.WARNING_MESSAGE);
		}
		catch(IOException e) //in case no file is selected
		{
			display("No file was selected", e.toString(), JOptionPane.ERROR_MESSAGE);

		}
	}
	
	static void display(String msg, String s, int t) {
		JOptionPane.showMessageDialog(null, msg, s, t);
		
	}
	
	public void copyFile(JFileChooser choose) throws IOException
    {
        File newFile;
        DataInputStream inputStream;
        DataOutputStream outputStream;
        
        choose.showSaveDialog(null);       
        newFile = choose.getSelectedFile();
 
        newFile.createNewFile();

        inputStream = new DataInputStream(new FileInputStream(f));
        outputStream = new DataOutputStream(new FileOutputStream(newFile));

        byte[] buffer = new byte[inputStream.available()];
        for (byte b : buffer)
        {
            outputStream.writeByte(inputStream.readByte());
        }
    }
	
	public void appendFile(JFileChooser choose) throws IOException
    {
        File newFile;
        FileWriter appender;
        DataInputStream inputStream = new DataInputStream(new FileInputStream(f));
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder textToAppend = new StringBuilder();
        
        String line = buffReader.readLine();
        
        while(line != null)
        {
            textToAppend.append(line + "\n");
            line = buffReader.readLine();
        }
        
        choose.showSaveDialog(null);
        newFile = choose.getSelectedFile();
        appender = new FileWriter(newFile, true); //second parameter is true in order to append the file
        
        appender.write(textToAppend.toString());
        appender.close();
    }
	
	public void overwriteFile(JFileChooser choose) throws IOException
    {
		File newFile;
        FileWriter appender;
        DataInputStream inputStream = new DataInputStream(new FileInputStream(f));
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder textToAppend = new StringBuilder();
        
        String line = buffReader.readLine();
        
        while(line != null)
        {
            textToAppend.append(line + "\n");
            line = buffReader.readLine();
        }
        
        choose.showSaveDialog(null);
        newFile = choose.getSelectedFile();
        appender = new FileWriter(newFile, false); //second parameter is false in order to overwrite the file
        
        appender.write(textToAppend.toString());
        appender.close();
    }
    
    public String fileAttributes() throws FileNotFoundException, IOException
    {
        StringBuilder attributes = new StringBuilder();
        
        attributes.append("Absolute File Path: " + "\"" + f.getAbsolutePath() + "\"\n");
        attributes.append("File Size: " + Math.round(f.length()/1024.0) + " KB\n");
        attributes.append("Lines: " + getNumOfLines() + "\n\n");
        attributes.append(listFiles() + "\n");
        
        return attributes.toString();
    }
    
    private String listFiles()
    {
        StringBuilder ls = new StringBuilder();
        File rootDirectory = f.getParentFile();
        File[] arrayOfFiles = rootDirectory.listFiles();
        
        for (File i : arrayOfFiles)
        {
            if (i.isFile())
            {
                ls.append("File:\t" + i.getName() + "\n");
            }
            else if (i.isDirectory())
            {
                ls.append("Directory:\t" + i.getName() + "\n");
            }
        }
        return ls.toString();
    }
    
    public String locateText(String text) throws FileNotFoundException, IOException
    {       
        StringBuilder allLines = new StringBuilder();

        String contents = new String(Files.readAllBytes(Paths.get(path)));
        
        String lines[] = contents.split("\n");
        
        allLines.append(searchText(lines, text, 0)); //accesses private recursive method to locate all lines containing the text
        return allLines.toString();
    }
    
    //private recursive method to locate the text
    private String searchText(String[] s, String search, int index) throws FileNotFoundException, IOException
    {
    	CharSequence text = search; //can't use .contains method with a String
    	if(s[index].contains(text)) //checks if the line contains the searched word
    	{
	    	if(index < s.length-1)
			{			
				return index + ": " + s[index] + "\n"  + searchText(s, search, index+1);
			}
			return index + ": " + s[index];
    	} else return ""; //mandatory return statement
    }
    
    public int getNumOfLines() throws IOException
    {
    	String contents = new String(Files.readAllBytes(Paths.get(path))); //reads the txt file as a string
    	String[] lines = contents.split("\n");
    	
        return lines.length;
    }
    
    public String getContents() throws FileNotFoundException, IOException
    {
        String contents = new String(Files.readAllBytes(Paths.get(path))); //reads the txt file as a string
        return contents;
    }
    

}
