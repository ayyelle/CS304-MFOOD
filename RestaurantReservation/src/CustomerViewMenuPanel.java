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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class CustomerViewMenuPanel extends JPanel {
	CustomerPanel parent;
	String customerId;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	JLabel imgLabel;
	JComboBox minmaxComboBox;
	JLabel minmaxPrice;
	JLabel minmaxOptionLabel;



	public CustomerViewMenuPanel(CustomerPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();

		Vector<String> restaurantOptions = s.getRestaurants();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("See Menu", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		restaurantComboBox = new JComboBox(restaurantOptions);
		restaurantLabel = new JLabel("Select a restaurant: ", JLabel.TRAILING);
		
		// min max
		Vector<String> options = new Vector<String>();
		//options.add("Show All");
		options.add("Min price");
		options.add("Max price");
		minmaxComboBox = new JComboBox(options);
		JLabel minmaxLabel = new JLabel("Min/Max Price: ", JLabel.TRAILING);
		
		minmaxPrice = new JLabel("", JLabel.TRAILING);
		minmaxOptionLabel = new JLabel("", JLabel.TRAILING);

		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(300, 300));
		displayResultPanel.setMinimumSize(new Dimension(300, 300));


		imgLabel = new JLabel();
		
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String optionSelection = (String) minmaxComboBox.getSelectedItem();
				String restaurant = (String) restaurantComboBox.getSelectedItem();

				Vector<String> colNames = new Vector<String>();
				colNames.add("Food Name");
				colNames.add("Price");

				SQLRestaurant s = new SQLRestaurant();
				Vector<Vector> data = s.getMenuItems(restaurant);
				String optionPrice = s.getPrice(restaurant, optionSelection);
				
				if (optionSelection == "Max price"){
					minmaxOptionLabel.setText("Most Expensive Item On Menu :" );
					
				}else{
					minmaxOptionLabel.setText("Cheapest Item On Menu :" );
					
				}
				//minmaxOptionLabel.setText(optionSelection);
				minmaxPrice.setText("$ "+optionPrice);
				
				displayResult = new JTable(data, colNames);

				displayResult.getColumnModel().getColumn(0).setMaxWidth(250);
				displayResult.getColumnModel().getColumn(1).setMaxWidth(50);

				displayResult.setRowHeight(40);
				displayResultPanel.getViewport().add(displayResult);
				if (data.size() == 0) {
					JOptionPane.showMessageDialog(null, "There are no food items to view, try another restaurant!", "No Menu To Display", JOptionPane.PLAIN_MESSAGE);
				}

				String iconPath = s.getImage(restaurant);
				Icon icon = new ImageIcon(getClass().getResource(iconPath));
				imgLabel.setIcon(icon);

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

		this.add(restaurantLabel, c);

		c.gridx = 0;
		c.gridy = 2;

		this.add(restaurantComboBox, c);

		c.insets = new Insets(10, 10, 10, 10);

		// min max stuff
		c.gridx = 0;
		c.gridy = 3;
		this.add(minmaxComboBox,c);
		
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(submit, c);
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(minmaxOptionLabel);
		
		c.gridx = 0;
		c.gridy = 6;
		this.add(minmaxPrice);
		

	

		c.gridx = 0;
		c.gridy = 7;
		this.add(displayResultPanel, c);

		c.gridx = 0;
		c.gridy = 8;
		this.add(imgLabel, c);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}
		});

	}

	public void start() {
		this.customerId = parent.getCustomerID();
		displayResultPanel.getViewport().remove(displayResult);

		Vector<String> restaurants = s.getRestaurants();

		restaurantComboBox.removeAllItems();
		for (int i = 0; i<restaurants.size(); i++) {
			restaurantComboBox.addItem(restaurants.get(i));

		}
	}

}
