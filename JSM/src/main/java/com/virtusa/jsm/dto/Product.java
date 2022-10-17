package com.virtusa.jsm.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "product")
public class Product {

	@Id
	@TableGenerator(name = "generate_product", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_product")
	int productid;
	String pname;
	String description;
	double weight;
	double purity;
	String material;
	String type;
	int quantity;	
	
	
//	pu*rateper10gm*wt/1000	formula for net cost of one product
	
			
//	@Column(name = "costper10gm")
	double rateper10gm;
//	@Column(name = "subtotal")
	double price;
//	@OneToOne(mappedBy = "")
//	@JsonIgnore
//	int shop_id;
	
	public Product() {
		super();
	}
		
	public Product(int productid, String pname, String description, double weight, double purity, String material,
			String type, int quantity, double rateper10gm, double price) {
		super();
		this.productid = productid;
		this.pname = pname;
		this.description = description;
		this.weight = weight;
		this.purity = purity;
		this.material = material;
		this.type = type;
		this.quantity = quantity;
		this.rateper10gm = rateper10gm;
		this.price = price;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getPurity() {
		return purity;
	}
	public void setPurity(double purity) {
		this.purity = purity;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getRateper10gm() {
		return rateper10gm;
	}
	public void setRateper10gm(double rateper10gm) {
		this.rateper10gm = rateper10gm;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [productid=" + productid + ", pname=" + pname + ", description=" + description + ", weight="
				+ weight + ", purity=" + purity + ", material=" + material + ", type=" + type + ", quantity=" + quantity
				+ ", rateper10gm=" + rateper10gm + ", price=" + price + "]";
	}


	
//	public Product(int id, String pname, String description, double weight, double purity, String material, String type,
//			int quantity, double rateper10gm, double price, int shop_id) {
//		super();
//		this.id = id;
//		this.pname = pname;
//		this.description = description;
//		this.weight = weight;
//		this.purity = purity;
//		this.material = material;
//		this.type = type;
//		this.quantity = quantity;
//		this.rateper10gm = rateper10gm;
//		this.price = price;
//		this.shop_id = shop_id;
//	}
	
	
	

	

//	public int getShop_id() {
//		return shop_id;
//	}
//
//	public void setShop_id(int shop_id) {
//		this.shop_id = shop_id;
//	}
//
//	@Override
//	public String toString() {
//		return "Product [id=" + id + ", pname=" + pname + ", description=" + description + ", weight=" + weight
//				+ ", purity=" + purity + ", material=" + material + ", type=" + type + ", quantity=" + quantity
//				+ ", rateper10gm=" + rateper10gm + ", price=" + price + ", shop_id=" + shop_id + "]";
//	}

	
	
}
