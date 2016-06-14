import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

public class CustomerFilterReviewsPanel extends JPanel {
	CustomerPanel parent;
	String customerID;
	
	JLabel titleLabel;
	JLabel subTitleLabel;
	
	JPanel ratingPanel;
	JLabel ratingLabel;
	Vector<String> ratingFilterOptions;
	JComboBox ratingOptions;
	
	JButton submit;
	
	JScrollPane tableScroll;
	JTable table;
	SQLRestaurant s;
	GridBagConstraints gc;

	
	
	public CustomerFilterReviewsPanel(CustomerPanel parent) {
		this.parent = parent;
		this.setLayout(new GridBagLayout());
		s = new SQLRestaurant();
		gc = new GridBagConstraints();
				
		initializeElements();
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = gc.HORIZONTAL;
		this.add(titleLabel, gc);
		gc.gridy = 1;
		this.add(subTitleLabel, gc);
		
		
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridy = 2;
		this.add(ratingPanel, gc);
		
		gc.insets = new Insets(5, 5, 5, 5);
		gc.gridy = 3;
		gc.fill = gc.NONE;
		this.add(submit, gc);
		
		gc.gridy = 4;
		this.add(tableScroll, gc);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent evt) {
				start();
			}

		});
	}
	

	private void initializeElements() {
		titleLabel = new JLabel("Select a rating to filter out the best restaurants!");
		titleLabel.setFont(new Font(titleLabel.getName(), Font.PLAIN, 20));
		subTitleLabel = new JLabel("The only reviews shown will be from restaurants where all reviews are at least the specified rating");
		subTitleLabel.setFont(new Font(subTitleLabel.getName(), Font.PLAIN, 16));

		ratingPanel = new JPanel();
		ratingLabel = new JLabel("Choose a rating filter: ", JLabel.TRAILING);
		ratingFilterOptions = new Vector<String>();
		for (int i = 1; i<=5; i++) {
			String rating = String.valueOf(i);
			ratingFilterOptions.add(rating);
		}
		ratingOptions = new JComboBox(ratingFilterOptions);
		ratingPanel.add(ratingLabel);
		ratingPanel.add(ratingOptions);
		
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ratingSelection = (String) ratingOptions.getSelectedItem();
				int numResults = fillTable(ratingSelection);
				if (numResults == 0) {
					String noResultsStr = "No restaurants found with all ratings above " + ratingSelection + ". Try a lower rating!";
					JOptionPane.showMessageDialog(null,noResultsStr, "No restaurants found", JOptionPane.PLAIN_MESSAGE);

				}
				
			}

			
		});

		table = new JTable();
		
		tableScroll = new JScrollPane();
		tableScroll.setPreferredSize(new Dimension(900, 400));
				
	}
	
	private int fillTable(String ratingSelection) {
		Vector<Vector> reviewResults = s.getFilteredReviews(ratingSelection);
		Vector<String> colNames = new Vector<String>();
		colNames.add("Restaurant");
		colNames.add("User");
		colNames.add("Rating");
		colNames.add("Comments");
		table = new JTable(reviewResults, colNames);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(70);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(700);
		table.setRowHeight(40);
		
		tableScroll.getViewport().add(table);
		return reviewResults.size();
		
	}


	private void start() {
		this.customerID = parent.getCustomerID();
		table = new JTable();
		tableScroll.getViewport().remove(table);
		
	}


}
