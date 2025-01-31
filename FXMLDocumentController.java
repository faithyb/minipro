
package sample;

import com.jfoenix.controls.JFXButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import module.RandomString;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 *
 * @author JISOO
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane layersignup;
    @FXML
    private AnchorPane layer2;
    @FXML
    private JFXButton signin;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label s1;
    @FXML
    private Label s2;
    @FXML
    private Label s3;
    @FXML
    private JFXButton signup;
    @FXML
    private Label a2;
    @FXML
    private Label b2;
    @FXML
    private Label a1;
    @FXML
    private Label b1;
    @FXML
    private JFXButton btnsignup;
    @FXML
    private JFXButton btnsignin;
    @FXML
    private TextField u1;
    @FXML
    private TextField u2;
    @FXML
    private TextField u3;
    @FXML
    private TextField n1;
    @FXML
    private TextField n2;
    @FXML
    private Label n3;
    @FXML
    private AnchorPane layer1;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        signup.setVisible(false);
        b1.setVisible(false);
        b2.setVisible(false);
        btnsignin.setVisible(false);
        n1.setVisible(false);
        n2.setVisible(false);
        n3.setVisible(false);
        u1.setVisible(true);
        u2.setVisible(true);
        u3.setVisible(true);
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


    @FXML
    private void btn(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);
        
        slide.setToX(491);
        slide.play();
        
        layer1.setTranslateX(-309);
        btnsignin.setVisible(true);
        b1.setVisible(true);
        b2.setVisible(true);
        
        s1.setVisible(true);
        s2.setVisible(true);
        s3.setVisible(true);
        signup.setVisible(true);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        signin.setVisible(false);
        a1.setVisible(false);
        a2.setVisible(false);
        btnsignup.setVisible(false);
        n1.setVisible(true);
        n2.setVisible(true);
        n3.setVisible(true);
        u1.setVisible(false);
        u2.setVisible(false);
        u3.setVisible(false);
        
        slide.setOnFinished((e->{
        
        
        }));
    }

    @FXML
    private void btn2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);
        
        slide.setToX(0);
        slide.play();
        
        layer1.setTranslateX(0);
        btnsignin.setVisible(false);
        b1.setVisible(false);
        b2.setVisible(false);
        
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        signup.setVisible(false);
        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
        signin.setVisible(true);
        a1.setVisible(true);
        a2.setVisible(true);
        btnsignup.setVisible(true);
        n1.setVisible(false);
        n2.setVisible(false);
        n3.setVisible(false);
        u1.setVisible(true);
        u2.setVisible(true);
        u3.setVisible(true);
        
        slide.setOnFinished((e->{
        
        
        }));
    }

    @FXML
    private void btnsignup(MouseEvent event) {
    	
    	// System.out.println("info : " + u1.getText());
    	  if(u1.getText().isEmpty() || u2.getText().isEmpty() || u3.getText().isEmpty()) {
    		 String tilte = "Data Field";
             String message = "Please fill in all the required information";
             TrayNotification tray = new TrayNotification();
             AnimationType type = AnimationType.POPUP;
         
             tray.setAnimationType(type);
             tray.setTitle(tilte);
             tray.setMessage(message);
             tray.setNotificationType(NotificationType.ERROR);
             tray.showAndDismiss(Duration.millis(3000));
    	 } else {
    		 
    		 String name = u1.getText();
             String email = u2.getText();
             String password = u3.getText();
             RandomString gen = new RandomString(8, ThreadLocalRandom.current());
             String rand = gen.nextString();

             StringBuilder sb = new StringBuilder();
             sb.append(rand + " ");
             sb.append(name + " ");
             sb.append(email + " ");
             sb.append(password);

             File file = new File("users.txt");
             try {
                 FileOutputStream fOut = new FileOutputStream(file, true);
                 OutputStreamWriter osw = new OutputStreamWriter(fOut);
                 osw.write(sb.toString() + "\n");
                 osw.flush();
                 osw.close();

                 String tilte = "Create Acount";
                 String message = "Acount Successfully Created!";
                 TrayNotification tray = new TrayNotification();
                 AnimationType type = AnimationType.POPUP;
             
                 tray.setAnimationType(type);
                 tray.setTitle(tilte);
                 tray.setMessage(message);
                 tray.setNotificationType(NotificationType.SUCCESS);
                 tray.showAndDismiss(Duration.millis(3000));
             } catch (IOException e) {
                 e.printStackTrace();
             }


             System.out.println("id : " +rand+ " name : " + name + " email : " + email + " password : " + password);
    	 }  
    	
    }

    @FXML
    private void sign(MouseEvent event) {
        
    }

    @FXML
    private void click(ActionEvent event) {
    	 if(n1.getText().isEmpty() || n2.getText().isEmpty()) { 
    		 String tilte = "Data Field";
             String message = "Please fill in all the required information";
             TrayNotification tray = new TrayNotification();
             AnimationType type = AnimationType.POPUP;
         
             tray.setAnimationType(type);
             tray.setTitle(tilte);
             tray.setMessage(message);
             tray.setNotificationType(NotificationType.ERROR);
    	 } else {
    		    String email = n1.getText();
    	        String password = n2.getText();

    	        if(ValidateUser(email,password) == true) {
    	        	 String tilte = "Successful";
    	             String message =  "Welcome : "  + n1.getText();
    	             TrayNotification tray = new TrayNotification();
    	             AnimationType type = AnimationType.POPUP;
    	         
    	             tray.setAnimationType(type);
    	             tray.setTitle(tilte);
    	             tray.setMessage(message);
    	             tray.setNotificationType(NotificationType.SUCCESS);
    	             tray.showAndDismiss(Duration.millis(3000));
    	             
    	             loadHome(event);
    	        }else {
    	        	 String tilte ="Sign In";
    	             String message = "Error Username "+" ' "+n1.getText()+" ' "+", and Password "+" ' "+n2.getText()+" ' "+" Wrong";
    	             TrayNotification tray = new TrayNotification();
    	             AnimationType type = AnimationType.POPUP;
    	         
    	             tray.setAnimationType(type);
    	             tray.setTitle(tilte);
    	             tray.setMessage(message);
    	             tray.setNotificationType(NotificationType.ERROR);
    	             tray.showAndDismiss(Duration.millis(3000));
    	        }
    	 }
    	     
    }
    

    public boolean ValidateUser(String email,String Password) {
        // Open the file
        FileInputStream fstream = null;
        BufferedReader br = null;
        boolean login = false;
        try {
            fstream = new FileInputStream("users.txt");
            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console - do what you want to do
               // System.out.println (strLine);
                StringTokenizer token = new StringTokenizer(strLine);
                String id = token.nextToken();
                String name = token.nextToken();
                String emails = token.nextToken();
                String pass = token.nextToken();
               // System.out.println("Status : " +login+ " email : " + emails + " password : " + pass);
                if((email.equalsIgnoreCase(emails)) && (Password.equalsIgnoreCase(pass))) {
                    login = true;
                    WriteID(id);
                    fstream.close();
                   // System.out.println("Status : " +login+ " email : " + emails + " password : " + pass);
                    return login;
                }
            }
            //Close the input stream
            fstream.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return login;
    }
    

    public void WriteID(String id) {
        PrintWriter fw = null;
        try {
            fw = new PrintWriter("current.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(id);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
    }
    
    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }
}

