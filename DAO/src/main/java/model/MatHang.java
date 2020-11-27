package model;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "MATHANG")
@JsonIgnoreProperties(value = {"IsDeleted"}, allowSetters= true)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maMatHang")
public class MatHang {
	@Id
	@Column(name = "MaMatHang")
	@GeneratedValue
	private long maMatHang;
	
	@Column(name = "TenMatHang", nullable = false)
	private String tenMatHang;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaLoaiMatHang", nullable = false)
	private LoaiMatHang loaiMatHang;
	
	@Column(name = "Gia", nullable = false)
	private long gia;
	
	@Column(name = "SoLuong", nullable = false)
	private int soLuong;
	
	@Column(name = "SoLuongDaBan", nullable = false)
	private int soLuongDaBan;
	
	@Column(name = "MoTa", nullable = false, columnDefinition = "TEXT")
	private String moTa;
	
//	@JsonBackReference
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "matHang")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<DanhGia> danhSachDanhGia;
	
//	@JsonBackReference
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany( mappedBy = "matHang")
	private List<Anh_MatHang> danhSachHinhAnh;
	
	@JsonIgnore
	@JsonIgnoreProperties("IsDeleted")
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
	
	public List<DanhGia> getDanhSachDanhGia() {
		return danhSachDanhGia;
	}
	
	public List<Anh_MatHang> getDanhSachHinhAnh() {
		return danhSachHinhAnh;
	}
	
	public long getGia() {
		return gia;
	}
	
	public LoaiMatHang getLoaiMatHang() {
		return loaiMatHang;
	}
	
	public long getMaMatHang() {
		return maMatHang;
	}
	
	public String getMoTa() {
		return moTa;
	}
	
	public int getSoLuong() {
		return soLuong;
	}
	
	public int getSoLuongDaBan() {
		return soLuongDaBan;
	}
	
	public String getTenMatHang() {
		return tenMatHang;
	}
	
	public void setDanhSachDanhGia(List<DanhGia> danhSachDanhGia) {
		this.danhSachDanhGia = danhSachDanhGia;
	}
	
	public void setDanhSachHinhAnh(List<Anh_MatHang> danhSachHinhAnh) {
		this.danhSachHinhAnh = danhSachHinhAnh;
	}
	
	public void setGia(long gia) {
		this.gia = gia;
	}
	
	public void setLoaiMatHang(LoaiMatHang loaiMatHang) {
		this.loaiMatHang = loaiMatHang;
	}
	
	public void setMaMatHang(long maMatHang) {
		this.maMatHang = maMatHang;
	}
	
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public void setSoLuongDaBan(int soLuongDaBan) {
		this.soLuongDaBan = soLuongDaBan;
	}
	
	public void setTenMatHang(String tenMatHang) {
		this.tenMatHang = tenMatHang;
	}
	
}
