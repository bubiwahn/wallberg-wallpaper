package tv.wallberg;
import java.util.Properties;

public class Hello {
	
	private String planet;
	
	public Hello() throws Exception {
		Properties conf = new Properties();
		conf.load(this.getClass().getClassLoader().getResourceAsStream("conf.properties"));
		planet = conf.getProperty("planet");
	}
	
	public String getPlanet() {
		return planet;
	}
	
	public static void main(String[] args) throws Exception {		
		System.out.println("Hello " + new Hello().getPlanet() + "!");
	}

}
