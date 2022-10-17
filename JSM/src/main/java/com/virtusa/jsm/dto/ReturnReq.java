package com.virtusa.jsm.dto;

import java.sql.Timestamp;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "returnreq")
public class ReturnReq {
		@Id
		@TableGenerator(name = "generate_returnreq", 
						table = "z_generate_id",	
						pkColumnName = "generate_name", 
						valueColumnName = "generate_value",
						allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_returnreq")
		int returnid;
		Timestamp dateTime;
		@OneToOne
		Bill bill;
		String status;
		String reason;
		public int getReturnid() {
			return returnid;
		}
		public void setReturnid(int returnid) {
			this.returnid = returnid;
		}
		public Timestamp getDateTime() {
			return dateTime;
		}
		public void setDateTime(Timestamp dateTime) {
			this.dateTime = dateTime;
		}
		public Bill getBill() {
			return bill;
		}
		public void setBill(Bill bill) {
			this.bill = bill;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public ReturnReq(int returnid, Timestamp dateTime, Bill bill, String status, String reason) {
			super();
			this.returnid = returnid;
			this.dateTime = dateTime;
			this.bill = bill;
			this.status = status;
			this.reason = reason;
		}
		
		public ReturnReq() {
			super();
		}
		@Override
		public String toString() {
			return "ReturnReq [returnid=" + returnid + ", dateTime=" + dateTime + ", bill=" + bill + ", status="
					+ status + ", reason=" + reason + "]";
		}
		
		
		
}
