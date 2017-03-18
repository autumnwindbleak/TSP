package Basic;


public class TspCity {
	
	private int NodeId;
	
	private int X;
	
	private int Y;
	
	public TspCity (int id, int x, int y){
		this.NodeId = id;
		this.X = x;
		this.Y = y;
		
	}
	
	public int getX(){
		return this.X;
	}
	
	
	public int getY(){
		return this.Y;
	}
	
	public int getNodeId(){
		return this.NodeId;
	}
	
	public double CalculateDistanceToCity(TspCity toCity){
		int Xdistance = Math.abs(this.X - toCity.getX() );
		int Ydistance = Math.abs(this.Y - toCity.getY() );
		
		double distance = Math.sqrt( (Xdistance * Xdistance) + (Ydistance * Ydistance) );
		return distance;
	}
	
}
