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
	JButton delete;
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
		delete = new JButton("Delete Review");

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
		
		c.gridx = 1;
		c.gridy = 5;
		this.add(delete, c);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}
		});
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = displayResult.getSelectedRow();
				String userName = (String) displayResult.getValueAt(row, 0);
				String comment = (String) displayResult.getValueAt(row, 2);
				if (deleteReview(resID, userName, comment)) {
					update();
					JOptionPane.showMessageDialog(null, "Review has been deleted!",
														"Delete review", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Failed to delete review!",
														"Delete review", JOptionPane.PLAIN_MESSAGE);
				}
			}

		});
	}

	public void start() {
		this.resID = parent.getRestaurantID();
		displayResult();
	}
	
	private void displayResult() {
		Vector<String> colNames = new Vector<String>();
		colNames.add("User");
		colNames.add("Rating");
		colNames.add("Comments");

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
	};

	private void update() {
		displayResultPanel.getViewport().remove(displayResult);
		displayResult();
	}
	
	private boolean deleteReview(String rid, String userName, String comment){
		return s.deleteReview(rid, userName, comment);
	}

}
