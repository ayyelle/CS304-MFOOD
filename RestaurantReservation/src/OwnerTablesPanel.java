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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class OwnerTablesPanel extends JPanel{

	RestaurantPanel parent;
	String resID;
	JButton addTable;
	JButton deleteTable;
	JLabel restaurantLabel;
	JLabel title;
	JScrollPane displayResultPanel;
	JTable displayResult;
	SQLRestaurant s;
//	
//	new reservation variables
//	String startdaytimeNew, partysizeNew, durationNew, tidNew, ridNew, usernameNew;

	public OwnerTablesPanel(RestaurantPanel parent) {
		this.parent = parent;
		this.resID = parent.getRestaurantID();
		s = new SQLRestaurant();
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = new JLabel("Manage Tables", JLabel.CENTER);
		title.setFont(new Font(title.getName(), Font.PLAIN, 20));
		displayResult = new JTable();

		displayResultPanel = new JScrollPane();
		displayResultPanel.setPreferredSize(new Dimension(900, 400));
		
		deleteTable = new JButton("Delete Table");
		deleteTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = displayResult.getSelectedRow();
				String tid = (String) displayResult.getValueAt(row, 0);
				s.deleteTable(resID, tid);
				update();
			}
		});
		
		//placement
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = c.REMAINDER;
		c.anchor = c.PAGE_START;
		c.insets = new Insets(30, 5, 5, 5);
		this.add(title, c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(deleteTable, c);

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

	private void update() {
		displayResultPanel.getViewport().remove(displayResult);
		start();
	}


	public void start() {
		
		displayResultPanel.getViewport().remove(displayResult);
		
		//show all the things immediately
		
		//column names
		Vector<String> colNames = new Vector<String>();
		colNames.add("Table ID");
		colNames.add("Table Size");
		colNames.add("Number Booked");

		resID = parent.getRestaurantID();
		Vector<Vector> data = s.getTablesForRestaurant(resID);
		displayResult = new JTable(data, colNames);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		displayResult.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		

		displayResult.setRowHeight(40);
		displayResultPanel.getViewport().add(displayResult);
		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no tables for this restaurant.",
					"No Tables Found", JOptionPane.PLAIN_MESSAGE);
		}
	}
		
}
