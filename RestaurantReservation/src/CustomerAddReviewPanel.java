import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class CustomerAddReviewPanel extends JPanel {
	JTextArea comments;
	JLabel commentsLabel;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JComboBox ratings;
	JLabel ratingLabel;
	JScrollPane commentTextPane;
	
	String customerId;
	CustomerPanel parent;
	
	SQLCustomer sqlCustomer;


	public CustomerAddReviewPanel(CustomerPanel parent) {
		this.parent = parent;

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		//TODO
		//need to fill with actual restaurants from database
		String[] restaurantComboBoxOptions = { "Earls-Vancouver", "Burgoo-Vancouver", "Sushi California-Broadway" };
		String[] ratingOptions = { "1", "2", "3", "4", "5" };

		this.setLayout(new GridBagLayout());
		comments = new JTextArea();
		comments.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		commentTextPane = new JScrollPane(comments);
		commentTextPane.setPreferredSize(new Dimension(400, 100));
		comments.setLineWrap(true);
		//comments.setPreferredSize(new Dimension(10, 40));
		commentsLabel = new JLabel("Comments: ", JLabel.TRAILING);
		submit = new JButton("Submit");
		restaurantComboBox = new JComboBox(restaurantComboBoxOptions);
		restaurantLabel = new JLabel("Select Restaurant: ", JLabel.TRAILING);
		ratings = new JComboBox(ratingOptions);
		ratingLabel = new JLabel("Select Rating: ", JLabel.TRAILING);

		
		c.gridx = 0;
		c.gridy = 0;
		this.add(restaurantLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(restaurantComboBox, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(ratingLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(ratings, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(commentsLabel, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(commentTextPane, c);
		
		c.gridx = 3;
		c.gridy = 2;
		this.add(submit, c);

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Get information from the fields
				String comment = comments.getText();
				System.out.println(comment);
				
				String selection = (String) restaurantComboBox.getSelectedItem();
				System.out.println(selection);
				
				String ratingSelection = (String) ratings.getSelectedItem();
				System.out.println(ratingSelection);
				
				comments.setText("");
				if (comment.equals("")) {
					JOptionPane.showMessageDialog(null,"Please enter a comment!", "Add Reviews", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,"Thank you for your comment!", "Add Reviews", JOptionPane.PLAIN_MESSAGE);
	
				}
				
				addReview(selection,comment,ratingSelection);
				
			}
			
		});
		
		addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentShown(ComponentEvent evt) {
	            start();
	        }
	    });
		

	}
	
	//add review via SQLCustomer method
	
	public void addReview(String restaurantName,String comment,String rating){
		sqlCustomer.addReview(restaurantName, comment, rating);
		
	}
	
	
	public void start() {
		this.customerId = parent.getCustomerID();
		System.out.println(customerId);
	}
	

}

