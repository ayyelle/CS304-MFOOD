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

public class OwnerPanel extends JPanel implements RestaurantPanel {
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

	public OwnerPanel(MainApp parent) {
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
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);

		restaurantWelcome = new JLabel("Welcome");
		restaurantName = new JLabel("Restaurant Name");
		JButton ownerPropertiesButton = new JButton("Manage Restaurant");
		JButton seeReviewsButton = new JButton("Reviews");
		JButton seeReservationsButton = new JButton("Reservations");
		JButton seeTablesButton = new JButton("Tables");
		JButton seeMenuButton = new JButton("Menu Items");
		JButton addMenuButton = new JButton("Add Menu Items");
		JButton addEmployeeButton = new JButton("Add New Employee");

		// all same width buttons
		ownerPropertiesButton.setPreferredSize(new Dimension(160, 30));
		seeReviewsButton.setPreferredSize(new Dimension(160, 30));
		seeReservationsButton.setPreferredSize(new Dimension(160, 30));
		seeTablesButton.setPreferredSize(new Dimension(160, 30));
		seeMenuButton.setPreferredSize(new Dimension(160, 30));
		addMenuButton.setPreferredSize(new Dimension(160, 30));
		addEmployeeButton.setPreferredSize(new Dimension(160, 30));

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		toolbar.add(restaurantWelcome, c);
		
		c.gridx = 0;
		c.gridy = 1;
		toolbar.add(restaurantName, c);

		c.gridx = 0;
		c.gridy = 3;
		toolbar.add(ownerPropertiesButton, c);

		c.gridx = 0;
		c.gridy = 4;
		toolbar.add(seeReviewsButton, c);

		c.gridy = 5;
		toolbar.add(seeReservationsButton, c);

		c.gridy = 6;
		toolbar.add(seeTablesButton, c);

		c.gridy = 7;
		toolbar.add(seeMenuButton, c);

		c.gridy = 8;
		toolbar.add(addMenuButton, c);

		c.gridy = 9;
		toolbar.add(addEmployeeButton, c);

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

		JPanel seeTables = new OwnerTablesPanel(this);
		restaurantCards.add(seeTables, "seeTables");

		JPanel seeMenu = new OwnerViewMenuPanel(this, "owner");
		restaurantCards.add(seeMenu, "SeeMenu");

		JPanel addMenu = new OwnerAddMenuPanel(this);
		restaurantCards.add(addMenu, "AddMenu");

		JPanel addEmployee = new OwnerAddEmployeePanel(this);
		restaurantCards.add(addEmployee, "AddEmployee");

		ownerPropertiesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "ownerProperties");
				;
			}
		});

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
		
		seeTablesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "seeTables");
				;
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

		addMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "AddMenu");
				;
			}
		});

		addEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (restaurantCards.getLayout());
				cl.show(restaurantCards, "AddEmployee");
				;
			}
		});

	}
	public void start() {
		this.restaurantId = parent.getRestaurantID();
		r = new SQLRestaurant();
		restaurantName.setText(r.getRestaurantName(restaurantId));
		userNameString = r.getOwnerFromRID(restaurantId);
		restaurantWelcome.setText("Welcome " + userNameString + "!");
		cl = (CardLayout) (restaurantCards.getLayout());
		cl.show(restaurantCards, "SeeReservations");
	}

	public String getRestaurantID() {
		return this.restaurantId;
	}

}
