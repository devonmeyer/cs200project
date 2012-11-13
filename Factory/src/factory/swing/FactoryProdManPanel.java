/*

author: Joey Huang
Last edited: 11/12/12 11:17pm
*/
package factory.swing;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import factory.managers.*;


public class FactoryProdManPanel extends JPanel implements ActionListener {
	JComboBox kitNameBox; // contain String names of saved kit configurations
	JSpinner spinner; // quantity of kits to produce
	JButton submitButton;
	JTextArea messageBox; // submission confirmations
	FactoryProductionManager factoryProductionManager;
	JTabbedPane tabContainer;
	JPanel newOrderPanel;
	
	JPanel schedulePanel;
	DefaultTableModel model;
	JTable table;
	
	public FactoryProdManPanel() { 
		newOrderPanel = new JPanel();
		kitNameBox = new JComboBox();
		kitNameBox.setPreferredSize(new Dimension(225,25));
		
		newOrderPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.insets = new Insets(0,0,25,0); 
		newOrderPanel.add(new JLabel("Factory Production Manager"),c);
		
		
		c.insets = new Insets(10,0,10,0);
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		newOrderPanel.add(new JLabel("Submit New Batch Order:"),c);
		
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		newOrderPanel.add(kitNameBox,c);

		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = 4;
		c.gridx = 2;
		c.gridwidth = 1;
		newOrderPanel.add(new JLabel("Quantity"),c);
		
		c.gridx = 4;
		SpinnerNumberModel qntyModel = new SpinnerNumberModel(1,1,500,1);
		spinner = new JSpinner(qntyModel);
		newOrderPanel.add(spinner,c);
		
		c.gridy = 5;
		c.gridx = 4;
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		newOrderPanel.add(submitButton,c);
		
		c.gridy = 6;
		c.gridx = 1;
		c.gridwidth = 4;
		messageBox = new JTextArea("System Messages\n",10,20);
		newOrderPanel.add(new JScrollPane(messageBox),c);
		
		
		// tab 2 production schedule
		schedulePanel = new JPanel();
		model = new DefaultTableModel();

		model.addColumn("No.");
		model.addColumn("Kit");
		model.addColumn("Qnty");

		table=new JTable(model);
		
		
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (i == 0) {
		        column.setPreferredWidth(30); 
		    } else if (i ==1) {
		        column.setPreferredWidth(200);
		    } else {
		    	column.setPreferredWidth(30);
		    }
		}

		// method to insert row / order
	/*	for(int i=0;i<4;i++) {
			Object[] rowData = {i+1,"Kit"+(i+1),"10"};
			model.insertRow(i,rowData);		
		}*/
	
		schedulePanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("<html><p style=\"margin:30px 0 30px 0;\">Production Schedule</html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		schedulePanel.add(label,BorderLayout.NORTH);
		
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(300,400));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(260,400));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		container.add(scrollPane);
		schedulePanel.add(container,BorderLayout.CENTER);
		
		//setSize(300,720);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setVisible(true);
		
		//javax.swing.Timer timer = new javax.swing.Timer(1000,this);
		//timer.start();
		
		tabContainer = new JTabbedPane();
		tabContainer.addTab("New Order",newOrderPanel);
		tabContainer.addTab("Schedule",schedulePanel);
		add(tabContainer);
	}
		
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource().equals(submitButton)) {		// print messages to be displayed in messageBox
			if (kitNameBox.getSelectedItem() == null)
				messageBox.append("No kit selected.\n");
			else {
				 factoryProductionManager.sendMessage((String)kitNameBox.getSelectedItem(),(String)spinner.getValue(), "hi");
					
			messageBox.append("Order Submitted.\n     Details: " + spinner.getValue() + " units of " + (String)kitNameBox.getSelectedItem() + "\n" );
			}
		}
	}
	
	public void addKit(String kitName) {	//add new kit name (String) to Jcombobox list - received from kit manager
		kitNameBox.addItem(kitName);	
		((JComboBox) kitNameBox.getItemAt(kitNameBox.getItemCount()-1)).addActionListener(this);
		kitNameBox.setSelectedIndex(0);
	}
	
	public void removeKit(String kitName) { // remove kit from list - received from kit manager
		kitNameBox.removeItem(kitName);	// should have been validated elsewhere
	}

	public void setManager(FactoryProductionManager fpm) {
		factoryProductionManager = fpm;
	}
	
/*	public void doCommand(ArrayList<String> parsedCommands) {
		// THIS IN MANAGER?
		
		 add new kit
		  addKit(kitName)
	
		 
		
		  removeKit
				
		
		 command : update kits remaining


		
	} */

	public void kitProduced() { // update kits remaining
		String numstr = (String)model.getValueAt(0,2);
		int num = Integer.parseInt(numstr);
		if(num >0) {
			num--;
			model.setValueAt(Integer.toString(num), 0, 2);
		}
		else if (num == 0) {
		//	int modelRow = table.convertRowIndexToModel(0);
		//	model.removeRow(modelRow);
			model.removeRow(0);
			for (int i = 0; i < model.getRowCount();i++) {
				model.setValueAt((i+1)+"",i,0);
			}
			table.revalidate();
		}
		else {
		
		}
	}
}
