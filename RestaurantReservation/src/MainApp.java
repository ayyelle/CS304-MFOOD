import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class MainApp implements ItemListener {
	JPanel cards; //a panel that uses CardLayout
	String customerId;
	String restId;
	String userType;
	String ownerId;

	//variables to help identify employee
	String empID;

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

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField restaurantID = new JTextField();
				JTextField workerID = new JTextField();
				JTextField lastName = new JTextField();
				JPasswordField password = new JPasswordField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("RestaurantID:"),
						restaurantID,

						new JLabel ("Employee ID/OwnerID:"),
						workerID,

						new JLabel("Password"),
						password
				};
				JOptionPane.showMessageDialog(null, inputs, "Restaurant Login", JOptionPane.PLAIN_MESSAGE);
				if (restaurantID.getText().equals("") || password.getText().equals("") || workerID.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill out all the fields!");
				} else {
					SQLRestaurant sql = new SQLRestaurant();
					ArrayList<String> userInfo = sql.getCredentials(restaurantID.getText(), workerID.getText(), password.getText());
					String userType = userInfo.get(0);
					if (userType.equals("OWNER")) {
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, "Home");
						cl.show(cards, "RestaurantOwner");
						restId = userInfo.get(1);
					} else if (userType.equals("EMP")) {
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, "Home");
						cl.show(cards, "RestaurantEmployee");
						restId = userInfo.get(1);	
						empID = workerID.getText();
					}
					else {
						JOptionPane.showMessageDialog(null, "Login failed! try again");
					}
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
				JOptionPane.showMessageDialog(null, inputs, "Customer Login", JOptionPane.PLAIN_MESSAGE);
				SQLCustomer sql = new SQLCustomer();
				if (userName.getText().equals("") || password.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill out all the fields!");
				} else {
					boolean result = sql.checkCredentials(userName.getText(), password.getText());
					if (result) {
						//Would query database to check entry exists
						CardLayout cl = (CardLayout)(cards.getLayout());
						customerId = userName.getText();
						cl.show(cards, "Home");
						cl.show(cards, "Customer");
					} else {
						JOptionPane.showMessageDialog(null, "Login failed! try again");
					}
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
		String iconPath = "images/logo.jpg";
		Icon icon = new ImageIcon(getClass().getResource(iconPath));
		JLabel label = new JLabel(icon);
		home.add(label);
		JLabel welcomeMsg = new JLabel("Welcome to Order up! Choose if you are a customer or a restaurant rep!");
		home.add(welcomeMsg);

		JButton signUp = new JButton("Don't have a customer log-in? Sign up now!");
		home.add(signUp);
		signUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField userName = new JTextField();
				JTextField password = new JTextField();
				JTextField firstName = new JTextField();
				JTextField lastName = new JTextField();
				JTextField phoneNum = new JTextField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("First Name"),
						firstName,
						new JLabel("Last Name"),
						lastName,
						new JLabel("Phone Number"),
						phoneNum,
						new JLabel("userName"),
						userName,
						new JLabel("Password"),
						password
				};
				JOptionPane.showMessageDialog(null, inputs, "New User", JOptionPane.PLAIN_MESSAGE);
				SQLCustomer sql = new SQLCustomer();
				if (userName.getText().equals("") || password.getText().equals("") || firstName.getText().equals("") || lastName.getText().equals("") || phoneNum.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Oops! Please fill in all the fields.");

				} else {
					String result = sql.addUser(userName.getText(), password.getText(), firstName.getText(), lastName.getText(), phoneNum.getText());
					if (result.equals("Success")) {
						//Would query database to check entry exists
						CardLayout cl = (CardLayout)(cards.getLayout());
						customerId = userName.getText();
						cl.show(cards, "Home");
						cl.show(cards, "Customer");
						JOptionPane.showMessageDialog(null, "Success!");
					} else if (result.equals("NotUnique")) {
						JOptionPane.showMessageDialog(null, "That Username is already in use! Please choose another.");
					} else {
						JOptionPane.showMessageDialog(null, "There was an error in making your account. Please try again!");

					}
				}

			}

		});


		JPanel ownerCard = new OwnerPanel(this);
		JPanel empCard = new EmployeePanel(this);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(home, "Home");
		cards.add(customerCard, "Customer");
		cards.add(ownerCard, "RestaurantOwner");
		cards.add(empCard, "RestaurantEmployee");

		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(1500, 800));

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

	public String getEID(){
		return empID;
	}

	public String getCustomerID() {
		return this.customerId;
	}

	public String getRestaurantID() {
		return this.restId;
	}
}