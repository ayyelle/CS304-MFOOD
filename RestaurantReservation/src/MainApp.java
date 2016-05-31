import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class MainApp implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    String customerId;
    String restId;
     
    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
    	//Panel to add the upper navigation buttons to
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        //Upper navigation buttons
        JButton homeButton = new JButton("Home");
        JButton restButton = new JButton("Restaurant");
        JButton customerButton = new JButton("Customer");
        
        //Adding actions to the main navigation buttons
        restButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField restaurantID = new JTextField();
				JTextField lastName = new JTextField();
				JPasswordField password = new JPasswordField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("RestaurantID:"),
						restaurantID,

						new JLabel("Password"),
						password
				};
				JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
				System.out.println("You entered " +
						restaurantID.getText() + ", " +
						password.getText());
				if (restaurantID.getText().equals("123")) {
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, "Restaurant");
				} else {
					JOptionPane.showMessageDialog(null, "Login failed! try again");
				}
				
			}
        	
        });
        customerButton.addActionListener(new ActionListener() {
        	

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JTextField userName = new JTextField();
				JPasswordField password = new JPasswordField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("userName"),
						userName,
						new JLabel("Password"),
						password
				};
				JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
				System.out.println("You entered " +
						userName.getText() + ", " +
						password.getText());
				if (!userName.getText().equals("123")) {
					//Would query database to check entry exists
						CardLayout cl = (CardLayout)(cards.getLayout());
						customerId = userName.getText();
						cl.show(cards, "Home");
						cl.show(cards, "Customer");
				} else {
					JOptionPane.showMessageDialog(null, "Login failed! try again");
				}
				
			}
        	
        });
        
        homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c1 = (CardLayout)(cards.getLayout());
				c1.show(cards, "Home");	
			}
        	
        });
        
        Label title = new Label("Welcome to Order Up!");
        title.setForeground(Color.BLUE);
        comboBoxPane.add(title);
        comboBoxPane.add(homeButton);
        comboBoxPane.add(restButton);
        comboBoxPane.add(customerButton);
        comboBoxPane.setBackground(Color.gray);

         
        //Create the "cards" for the card layout
        JPanel customerCard = new CustomerPanel(this);
        JPanel home = new JPanel();
        JLabel welcomeMsg = new JLabel("Welcome to Order up! Choose if you are a customer or a restaurant rep!");
        home.add(welcomeMsg);
         
        JPanel restaurantCard = new JPanel();
        restaurantCard.add(new JTextField("TextField", 20));
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(home, "Home");
        cards.add(customerCard, "Customer");
        cards.add(restaurantCard, "Restaurant");
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.setPreferredSize(new Dimension(1000, 700));
        
    }
     
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Order Up!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        MainApp demo = new MainApp();
        demo.addComponentToPane(frame.getContentPane());
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public String getCustomerID() {
    	return this.customerId;
    }
}