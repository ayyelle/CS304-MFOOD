import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class CustomerReservationPanel extends JPanel {

	CustomerPanel parent;
	String customerId;
	JLabel title;
	SQLRestaurant s;

	public CustomerReservationPanel(CustomerPanel parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		
		s = new SQLRestaurant();
		JPanel formPanel = new JPanel();
		title = new JLabel("Make a reservation!");
		title.setFont(new Font(title.getName(), Font.PLAIN, 30));
		this.add(title, BorderLayout.NORTH);
		formPanel.setPreferredSize(new Dimension(10, 10));
		this.add(formPanel, BorderLayout.CENTER);
		formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		
		SpringLayout layout = new SpringLayout();
		formPanel.setLayout(layout);
		JLabel label = new JLabel("Choose a restaurant: ");
		formPanel.add(label);
		
		s = new SQLRestaurant();
		
		Vector<String> restaurantOptions = s.getRestaurants();
		JComboBox restaurantCombo = new JComboBox(restaurantOptions);
		formPanel.add(restaurantCombo);
		

		JLabel label1 = new JLabel("Another Label: ");
		formPanel.add(label1);
		JTextField text1 = new JTextField("Text field 2", 15);
		formPanel.add(text1);

		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, formPanel);
		layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, formPanel);

		layout.putConstraint(SpringLayout.WEST, restaurantCombo,
				5,
				SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, restaurantCombo,
				5,
				SpringLayout.NORTH, formPanel);

		layout.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, formPanel);
		layout.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.SOUTH, label);

		layout.putConstraint(SpringLayout.WEST, text1,
				5,
				SpringLayout.EAST, label1);
		layout.putConstraint(SpringLayout.NORTH, text1,
				5,
				SpringLayout.SOUTH, restaurantCombo);



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
	}


}
