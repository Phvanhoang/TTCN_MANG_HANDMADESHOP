package model;
import java.sql.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "TAIKHOAN")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maTaiKhoan")
public class TaiKhoan {
	@Id
	@Column(name = "MaTaiKhoan")
	@GeneratedValue
	private long maTaiKhoan;
	
	@Column(name = "TenDangNhap", nullable = false)
	private String tenDangNhap;
	
	@Column(name = "MatKhau", nullable = false)
	private String matKhau;
	
	@JsonManagedReference
	@OneToOne
	@JoinColumn(name = "MaDacQuyen", nullable = false)
	private DacQuyen dacQuyen;
	
	@Column(name = "ThoiGianDangKy", nullable = false)
	private Date thoiGianDangKy;
	
	@Column(name = "TrangThai", nullable = false)
	private boolean trangThai;
	
	@JsonManagedReference
	@OneToOne
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung;
	
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
	
	public long getMaTaiKhoan() {
		return maTaiKhoan;
	}
	
	public String getMatKhau() {
		return matKhau;
	}
	
	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}
	
	public DacQuyen getDacQuyen() {
		return dacQuyen;
	}
	
	public String getTenDangNhap() {
		return tenDangNhap;
	}
	
	public Date getThoiGianDangKy() {
		return thoiGianDangKy;
	}
	
	public boolean isTrangThai() {
		return trangThai;
	}
	
	public void setMaTaiKhoan(long maTaiKhoan) {
		this.maTaiKhoan = maTaiKhoan;
	}
	
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	
	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}
	
	public void setDacQuyen(DacQuyen dacQuyen) {
		this.dacQuyen = dacQuyen;
	}
	
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
	
	public void setThoiGianDangKy(Date thoiGianDangKy) {
		this.thoiGianDangKy = thoiGianDangKy;
	}
	
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

}