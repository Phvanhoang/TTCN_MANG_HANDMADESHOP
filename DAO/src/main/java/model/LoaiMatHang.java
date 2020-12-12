package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "LOAIMATHANG")
public class LoaiMatHang {
	@Id
	@Column(name = "MaLoaiMatHang")
	@GeneratedValue
	private long maLoaiMatHang;
	
	@Column(name = "TenLoaiMatHang")
	private String tenLoaiMatHang;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiMatHang")
	private Set<MatHang> danhSachMatHang;
	
	@Column(name = "IsDeleted", nullable = false)
	private boolean isDeleted;
	
	@ManyToOne
	@JoinColumn(name = "UpdatedBy", nullable = true)
	private TaiKhoan updatedBy;
	
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
	
	public Set<MatHang> getDanhSachMatHang() {
		return danhSachMatHang;
	}
	
	public long getMaLoaiMatHang() {
		return maLoaiMatHang;
	}
	
	public String getTenLoaiMatHang() {
		return tenLoaiMatHang;
	}
	
	public void setDanhSachMatHang(Set<MatHang> danhSachMatHang) {
		this.danhSachMatHang = danhSachMatHang;
	}
	
	public void setMaLoaiMatHang(long maLoaiMatHang) {
		this.maLoaiMatHang = maLoaiMatHang;
	}
	
	public void setTenLoaiMatHang(String tenLoaiMatHang) {
		this.tenLoaiMatHang = tenLoaiMatHang;
	}
	
}
