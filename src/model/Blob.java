package model;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
 
public class Blob extends Component{
    private int x;
    private int y;
 
    private int size;
    private Color color;
    private String id;
    
    
    public Blob(String id, int x, int y){
        this.x = x;
        this.y = y;
        this.size = 40;
        this.id = id;
        Random rand = new Random();
        int r = rand.nextInt(128)+127;
        int green = rand.nextInt(128)+127;
        int b = rand.nextInt(128)+127;
        this.color = new Color(r,green,b);
       // this.color = new Color(-65536);
    }
    
    public Blob(String id, int x, int y, int size, Color color){
    	this.x=x;
    	this.y=y;
    	this.size=size;
    	this.id = id;
    	this.color = color;

    }
    
    public Blob(String id, int x, int y, int size){
    	this.x=x;
    	this.y=y;
    	this.size=size;
    	this.id = id;
    }
    
    public int getX() {
		return x;
	}
 
 
	public void setX(int x) {
		this.x = x;
	}
 
 
	public int getY() {
		return y;
	}
 
 
	public void setY(int y) {
		this.y = y;
	}
	
	public void setOvalSize(int size){
		this.size = size;
	}
	
	public int getOvalSize(){
		return size;
	}
	
	public String getID(){
		return id;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
 
 
	public synchronized void paint(Graphics g){

		//Font ID = new Font("id",Font.BOLD,15); //에러
		//Font Score = new Font("size",Font.PLAIN,10); //에러
        g.setColor(color);
        g.fillOval(x, y, size, size);
        g.setColor(Color.BLACK);
       // g.setFont(ID);//에러
        if(id.length()==1)
        	g.drawString(id, x+size/2-id.length()*3, y+size/2);//숫자,영어일때됌. 한글안댐
        else
        	g.drawString(id, x+size/2-id.length()*3-3, y+size/2);//숫자,영어일때됌. 한글안댐
      //  g.setFont(Score);//에러
        if(size<100) //점수 표시 완료
        	g.drawString(String.valueOf(size), x+size/2-4, y+size/2+11); 
        else
        	g.drawString(String.valueOf(size), x+size/2-8, y+size/2+11);
       
    }
 
}