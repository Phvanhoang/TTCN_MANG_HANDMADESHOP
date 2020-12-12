package model;
import java.sql.Date;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "DACQUYEN")
public class DacQuyen {
	@Id
	@Column(name = "MaDacQuyen")
	@GeneratedValue
	private long maDacQuyen;
	
	@Column(name = "TenDacQuyen", nullable = false)
	private String tenDacQuyen;
	
	@OneToMany(mappedBy = "dacQuyen")
	private Set<TaiKhoan> danhSachTaiKhoan;
	
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
	public Set<TaiKhoan> getDanhSachTaiKhoan() {
		return danhSachTaiKhoan;
	}
	
	public void setDanhSachTaiKhoan(Set<TaiKhoan> danhSachTaiKhoan) {
		this.danhSachTaiKhoan = danhSachTaiKhoan;
	}
	
	public long getMaDacQuyen() {
		return maDacQuyen;
	}
	
	public String getTenDacQuyen() {
		return tenDacQuyen;
	}
	
	public void setMaDacQuyen(long maDacQuyen) {
		this.maDacQuyen = maDacQuyen;
	}
	
	public void setTenDacQuyen(String tenDacQuyen) {
		this.tenDacQuyen = tenDacQuyen;
	}
}
