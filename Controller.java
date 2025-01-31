package sample;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller implements Initializable{
	
	@FXML
    private VBox vbox;

    private Parent fxml;

    private Boolean validate = true;
    @FXML
    private void open_home(ActionEvent event) {
        if(validate) {           
            loadHome(event);
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	 
	 private void loadHome(ActionEvent event) {
	        try {
	            //add you loading or delays - ;-)
	            Node node = (Node) event.getSource();
	            Stage stage = (Stage) node.getScene().getWindow();
	            //stage.setMaximized(true);
	            stage.close();
	            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("DashFX.fxml")));
	            stage.setScene(scene);
	            stage.show();

	        }
	        catch (IOException ex) {
	            System.err.println(ex.getMessage());
	        }
	    }
	 
}
