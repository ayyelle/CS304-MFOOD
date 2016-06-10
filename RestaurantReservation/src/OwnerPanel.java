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

public class OwnerPanel extends JPanel implements restaurantPanel {
	String restaurantId;
	MainApp parent;
	JPanel toolbar;
	JLabel restaurantWelcome;
	JPanel restaurantCards;
	
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
	
	public void start() {
		this.restaurantId = parent.getRestaurantID();
		restaurantWelcome.setText("Welcome Owner of restaurant: RID" + restaurantId + "!");
        CardLayout cl = (CardLayout)(restaurantCards.getLayout());
        cl.show(restaurantCards, "SeeReservations");
        cl.show(restaurantCards, "viewReviews");
        cl.show(restaurantCards, "seeMenuItems");
        cl.show(restaurantCards, "addMenuItems");
	}
	
	public void setUpPanel() {
		this.restaurantId = parent.getRestaurantID();
		System.out.println("OWNERPANEL.JAVA: " + restaurantId);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);

		restaurantWelcome = new JLabel("Welcome");
		JButton ownerPropertiesButton = new JButton("Manage Restaurant");
		JButton seeReviewsButton = new JButton("See Reviews");
		JButton seeReservationsButton = new JButton("See Reservations");
		JButton seeMenuButton = new JButton("See Menu Items");
		JButton addMenuButton = new JButton("Add Menu Items");

		c.gridx = 0;
		c.gridy = 0;
		toolbar.add(restaurantWelcome, c);
		
		c.gridx = 0;
		c.gridy = 1;
		toolbar.add(ownerPropertiesButton, c);
		
		c.gridx = 0;
		c.gridy = 2;
		toolbar.add(seeReviewsButton, c);

		 c.gridy = 3;
		 toolbar.add(seeReservationsButton, c);
		 
		 c.gridy = 4;
		 toolbar.add(seeMenuButton, c);
		 
		 c.gridy = 5;
		 toolbar.add(addMenuButton, c);
		 
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(toolbar, BorderLayout.WEST);
		
		restaurantCards = new JPanel(new CardLayout());
		add(restaurantCards, BorderLayout.CENTER);
		
		JPanel ownerProperties = new OwnerRestaurantProperties(this);
		restaurantCards.add(ownerProperties, "ownerProperties");
		
		JPanel viewReviews = new restaurantViewReviewsPanel(this);
		restaurantCards.add(viewReviews, "ViewReviews");
		//Just adding another button as a place-holder
		JPanel seeReservations = new JPanel();
		restaurantCards.add(seeReservations, "SeeReservations");
		
		JPanel seeMenu = new RestaurantViewMenuPanel(this);
		restaurantCards.add(seeMenu, "SeeMenu");
		
		JPanel addMenu = new OwnerAddMenuPanel(this);
		restaurantCards.add(addMenu, "AddMenu");
		
		
		
		ownerPropertiesButton.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		CardLayout cl = (CardLayout) (restaurantCards.getLayout());
        		cl.show(restaurantCards, "ownerProperties");;
        	}
        });
		
        seeReviewsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(restaurantCards.getLayout());
		        cl.show(restaurantCards, "ViewReviews");
				
			}
        	
        });
        
        seeReservationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(restaurantCards.getLayout());
		        cl.show(restaurantCards, "SeeReservations");
			}
        });
        
        seeMenuButton.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		CardLayout cl = (CardLayout) (restaurantCards.getLayout());
        		cl.show(restaurantCards, "SeeMenu");;
        	}
        });
        
        addMenuButton.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		CardLayout cl = (CardLayout) (restaurantCards.getLayout());
        		cl.show(restaurantCards, "AddMenu");;
        	}
        });

	}
	
	public String getRestaurantID() {
		return this.restaurantId;
	}
	
	
	
	
	
}
