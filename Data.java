package module;

public class Data {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumhighwattsusage() {
		return numhighwattsusage;
	}
	public void setNumhighwattsusage(int numhighwattsusage) {
		this.numhighwattsusage = numhighwattsusage;
	}
	public int getWatspermonth() {
		return watspermonth;
	}
	public void setWatspermonth(int watspermonth) {
		this.watspermonth = watspermonth;
	}
	private int numhighwattsusage;
	private int watspermonth;
	
	public Data() {
		
	}
    public Data(String name,int watspermonth,int numhighwattsusage) {
		this.name = name;
		this.watspermonth = watspermonth;
		this.numhighwattsusage = numhighwattsusage;
	
	}
    
    


}
