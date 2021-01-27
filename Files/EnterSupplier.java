import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class EnterSupplier {

	public JFrame frame;
	private JTextField supplierIDTF;
	private JTextField supplierNameTF;
	private JLabel supplierIDLabel;
	private JLabel supplierNameLabel;
	private JTextField streetAddressTF;
	private JLabel streetAddressLabel;
	private JTextField cityTF;
	private JTextField stateTF;
	private JLabel cityLabel;
	private JLabel stateLabel;
	private JTextField zipCodeTF;
	private JLabel zipCodeLabel;

	// Create the view - Constructor
	public EnterSupplier() { initialize(); }
	
	// Set up the view
	public static void setupView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterSupplier window = new EnterSupplier();
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

	// Initialize the contents of the frame.
	private void initialize() {
		// I think this sets up the coordinates
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 637);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createTitleLabel();
		createSupplierIDLabel(); createSupplierIDTF(); createSupplierNameLabel(); 
		createSupplierNameTF(); createStreetAddressLabel(); createStreetAddressTF(); 
		createCityLabel(); createCityTF(); createStateLabel(); 
		createStateTF(); createZipCodeLabel(); createZipCodeTF();
		createEnterButton(); createBackButton();
	}
	
	public void createTitleLabel() {
		JLabel titleLabel = new JLabel("Enter Supplier");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(155, 31, 142, 25);
		frame.getContentPane().add(titleLabel);
	}
	
	public void createSupplierIDLabel() {
		supplierIDLabel = new JLabel("Supplier ID Number");
		supplierIDLabel.setBounds(32, 99, 155, 16);
		frame.getContentPane().add(supplierIDLabel);
	}
	
	public void createSupplierIDTF() {
		supplierIDTF = new JTextField();
		supplierIDTF.setBounds(29, 119, 396, 33);
		frame.getContentPane().add(supplierIDTF);
		supplierIDTF.setColumns(10);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(supplierIDTF, 6, true);
	}
	
	public void createSupplierNameLabel() {
		supplierNameLabel = new JLabel("Supplier Name");
		supplierNameLabel.setBounds(32, 171, 105, 16);
		frame.getContentPane().add(supplierNameLabel);
	}
	
	public void createSupplierNameTF() {
		supplierNameTF = new JTextField();
		supplierNameTF.setColumns(10);
		supplierNameTF.setBounds(29, 191, 396, 33);
		frame.getContentPane().add(supplierNameTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(supplierNameTF, 20, false);
	}
	
	public void createStreetAddressLabel() {
		streetAddressLabel = new JLabel("Street Address");
		streetAddressLabel.setBounds(32, 243, 105, 16);
		frame.getContentPane().add(streetAddressLabel);
	}
	
	public void createStreetAddressTF() {
		streetAddressTF = new JTextField();
		streetAddressTF.setColumns(10);
		streetAddressTF.setBounds(29, 263, 396, 33);
		frame.getContentPane().add(streetAddressTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(streetAddressTF, 30, false);
	}
	
	public void createCityLabel() {
		cityLabel = new JLabel("City");
		cityLabel.setBounds(32, 320, 105, 16);
		frame.getContentPane().add(cityLabel);
	}

	public void createCityTF() {
		cityTF = new JTextField();
		cityTF.setColumns(10);
		cityTF.setBounds(29, 340, 396, 33);
		frame.getContentPane().add(cityTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(cityTF, 25, false);
	}
	
	public void createStateLabel() {
		stateLabel = new JLabel("State");
		stateLabel.setBounds(32, 392, 105, 16);
		frame.getContentPane().add(stateLabel);
	}

	public void createStateTF() {
		stateTF = new JTextField();
		stateTF.setColumns(10);
		stateTF.setBounds(29, 412, 396, 33);
		frame.getContentPane().add(stateTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(stateTF, 20, false);
	}
	
	public void createZipCodeLabel() {
		zipCodeLabel = new JLabel("Zip Code");
		zipCodeLabel.setBounds(32, 464, 105, 16);
		frame.getContentPane().add(zipCodeLabel);
	}

	public void createZipCodeTF() {
		zipCodeTF = new JTextField();
		zipCodeTF.setColumns(10);
		zipCodeTF.setBounds(29, 484, 396, 33);
		frame.getContentPane().add(zipCodeTF);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(zipCodeTF, 10, true);
	}
	
	public void createEnterButton() {
		JButton enterButton = new JButton("Enter");
		enterButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		enterButton.setBounds(29, 545, 396, 45);
		frame.getContentPane().add(enterButton);
		
		// Create/Perform Button Action:
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Run code here for inserting data into database
				enterButtonClicked();
			}
		});
	}
	
	public void enterButtonClicked() {
		try {
			// Setup & then execute the query 
			Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
			String query = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?);"; // Enter the query
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, Integer.parseInt(supplierIDTF.getText()));
			stm.setString(2, supplierNameTF.getText());
			stm.setString(3, streetAddressTF.getText());
			stm.setString(4, cityTF.getText());
			stm.setString(5, stateTF.getText());
			stm.setInt(6, Integer.parseInt(zipCodeTF.getText()));
			stm.executeUpdate(); // Execute the query
			ShowMessage.message("Supplier Entered Successfully");
			
			// Close the connection to the database after insertion of data is complete
			connection.close();
		} catch (java.lang.NumberFormatException e) {
			ShowMessage.message("One or more required text fields are empty or invalid input was entered.");
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			ShowMessage.message("The Supplier ID you entered is already in use. Please enter a different Supplier ID.");
		} catch (Exception e) {
			ShowMessage.message("An error occurred or input is invalid. Please try again.");
			System.out.println(e);
		}
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
