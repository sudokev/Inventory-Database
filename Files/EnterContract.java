import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class EnterContract {

	public JFrame frame;
	private JTextField amountTF;
	private JTextField priceTF;
	private static JComboBox<String> supplierCB;
	private static JComboBox<String> itemCB;
	private JTextArea previewBox;
	private JButton enterContractButton;
	private JButton enterItemButton;
	private JButton resetContractButton;
	
	static DefaultComboBoxModel<String> supplierCBModel = new DefaultComboBoxModel<String>();
	static DefaultComboBoxModel<String> itemCBModel = new DefaultComboBoxModel<String>();
	
	private int currentContractID;
	private ArrayList<itemInContract> items = new ArrayList<itemInContract>();
	
	// Create the view - Constructor
	public EnterContract() { initialize(); }

	// Set up the view
	public static void setupView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterContract window = new EnterContract();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Launch the view
	public static void main(String[] args) {
		setupView();
	}
	
	public static void populateSupplierCB() {
		try {
			// Setup & then execute the query 
			Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM supplier;"; // Enter the query
			
			supplierCBModel = new DefaultComboBoxModel<String>();
			// Suppliers
			ResultSet supplierResult = stm.executeQuery(query); // Execute the query
			while (supplierResult.next()) {
				// Create variables for the password and full name found in the database for this user.
				String supplierID = supplierResult.getString("supplierID"); String supplierName = supplierResult.getString("name");
				
				// Populate combo box here:
				supplierCBModel.addElement("ID: " + supplierID + " Name: " + supplierName);
			}
			supplierCB.setModel(supplierCBModel);
			
			// Close the connection to the database after sign in is complete.
			connection.close();
		} catch (Exception e) {
			ShowMessage.message("Warning: No supplier data was found in the database. Please enter at least one supplier first.");
			System.out.println(e);
		}
	}
	
	public static void populateItemCB() {
		try {
			// Setup & then execute the query 
			Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM item;"; // Enter the query
			
			itemCBModel = new DefaultComboBoxModel<String>();
			// Items
			ResultSet itemResult = stm.executeQuery(query); // Execute the query
			while (itemResult.next()) {
				String itemID = itemResult.getString("itemID"); String itemDescription = itemResult.getString("itemDescription");
				System.out.println("ID: " + itemID + " Description: " + itemDescription);
				
				// Populate combo box here:
				itemCBModel.addElement("ID: " + itemID + " Desc: " + itemDescription);
			}
			itemCB.setModel(itemCBModel);
			
			// Close the connection to the database after sign in is complete.
			connection.close();
		} catch (Exception e) {
			ShowMessage.message("Warning: No item data was found in the database. Please enter at least one item first.");
			System.out.println(e);
		}
	}

	// Initialize the contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 623, 534);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		getContractID();
		createBackButton(); createTitleLabel(); createPreviewBox();
		createSupplierLabel(); createCashSignLabel();
		createSupplierCB(); createItemLabel(); createItemCB();
		createAmountLabel(); createAmountTF(); createPriceLabel();
		createPriceTF(); createEnterItemButton(); createEnterContractButton();
		createResetContractButton(); createContractPreviewLabel();
	}
	
	public void getContractID() {
		try {
			// Setup & then execute the query 
			Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT count(contractID) FROM contract;"; // Enter the query
			
			// Result
			ResultSet result = stm.executeQuery(query); // Execute the query
			result.first();
			// Create variables for the password and full name found in the database for this user.
			String numberOfRows = result.getString("count(contractID)");
			
			// Turn 'numberOfRows' into an integer and add 1 to get the next contractID
			currentContractID = Integer.parseInt(numberOfRows) + 1;
			
			System.out.println("The next contract ID is: " + currentContractID);
			
			// Close the connection to the database after sign in is complete.
			connection.close();
		} catch (Exception e) {
			ShowMessage.message("An unknown error occurred. Please try again later.");
			System.out.println(e);
		}
	}
	
	public void createTitleLabel() {
		JLabel titleLabel = new JLabel("Enter Contract");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(230, 31, 136, 25);
		frame.getContentPane().add(titleLabel);
	}
	
	public void createSupplierLabel() {
		JLabel supplierLabel = new JLabel("Supplier");
		supplierLabel.setBounds(28, 121, 196, 16);
		frame.getContentPane().add(supplierLabel);
	}
	
	public void createSupplierCB() {
		supplierCB = new JComboBox<String>();
		supplierCB.setBounds(23, 139, 199, 33);
		populateSupplierCB();
		frame.getContentPane().add(supplierCB);
	}
	
	public void createItemLabel() {
		JLabel itemLabel = new JLabel("Item");
		itemLabel.setBounds(30, 184, 196, 16);
		frame.getContentPane().add(itemLabel);
	}
	
	public void createItemCB() {
		itemCB = new JComboBox<String>();
		itemCB.setBounds(25, 202, 199, 33);
		populateItemCB();
		frame.getContentPane().add(itemCB);
	}
	
	public void createAmountLabel() {
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setBounds(28, 247, 196, 16);
		frame.getContentPane().add(amountLabel);
	}
	
	public void createAmountTF() {
		amountTF = new JTextField();
		amountTF.setColumns(10);
		amountTF.setBounds(25, 265, 199, 33);
		frame.getContentPane().add(amountTF);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(amountTF, 6, true);
	}
	
	public void createPriceLabel() {
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setBounds(28, 313, 196, 16);
		frame.getContentPane().add(priceLabel);
	}
	
	public void createPriceTF() {
		priceTF = new JTextField();
		priceTF.setColumns(10);
		priceTF.setBounds(25, 331, 199, 33);
		frame.getContentPane().add(priceTF);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(priceTF, 8);
	}
	
	public void createCashSignLabel() {
		JLabel cashSignLabel = new JLabel("$");
		cashSignLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cashSignLabel.setBounds(14, 336, 10, 20);
		frame.getContentPane().add(cashSignLabel);
	}
	
	public void createEnterItemButton() {
		enterItemButton = new JButton("Enter Item");
		enterItemButton.setBounds(25, 379, 200, 35);
		frame.getContentPane().add(enterItemButton);
		enterItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterItemButtonClicked();
			}
		});
	}
	
	public Boolean checkArrayForItem(itemInContract item) {
		Boolean itemIsAlreadyInArray = false;
		for (itemInContract i: items) { 
			if (item.getItemID().equals(i.getItemID())) { itemIsAlreadyInArray = true; } }
		return itemIsAlreadyInArray;
	}
	
	public void enterItemButtonClicked() {
		if (!amountTF.getText().isBlank() && !priceTF.getText().isBlank()) {
			// Create an object:
			itemInContract item = new itemInContract();
			
			// Set values (do this each time the ‘enterItem’ button is clicked):
			item.setContractID(currentContractID);
			item.setItemID(getSelectedItemID());
			item.setContractAmount(amountTF.getText());
			item.setContractPrice(priceTF.getText());
			
			// Add ‘item’ to the 'items' array list (as well as other tasks) as long as it's not already in there:
			if (!checkArrayForItem(item)) {
				items.add(item);
				// Update the preview to show the latest information about the contract
				updateContractPreview();
				// Don't allow the user to switch to a different supplier after adding an item
				supplierCB.setEnabled(false);
			} else {
				ShowMessage.message("This item is already in this contract preview.");
			}
		} else { // Ran when the user didn't enter a value in a text field
			ShowMessage.message("Please enter values in all text fields.");
		}
	}
	
	boolean firstItemWasAdded = false;
	
	public void updateContractPreview() {
		String selectedSupplierInfo = (String) supplierCB.getSelectedItem();
		
		String selectedItemInfo = (String) itemCB.getSelectedItem();
		String amountAndPrice = "Amount: " + amountTF.getText() + " | Price: $" + priceTF.getText();
		String itemInfo = selectedItemInfo + "\n" + amountAndPrice;
		
		if (!firstItemWasAdded) { 
			// Add both the supplier and the item to the preview
			previewBox.append("Supplier: \n" + selectedSupplierInfo + "\n\nItem: \n" + itemInfo + "\n");
			firstItemWasAdded = true; enterContractButton.setEnabled(true); resetContractButton.setEnabled(true);
		} else {
			// Only add the item to the preview
			previewBox.append("\nItem: \n" + itemInfo + "\n");
		}
	}
	
	public String getSelectedItemID() {
		// Get the Item ID
		String selectedItem = (String)itemCB.getSelectedItem();
		String itemID = selectedItem.substring(selectedItem.indexOf(" "), selectedItem.indexOf(" D")).stripLeading();
		System.out.println("ID of selected item = " + itemID);
		return itemID;
	}
	
	public void createEnterContractButton() {
		enterContractButton = new JButton("Enter Contract");
		enterContractButton.setEnabled(false);
		enterContractButton.setBounds(25, 447, 285, 40);
		frame.getContentPane().add(enterContractButton);
		enterContractButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertContract();
				insertItemsInContract();
			}
		});
	}
	
	public void insertContract() {
		if (!items.isEmpty()) {
			try {
				// Setup & then execute the query
				Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
				String query = "INSERT INTO contract VALUES (?, ?, ?);"; // Enter the query
				PreparedStatement stm = connection.prepareStatement(query);
				stm.setInt(1, currentContractID);
				stm.setDate(2, Date.getCurrentDate());
				stm.setInt(3, Integer.parseInt(getSelectedSupplierID()));
				stm.executeUpdate(); // Execute the query

				// Close the connection to the database after insertion of data is complete
				connection.close();
				ShowMessage.message("Contract Entered Successfully");
				// Disable enter contract button and enter item button to avoid issues - force
				// the user to reset the preview
				enterContractButton.setEnabled(false);
				enterItemButton.setEnabled(false);
			} catch (Exception e) {
				ShowMessage.message("An error occurred or input is invalid or missing. Please try again.");
				System.out.println(e);
			}
		} else if (items.isEmpty()) { ShowMessage.message("Please enter an item."); }
	}
	
	public String getSelectedSupplierID() {
		// Get the Supplier ID
		String selectedSupplier = (String)supplierCB.getSelectedItem();
		String supplierID = selectedSupplier.substring(selectedSupplier.indexOf(" "), selectedSupplier.indexOf(" N")).stripLeading();
		System.out.println("ID of selected supplier = " + supplierID);
		return supplierID;
	}

	public void insertItemsInContract() {
		if (!items.isEmpty()) {
			System.out.println("Size of 'items' array list = " + items.size());
			for (int i = 0; i <= items.size() - 1; i++) { // Go through each item in the array and add it to the database.
				try {
					// Setup & then execute the query
					Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
					String query = "INSERT INTO itemsInContract VALUES (?, ?, ?, ?);"; // Enter the query
					PreparedStatement stm = connection.prepareStatement(query);
					stm.setInt(1, items.get(i).getContractID());
					stm.setInt(2, Integer.parseInt(items.get(i).getItemID()));
					stm.setInt(3, Integer.parseInt(items.get(i).getContractAmount()));
					stm.setDouble(4, Double.parseDouble(items.get(i).getContractPrice()));
					stm.executeUpdate(); // Execute the query
					
					// Close the connection to the database after insertion of data is complete
					connection.close();
				} catch (Exception e) {
					ShowMessage.message("An error occurred or input is invalid or missing. Please try again.");
					System.out.println(e);
				}
			}
		}
	}
	
	public void createResetContractButton() {
		resetContractButton = new JButton("Reset Contract Preview");
		resetContractButton.setEnabled(false);
		resetContractButton.setBounds(310, 447, 285, 40);
		frame.getContentPane().add(resetContractButton);
		resetContractButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtonClicked();
			}
		});
	}
	
	public void resetButtonClicked() {
		// Get new contractID
		getContractID();
		
		// Reset the array list of items
		items = new ArrayList<itemInContract>();
		
		// Reset the contract preview text pane
		previewBox.setText(""); firstItemWasAdded = false;
		
		// Enable the Supplier CB, enter contract button, and enter item button
		supplierCB.setEnabled(true); resetContractButton.setEnabled(false);
		enterContractButton.setEnabled(false); enterItemButton.setEnabled(true);
	}
	
	public void createContractPreviewLabel() {
		JLabel contractPreviewLabel = new JLabel("Contract Preview");
		contractPreviewLabel.setBounds(371, 117, 105, 16);
		frame.getContentPane().add(contractPreviewLabel);
	}
	
	public void createPreviewBox() {
		previewBox = new JTextArea();
		previewBox.setEditable(false);
		previewBox.setBounds(252, 139, 343, 275);
		frame.getContentPane().add(previewBox);
	}
	
	public void createBackButton() {
		JButton backButton = new JButton("");
		backButton.setIcon(new ImageIcon(this.getClass().getResource("/backArrow.png")));
		backButton.setBounds(25, 20, 73, 46);
		frame.getContentPane().add(backButton);
		
		// Create/Perform Button Action:
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
}

class itemInContract {
	
	private int contractID;
	private String itemID;
	private String contractAmount;
	private String contractPrice;
	
	public void setContractID(int entry) {
		contractID = entry;
	}
	
	public int getContractID() {
		return contractID;
	}
	
	public void setItemID(String entry) {
		itemID = entry;
	}
	
	public String getItemID() {
		return itemID;
	}
	
	public void setContractAmount(String entry) {
		contractAmount = entry;
	}
	
	public String getContractAmount() {
		return contractAmount;
	}
	
	public void setContractPrice(String entry) {
		contractPrice = entry;
	}
	
	public String getContractPrice() {
		return contractPrice;
	}
	
}