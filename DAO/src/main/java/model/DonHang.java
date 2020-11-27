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
@Table(name = "DONHANG")@JsonIdentityInfo(
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
	private NguoiDung nguoiDung;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaTrangThaiDonHang", nullable = false)
	private TrangThaiDonHang trangThaiDonHang;
	
	@Column(name = "ThoiGian", nullable = false)
	private Date thoiGian;
	
	@Column(name = "GiaTongCong", nullable = false)
	private Date giaTongCong;
	
	@Column(name = "DiaChiGiaoHang", nullable = false)
	private String diaChiGiaoHang;
	
	@Column(name = "SDTGiaoHang", nullable = false)
	private String SDTGiaoHang;
	
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
	
	public Date getGiaTongCong() {
		return giaTongCong;
	}
	
	public long getMaDonHang() {
		return maDonHang;
	}
	
	public NguoiDung getNguoiDung() {
		return nguoiDung;
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
	
	public void setGiaTongCong(Date giaTongCong) {
		this.giaTongCong = giaTongCong;
	}
	
	public void setMaDonHang(long maDonHang) {
		this.maDonHang = maDonHang;
	}
	
	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
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
	 
}
