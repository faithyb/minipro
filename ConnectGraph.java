package module;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.sun.javafx.geom.Point2D;

import module.Graph.Edge;
import module.Graph.Vertex;

public class ConnectGraph {

	List<Vertex<String>> myvets = new ArrayList<Vertex<String>>();
	List<Edge<Integer>> myedge   = new ArrayList<Edge<Integer>>();;
	
	public ConnectGraph( ) {
		
	}
			
	public void  FindShortestPath() {
		
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
	                Vertex<String>  v =  new Vertex(name,mummonth);
	                myvets.add(v);
	               
                }else if(token.countTokens() == 4) {
                	 String name = token.nextToken();
                	 String second = token.nextToken();
                	 String full = name + " " + second;
 	                int mummonth =Integer.parseInt(token.nextToken().toString());
 	                int numhigu = Integer.parseInt(token.nextToken().toString());
 	                data.setName(full);
 	                data.setWatspermonth(mummonth);
 	                data.setNumhighwattsusage(numhigu);	 
 	                Vertex<String>  v =  new Vertex(name,mummonth);
	                myvets.add(v);
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
        		Edge<Integer> es = new Edge(data.getNumhighwattsusage(),myvets.get(counter),myvets.get(counter + 1)); 
            	myedge.add(es);
            	
        	} 
        	
        	counter++;
		}
		
		Graph  mygraph = new Graph(myvets,myedge);
		
		System.out.println("Vertexes  : "  + mygraph.getVertices().toString());
		System.out.println("Edges  : "  + mygraph.getEdges().toString());
	}
	
}
