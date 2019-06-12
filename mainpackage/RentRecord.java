package mainpackage;

import java.sql.Date;

public class RentRecord {
	
	private Integer id;
	private Date orderDate;
	private String car;
	private String location;
	private Date pick_upDate;
	private Date drop_offDate;
	
	public RentRecord(int id, Date orderDate, String car, String location, Date pick_upDate, Date drop_offDate) {
		this.setId(id);
		this.setOrderDate(orderDate);
		this.setCar(car);
		this.setLocation(location);
		this.setPick_upDate(pick_upDate);
		this.setDrop_offDate(drop_offDate);
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCar() {
		return this.car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getPick_upDate() {
		return this.pick_upDate;
	}

	public void setPick_upDate(Date pick_upDate) {
		this.pick_upDate = pick_upDate;
	}

	public Date getDrop_offDate() {
		return this.drop_offDate;
	}

	public void setDrop_offDate(Date drop_offDate) {
		this.drop_offDate = drop_offDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
