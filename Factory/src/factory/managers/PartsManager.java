//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200
package factory.managers;


import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

import factory.Part;
import factory.client.Client;
import factory.graphics.KitAssemblyPanel;
import factory.swing.KitAssManPanel;
import factory.swing.KitManPanel;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {
	
	private static final long serialVersionUID = -205350261062308096L;
	
	PartsManPanel buttons;
	
	ArrayList<String> parts;
	// Kit Configurations ArrayList

	public PartsManager() {
		super(Client.Type.pm, null, null);

		PartsManPanel buttons = new PartsManPanel();
		buttons.setManager(this);
		
		setInterface();
		parts = new ArrayList<String>();
		/*parts.add(new Part("Eye",1,"This is used to see.","Images/eye.png",1));
		parts.add(new Part("Body",2,"This is used as the base.","Images/body.png",5));
		parts.add(new Part("Hat",3,"This is used to cover the head.","Images/hat.png",2));
		parts.add(new Part("Arm",4,"This is used to grab things.","Images/arm.png",2));
		parts.add(new Part("Shoe",5,"This is used to walk.","Images/shoe.png",2));
		parts.add(new Part("Mouth",6,"This is used to talk.","Images/mouth.png",1));
		parts.add(new Part("Nose",7,"This is used to smell.","Images/nose.png",1));
		parts.add(new Part("Moustache",8,"This is used to look cool.","Images/moustache.png",1));
		parts.add(new Part("Ear",9,"This is used to hear.","Images/ear.png",2));
		*/
		//loadData();
		//this.addWindowListener(this);
	}
	
	public static void main(String[] args){
		PartsManager p = new PartsManager();
	}
	
	public void setInterface() {
		UI = buttons;
		
		add(UI, BorderLayout.LINE_END);
		pack();
		setVisible(true);
	}
	
	public void sendMessage(String option, String itemName, String filePath){
		String message = null;
		
		if(option.equals("add")){
			message = "pm km cmd addpartname " + itemName + " " + filePath;
		}
		
		else if (option.equals("remove")){
			message = "pm km cmd rmpartname " + itemName + " " + filePath;
		}
		
		sendCommand(message);
		
	}

	@Override
	public void doCommand(ArrayList<String> pCmd) {
		int size = pCmd.size();
	//parameters lay between i = 2 and i = size - 2
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action.equals("cmd")){
			/*if(identifier.equals(command1))
			 * do(command1);
			 * else if(identifier.equals(command2))
			 * do(command2);
			 */
		}
		else if(action.equals("req")){
			/*if(identifier.equals(request1))
			 * do(request1);
			 * else if(identifier.equals(request2))
			 * do(request2);
			 */
		}
		else if(action.equals("get")){
			/*if(identifier.equals(get1))
			 * do(get1);
			 * else if(identifier.equals(get2))
			 * do(get2);
			 */
		}
		else if(action.equals("set")){
			/*if(identifier.equals(set1))
			 * do(set1);
			 * else if(identifier.equals(set2))
			 * do(set2);
			 */
		}
		else if(action.equals("cnf")){
			/*if(identifier.equals(confirm1))
			 * do(confirm1);
			 * else if(identifier.equals(confirm2))
			 * do(confirm2);
			 */
		}
		else if(action.equals("err")){
			String error;
			error = new String();
			for(int i = 1; i<this.parsedCommand.size(); i++)
				error.concat(parsedCommand.get(i));
			System.out.println(error);
		
			
		}
	}
	
	public ArrayList<String> getParts(){
		return parts;
	}
	
	public void saveData(){
		FileOutputStream f;
		ObjectOutputStream o;
		try {    // uses output stream to serialize and save array of Players
			
			f = new FileOutputStream("InitialData/initialParts.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(parts);
			o.close();
			System.out.println("It worked");
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			parts = (ArrayList<String>) o.readObject();
		}catch(IOException e){
			parts = new ArrayList<String>();
		} catch(ClassNotFoundException c){
			parts = new ArrayList<String>();
		}
	}
}
