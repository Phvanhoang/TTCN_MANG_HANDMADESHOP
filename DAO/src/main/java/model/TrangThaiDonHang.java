package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "TRANGTHAIDONHANG")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maTrangThai")
public class TrangThaiDonHang {
	@Id
	@Column(name = "MaTrangThai")
	@GeneratedValue
	private long matrangThai;
	
	@Column(name = "TenTrangThai")
	private String tenTrangThai;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trangThaiDonHang")
	private Set<DonHang> danhSachDonHang;
	
	@JsonIgnore
	@Column(name = "IsDeleted", nullable = false)
	private boolean isDeleted;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "UpdatedBy", nullable = true)
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
	
	public Set<DonHang> getDanhSachDonHang() {
		return danhSachDonHang;
	}
	
	public long getMatrangThai() {
		return matrangThai;
	}
	
	public String getTenTrangThai() {
		return tenTrangThai;
	}
	
	public void setDanhSachDonHang(Set<DonHang> danhSachDonHang) {
		this.danhSachDonHang = danhSachDonHang;
	}
	
	public void setMatrangThai(long matrangThai) {
		this.matrangThai = matrangThai;
	}
	
	public void setTenTrangThai(String tenTrangThai) {
		this.tenTrangThai = tenTrangThai;
	}
}
