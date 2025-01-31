package module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javafx.util.Duration;
import module.Graph.Edge;
import module.Graph.Vertex;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class GraphController {


	List<Vertex<String>> myvets = new ArrayList<Vertex<String>>();
	List<Edge<Integer>> myedge   = new ArrayList<Edge<Integer>>();;
	Vertex<String>  v;
    int counter = 0;
    Graph  mygraph;
	
	public Vertex<String> getV() {
		return v;
	}

	public void setV(Vertex<String> v,int Monthlywattage) {
		this.v = v;
		/*myvets.add(v);
		while(myvets.size() > 1) {
			if(counter < myvets.size() - 1) {
        		Edge<Double> es = new Edge(counter,myvets.get(counter),myvets.get(counter + 1)); 
            	myedge.add(es);          	
        	}
        	
        	counter++;
		} */
		  myvets.add(v);
		  mygraph.getVertices().add(v);
		  
		  
		  int esize = mygraph.getEdges().size();
		  int vsize = mygraph.getVertices().size();
		  
		  System.out.println("Edges Size : " + esize);
		  System.out.println("Last Vertext Indezx :" + vsize);
		  
		  System.out.println("Verteces :" +  mygraph.getVertices().toString());
		  Edge<Integer> es = new Edge(Monthlywattage,myvets.get(vsize-2),myvets.get(vsize-1)); 
		  myedge.add(es);
		  mygraph.getEdges().add(es);
		  
	}
	
	public void EditVertice(Vertex<String> v,int position) { 
		 mygraph.getVertices().remove(position);
		 mygraph.getVertices().add(position, v);;
	}
	
	public void removeVertAndEdge(int position) {
		 System.out.println(" Position : " + position);
		 mygraph.getVertices().remove(position);
		 mygraph.getEdges().remove(myvets.get(position).getEdge(myvets.get(position)));
		 myedge.remove(myvets.get(position).getEdge(myvets.get(position)));
		 myvets.remove(position);
		// mygraph.getEdges().remove(mygraph.getVertices().get(position));
		/* mygraph.getEdges().remove(vs.getEdge(vs));
		 myedge.remove(vs.getEdge(vs));
		 mygraph.getVertices().remove(vs);
		 myvets.remove(vs);	 */		
		 
		 System.out.println("New vertece : " + myvets.toString());
		 System.out.println("New Edge : " + myedge.toString());
	}
	
	public Graph getGraph() {
		
			 
		 
		 return mygraph;
	}
	
	
    public void loadGraph() {
    	List<Vertex<String>> vert = new ArrayList<Vertex<String>>();
    	List<Edge<Integer>> edge   = new ArrayList<Edge<Integer>>();;
    	
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
	                int nummonthly = Integer.parseInt(token.nextToken().toString());
	                int numhigu = Integer.parseInt(token.nextToken().toString());
	                data.setName(name);
	                data.setWatspermonth(nummonthly);
	                data.setNumhighwattsusage(numhigu);
	                Vertex<String>  v =  new Vertex(name,nummonthly);
	                vert.add(v);
                }else if(token.countTokens() == 4) {
                	 String name = token.nextToken();
                	 String second = token.nextToken();
                	 String full = name + " " + second;
                	 int nummonthly = Integer.parseInt(token.nextToken().toString());
 	                int numhigu = Integer.parseInt(token.nextToken().toString());
 	                data.setName(full);
 	                data.setWatspermonth(nummonthly);
 	                data.setNumhighwattsusage(numhigu);	
 	               Vertex<String>  v =  new Vertex(name,nummonthly);
	               vert.add(v);
                }
                Datas.add(data);
            }
            //System.out.println (name);
            //Close the input stream
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int counter = 0;
        for (Data data : Datas) {
        	if(counter < Datas.size() - 1) {
        		Edge<Integer> es = new Edge(data.getNumhighwattsusage(),vert.get(counter),vert.get(counter + 1)); 
            	edge.add(es);            	
        	} 
        
        	counter++;
		}
		
        myvets.addAll(vert);
        myedge.addAll(edge);
        mygraph = new Graph(vert,edge);
        int esize = mygraph.getEdges().size();
        int vsize = mygraph.getVertices().size();
		  //int last = mygraph.getVertices().lastIndexOf(vert.get(vsize));
		  
		  System.out.println("Edges Size : " + esize);
		  System.out.println("Vertext Size :" + vsize);
	}

	public GraphController () {
       //  mygraph = graph;
		loadGraph();
		

	}
	
	
}
