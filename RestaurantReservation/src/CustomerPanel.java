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
        cl.show(customerCards, "customerAddReview");
        cl.show(customerCards, "viewReviews");
	}
	
	public void setUpPanel() {
		this.customerId = parent.getCustomerID();
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);

		customerWelcome = new JLabel("Welcome");
		JButton seeReviewsButton = new JButton("See Reviews");
		JButton addReviewButton = new JButton("Add a Review");
		JButton reserveTable = new JButton("Make a reservation");
		JButton seeReservationsButton = new JButton ("See your reservations");
		JButton deleteReservationsButton = new JButton("Delete your reservations");
		JButton filterReviewsButton = new JButton("Filter Reviews");
		JButton seeMenuButton = new JButton("See Menu Items");
		JButton seeMaxAverage = new JButton("See Highest or Average Ratings");
		
		
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
		c.gridy = 4;
		toolbar.add(seeReservationsButton, c);
		c.gridy = 5;
		toolbar.add(deleteReservationsButton, c);
		c.gridy = 6;
		toolbar.add(filterReviewsButton, c);
		c.gridy = 7;
		toolbar.add(seeMenuButton, c);
		c.gridy = 8;
		toolbar.add(seeMaxAverage, c);
		
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(toolbar, BorderLayout.WEST);
		
		customerCards = new JPanel(new CardLayout());
		add(customerCards, BorderLayout.CENTER);
		JPanel viewReviews = new ViewReviewsPanel(this);
		JPanel customerAddReview = new CustomerAddReviewPanel(this);
		JPanel reservationCard = new CustomerReservationPanel(this);
		JPanel seeOwnReservations = new CustomerViewReservations(this);
		JPanel deleteReservations = new CustomerDeleteReservation(this);
		JPanel filterReviews = new CustomerFilterReviewsPanel(this);
		JPanel seeMenuItems = new CustomerViewMenuPanel(this);
		JPanel seeMaxAverageItems = new CustomerCuisineMaxAverage(this);

		customerCards.add(viewReviews, "ViewReviews");
		customerCards.add(customerAddReview, "AddReview");
		customerCards.add(reservationCard, "makeReservation");
		customerCards.add(seeOwnReservations, "seeReservations");
		customerCards.add(deleteReservations,"deleteReservation");
		customerCards.add(filterReviews, "filterReviews");
		customerCards.add(seeMenuItems,"seeMenuItems");
		customerCards.add(seeMaxAverageItems,"seeMaxAverage");
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
        
        filterReviewsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "filterReviews");				
			}
        	
        });
        
        seeMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "seeMenuItems");				
			}
        	
        });
        
        seeReservationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "seeReservations");				
			}
        	
        });

        deleteReservationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "deleteReservation");	
			}
        });

        
        seeMaxAverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(customerCards.getLayout());
		        cl.show(customerCards, "seeMaxAverage");				
			}
        	
        });

	}
	
	public String getCustomerID() {
		return this.customerId;
	}
	
	
	
}
