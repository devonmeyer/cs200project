//Minh La

package factory.graphics.GUILaneManager;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import factory.Part;

public class GUIGraphicBin{

	ArrayList <GUIGraphicItem> binItems;
	ImageIcon binImage;
	String partName;
	ImageIcon binItemsStackImage;
	int binSize;

	public GUIGraphicBin(Part part){
		partName = part.name;
		binSize = 14;		//Number of items in bin
		binItems = new ArrayList<GUIGraphicItem>();
		for(int i = 0; i < binSize;i++){
			//binItems.add(new GraphicItem(-40, 0, "Images/"+partName+".png"));
			binItems.add(new GUIGraphicItem(-40, 0, "Images/eyesItem.png"));
		}
		//Declaration of items in bin's location
		/*for(int i = 0;i<binItems.size();i++){
			binItems.get(i).setX(-40);
			binItems.get(i).itemImage = new ImageIcon("Images/" + partName + ".png");
		}*/
		binItemsStackImage = new ImageIcon("Images/binCrate.png");
		binImage = new ImageIcon("Images/binCrate.png");
	}

	public ArrayList<GUIGraphicItem> getBinItems(){
		return binItems;
	}

	public ImageIcon getBinImage(){
		return binImage;
	}
	
	public void paint(Graphics g, int x, int y) {
		g.drawImage(binImage.getImage(), x, y, null);
	}

}