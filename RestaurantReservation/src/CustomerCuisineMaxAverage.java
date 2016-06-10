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

public class CustomerCuisineMaxAverage extends JPanel {
	CustomerPanel parent;
	String customerId;
	JButton maxButton;
	JButton averageButton;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	SQLCustomer sc;
	

	
	public CustomerCuisineMaxAverage(CustomerPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();
		sc = new SQLCustomer();
		
		//Vector<String> restaurantOptions = s.getRestaurants();
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	title = new JLabel("Choose Max or Average Ratings", JLabel.CENTER);
	title.setFont(new Font(title.getName(), Font.PLAIN, 20));
	//restaurantComboBox = new JComboBox(restaurantOptions);
	//restaurantLabel = new JLabel("Select a restaurant: ", JLabel.TRAILING);

	displayResult = new JTable();
	
	displayResultPanel = new JScrollPane();
	displayResultPanel.setPreferredSize(new Dimension(500, 300));

	maxButton = new JButton("See Max Rating of All Cuisines");
	averageButton = new JButton("See Average Rating of All Cuisines");
	
	maxButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//String restaurant = (String) restaurantComboBox.getSelectedItem();

			//System.out.println(restaurant);
			Vector<String> colNames = new Vector<String>();
			colNames.add("Cuisine");
			colNames.add("Highest Rated Restaurant");
			colNames.add("Rating");
			//colNames.add("Table ID");

			SQLRestaurant s = new SQLRestaurant();
			Vector<Vector> data = s.getMaxCusine();
			displayResult = new JTable(data, colNames);

			displayResult.getColumnModel().getColumn(0).setMaxWidth(150);
			displayResult.getColumnModel().getColumn(1).setMaxWidth(250);
			displayResult.getColumnModel().getColumn(2).setMaxWidth(100);
			//displayResult.getColumnModel().getColumn(3).setMaxWidth(0);
			
			displayResult.setRowHeight(40);
			displayResultPanel.getViewport().add(displayResult);
			
		}
		
	});
		
	averageButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//String restaurant = (String) restaurantComboBox.getSelectedItem();

			//System.out.println(restaurant);
			Vector<String> colNames = new Vector<String>();
			colNames.add("Cuisine");
			colNames.add("Average Rating");
			//colNames.add("Rating");
			//colNames.add("Table ID");

			SQLRestaurant s = new SQLRestaurant();
			Vector<Vector> data = s.getAverageCuisine();
			displayResult = new JTable(data, colNames);

			displayResult.getColumnModel().getColumn(0).setMaxWidth(150);
			displayResult.getColumnModel().getColumn(1).setMaxWidth(150);
			//displayResult.getColumnModel().getColumn(2).setMaxWidth(50);
			//displayResult.getColumnModel().getColumn(3).setMaxWidth(0);
			
			displayResult.setRowHeight(40);
			displayResultPanel.getViewport().add(displayResult);
			
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
	this.add(maxButton, c);
	c.gridx = 1;
	c.gridy = 4;
	this.add(averageButton, c);
	
	c.gridx = 1;
	c.gridy = 5;
	this.add(displayResultPanel, c);
    
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent evt) {
            start();
        }
    });
    
	}
	
	public void start() {
		this.customerId = parent.getCustomerID();
		System.out.println(customerId);
		displayResultPanel.getViewport().remove(displayResult);
	}
    
}