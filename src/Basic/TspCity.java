package Basic;

/**
 * This class is implemented for the representation of a city
 * @author zhuoyingli
 *
 */
public class TspCity {
	
	/**
	 * Node Id of a city
	 */
	private int NodeId;
	
	/**
	 * X coordinate of a city
	 */
	private double X;
	
	/**
	 * Y coordinate of a city
	 */
	private double Y;
	
	/**
	 * Constructor of TspCity
	 * @param id nodeid
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public TspCity (int id, double x, double y){
		this.NodeId = id;
		this.X = x;
		this.Y = y;
		
	}
	
	/**
	 * Get the x coordinate of the city
	 * @return double x coordinate
	 */
	public double getX(){
		return this.X;
	}
	
	/**
	 * Get the y coordinate of the city
	 * @return double y coordinate
	 */
	public double getY(){
		return this.Y;
	}
	
	/**
	 * Get the Node Id of the city
	 * @return int  Node Id
	 */
	public int getNodeId(){
		return this.NodeId;
	}
	
	/**
	 * Calculate the distance to a city
	 * @param toCity the city to calculate to distance
	 * @return double the distance between two city
	 */
	public double CalculateDistanceToCity(TspCity toCity){
		double Xdistance = Math.abs(this.X - toCity.getX() );
		double Ydistance = Math.abs(this.Y - toCity.getY() );
		
		double distance = Math.sqrt( (Xdistance * Xdistance) + (Ydistance * Ydistance) );
		return distance;
	}
	
}
