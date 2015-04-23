package bits.csg523.project;

public class Jogger {

	private String name="";
	private int age=0;
	private float height;
	private float weight;
	private float BMI=0;
	private String passwd="123456";
	
	/*Set Functions*/
	public void setName(String name){
		this.name=name;
	}
	public void setAge(int age){
		this.age=age;
	}
	public void setHeight(float height){
		this.height=height;
	}
	public void setWeight(float weight){
		this.weight=weight;
	}
	
	public void setBMI(){
		this.BMI=(this.weight*10000)/(this.height*this.height);
	}
	public void setPasswd(String pass){
		this.passwd=pass;
	}
	
	/*Get functions*/
	public String getName(){
		return this.name;
	}
	public int getAge(){
		return this.age;
	}
	public float getHeight(){
		return this.height;
	}
	public float getWeight(){
		return this.weight;
	}
	public String getPasswd(){
		return this.passwd;
	}
	public float getBMI() {
		return this.BMI;
	}
}