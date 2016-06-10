import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OwnerRestaurantProperties extends JPanel {
	restaurantPanel parent;
	String restaurantId;
	JButton updateName;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel nameLabel;
	JLabel locationLabel;
	JTextField nameInput;
	JTextField locationInput;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	String restaurantName;
	
	public OwnerRestaurantProperties (restaurantPanel parent) {
		this.parent = parent;
		
		addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentShown(ComponentEvent evt) {
	            start();
	        }
	    });

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		this.setLayout(new GridBagLayout());
		
		//Create the components
		restaurantLabel = new JLabel("Manage " + restaurantName, JLabel.CENTER);
		restaurantLabel.setFont(new Font(restaurantLabel.getName(), Font.PLAIN, 20));
		
		nameLabel = new JLabel("Restaurant Name", JLabel.TRAILING);
		nameInput = new JTextField();
		nameInput.setPreferredSize(new Dimension(400, 20));

		locationLabel = new JLabel("Restaurant Location", JLabel.TRAILING);
		locationInput = new JTextField();
		locationInput.setPreferredSize(new Dimension(400, 20));
		
		updateName = new JButton("Update Restaurant Name");



		//Place the components
		c.gridx = 1;
		c.gridy = 0;
		this.add(restaurantLabel, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(nameLabel, c);
		c.gridx = 2;
		c.gridy = 2;
		this.add(nameInput,  c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(locationLabel, c);
		c.gridx = 2;
		c.gridy = 3;
		this.add(locationInput,  c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(updateName,  c);
		
		updateName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Get information from the fields
				String name = nameInput.getText();
				String location = locationInput.getText();
				
				if (name.equals("")||location.equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill in all textfields!", "Update Name", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,"Restaurant Name Updated!", "Update Name", JOptionPane.PLAIN_MESSAGE);
	
				}
				
				updateRestaurantName(restaurantId, name, location);
				
			}
			
		});
	}
	
	private void updateRestaurantName(String rid, String name, String location) {
		s.updateRestaurantName(rid, name, location);
	}
	
	private void start() {
		s = new SQLRestaurant();
		this.restaurantId = parent.getRestaurantID();
		this.restaurantName = s.getRestaurantName(restaurantId);
	}
}
