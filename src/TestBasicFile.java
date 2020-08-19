import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TestBasicFile {
	
	public static void main(String[] args) throws IOException {
		boolean done = false;

		BasicFile f = new BasicFile();
		JFileChooser choose = new JFileChooser();
		boolean fileOpened = false;
		
		
		String menu = "Select an option: \n"
				+ "1. Open File \n"
				+ "2. Copy File \n"
				+ "3. Append Text File \n" 
				+ "4. Overwrite Text File \n" 
				+ "5. Display Attributes \n"  
				+ "6. Display Contents \n" 
				+ "7. Search File \n" 
				+ "8. Quit";	
		
		while(!done)
		{
			String s = JOptionPane.showInputDialog(menu);
			try
			{
				int i = Integer.parseInt(s);
				switch(i)
				{
					case 1:
						f.openFile(choose);
						fileOpened = true;
					break;
					case 2:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
						f.copyFile(choose);
					break;
					case 3:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
						f.appendFile(choose);
					break;
					case 4:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
						f.overwriteFile(choose);
					break;
					case 5:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
	                    display(f.fileAttributes(), "File Attributes");
					break;
					case 6:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
	                    display(f.getContents(), "File Contents");
					break;
					case 7:
						if(!fileOpened)
						{
							display("A file needs to be selected before choosing this option!", "File not found");
							break;
						}
	                    String text = JOptionPane.showInputDialog(null,"Text to locate"); 
	                    display(f.locateText(text), "Search Results");
					break;
					case 8:
						done = true;
					break;
					default:
						display("This option is undefined", "Error");
					break;
						
				}
			} catch(NumberFormatException | NullPointerException e)
			{
				display(e.toString(), "Error");
			}
		}
	
	}
	
	static void display(String message, String title)
	{
		JTextArea text = new JTextArea(message,30,50);
        JScrollPane pane = new JScrollPane(text);
        JOptionPane.showMessageDialog(null, pane, title, JOptionPane.INFORMATION_MESSAGE);
	}
	

}
