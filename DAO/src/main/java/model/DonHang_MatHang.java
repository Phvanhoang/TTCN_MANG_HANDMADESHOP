package model;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "DONHANG_MATHANG")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class DonHang_MatHang {
	@EmbeddedId
	private DonHang_MatHang_Key id;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maDonHang")
	@JoinColumn(name = "MaDonHang")
	private DonHang donHang;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maMatHang")
	@JoinColumn(name = "MaMatHang")
	private MatHang matHang;
	
	@Column(name = "SoLuong", nullable = true)
	private int soLuong;
	
	@JsonIgnore
	@Column(name = "IsDeleted", nullable = false)
	private boolean isDeleted;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "UpdatedBy", nullable = false)
	private TaiKhoan updatedBy;
	
	@JsonIgnore
	@Column(name = "UpdatedAt", nullable = true)
	private Date updatedAt;
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public TaiKhoan getUpdatedBy() {
		return updatedBy;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void setUpdatedBy(TaiKhoan updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public DonHang getDonHang() {
		return donHang;
	}
	
	public MatHang getMatHang() {
		return matHang;
	}
	
	public DonHang_MatHang_Key getId() {
		return id;
	}
	
	public int getSoLuong() {
		return soLuong;
	}
	
	public void setDonHang(DonHang donHang) {
		this.donHang = donHang;
	}
	
	public void setId(DonHang_MatHang_Key id) {
		this.id = id;
	}
	
	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
}
