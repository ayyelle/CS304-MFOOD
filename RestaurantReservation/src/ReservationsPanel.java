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

public class ReservationsPanel extends JPanel{
	
	restaurantPanel parent;
	String resID;
	JButton submit;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;

	public ReservationsPanel(restaurantPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("View Reservations", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(900, 400));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = c.REMAINDER;
		c.anchor = c.PAGE_START;
		c.insets = new Insets(30, 5, 5, 5);
		this.add(title, c);

		c.gridx = 1;
		c.gridy = 4;
		this.add(displayResultPanel, c);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}
		});
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

		SQLRestaurant s = new SQLRestaurant();

		Vector<Vector> data = s.getReservations(resID);
		displayResult = new JTable(data, colNames);

		displayResult.getColumnModel().getColumn(0).setMinWidth(200);
		displayResult.getColumnModel().getColumn(1).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(2).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(3).setMaxWidth(80);
		displayResult.getColumnModel().getColumn(4).setMaxWidth(80);

		displayResult.setRowHeight(40);
		displayResultPanel.getViewport().add(displayResult);
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no reservations for this restaurant yet!",
					"No Reviews Found", JOptionPane.PLAIN_MESSAGE);
		}
	}

		
}
