import java.awt.CardLayout;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class OwnerReservationsPanel extends JPanel{
	
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
	
	//new reservation variables
	String startdaytimeNew, partysizeNew, durationNew, tidNew, ridNew, usernameNew;

	public OwnerReservationsPanel(RestaurantPanel parent, JPanel restaurantCards) {

		this.parent = parent;
		this.restaurantCards = restaurantCards;
		s = new SQLRestaurant();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("View Reservations", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(900, 400));

		addReservation = new JButton("Add Reservation");
		addReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "createReservation");
			}
		});
		
		
		//TODO: delete reservation
		deleteReservation = new JButton("Delete Selected Reservation");
		deleteReservation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = displayResult.getSelectedRow();
				String startdaytime_del = (String) displayResult.getValueAt(row, 0);
				String tid_del = (String) displayResult.getValueAt(row, 3);
				String rid_del = (String) displayResult.getValueAt(row, 4);
				String username_del = (String) displayResult.getValueAt(row, 6);
				System.out.println("Reservation to be deleted: " + startdaytime_del + 
						" " + tid_del + " " + rid_del + " " + username_del);
				s.ownerDeleteBooking(rid_del, startdaytime_del, username_del, tid_del);
				update();
			}
		});
			
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
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 10);
		this.add(deleteReservation, c);

		c.gridx = 1;
		c.gridy = 4;
		this.add(displayResultPanel, c);
		
		this.resID = parent.getRestaurantID();
		JPanel createReservation = new OwnerAddReservation(this, resID);
		restaurantCards.add(createReservation, "createReservation");

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}
		});
	}
	String getRestuarantID(){
		return parent.getRestaurantID();
	}
	private void update() {
		displayResultPanel.getViewport().remove(displayResult);
		start();
	}

	public void start() {
		this.resID = parent.getRestaurantID();
		System.out.println("In ReservationsPanel resID: " + resID);
		displayResultPanel.getViewport().remove(displayResult);
		
		//show all the things immediately
		Vector<String> colNames = new Vector<String>();
		colNames.add("Start Day Time");
		colNames.add("Duration");
		colNames.add("Party Size");
		colNames.add("TID");
		colNames.add("RID");
		colNames.add("Customer Name");
		colNames.add("Customer Username");

		SQLRestaurant s = new SQLRestaurant();

		Vector<Vector> data = s.getReservations(resID);
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
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no reservations for this restaurant yet!",
					"No Reviews Found", JOptionPane.PLAIN_MESSAGE);
		}
	}

		
}
