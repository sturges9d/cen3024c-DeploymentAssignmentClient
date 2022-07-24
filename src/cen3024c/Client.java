package cen3024c;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Stephen Sturges Jr
 * @version 07/22/2022
 *
 */
public class Client extends Application {
	
	@FXML
	private TextField valueField;
	@FXML
	private TextField responseField;
	@FXML
	private Button checkValueButton;
	
	public void handleCheckValueButtonClick() {
		System.out.println("Check Value button was clicked.");
		try {
			// Get input from user.
			int value = Integer.parseInt(valueField.getText());
			
			// Create socket to connect to the server.
			Socket connection = new Socket("localhost", 1236);
			// Create input stream to receive data from the server.
			DataInputStream input = new DataInputStream(connection.getInputStream());
			// Create output stream to send data to the server.
			DataOutputStream output = new DataOutputStream(connection.getOutputStream());
			
			// Send user's input to the server.
			output.writeInt(value);
			
			// Receive response from the server.
			BufferedReader serverResponse = new BufferedReader(new InputStreamReader(input));
			
			// Display result.
			String result = serverResponse.readLine();
			System.out.println("Is " + value + " a prime number?\nResponse: " + result); // Display result in console for debugging.
			responseField.setText(result); // Display result in UI.
			
			// Close socket.
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Connect Client.java class with ClientUI.fxml to create UI.
		Parent root = FXMLLoader.load(getClass().getResource("ClientUI.fxml"));
		Scene scene = new Scene(root,605,214);
		primaryStage.setTitle("Prime Number Checker");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}