import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class OwnerViewMenuPanel extends JPanel {
	restaurantPanel parent;
	String restaurantId;
	JButton submit;
	JButton delete;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	String restaurantName;
	
	public OwnerViewMenuPanel(restaurantPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();
		
		Vector<String> restaurantOptions = s.getRestaurants();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		title = new JLabel("Menu - ", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
	
		displayResult = new JTable();
		
		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(300, 300));
	
		submit = new JButton("Show Menu");
		delete = new JButton("Delete Item");
	
		submit.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			displayResult();
			
		}
		
	});
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = displayResult.getSelectedRow();
				String foodName = (String) displayResult.getValueAt(row, 0);
				System.out.println("Food name to be deleted: " + foodName);
				System.out.println("Restaurant ID: " + restaurantId);
				deleteFoodItem(restaurantId, foodName);
				update();
			}
			
		});
	
	c.gridx = 0;
	c.gridy = 0;
	c.gridwidth = c.REMAINDER;
	c.anchor = c.PAGE_START;
	c.insets = new Insets(30, 5, 5, 5);
	this.add(title, c);
	
	
	c.gridx = 0;
	c.gridy = 1;
	c.insets = new Insets(0, 0, 0, 0);
	
	//this.add(restaurantLabel, c);
	
	c.gridx = 0;
	c.gridy = 2;

	//this.add(restaurantComboBox, c);
	
	c.insets = new Insets(10, 10, 10, 10);

	c.gridx = 1;
	c.gridy = 3;
	this.add(submit, c);
	
	c.gridx = 1;
	c.gridy = 4;
	this.add(displayResultPanel, c);
	
	c.gridx = 1;
	c.gridy = 5;
	this.add(delete, c);
    
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent evt) {
            start();
        }
    });
    
	}
	
	public void start() {
		s = new SQLRestaurant();
		this.restaurantId = parent.getRestaurantID();
		this.restaurantName = s.getRestaurantName(restaurantId);
		title.setText("Menu: " + restaurantName);
		displayResult();
	}

	
	private void deleteFoodItem(String restaurantID, String foodName) {
		s.deleteFoodItem(restaurantID, foodName);
	}
	
	private void displayResult(){
		Vector<String> colNames = new Vector<String>();
		colNames.add("Food Name");
		colNames.add("Price");

		SQLRestaurant s = new SQLRestaurant();
		Vector<Vector> data = s.getRestaurantMenuItems(restaurantId);
		displayResult = new JTable(data, colNames);

		displayResult.getColumnModel().getColumn(0).setMaxWidth(250);
		displayResult.getColumnModel().getColumn(1).setMaxWidth(50);
		
		displayResult.setRowHeight(40);
		displayResultPanel.getViewport().add(displayResult);
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "You don't have any menu items!", "No Menu To Display", JOptionPane.PLAIN_MESSAGE);
		}
	};
	
	private void update(){
		displayResultPanel.getViewport().remove(displayResult);
		displayResult();
	}
    
}
