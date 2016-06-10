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

public class OwnerAddMenuPanel extends JPanel {
	restaurantPanel parent;
	String restaurantId;
	JButton add;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel foodLabel;
	JLabel priceLabel;
	JLabel foodId;
	JTextField inputName;
	JTextField inputPrice;
	JTextField inputFid;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	String restaurantName;
	
	public OwnerAddMenuPanel (restaurantPanel parent) {
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
		restaurantLabel = new JLabel("Add item for " + restaurantName, JLabel.CENTER);
		restaurantLabel.setFont(new Font(restaurantLabel.getName(), Font.PLAIN, 20));

		foodLabel = new JLabel("Food Name", JLabel.TRAILING);
		inputName = new JTextField();
		inputName.setPreferredSize(new Dimension(400, 20));

		priceLabel = new JLabel("Price", JLabel.TRAILING);
		inputPrice = new JTextField();
		inputPrice.setPreferredSize(new Dimension(400, 20));
		
		foodId = new JLabel("Item ID", JLabel.TRAILING);
		inputFid = new JTextField();
		inputFid.setPreferredSize(new Dimension(400, 20));
		
		add = new JButton("Add Menu Item");



		//Place the components
		c.gridx = 1;
		c.gridy = 0;
		this.add(restaurantLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(foodLabel, c);
		c.gridx = 2;
		c.gridy = 1;
		this.add(inputName,  c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(priceLabel, c);
		c.gridx = 2;
		c.gridy = 2;
		this.add(inputPrice,  c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(foodId, c);
		c.gridx = 2;
		c.gridy = 3;
		this.add(inputFid,  c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(add,  c);
		
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Get information from the fields
				String name = inputName.getText();
				String price = inputPrice.getText();
				String fid = inputFid.getText();
				int fidInt = Integer.parseInt(fid);
				
				System.out.println(fid);
				
				if (name.equals("")||price.equals("")||fid.equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill in all textfields!", "Add Menu Item", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,"Added!", "Add Menu Item", JOptionPane.PLAIN_MESSAGE);
	
				}
				
				addFoodItem(fidInt, name, price, restaurantId);
				
			}
			
		});
	}
	
	private void addFoodItem(int fid, String name, String price, String rid) {
		s.addFoodItem(fid, name, price, rid);
	}
	
	private void start() {
		System.out.println("Start(); in Owner add menu--");
		s = new SQLRestaurant();
		this.restaurantId = parent.getRestaurantID();
		this.restaurantName = s.getRestaurantName(restaurantId);
		restaurantLabel.setText("Add item for " + restaurantName);
	}
}
