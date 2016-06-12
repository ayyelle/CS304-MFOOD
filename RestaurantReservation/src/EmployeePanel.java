import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EmployeePanel extends JPanel implements RestaurantPanel {
	String restaurantId;
	MainApp parent;
	SQLRestaurant r;
	JPanel toolbar;
	JLabel restaurantWelcome;
	JLabel restaurantName;
	JLabel userName;
	JPanel restaurantCards;
	String userNameString;
	CardLayout cl;
	String empID;

	public EmployeePanel(MainApp parent) {
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

	public void setUpPanel() {
		// this.restaurantId = parent.getRestaurantID();
		System.out.println("OWNERPANEL.JAVA: " + restaurantId);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);

		restaurantWelcome = new JLabel("Welcome");
		restaurantName = new JLabel("Restaurant Name");
		JButton seeReviewsButton = new JButton("Reviews");
		JButton seeReservationsButton = new JButton("Reservations");
		JButton seeMenuButton = new JButton("Menu Items");

		// all same width buttons
		seeReviewsButton.setPreferredSize(new Dimension(160, 30));
		seeReservationsButton.setPreferredSize(new Dimension(160, 30));
		seeMenuButton.setPreferredSize(new Dimension(160, 30));

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		toolbar.add(restaurantWelcome, c);
		
		c.gridx = 0;
		c.gridy = 1;
		toolbar.add(restaurantName, c);

		c.gridx = 0;
		c.gridy = 4;
		toolbar.add(seeReviewsButton, c);

		c.gridy = 5;
		toolbar.add(seeReservationsButton, c);

		c.gridy = 6;
		toolbar.add(seeMenuButton, c);

		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(toolbar, BorderLayout.WEST);

		// Panels
		restaurantCards = new JPanel(new CardLayout());
		add(restaurantCards, BorderLayout.CENTER);

		JPanel ownerProperties = new OwnerRestaurantProperties(this);
		restaurantCards.add(ownerProperties, "ownerProperties");

		JPanel viewReviews = new OwnerReviews(this);
		restaurantCards.add(viewReviews, "ViewReviews");

		JPanel seeReservations = new OwnerReservationsPanel(this, restaurantCards);
		restaurantCards.add(seeReservations, "SeeReservations");

		JPanel seeMenu = new OwnerViewMenuPanel(this, "emp");
		restaurantCards.add(seeMenu, "SeeMenu");

		seeReviewsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "ViewReviews");
			}
		});

		seeReservationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "SeeReservations");
			}
		});
		

		seeMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "SeeMenu");
				;
			}
		});


	}
	public void start() {
		this.restaurantId = parent.getRestaurantID();
		this.empID = parent.getEID();
		r = new SQLRestaurant();
		restaurantName.setText(r.getRestaurantName(restaurantId));
		userNameString = r.getEmployeeName(restaurantId, empID);
		restaurantWelcome.setText("Welcome " + userNameString + "!");
		cl = (CardLayout) (restaurantCards.getLayout());
		cl.show(restaurantCards, "SeeReservations");
	}

	public String getRestaurantID() {
		return this.restaurantId;
	}

}
