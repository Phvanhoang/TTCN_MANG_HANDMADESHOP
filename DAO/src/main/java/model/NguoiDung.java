package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "NGUOIDUNG")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maNguoiDung")
public class NguoiDung {
	@Id
	@Column(name = "MaNguoiDung")
	@GeneratedValue
	private long maNguoiDung;
	
	@Lob
	@Column(name = "AnhDaiDien", nullable = true)
	private byte[] anhDaiDien;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaGioiTinh", nullable = true)
	private GioiTinh gioiTinh;
	
	@Column(name = "HoTen", nullable = true)
	private String HoTen;
	
	@Column(name = "ThanhPho", nullable = true)
	private String ThanhPho;
	
	@Column(name = "SDT", nullable = true)
	private String SDT;
	
	@Column(name = "NgaySinh", nullable = true)
	private Date NgaySinh;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, mappedBy="nguoiDung")
	private TaiKhoan taiKhoan;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nguoiDung")
	private Set<DonHang> sanhSachDonHang;
	
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
	
	public byte[] getAnhDaiDien() {
		return anhDaiDien;
	}
	
	public GioiTinh getGioiTinh() {
		return gioiTinh;
	}
	
	public long getMaNguoiDung() {
		return maNguoiDung;
	}
	
	public Date getNgaySinh() {
		return NgaySinh;
	}
	
	public Set<DonHang> getSanhSachDonHang() {
		return sanhSachDonHang;
	}
	
	public String getSDT() {
		return SDT;
	}
	
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	
	public String getThanhPho() {
		return ThanhPho;
	}
	
	public void setAnhDaiDien(byte[] anhDaiDien) {
		this.anhDaiDien = anhDaiDien;
	}
	
	public void setGioiTinh(GioiTinh gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
	public void setMaNguoiDung(long maNguoiDung) {
		this.maNguoiDung = maNguoiDung;
	}
	
	public void setNgaySinh(Date ngaySinh) {
		NgaySinh = ngaySinh;
	}
	
	public void setSanhSachDonHang(Set<DonHang> sanhSachDonHang) {
		this.sanhSachDonHang = sanhSachDonHang;
	}
	
	public void setSDT(String sDT) {
		SDT = sDT;
	}
	
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
	public void setThanhPho(String thanhPho) {
		ThanhPho = thanhPho;
	}
	
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	
	public String getHoTen() {
		return HoTen;
	}
}
