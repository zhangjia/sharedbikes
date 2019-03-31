package tv.zhangjia.bike.entity;

import java.util.List;

public class Location {
	private int id; //位置ID
	private String location;  //位置名称
	private List<Bike> bikes; //自行车
	
	
	public Location(int id, String location) {
		super();
		this.id = id;
		this.location = location;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<Bike> getBikes() {
		return bikes;
	}
	public void setBikes(List<Bike> bikes) {
		this.bikes = bikes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikes == null) ? 0 : bikes.hashCode());
		result = prime * result + id;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (bikes == null) {
			if (other.bikes != null)
				return false;
		} else if (!bikes.equals(other.bikes))
			return false;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return  + id + "\t" + location + "\t" + bikes.size() + "\n";
	}
	public Location(int id, String location, List<Bike> bikes) {
		super();
		this.id = id;
		this.location = location;
		this.bikes = bikes;
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Location(String location, List<Bike> bikes) {
		super();
		this.location = location;
		this.bikes = bikes;
	}
	
	
	
}
