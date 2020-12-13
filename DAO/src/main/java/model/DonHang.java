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
@Table(name = "DONHANG")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maDonHang")
public class DonHang {
	@Id
	@Column(name = "MaDonHang")
	@GeneratedValue
	private long maDonHang;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung_DonHang;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaTrangThaiDonHang", nullable = false)
	private TrangThaiDonHang trangThaiDonHang;
	
	@Column(name="TenNguoiNhanHang", nullable = true)
	private String tenNguoiNhanHang;
	
	@Column(name = "ThoiGian", nullable = false)
	private Date thoiGian;
	
	@Column(name = "GiaTongCong", nullable = false)
	private long giaTongCong;
	
	@Column(name = "DiaChiGiaoHang", nullable = false)
	private String diaChiGiaoHang;
	
	@Column(name = "SDTGiaoHang", nullable = false)
	private String SDTGiaoHang;
	
	@Column(name= "ChuThich", nullable = false, columnDefinition = "TEXT")
	private String chuThich;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "donHang")
	private Set<DonHang_MatHang> danhSachMatHang;
	
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
	
	public Set<DonHang_MatHang> getDanhSachMatHang() {
		return danhSachMatHang;
	}
	
	public String getDiaChiGiaoHang() {
		return diaChiGiaoHang;
	}
	
	public long getGiaTongCong() {
		return giaTongCong;
	}
	
	public long getMaDonHang() {
		return maDonHang;
	}
	
	public NguoiDung getNguoiDung() {
		return nguoiDung_DonHang;
	}
	
	public String getSDTGiaoHang() {
		return SDTGiaoHang;
	}
	
	public Date getThoiGian() {
		return thoiGian;
	}
	
	public TrangThaiDonHang getTrangThaiDonHang() {
		return trangThaiDonHang;
	}
	
	public void setDanhSachMatHang(Set<DonHang_MatHang> danhSachMatHang) {
		this.danhSachMatHang = danhSachMatHang;
	}
	
	public void setDiaChiGiaoHang(String diaChiGiaoHang) {
		this.diaChiGiaoHang = diaChiGiaoHang;
	}
	
	public void setGiaTongCong(long giaTongCong) {
		this.giaTongCong = giaTongCong;
	}
	
	public void setMaDonHang(long maDonHang) {
		this.maDonHang = maDonHang;
	}
	
	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung_DonHang = nguoiDung;
	}
	
	public void setSDTGiaoHang(String sDTGiaoHang) {
		SDTGiaoHang = sDTGiaoHang;
	}
	
	public void setThoiGian(Date thoiGian) {
		this.thoiGian = thoiGian;
	}
	
	public void setTrangThaiDonHang(TrangThaiDonHang trangThaiDonHang) {
		this.trangThaiDonHang = trangThaiDonHang;
	}
	
	public String getTenNguoiNhanHang() {
		return this.tenNguoiNhanHang;
	}
	
	public void setTenNguoiNhanHang(String tenNguoiNhanHang) {
		this.tenNguoiNhanHang = tenNguoiNhanHang;
	}
	
	public String getChuThich() {
		return chuThich;
	}
	
	public void setChuThich(String chuThich) {
		this.chuThich = chuThich;
	}
}
