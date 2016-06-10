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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OwnerAddEmployeePanel extends JPanel {
	restaurantPanel parent;
	String restaurantId;
	JButton add;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel nameLabel;
	JLabel eidLabel;
	JTextField inputName;
	JTextField inputEid;
	JLabel pLabel;
	JPasswordField inputPassword;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	String restaurantName;
	
	public OwnerAddEmployeePanel (restaurantPanel parent) {
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
		restaurantLabel = new JLabel("Add Employee to " + restaurantName, JLabel.CENTER);
		restaurantLabel.setFont(new Font(restaurantLabel.getName(), Font.PLAIN, 20));

		nameLabel = new JLabel("Employee Name", JLabel.TRAILING);
		inputName = new JTextField();
		inputName.setPreferredSize(new Dimension(400, 20));
		
		eidLabel = new JLabel("Employee ID", JLabel.TRAILING);
		inputEid = new JTextField();
		inputEid.setPreferredSize(new Dimension(400, 20));
		
		pLabel = new JLabel("Password", JLabel.TRAILING);
		inputPassword = new JPasswordField();
		inputPassword.setPreferredSize(new Dimension(400, 20));
		
		add = new JButton("Add Employee");



		//Place the components
		c.gridx = 1;
		c.gridy = 0;
		this.add(restaurantLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(nameLabel, c);
		c.gridx = 2;
		c.gridy = 1;
		this.add(inputName,  c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(pLabel, c);
		c.gridx = 2;
		c.gridy = 2;
		this.add(inputPassword,  c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(eidLabel, c);
		c.gridx = 2;
		c.gridy = 3;
		this.add(inputEid,  c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(add,  c);
		
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Get information from the fields
				String name = inputName.getText();
				String password = inputPassword.getText();
				String eid = inputEid.getText();
				int eidInt = Integer.parseInt(eid);
				
				
				if (name.equals("")||password.equals("")||eid.equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill in all textfields!", "Add Employee", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,"New employee added!", "Add Employee", JOptionPane.PLAIN_MESSAGE);
	
				}
				
				addEmployee(eidInt, name, password, restaurantId);
				
			}
			
		});
	}
	
	private void addEmployee(int eid, String name, String password, String rid) {
		s.addEmployee(eid, name, password, rid);
	}
	
	private void start() {
		s = new SQLRestaurant();
		this.restaurantId = parent.getRestaurantID();
		this.restaurantName = s.getRestaurantName(restaurantId);
	}
}
