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

public class RestaurantViewMenuPanel extends JPanel {
	RestaurantPanel parent;
	String restaurantId;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	

	
	public RestaurantViewMenuPanel(RestaurantPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();
		
		Vector<String> restaurantOptions = s.getRestaurants();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//title = new JLabel("See Menu", JLabel.CENTER);
		title = new JLabel("Menu", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		//restaurantComboBox = new JComboBox(restaurantOptions);
		//restaurantLabel = new JLabel("Select a restaurant: ", JLabel.TRAILING);
	
		displayResult = new JTable();
		
		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(300, 300));
	
		submit = new JButton("Show Menu");	
		submit.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
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
    
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent evt) {
            start();
        }
    });
    
	}
	
	public void start() {
		this.restaurantId = parent.getRestaurantID();
		displayResultPanel.getViewport().remove(displayResult);
	}

	
	private void deleteFoodItem(String restaurantID, String foodName) {
		s.deleteFoodItem(restaurantID, foodName);
	}
    
}
