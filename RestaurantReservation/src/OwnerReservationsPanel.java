import java.awt.CardLayout;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

@SuppressWarnings("serial")
public class OwnerReservationsPanel extends JPanel {

	RestaurantPanel parent;
	String resID;
	JButton addReservation;
	JButton deleteReservation;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
	JPanel restaurantCards;

	// pick date
	UtilDateModel model;
	Properties p;
	boolean updateDate;

	// new reservation variables
	String startdaytimeNew, partysizeNew, durationNew, tidNew, ridNew, usernameNew;

	@SuppressWarnings("static-access")
	public OwnerReservationsPanel(RestaurantPanel parent, JPanel restaurantCards) {

		this.parent = parent;
		this.restaurantCards = restaurantCards;
		s = new SQLRestaurant();
		p = new Properties();
		model = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("View Reservations", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(900, 400));

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

		datePicker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date today = new Date();
				Date selectedDate = (Date) datePicker.getModel().getValue();
				String formattedDate = formatDate(selectedDate);
				String formattedDateEnd = formatDatePlusOne(selectedDate);
				if (selectedDate.before(today)) {
					try{
						JOptionPane.showMessageDialog(null, "Oops! Selected day must be in the future.", 
								"Date Missing",JOptionPane.PLAIN_MESSAGE);
					}catch(Exception e){
						e.getMessage();
					}
					start("","");
				} else {
					System.out.println("Date Selected: "+formattedDate);
					start(formattedDate, formattedDateEnd);
					//do stuff
					
					
//					boolean result = s.getReservationsByDate(selectedDate);
//					if (result) {
//						String success = "Success! Customer confirmed for " + selectedTime + " on "
//								+ formatDate(selectedDate) + ".";
//						JOptionPane.showMessageDialog(null, success, "Reservation successful!",
//								JOptionPane.PLAIN_MESSAGE);
//
//					} else {
//						String fail = "Sorry, there are no reservations at the selected day and time. Please try another combination!";
//						JOptionPane.showMessageDialog(null, fail, "Reservation unsuccessful",
//								JOptionPane.PLAIN_MESSAGE);
//					}
				}

			}

		});

		addReservation = new JButton("Add Reservation");
		addReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "createReservation");
			}
		});

		deleteReservation = new JButton("Delete Selected Reservation");
		deleteReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = displayResult.getSelectedRow();
				String startdaytime_del = (String) displayResult.getValueAt(row, 0);
				String tid_del = (String) displayResult.getValueAt(row, 3);
				String rid_del = (String) displayResult.getValueAt(row, 4);
				String username_del = (String) displayResult.getValueAt(row, 6);
				System.out.println("Reservation to be deleted: " + startdaytime_del + " " + tid_del + " " + rid_del
						+ " " + username_del);
				s.ownerDeleteBooking(rid_del, startdaytime_del, username_del, tid_del);
				start("","");
			}
		});

		// TODO: select reservations by date

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = c.REMAINDER;
		c.anchor = c.PAGE_START;
		c.insets = new Insets(30, 5, 5, 5);
		this.add(title, c);

		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		this.add(addReservation, c);

		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 10);
		this.add(deleteReservation, c);

		c.gridy = 3;
		c.insets = new Insets(6, 3, 9, 3);
		this.add(datePicker, c);

		c.gridy = 4;
		this.add(displayResultPanel, c);

		this.resID = parent.getRestaurantID();
		JPanel createReservation = new OwnerAddReservation(this, resID);
		restaurantCards.add(createReservation, "createReservation");

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start("","");
			}
		});
	}

	String getRestuarantID() {
		return parent.getRestaurantID();
	}

//	private void update() {
//		displayResultPanel.getViewport().remove(displayResult);
//		start();
//	}
	
	@SuppressWarnings("deprecation")
	private String formatDate(Date date) {
		int day = date.getDate();
		int month = date.getMonth()+1;
		int year = date.getYear()+1900;
		String result;
		
		if(day<10 && month<10){
			result = year + "-0" + month + "-0" + day + " 01:00:00.000000";
		}else if(day<10 && month>=10){
			result = year + "-"+month+"-0"+day + " 01:00:00.000000";
		}else if(day>=10 && month<10){
			result = year + "-0"+month+"-"+day + " 01:00:00.000000";
		}else{
			result = year + "-"+month+"-"+day + " 01:00:00.000000";
		}
		return result;
	}
	@SuppressWarnings("deprecation")
	private String formatDatePlusOne(Date date) {
		int day = date.getDate()+1;
		int month = date.getMonth()+1;
		int year = date.getYear()+1900;
		String result;
		
		if(day<10 && month<10){
			result = year + "-0" + month + "-0" + day + " 01:00:00.000000";
		}else if(day<10 && month>=10){
			result = year + "-"+month+"-0"+day + " 01:00:00.000000";
		}else if(day>=10 && month<10){
			result = year + "-0"+month+"-"+day + " 01:00:00.000000";
		}else{
			result = year + "-"+month+"-"+day + " 01:00:00.000000";
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public void start(String newDate, String newDateEnd) {
		this.resID = parent.getRestaurantID();
		//System.out.println("In ReservationsPanel resID: " + resID);
		displayResultPanel.getViewport().remove(displayResult);

		// Date Picker stuff
		p = new Properties();
		model = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		// show all the things immediately
		Vector<String> colNames = new Vector<String>();
		colNames.add("Start Day Time");
		colNames.add("Duration");
		colNames.add("Party Size");
		colNames.add("TID");
		colNames.add("RID");
		colNames.add("Customer Name");
		colNames.add("Customer Username");

		SQLRestaurant s = new SQLRestaurant();
		Vector<Vector> data;
		if(!newDate.isEmpty()){
			data = s.getReservationsByDate(resID, newDate, newDateEnd);
			System.out.println("getReservationsByDate returned data:"+data);
		}else{
			data = s.getReservations(resID);
			System.out.println("getReservationsByDate returned data:"+data);
		}
		if (data.size() == 0) {
			try{
				JOptionPane.showMessageDialog(null, "No Reservations!","No Reservations", 
						JOptionPane.PLAIN_MESSAGE);
			}catch(Exception e){
				e.getMessage();
			}
			return;
		}
		
		displayResult = new JTable(data, colNames);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		displayResult.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		displayResult.getColumnModel().getColumn(0).setMinWidth(200);
		displayResult.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		displayResult.getColumnModel().getColumn(1).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		displayResult.getColumnModel().getColumn(2).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		displayResult.getColumnModel().getColumn(3).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		displayResult.getColumnModel().getColumn(4).setMaxWidth(80);

		displayResult.setRowHeight(40);
		displayResultPanel.getViewport().add(displayResult);
		
	}

}
