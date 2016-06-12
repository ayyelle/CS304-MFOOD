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

public class OwnerReviews extends JPanel {
	RestaurantPanel parent;
	String resID;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;

	// Restaurant Side
	String ownerID;
	String empID;

	public OwnerReviews(RestaurantPanel parent) {
		this.parent = parent;
		s = new SQLRestaurant();

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("View Reviews", JLabel.CENTER);
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
		System.out.println("OwnerReviews rid: " + resID);
		
		Vector<String> colNames = new Vector<String>();
		colNames.add("User");
		colNames.add("Rating");
		colNames.add("Comments");

		// only get reviews for restaurant owner
		// if from restaurant side

		Vector<Vector> data = s.getReviewsByRID(resID);
		displayResult = new JTable(data, colNames);

		displayResult.getColumnModel().getColumn(0).setMaxWidth(200);
		displayResult.getColumnModel().getColumn(1).setMaxWidth(50);

		displayResult.setRowHeight(40);
		displayResultPanel.getViewport().add(displayResult);
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no reviews for this restaurant yet!",
					"No Reviews Found", JOptionPane.PLAIN_MESSAGE);
		}

	}

}
