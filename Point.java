package module;

public class Point {
   private String name;
   public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public double getX() {
	return x;
}

public void setX(double x) {
	this.x = x;
}

public double getY() {
	return y;
}

public void setY(double y) {
	this.y = y;
}

private double x;
   private double y;
   
   public Point(String name,double x,double y) {
	    this.name = name;
	    this.x = x;
	    this.y = y;
   }
}
