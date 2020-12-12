package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="GIOITINH")
public class GioiTinh {
	@Id
	@Column(name = "MaGioiTinh", nullable = false)
	@GeneratedValue
	private long maGioiTinh;
	
	@Column(name = "TenGioiTinh", nullable = false)
	@Nationalized
	private String tenGioiTinh;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gioiTinh")
	private Set<NguoiDung> danhSachNguoiDung;
	
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
	
	public Set<NguoiDung> getDanhSachNguoiDung() {
		return danhSachNguoiDung;
	}
	
	public long getMaGioiTinh() {
		return maGioiTinh;
	}
	
	public String getTenGioiTinh() {
		return tenGioiTinh;
	}
	
	public void setDanhSachNguoiDung(Set<NguoiDung> danhSachNguoiDung) {
		this.danhSachNguoiDung = danhSachNguoiDung;
	}
	
	public void setMaGioiTinh(long maGioiTinh) {
		this.maGioiTinh = maGioiTinh;
	}
	
	public void setTenGioiTinh(String tenGioiTinh) {
		this.tenGioiTinh = tenGioiTinh;
	}
}
