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
import javax.swing.JOptionPane;
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
	Vector<String> restaurantOptions;
	JComboBox restaurantComboBox;
	JPanel formPanel;
	GridBagLayout layout;
	GridBagConstraints gc;
	JLabel restaurantLabel;
	JLabel dateLabel;
	JButton submit;
	JComboBox timesComboBox;
	Vector<String> timeOptions;
	
	JLabel timeLabel;
	
	UtilDateModel model;
	Properties p;
	

	public CustomerReservationPanel(CustomerPanel parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		initializeElements();
		
		this.add(title, BorderLayout.NORTH);
		this.add(formPanel, BorderLayout.CENTER);

		formPanel.setLayout(layout);
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 0;
		gc.gridy = 0;
		formPanel.add(restaurantLabel, gc);
				
		gc.gridx = 1;
		formPanel.add(restaurantComboBox, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		formPanel.add(dateLabel, gc);

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

		gc.gridx = 1;
		formPanel.add(datePicker, gc);
		
		gc.gridy = 2;
		gc.gridx = 0;
		formPanel.add(timeLabel, gc);
		
		gc.gridx = 1;
		formPanel.add(timesComboBox, gc);
		
		gc.gridx = 1;
		gc.gridy = 3;
		formPanel.add(submit, gc);
		
		//Getting the information after submit button pressed
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date selectedDate = (Date) datePicker.getModel().getValue();
				String selectedTime = (String) timesComboBox.getSelectedItem();
				if (selectedDate == null) {
					JOptionPane.showMessageDialog(null,"Please select a date!", "Date Missing", JOptionPane.PLAIN_MESSAGE);

				} else {
					System.out.println("Day: " + selectedDate.getDate());
					System.out.println("Month: " + (selectedDate.getMonth()+1));
					System.out.println("Year: " + (selectedDate.getYear()+1900));
					System.out.println("Time: " + selectedTime);
				}
				
			}
			
		});


		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}
		});

	}



	private void initializeElements() {
		s = new SQLRestaurant();
		
		formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(10, 10));
		formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		
		title = new JLabel("Make a reservation!");
		title.setFont(new Font(title.getName(), Font.PLAIN, 30));
		
		layout = new GridBagLayout();
		gc = new GridBagConstraints();
		 
		restaurantOptions = s.getRestaurants();
		restaurantComboBox = new JComboBox(restaurantOptions);

		restaurantLabel = new JLabel("Choose a restaurant: ");

		dateLabel = new JLabel("Choose Date: ");

		//Date Picker stuff
		model = new UtilDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		submit = new JButton("Submit Reservation Request");
		
		timeLabel = new JLabel("Select a reservation time:");
		timeOptions = new Vector<String>();
		for (int i = 5; i <= 11; i++) {
			String str = i + ":00 PM";
			timeOptions.add(str);
		}
		timesComboBox = new JComboBox(timeOptions);
		
		
	}



	public void start() {
		this.customerId = parent.getCustomerID();
		System.out.println(customerId);
	}


}
