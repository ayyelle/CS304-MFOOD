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
		//this.customerId = parent.getCustomerID();

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
				//String restaurantName = booking.substring(0, booking.indexOf("-") + 1);
				//System.out.println("restaurant name in delete " + restaurantName);
				//String startDayTime = booking.substring(booking.indexOf("-") + 1, booking.indexOf("-")+4);
				//System.out.println(startDayTime);
				//String partySize = booking.substring(booking.indexOf("-") + 5,booking.indexOf("-")+6);
				//System.out.println(partySize);
				//String tid = booking.substring(booking.indexOf("-") + 6);
				//System.out.println(tid);
				if (booking == null){
					JOptionPane.showMessageDialog(null, "You have no reservations to cancel!", "No Reservations", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				String[] b = booking.split("-"); 
				String one = b[0];
				//System.out.println("split one " + one);
				String two = b[1];
				//System.out.println("split two " + two);
				String three = b[2];
				//System.out.println("split three " + three);
				String four = b[3];
				//System.out.println("split four " + four);
				String five = b[4];
				//System.out.println("split five " + five);
				String six = b[5];
				//System.out.println("split six " + six);
				String seven = b[6];
				//System.out.println("table id is " + seven);
				String tid=seven;
				
				String locationConcat = one+"-"+two;
				//System.out.println("location concated is" + locationConcat);
				String startDayTimeC = three+"-"+four+"-"+five;
				//System.out.println("startday time concat is " + startDayTimeC);
				String[] splitfive = five.split(" ");
				String dateC = three+":"+four+":"+splitfive[0];

				//System.out.println(booking);
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
				Date date = new Date();
				System.out.println("date is " + dateC);
				System.out.println(dateFormat.format(date));
				if (dateC == dateFormat.format(date)){
					JOptionPane.showMessageDialog(null, "Cannot cancel same day reservation", "Call Restaurant", JOptionPane.PLAIN_MESSAGE);
					return;	
				}
				//Vector<String> colNames = new Vector<String>();
				//colNames.add("Food Name");
				//colNames.add("Price");

				//SQLRestaurant s = new SQLRestaurant();
				Boolean result = sc.deleteBooking(locationConcat,startDayTimeC,customerId,tid);
				if (result){
					JOptionPane.showMessageDialog(null, "Your selected reservation has been cancelled!", "Successful", JOptionPane.PLAIN_MESSAGE);
					start();
				}
				//Vector<Vector> data = s.getMenuItems(restaurant);
				//displayResult = new JTable(data, colNames);

				//displayResult.getColumnModel().getColumn(0).setMaxWidth(250);
				//displayResult.getColumnModel().getColumn(1).setMaxWidth(50);

				//displayResult.setRowHeight(40);
				//displayResultPanel.getViewport().add(displayResult);
				//if (data.size() == 0) {
				//JOptionPane.showMessageDialog(null, "There are no food items to view, try another restaurant!", "No Menu To Display", JOptionPane.PLAIN_MESSAGE);
				//}

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
		//this.add(displayResultPanel, c);

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
