package com.sirier.domain;
// Generated 2017-8-12 21:54:27 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * NoticeBill generated by hbm2java
 */
@Entity
@Table(name="qp_noticebill"
    ,catalog="bos2"
)
public class NoticeBill  implements java.io.Serializable {


     private String id;
     private String staffId;
     private Integer customerId;
     private String customerName;
     private String delegater;
     private String telephone;
     private String pickaddress;
     private String arrivecity;
     private String product;
     private Date pickdate;
     private Integer num;
     private Integer weight;
     private String volume;
     private String remark;
     private String ordertype;
     private Integer userId;
     private Set<WorkBill> workBills = new HashSet<WorkBill>(0);

    public NoticeBill() {
    }

    public NoticeBill(String staffId, Integer customerId, String customerName, String delegater, String telephone, String pickaddress, String arrivecity, String product, Date pickdate, Integer num, Integer weight, String volume, String remark, String ordertype, Integer userId, Set<WorkBill> workBills) {
       this.staffId = staffId;
       this.customerId = customerId;
       this.customerName = customerName;
       this.delegater = delegater;
       this.telephone = telephone;
       this.pickaddress = pickaddress;
       this.arrivecity = arrivecity;
       this.product = product;
       this.pickdate = pickdate;
       this.num = num;
       this.weight = weight;
       this.volume = volume;
       this.remark = remark;
       this.ordertype = ordertype;
       this.userId = userId;
       this.workBills = workBills;
    }
   
     @GenericGenerator(name="generator", strategy="uuid")@Id @GeneratedValue(generator="generator")
    
    @Column(name="ID", unique=true, nullable=false, length=32)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="STAFF_ID", length=32)
    public String getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
    @Column(name="CUSTOMER_ID")
    public Integer getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    @Column(name="CUSTOMER_NAME", length=20)
    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    @Column(name="DELEGATER", length=20)
    public String getDelegater() {
        return this.delegater;
    }
    
    public void setDelegater(String delegater) {
        this.delegater = delegater;
    }
    
    @Column(name="TELEPHONE", length=20)
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="PICKADDRESS", length=200)
    public String getPickaddress() {
        return this.pickaddress;
    }
    
    public void setPickaddress(String pickaddress) {
        this.pickaddress = pickaddress;
    }
    
    @Column(name="ARRIVECITY", length=20)
    public String getArrivecity() {
        return this.arrivecity;
    }
    
    public void setArrivecity(String arrivecity) {
        this.arrivecity = arrivecity;
    }
    
    @Column(name="PRODUCT", length=20)
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String product) {
        this.product = product;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="PICKDATE", length=0)
    public Date getPickdate() {
        return this.pickdate;
    }
    
    public void setPickdate(Date pickdate) {
        this.pickdate = pickdate;
    }
    
    @Column(name="NUM", precision=8, scale=0)
    public Integer getNum() {
        return this.num;
    }
    
    public void setNum(Integer num) {
        this.num = num;
    }
    
    @Column(name="WEIGHT", precision=8, scale=0)
    public Integer getWeight() {
        return this.weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    @Column(name="VOLUME", length=20)
    public String getVolume() {
        return this.volume;
    }
    
    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    @Column(name="REMARK")
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="ORDERTYPE", length=20)
    public String getOrdertype() {
        return this.ordertype;
    }
    
    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }
    
    @Column(name="USER_ID")
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="noticeBill")
    public Set<WorkBill> getWorkBills() {
        return this.workBills;
    }
    
    public void setWorkBills(Set<WorkBill> workBills) {
        this.workBills = workBills;
    }




}


