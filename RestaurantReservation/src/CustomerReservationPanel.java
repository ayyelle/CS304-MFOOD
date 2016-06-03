import java.awt.BorderLayout;
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
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/*JDatePicker utility from: https://jdatepicker.org. Using their jar for this component */
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

				GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		formPanel.setLayout(layout);
		JLabel label = new JLabel("Choose a restaurant: ");
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 0;
		gc.gridy = 0;
		formPanel.add(label, gc);
		
		s = new SQLRestaurant();
		
		Vector<String> restaurantOptions = s.getRestaurants();
		JComboBox restaurantCombo = new JComboBox(restaurantOptions);
		gc.gridx = 1;
		formPanel.add(restaurantCombo, gc);
		

		JLabel label1 = new JLabel("Choose Date: ");
		gc.gridx = 0;
		gc.gridy = 1;
		formPanel.add(label1, gc);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		JTextField text1 = new JTextField("Text field 2", 15);
		gc.gridx = 1;
		formPanel.add(datePicker, gc);
		
		JButton submit = new JButton("submit");
		gc.gridx = 0;
		gc.gridy = 2;
		formPanel.add(submit, gc);
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date selectedDate = (Date) datePicker.getModel().getValue();
				System.out.println("Day: " + selectedDate.getDate());
				System.out.println("Month: " + (selectedDate.getMonth()+1));
				System.out.println("Year: " + (selectedDate.getYear()+1900));
				
			}
			
		});


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
