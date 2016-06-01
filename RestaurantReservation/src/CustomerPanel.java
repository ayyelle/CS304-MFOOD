import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CustomerPanel extends JPanel {
	String customerId;
	MainApp parent;
	JPanel toolbar;
	JLabel customerWelcome;
	JPanel customerCards;
	
	public CustomerPanel(MainApp parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		toolbar = new JPanel();
		toolbar.setSize(100, 1000);
		toolbar.setLayout(new GridBagLayout());
		addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                start();
            }
        });
		setUpPanel();	
	}
	
	public void start() {
		this.customerId = parent.getCustomerID();
		customerWelcome.setText("Welcome " + customerId + "!");
        CardLayout cl = (CardLayout)(customerCards.getLayout());
        cl.show(customerCards, "card2");
        cl.show(customerCards, "card1");
	}
	
	public void setUpPanel() {
		this.customerId = parent.getCustomerID();
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);
		//c.anchor = GridBagConstraints.LINE_START;
		//c.weighty = 1.0;
		//c.weightx = 1.0;
		customerWelcome = new JLabel("Welcome");
		JButton seeReviewsButton = new JButton("See Reviews");
		JButton addReviewButton = new JButton("Add a Review");
		JButton reserveTable = new JButton("Make a reservation");
		//c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		toolbar.add(customerWelcome, c);
		
		c.gridx = 0;
		c.gridy = 1;
		toolbar.add(seeReviewsButton, c);
		
		c.gridx = 0;
		c.gridy = 2;
		toolbar.add(addReviewButton, c);
		c.gridy = 3;
		toolbar.add(reserveTable, c);
		
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(toolbar, BorderLayout.WEST);
		

		
        
		customerCards = new JPanel(new CardLayout());
		add(customerCards, BorderLayout.CENTER);
		JPanel card1 = new CustomerViewReviewsPanel(this);
		JPanel card2 = new CustomerAddReviewPanel(this);
		JPanel reservationCard = new CustomerReservationPanel(this);
		customerCards.add(card1, "ViewReviews");
		customerCards.add(card2, "AddReview");
		customerCards.add(reservationCard, "makeReservation");
        seeReviewsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "ViewReviews");
				
			}
        	
        });
        addReviewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "AddReview");
				
			}
        	
        });
        reserveTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "makeReservation");
				
			}
        	
        });

	}
	
	public String getCustomerID() {
		return this.customerId;
	}
	
	
	
}
