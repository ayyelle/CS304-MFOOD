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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CustomerViewReviewsPanel extends JPanel {
	CustomerPanel parent;
	String customerId;
	JButton submit;
	JComboBox restaurantComboBox;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTextArea displayResult;
	
	public CustomerViewReviewsPanel(CustomerPanel parent) {
		this.parent = parent;
		
		String[] restaurantOptions = {"Burgoo", "SushiPlace", "TeryakiPlace-Robson" };
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	title = new JLabel("View Reviews", JLabel.CENTER);
	title.setFont(new Font(title.getName(), Font.PLAIN, 20));
	restaurantComboBox = new JComboBox(restaurantOptions);
	restaurantLabel = new JLabel("Select a restaurant: ", JLabel.TRAILING);
	
	displayResult = new JTextArea();
	displayResult.setLineWrap(true);
	displayResultPanel = new JScrollPane(displayResult);
	displayResult.setSize(400, 500);
	displayResultPanel.setPreferredSize(new Dimension(600, 200));
	displayResult.setVisible(false);
	displayResult.setText("SHOULDNT SEE THIS");
	submit = new JButton("Submit");
	
	submit.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String restaurant = (String) restaurantComboBox.getSelectedItem();
			System.out.println(restaurant);
			displayResult.setText("Rating: 3\n"
					+ "Restaurant comments: This is OK");
			displayResult.setVisible(true);
			
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
	this.add(displayResultPanel, c);
    
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
		displayResult.setText("");
	}
    
}

