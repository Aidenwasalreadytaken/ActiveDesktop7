import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
public class GadgetMaker
{
	public static void main(String[] args)
	{
		try
		{
			//getting the gadget name--------------------------------
			System.out.println("Enter a name for the gadget:");
			Scanner sc = new Scanner(System.in);
			String gadgetName = sc.nextLine();
			//Getting gif directory----------------------------------
			System.out.println("Enter the name of the gif:");
			String filePath = sc.nextLine();
			//Scanner.close();
			//-------------------------------------------------------
			BufferedImage bimg = ImageIO.read(new File(filePath));
			int height = bimg.getHeight();
			int width = bimg.getWidth();		
			
			writeFiles(height, width, gadgetName, filePath);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void writeFiles(int height, int width, String gadgetName, String filePath)
	{
	
		try
		{
			String target = System.getenv("USERPROFILE") + "/AppData/Local/Microsoft/Windows Sidebar/Gadgets/" + gadgetName + ".gadget"; 
			new File(target).mkdir();
			Files.copy( new File(filePath).toPath(), new File(target + "/" +filePath).toPath(), REPLACE_EXISTING);
			Files.copy( new File("space.png").toPath(), new File(target + "/space.png").toPath(), REPLACE_EXISTING);
			
		
			File fileDir = new File(target + "/Gadget.xml");
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
			
			out.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
						"<gadget>\n" +
						  "<name>" + gadgetName + "</name>\n" +
						  "<namespace>GIF.gadget</namespace>\n"+
						  "<version>1.0.0.0</version>\n\n"+
						  "<icons>\n"+
							"<icon height=\"100\" width=\"100\" src=\""+filePath+"\" />\n"+
						  "</icons>\n\n"+
						  "<hosts>\n"+
							"<host name=\"sidebar\">\n"+
							  "<base type=\"HTML\" apiVersion=\"1.0.0\" src=\"GIF.html\" />\n"+
							  "<permissions>Full</permissions>\n"+
							  "<platform minPlatformVersion=\"1.0\" />\n"+
							"</host>\n"+
						  "</hosts>\n"+
						"</gadget>");
			
			out.flush();
			out.close();
			
			fileDir = new File(target + "/GIF.html");
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
			
			out.append("<html>\n"+
						  "<head>\n"+
							"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"+
							"<style type=\"text/css\">\n"+
							 " body, img {\n"+
								"margin:  0;\n"+
								"width: "+ width +";\n"+
								"height: "+ height +";\n"+
							  "}\n"+
							"</style>\n"+
						 " </head>\n"+
						  "<body background=\"space.png\">\n"+
							"<img src=\""+ filePath +"\">\n"+
						 " </body>\n"+
						"</html>\n");
			
			out.flush();
			out.close();
		}
	    catch (UnsupportedEncodingException e) 
	    {
			e.printStackTrace();
	    } 
	    catch (IOException e) 
	    {
			e.printStackTrace();
	    }
	    catch (Exception e)
	    {
			e.printStackTrace();
	    }
	}
}