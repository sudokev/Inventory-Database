import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EnterProject {

	public JFrame frame;
	private JTextField projectIDTF;
	private JTextField projectDataTF;
	
	// Create the view - Constructor
	public EnterProject() { initialize(); }
	
	// Set up the view 
	public static void setupView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterProject window = new EnterProject();
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

		createTitleLabel();
		createProjectIDTF();
		createProjectIDLabel();
		createBackButton();
		createProjectDataTF();
		createDescriptionLabel();
		createEnterButton();
	}

	public void createTitleLabel() {
		JLabel titleLabel = new JLabel("Enter Project");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(158, 31, 125, 25);
		frame.getContentPane().add(titleLabel);
	}

	public void createProjectIDTF() {
		projectIDTF = new JTextField();
		projectIDTF.setColumns(10);
		projectIDTF.setBounds(29, 119, 396, 33);
		frame.getContentPane().add(projectIDTF);
		// Consume key event when character length is exceeded or character is a letter
		Limit.limitCharacters(projectIDTF, 6, true);
	}

	public void createProjectIDLabel() {
		JLabel projectIDLabel = new JLabel("Project ID Number");
		projectIDLabel.setBounds(32, 99, 144, 16);
		frame.getContentPane().add(projectIDLabel);
	}

	public void createProjectDataTF() {
		projectDataTF = new JTextField();
		projectDataTF.setBounds(29, 185, 396, 33);
		frame.getContentPane().add(projectDataTF);
		// Consume key event when character length is exceeded
		Limit.limitCharacters(projectDataTF, 20, false);
	}

	public void createDescriptionLabel() {
		JLabel projectDataLabel = new JLabel("Project Data");
		projectDataLabel.setBounds(32, 165, 105, 16);
		frame.getContentPane().add(projectDataLabel);
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
			String query = "INSERT INTO project VALUES (?, ?);"; // Enter the query
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, Integer.parseInt(projectIDTF.getText()));
			stm.setString(2, projectDataTF.getText());
			stm.executeUpdate(); // Execute the query
			ShowMessage.message("Project Entered Successfully");

			// Close the connection to the database after insertion of data is complete
			connection.close();
		} catch (java.lang.NumberFormatException e) {
			ShowMessage.message("One or more required text fields are empty or invalid input was entered.");
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			ShowMessage.message("The Project ID you entered is already in use. Please enter a different Project ID.");
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
