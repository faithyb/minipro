package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import module.ConnectGraph;
import module.Data;
import module.Graph;
import module.GraphController;
import module.RandomString;
import module.Graph.Vertex;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class DashFX implements Initializable {
	
	    @FXML
	    private VBox pnItems = null;
	    
	   @FXML
	    private Button btnhome;

	    @FXML
	    private Button btnInput;

	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btngraphdt;

	    @FXML
	    private Button btngraph;

	    @FXML
	    private Button btnsignout;
	    
	    
	    @FXML
	    private AnchorPane parent;
	    @FXML
	    private LineChart<?, ?> lineChart;
	    
	    @FXML
	    private PieChart pieChart;
	  
	    @FXML
	    private NumberAxis y;
	    @FXML
	    private CategoryAxis x;
	    
	    @FXML
	    private Pane pnlinput;


	    @FXML
	    private Pane pnldatalink;

	    @FXML
	    private Pane pnlgraph;
	    
	    @FXML
	    private Pane pnlsignout;

	    @FXML
	    private Pane pnlhome;
	    
	    @FXML
	    private Label lblname;
	    
	    //Table
	    @FXML
	    private TableView<Data> tbData;
	    @FXML
	    public TableColumn<Data, String> pronvice;

	    @FXML
	    public TableColumn<Data, Double> mwattage;

	    @FXML
	    public TableColumn<Data, Integer> mumplace;
	   

	    @FXML
	    private JFXButton btnAdd;
	    
	    @FXML
	    private JFXButton btChangeUpdate;
	    
	    @FXML
	    private JFXButton btnRemoveData;
	    
	    @FXML
	    private JFXTextField txtmonthave;
	    
	    @FXML
	    private JFXTextField txtnumhigusage;
	    
	    @FXML
	    private JFXTextField txtMaxpowersuply;
	    
	    @FXML
	    private JFXTextField txtmaxretio;
	    
	    @FXML
	    private Label lblStatus;
	    
	    @FXML
	    private JFXComboBox<String> cmdprovname;
	    
	   Timer timer;
	    
	    int lengh;
	    
	    private List<Data> myData =  new ArrayList<Data>();
	    private List<String> currentP =  new ArrayList<String>();
	    private List<Data> myDatas =  new ArrayList<Data>();
	    private GraphController graphcontroller = new GraphController();
	    private Graph graph ;
	    private boolean ischanged = false;
	    private String montly,waatage;
	    private int index; 
	 
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	String uname = getName(GetId());
	    	lblname.setText(uname);
	    	pnlhome.setStyle("-fx-background-color : #ffffff;-fx-background-radius: 10");
	        pnlhome.toFront();
	        btnRemoveData.setVisible(false);
	        LoadGraph();
		    fillChart();
		    loadChart();
		    graph = graphcontroller.getGraph();

		      DrawCirlce();
		      timer = new Timer();
		       timer.schedule(new RemindTask(),
		                       0,        //initial delay
		                       1*1000);  //subsequent rate
		     //  ConnectGraph col = new ConnectGraph(); 
		      //col.FindShortestPath(); 
	    }
	    
	    @FXML
	    private void btnCalculate(MouseEvent event) {
	    	   
	    	//  pnlgraph.getChildren().clear();
	    	  pieChart.getData().clear();
	    	  lblStatus.setText("");
			  int maxwats = Integer.parseInt(txtMaxpowersuply.getText());
			  int ration = Integer.parseInt(txtmaxretio.getText());
			  double totalLocation = 0;
			  
			  List<PieChart.Data> pdata = new ArrayList<PieChart.Data>();
			  int per = ration * 10;
			   
			  for (Data ds : myData) {
			        int r = ds.getNumhighwattsusage() / ration;
			        double v = (r / myData.size()) * per;
			        double curtot = r + v;
			        totalLocation += r + v;
			        PieChart.Data psl = new PieChart.Data(ds.getName(), curtot);
					pdata.add(psl);
					 
			  }
			  
			  
		        
		        double max = (double)maxwats;
		        
		        System.out.println("max : "+ max + " tot : " + totalLocation);
		        
		        double  savedwats = maxwats - totalLocation;
		        double  perc = (savedwats / max) * 100;
		        
                String texts = perc + " % Saved! " + savedwats + " Watts";
                lblStatus.setText(texts);
			  
			    ObservableList<PieChart.Data> pieChartData =  FXCollections.observableArrayList(pdata);
			   // System.out.println("Names : " + pieChartData.toString());
		    
	        pieChart.getData().addAll(pieChartData);
	        pieChart.setTitle("Power Share");
	        
	        
	        final Label caption = new Label("");
	        caption.setTextFill(Color.DARKORANGE);
	        caption.setStyle("-fx-font: 24 arial;");

	        for (final PieChart.Data data : pieChart.getData()) {
	            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
	                e -> {
	                    double total = 0;
	                    for (PieChart.Data d : pieChart.getData()) {
	                        total += d.getPieValue();
	                    }
	                    caption.setTranslateX(e.getSceneX());
	                    caption.setTranslateY(e.getSceneY());
	                    String text = String.format("%.1f%%", 100*data.getPieValue()/total) ;
	                    caption.setText(text);
	                 }
	                );
	        }
	        
	        pnlgraph.getChildren().addAll(caption);
	    }
	    
	    
	    @FXML
	    private void btChangeUpdate(MouseEvent event) {
	    	 if(ischanged == false) {
	    		 ischanged = true;
	    		 btnAdd.setText("Update");
	    		 btChangeUpdate.setText("Update");
	    		 btnRemoveData.setVisible(true);
	    		 System.out.println("on update");
	    		 
	    		

	    		 if(!cmdprovname.getItems().isEmpty()) {
	    			 cmdprovname.getItems().clear();
	    			 currentP.clear();
	    			 loadCurrent();
	    		 } 
	    		
	    		 ObservableList<String> ps = FXCollections.observableArrayList(currentP);
	    	     cmdprovname.setItems(ps);
	    	     
	    		 
	    	 }else {
	    		 ischanged = false;
	    		 btnAdd.setText("Add Data");
	    		 btChangeUpdate.setText("Add");
	    		 btnRemoveData.setVisible(false);
	    		 System.out.println("on Add");
	    		 
	    		 cmdprovname.getItems().clear();
	    		 cmdprovname.setItems(getUnused(myDatas));
	    	 } 
	    }
	    
	    @FXML
	    private void btnRemoveData(MouseEvent event) {
	    	String province = cmdprovname.getValue();
   		     String original = province + " " + waatage + " " + montly;

   		     
   		     System.out.println("Delete : " + original);
				try {
					DeleteSelectedLine(original, "PowerUsage.txt");
					
		    		  int positiion = cmdprovname.getSelectionModel().getSelectedIndex();
		    		  graphcontroller.removeVertAndEdge(positiion);
		    		  graph = graphcontroller.getGraph();
		    		 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			
	    }
	    
	    @FXML
	    private void ProvCombo(ActionEvent event) {

	    	 if(ischanged) {
	    		 //  System.out.println(cmdprovname.getValue() +" Size : " + myDatas.size());
	    		   String province = null;
	    		   int count = 0;
                   
	    		   if(!cmdprovname.getItems().isEmpty()) {
	    			   province = cmdprovname.getValue();
	    			   for (Data data : myDatas) {
	   					if(province.equalsIgnoreCase(data.getName())) {
	   						txtmonthave.setText(""+data.getWatspermonth());;
	   			            txtnumhigusage.setText(""+ data.getNumhighwattsusage());
	   			            waatage = "" +data.getWatspermonth();
	   			            montly = ""+data.getNumhighwattsusage();
	   			            index =count;
	   			             
	   					}
	   					count++;
	   				  }
	    		   }
	    		 
	    	 } 
	     
	    }
	   
	    @FXML
	    private void btnAdd(MouseEvent event) {
	    	 if(ischanged) {
	    		  String province = cmdprovname.getValue();
	    		  String original = province + " " + waatage + " " + montly;
	    		  String replace = province + " " +  txtmonthave.getText().toString() + " " + txtnumhigusage.getText().toString();
	    		  replaceSelected(original, replace, "PowerUsage.txt");
	    		
	    	  }
	    	  else {
	    		  String name = cmdprovname.getValue().toString();
	              int mummonth = Integer.parseInt(txtmonthave.getText().toString());
	              int numhigu = Integer.parseInt(txtnumhigusage.getText().toString());
	             
	               StringBuilder sb = new StringBuilder();
	               sb.append(name + " ");
	               sb.append(mummonth + " ");
	               sb.append(numhigu);
	               
	               
	               File file = new File("PowerUsage.txt");
	               try {
	                   FileOutputStream fOut = new FileOutputStream(file, true);
	                   OutputStreamWriter osw = new OutputStreamWriter(fOut);
	                   osw.write(sb.toString() + "\n");
	                   osw.flush();
	                   osw.close();
	                   
	                
	                   Vertex<String>  v = new Vertex(name,mummonth);
	                   graphcontroller.setV(v,numhigu);
	                   graph = graphcontroller.getGraph();

	                   String tilte = "Data";
	                   String message = "Data Successfully Added!";
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
	    	  }
	    	 
	    	 txtmonthave.setText("");
	    	 txtnumhigusage.setText(""); 
	    }
	    
	    public void DrawCirlce() {
	    	    pnldatalink.getChildren().clear();;
	    	    loadCircleData();
	    	    
	    	    final Canvas canvas = new Canvas(936,583);
	    	    GraphicsContext apen = canvas.getGraphicsContext2D();    	    
	    	    int r = 15;	    	        	   
	    	    int counter = 0;
	    	    List<Integer> storex = new ArrayList<Integer>();
	    	    List<Integer> storey = new ArrayList<Integer>();
	    	    if(!myData.isEmpty()) {
	    	    	  Random  rndx = new Random();
	    	    	 for (Data data : myData) {
	    	    		 
	    	    		 int y =  nextIntInRange(50, 500, rndx);
	    	    		 int x =   nextIntInRange(20, 800, rndx);
	    	    		 
	    	    		 if(counter == 0) {
	    	    			apen.setFill(Color.web("#393351"));
	 	 		    	    apen.fillOval(x-r, y-r,r*2, r*2);
	 	 		    	    
	 	 		    	    apen.setStroke(Color.BLACK);
	 	 		    	    apen.strokeOval(x-r, y-r,r*2, r*2);
	 	 		    	    
	 	 		    	    apen.setFont(Font.font("Arial",14));
	 	 		    	    apen.setFill(Color.web("#393351"));
	 	 		    	    apen.fillText(data.getName(),x+r,y-r);
	 	 		    	    
	 	 		    	    apen.setFont(Font.font("Arial",14));
	 	 		    	    apen.setFill(Color.BROWN);
	 	 		    	    apen.fillText("(" + data.getWatspermonth()+ " Watts)",x+r+15,y-r-15);
	 	 		    	    
	 	 		    	    apen.setStroke(Color.BLACK);
	 	 		    	    apen.strokeLine(x,y, x, y);
	    	    		 }else {
	    	    			apen.setFill(Color.web("#393351"));
	 	 		    	    apen.fillOval(x-r, y-r,r*2, r*2);
	 	 		    	    
	 	 		    	    apen.setStroke(Color.BLACK);
	 	 		    	    apen.strokeOval(x-r, y-r,r*2, r*2);
	 	 		    	    
	 	 		    	    apen.setFont(Font.font("Arial",14));
	 	 		    	    apen.setFill(Color.web("#393351"));
	 	 		    	    apen.fillText(data.getName(),x+r,y-r);
	 	 		    	    	 	 		    	  
	 	 		    	    apen.setFont(Font.font("Arial",14));
	 	 		    	    apen.setFill(Color.BROWN);
	 	 		    	    apen.fillText("(" + data.getWatspermonth()+" Watts)",x+r+15,y-r-15);
	 	 		    	    
	 	 		    	    apen.setStroke(Color.BLACK);
	 	 		    	    apen.strokeLine(x,y, storex.get(counter-1),storey.get(counter-1));
	    	    		 }
	 	    	       
	 		    	   
	 		    	    storex.add(x);
	 		    	    storey.add(y);
	 		    	    
	 		    	    counter++;
	 		    	   System.out.println(" X : " + x + " Y : " + y + " Data : " + data.getNumhighwattsusage());    	
	 				}	    	    
	    	    	 
	    	    }
	    	   

		      pnldatalink.getChildren().add(canvas);
		    
	    	//System.out.println("" + getrand);    	 
	    }
	    
	    private int nextIntInRange(int min, int max, Random rng) {
	    	   if (min > max) {
	    		      throw new IllegalArgumentException("Cannot draw random int from invalid range [" + min + ", " + max + "].");
	    		   }
	    		   int diff = max - min;
	    		   if (diff >= 0 && diff != Integer.MAX_VALUE) {
	    		      return (min + rng.nextInt(diff + 1));
	    		   }
	    		   int i;
	    		   do {
	    		      i = rng.nextInt();
	    		   } while (i < min || i > max);
	    		   return i;
    	}
	    
	   private  ObservableList<String> Province = FXCollections.observableArrayList(
	            "Western Cape", "Eastern Cape", "Northern Cape", "North West", "Free State", "Kwazulu Natal", "Gauteng","Limpopo","Mpumlanga");

	    
	  /*  private ObservableList<Data> dataInformation = FXCollections.observableArrayList(
	    	new Data("Gauteng",2, 6),
            new Data("Limpopo",4, 7),
            new Data("North West",8, 13),
            new Data("Mpulanga",10, 15)
	    	
	    ); */
	    
	    
	   private void loadChart()
	    {
		   
		 //  int maxwats = Integer.parseInt(txtMaxpowersuply.getText());
		 //  int ration = Integer.parseInt(txtmaxretio.getText());

		     List<PieChart.Data> pdata = new ArrayList<PieChart.Data>();
		   
			    for(Data dt : myData) {
				   PieChart.Data psl = new PieChart.Data(dt.getName(), dt.getNumhighwattsusage());
				   pdata.add(psl);
				 
			   } 
			   
			 
			    ObservableList<PieChart.Data> pieChartData =  FXCollections.observableArrayList(pdata);
			    System.out.println("Names : " + pieChartData.toString());
		     /*  for(int k = 0;k < pdata.size();k++) {
		    	   //pieChart.getData().add(pdata.get(k));

		       } */
	      /*  PieChart.Data slice1 = new PieChart.Data("Classes", 213);
	        PieChart.Data slice2 = new PieChart.Data("Attendance"  , 67);
	        PieChart.Data slice3 = new PieChart.Data("Teachers" , 36); */

	       /* pieChart.getData().add(slice1);
	        pieChart.getData().add(slice2); */
	        pieChart.getData().addAll(pieChartData);
	        pieChart.setTitle("Power Share");
	        
	        final Label caption = new Label("");
	        caption.setTextFill(Color.DARKORANGE);
	        caption.setStyle("-fx-font: 24 arial;");

	        for (final PieChart.Data data : pieChart.getData()) {
	            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
	                e -> {
	                    double total = 0;
	                    for (PieChart.Data d : pieChart.getData()) {
	                        total += d.getPieValue();
	                    }
	                    caption.setTranslateX(e.getSceneX());
	                    caption.setTranslateY(e.getSceneY());
	                    String text = String.format("%.1f%%", 100*data.getPieValue()/total) ;
	                    caption.setText(text);
	                 }
	                );
	        }
	        
	        pnlgraph.getChildren().addAll(caption);

	    }
	    
	    private void LoadGraph() {
	    
	    	FileInputStream fstream = null;	    	
	    	List<Data> Datas = new ArrayList<Data>();
	    	
	        try {
	            fstream = new FileInputStream("PowerUsage.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                StringTokenizer token = new StringTokenizer(strLine);
	                Data data = new Data();
	                
	                if(token.countTokens() == 3) {
	                	  
		                String name = token.nextToken();
		                int mummonth = Integer.parseInt(token.nextToken().toString());
		                int numhigu = Integer.parseInt(token.nextToken().toString());
		                data.setName(name);
		                data.setWatspermonth(mummonth);
		                data.setNumhighwattsusage(numhigu);
	                }else if(token.countTokens() == 4) {
	                	 String name = token.nextToken();
	                	 String second = token.nextToken();
	                	 String full = name + " " + second;
	 	                int mummonth = Integer.parseInt(token.nextToken().toString());
	 	                int numhigu = Integer.parseInt(token.nextToken().toString());
	 	                data.setName(full);
	 	                data.setWatspermonth(mummonth);
     	                data.setNumhighwattsusage(numhigu);	 	               
	                }
	                Datas.add(data);
	                myDatas.add(data);
	              
	            }
	            //System.out.println (name);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	
	        lengh = Datas.size();
	        ObservableList<Data> dataInformation = FXCollections.observableList(Datas)  ;
	    	
	    	
	    	 pronvice.setCellValueFactory(new PropertyValueFactory<Data,String>("name"));
	    	 mwattage.setCellValueFactory(new PropertyValueFactory<Data,Double>("watspermonth"));
	         mumplace.setCellValueFactory(new PropertyValueFactory<Data,Integer>("numhighwattsusage"));
	         tbData.setItems(dataInformation); 
	    }
	    
	    private void loadCircleData() {
	    	
	    	myData.clear();
	    	FileInputStream fstream = null;	    		    	
	        try {
	            fstream = new FileInputStream("PowerUsage.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                StringTokenizer token = new StringTokenizer(strLine);
	                Data data = new Data();
	                
	                if(token.countTokens() == 3) {
	                	  
		                String name = token.nextToken();
		                int nummontly = Integer.parseInt(token.nextToken().toString());
		                int numhigu = Integer.parseInt(token.nextToken().toString());
		                data.setName(name);
		                data.setWatspermonth(nummontly);
		                data.setNumhighwattsusage(numhigu);
	                }else if(token.countTokens() == 4) {
	                	 String name = token.nextToken();
	                	 String second = token.nextToken();
	                	 String full = name + " " + second;;
	                	 int nummontly = Integer.parseInt(token.nextToken().toString());
	 	                int numhigu = Integer.parseInt(token.nextToken().toString());
	 	                data.setName(full);
	 	                data.setWatspermonth(nummontly);;
     	                data.setNumhighwattsusage(numhigu);	 	               
	                }
	                myData.add(data);
	            }
	            //System.out.println (name);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    	
	    }

	 
	    private void fillChart() {
	    	
	    	FileInputStream fstream = null;	    	
            List<Data> Datas = new ArrayList<Data>();
	    	
	        try {
	            fstream = new FileInputStream("PowerUsage.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                StringTokenizer token = new StringTokenizer(strLine);
	                Data data = new Data();
	                
	                if(token.countTokens() == 3) {
	                	  
		                String name = token.nextToken();
		                int mummonth = Integer.parseInt(token.nextToken().toString());
		                int numhigu = Integer.parseInt(token.nextToken().toString());
		                data.setName(name);
		                data.setWatspermonth(mummonth);
		                data.setNumhighwattsusage(numhigu);
	                }else if(token.countTokens() == 4) {
	                	 String name = token.nextToken();
	                	 String second = token.nextToken();
	                	 String full = name + " " + second;
	 	                int mummonth = Integer.parseInt(token.nextToken().toString());
	 	                int numhigu = Integer.parseInt(token.nextToken().toString());
	 	                data.setName(full);
	 	                data.setWatspermonth(mummonth);
     	                data.setNumhighwattsusage(numhigu);	 	               
	                }
	                Datas.add(data);
	                currentP.add(data.getName());
	            }
	            //System.out.println (name);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        XYChart.Series series = new XYChart.Series();
	        
	        for(int i = 0 ;i < Datas.size();i++) {
	        	 Data a = Datas.get(i);
	        	 series.getData().add(new XYChart.Data(a.getName(), a.getNumhighwattsusage()));
	        } 
	      
	        lineChart.getData().addAll(series);
	        
	       // System.out.println(getUnused(Datas).toString());
	        cmdprovname.setItems(getUnused(Datas));
	    }
	    
 private void loadCurrent() {
	    	
	    	FileInputStream fstream = null;	    	
            List<Data> Datas = new ArrayList<Data>();
	    	
	        try {
	            fstream = new FileInputStream("PowerUsage.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                StringTokenizer token = new StringTokenizer(strLine);
	                Data data = new Data();
	                
	                if(token.countTokens() == 3) {
	                	  
		                String name = token.nextToken();
		                int mummonth =Integer.parseInt(token.nextToken().toString());
		                int numhigu = Integer.parseInt(token.nextToken().toString());
		                data.setName(name);
		                data.setWatspermonth(mummonth);
		                data.setNumhighwattsusage(numhigu);
	                }else if(token.countTokens() == 4) {
	                	 String name = token.nextToken();
	                	 String second = token.nextToken();
	                	 String full = name + " " + second;
	 	                int mummonth = Integer.parseInt(token.nextToken().toString());
	 	                int numhigu = Integer.parseInt(token.nextToken().toString());
	 	                data.setName(full);
	 	                data.setWatspermonth(mummonth);
     	                data.setNumhighwattsusage(numhigu);	 	               
	                }
	                Datas.add(data);
	                currentP.add(data.getName());
	            }
	            //System.out.println (name);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	       
	        
	    }
	    
	    public void handleClicks(ActionEvent actionEvent) {
	    	if (actionEvent.getSource() == btnhome) {
	            pnlhome.setStyle("-fx-background-color : #ffffff;-fx-background-radius: 10");
	            pnlhome.toFront();
	            lineChart.getData().clear();
	            currentP.clear();
	            fillChart();
	        }
	        if (actionEvent.getSource() == btnInput) {
	            pnlinput.setStyle("-fx-background-color : #ffffff;-fx-background-radius: 10");
	            pnlinput.toFront();
	        }
	        if(actionEvent.getSource()==btngraphdt)
	        {
	            pnldatalink.setStyle("-fx-background-color : #ffffff;-fx-background-radius: 10");
	            pnldatalink.toFront();
	          
	          //  canvas;
	           // pnldatalink.getChildren().remove(apen);
	            
	            DrawCirlce();
	           
	            
	    		System.out.println("Vertexes  : "  + graph.getVertices().toString());
	    		System.out.println("Edges  : "  + graph.getEdges().toString());
	        }       
	        if (actionEvent.getSource() == btngraph) {
	            pnlgraph.setStyle("-fx-background-color : #ffffff;-fx-background-radius: 10");
	            pnlgraph.toFront();
	            
	            pieChart.getData().clear();
	            loadChart();
	        }
	        if(actionEvent.getSource()==btnsignout)
	        {
	        	open_logout(actionEvent);
	        } 
	    }
	    
	    private String GetId() {
	        // Open the file
	        FileInputStream fstream = null;
	        String dataid = null;
	        try {
	            fstream = new FileInputStream("current.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                dataid = strLine;
	            }
	            //System.out.println (dataid);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return dataid;
	    }

	    public String getName(String id) {
	        // Open the file
	        FileInputStream fstream = null;
	        String name = null;
	        try {
	            fstream = new FileInputStream("users.txt");
	            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	            String strLine;
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null)   {
	                // Print the content on the console - do what you want to do
	                //System.out.println (strLine);
	                StringTokenizer token = new StringTokenizer(strLine);
	                String ids = token.nextToken();
	                if(id.equalsIgnoreCase(ids)){
	                    String names = token.nextToken();
	                    name = names;
	                    //System.out.println (name);
	                    return name;
	                }
	            }
	            //System.out.println (name);
	            //Close the input stream
	            fstream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return name;
	    }
	    
	    private void open_logout(ActionEvent event) {
	        try {
	            //add you loading or delays - ;-)
	            Node node = (Node) event.getSource();
	            Stage stage = (Stage) node.getScene().getWindow();
	            //stage.setMaximized(true);
	            stage.close();
	            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
	            scene.setFill(Color.TRANSPARENT);
	           // stage.initStyle(StageStyle.TRANSPARENT);
	            stage.setScene(scene);
	            stage.show();

	        }
	        catch (IOException ex) {
	            System.err.println(ex.getMessage());
	        }
	    }


	   class RemindTask extends TimerTask {
	        public void run() {
	        	//tbData.getItems().clear();
	        	myDatas.clear();
	            LoadGraph();
	        }
	    } 
	    
	    public static void DeleteSelectedLine(String linetoremove,String FileName) throws IOException {
	    	 try {

	    		  System.out.println("Line to Remove : " + linetoremove);
	    	      File inFile = new File(FileName);

	    	      if (!inFile.isFile()) {
	    	        System.out.println("Parameter is not an existing file");
	    	        return;
	    	      }

	    	      //Construct the new file that will later be renamed to the original filename.
	    	      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

	    	      BufferedReader br = new BufferedReader(new FileReader(FileName));
	    	      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

	    	      String line = null;

	    	      //Read from the original file and write to the new
	    	      //unless content matches data to be removed.
	    	      while ((line = br.readLine()) != null) {

	    	        if (!line.trim().equals(linetoremove)) {
	    	        	
	    	          pw.println(line);
	    	          pw.flush();
	    	        }
	    	      }
	    	      pw.close();
	    	      br.close();

	    	      //Delete the original file
	    	      if (!inFile.delete()) {
	    	        System.out.println("Could not delete file");
	    	        return;
	    	      }

	    	      //Rename the new file to the filename the original file had.
	    	      if (!tempFile.renameTo(inFile))
	    	        System.out.println("Could not rename file");

	    	    }
	    	    catch (FileNotFoundException ex) {
	    	      ex.printStackTrace();
	    	    }
	    	    catch (IOException ex) {
	    	      ex.printStackTrace();
	    	    }
	   }
	    
	    
	    public static void replaceSelected(String original,String replaceWith,String filename) {
	        try {
	            // input the file content to the StringBuffer "input"
	            BufferedReader file = new BufferedReader(new FileReader(filename));
	            StringBuffer inputBuffer = new StringBuffer();
	            String line;

	            while ((line = file.readLine()) != null) {
	                inputBuffer.append(line);
	                inputBuffer.append('\n');
	            }
	            file.close();
	            String inputStr = inputBuffer.toString();

	            System.out.println(inputStr); // display the original file for debugging

	            // logic to replace lines in the string (could use regex here to be generic)
	              inputStr = inputStr.replace(original, replaceWith); 
	           
	              //System.out.println("----------------------------------\n" +" "+ original + " " + replaceWith);
	            // display the new file for debugging
	            System.out.println("----------------------------------\n" + inputStr);

	            // write the new string with the replaced line OVER the same file
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            fileOut.write(inputStr.getBytes());
	            fileOut.close();

	        } catch (Exception e) {
	            System.out.println("Problem reading file.");
	        }
	    }
	    
	    public ObservableList<String> getUnused(List<Data> Datasss) {
	    	 ObservableList<String> AllList = FXCollections.observableArrayList(
 		            "Western Cape", "Eastern Cape", "Northern Cape", "North West", "Free State", "Kwazulu Natal", "Gauteng","Limpopo","Mpumlanga");
	    	 ObservableList<String> newList = null;
	    	 
	    	 ObservableList<String> temp = FXCollections.observableArrayList(
	 		            "Western Cape", "Eastern Cape", "Northern Cape", "North West", "Free State", "Kwazulu Natal", "Gauteng","Limpopo","Mpumlanga");
	         System.out.println("Existig  : " + Datasss.size() + " Original : " + AllList.size());
	    	 for (Data data : Datasss) {
	    		 for (String list : AllList) {
	    			 if(data.getName().equals(list)){;
	    			 //   AllList.remove(list);
	    			 temp.remove(list);
	    			 System.out.println("Match  : " + list);
	    			 } else {
	    				 
	    			 }
				}
	    		 for(int k = 0; k < AllList.size();k++) {
	    			
	    		 }
			}
	    	return temp;
	    }
	
	        
}
