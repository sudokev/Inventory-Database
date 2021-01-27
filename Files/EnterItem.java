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

public class EnterItem {

	public JFrame frame;
	private JTextField itemIDTF;
	private JTextField descriptionTF;

	// Create the view - Constructor
	public EnterItem() { initialize(); }
	
	// Set up the view
	public static void setupView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterItem window = new EnterItem();
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

	// Initialize the contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 338);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createTitleLabel(); createItemIDTF(); createItemIDLabel();
		createBackButton(); createDescriptionTF(); createDescriptionLabel();
		createEnterButton();
	}
	
	public void createTitleLabel() {
		JLabel titleLabel = new JLabel("Enter Item");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(171, 31, 98, 25);
		frame.getContentPane().add(titleLabel);
	}
	
	public void createItemIDTF() {
		itemIDTF = new JTextField();
		itemIDTF.setColumns(10);
		itemIDTF.setBounds(29, 119, 396, 33);
		frame.getContentPane().add(itemIDTF);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(itemIDTF, 8, true);
	}

	public void createItemIDLabel() {
		JLabel itemIDLabel = new JLabel("Item ID Number");
		itemIDLabel.setBounds(32, 99, 105, 16);
		frame.getContentPane().add(itemIDLabel);
	}
	
	public void createDescriptionTF() {
		descriptionTF = new JTextField();
		descriptionTF.setColumns(10);
		descriptionTF.setBounds(29, 185, 396, 33);
		frame.getContentPane().add(descriptionTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(descriptionTF, 20, false);
	}
	
	public void createDescriptionLabel() {
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(32, 165, 105, 16);
		frame.getContentPane().add(descriptionLabel);
	}
	
	public void createEnterButton() {
		JButton enterButton = new JButton("Enter");
		enterButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		enterButton.setBounds(29, 246, 396, 45);
		frame.getContentPane().add(enterButton);
		
		// Create/Perform Button Action:
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterButtonClicked();
			}
		});
	}
	
	public void enterButtonClicked() {
		try {
			// Setup & then execute the query 
			Class.forName("com.mysql.cj.jdbc.Driver"); // Setup the class driver?
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/css", "root", "rootuser"); // Connect to database
			String query = "INSERT INTO item VALUES (?, ?);"; // Enter the query
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, Integer.parseInt(itemIDTF.getText()));
			stm.setString(2, descriptionTF.getText());
			stm.executeUpdate(); // Execute the query
			ShowMessage.message("Item Entered Successfully");
			
			// Close the connection to the database after insertion of data is complete
			connection.close();
		} catch (java.lang.NumberFormatException e) {
			ShowMessage.message("One or more required text fields are empty or invalid input was entered.");
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			ShowMessage.message("The Item ID you entered is already in use. Please enter a different Item ID.");
		} catch (Exception e) {
			ShowMessage.message("An error occurred. Please try again.");
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