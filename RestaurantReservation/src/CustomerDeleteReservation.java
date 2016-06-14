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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class CustomerDeleteReservation extends JPanel {
	CustomerPanel parent;
	String customerId;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	SQLCustomer sc;
	Vector<String> tableBookings;



	public CustomerDeleteReservation(CustomerPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();
		sc= new SQLCustomer();

		tableBookings = sc.getReservationsAsString(customerId);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("Delete a Reservation", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		restaurantComboBox = new JComboBox();
		restaurantLabel = new JLabel("Select a booking: ", JLabel.TRAILING);

		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(300, 300));

		submit = new JButton("Submit");

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String booking = (String) restaurantComboBox.getSelectedItem();
				if (booking == null){
					JOptionPane.showMessageDialog(null, "You have no reservations to cancel!", "No Reservations", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				String[] b = booking.split("-"); 
				String one = b[0];
				String two = b[1];
				String three = b[2];
				String four = b[3];
				String five = b[4];
				String six = b[5];
				String seven = b[6];
				String tid=seven;
				
				String locationConcat = one+"-"+two;
				String startDayTimeC = three+"-"+four+"-"+five;
				String[] splitfive = five.split(" ");
				String dateC = three+":"+four+":"+splitfive[0];
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
				Date date = new Date();
				System.out.println("date is " + dateC);
				System.out.println(dateFormat.format(date));
				if (dateC == dateFormat.format(date)){
					JOptionPane.showMessageDialog(null, "Cannot cancel same day reservation", "Call Restaurant", JOptionPane.PLAIN_MESSAGE);
					return;	
				}

				Boolean result = sc.deleteBooking(locationConcat,startDayTimeC,customerId,tid);
				if (result){
					JOptionPane.showMessageDialog(null, "Your selected reservation has been cancelled!", "Successful", JOptionPane.PLAIN_MESSAGE);
					start();
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

		this.add(restaurantLabel, c);

		c.gridx = 0;
		c.gridy = 2;

		this.add(restaurantComboBox, c);

		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 1;
		c.gridy = 3;
		this.add(submit, c);

		c.gridx = 1;
		c.gridy = 4;

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
		
		Vector<String> tableBookings = sc.getReservationsAsString(customerId);
		
		//Adding the Customer's reservations to the drop-down box
		restaurantComboBox.removeAllItems();
		for (int i = 0; i<tableBookings.size(); i++) {
			restaurantComboBox.addItem(tableBookings.get(i));

		}

	}

}
