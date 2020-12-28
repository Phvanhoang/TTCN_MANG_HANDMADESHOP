package model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "MATHANG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maMatHang")
public class MatHang extends AuditModel<TaiKhoan> {
	private static final long serialVersionUID = 8320053786535532224L;

	@Id
	@Column(name = "MaMatHang")
	@GeneratedValue
	private long maMatHang;

	@Column(name = "TenMatHang", nullable = false)
	private String tenMatHang;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MaLoaiMatHang", nullable = false)
	private LoaiMatHang loaiMatHang;

	@Column(name = "Gia", nullable = false)
	private long gia;

	@Column(name = "SoLuong", nullable = false)
	private int soLuong;

	@Column(name = "Rate", nullable = false)
	private float rate;

	@Column(name = "SoLuotDanhGia", nullable = false)
	private int soLuotDanhGia;

	@Column(name = "SoLuongDaBan", nullable = false)
	private int soLuongDaBan;

	@Column(name = "MoTa", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String moTa;

	@JsonBackReference
//	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "matHang")
//	@Fetch(value = FetchMode.SUBSELECT)
	private List<DanhGia> danhSachDanhGia;

//	@JsonBackReference
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "matHang")
	private List<Anh_MatHang> danhSachHinhAnh;

	@JsonBackReference
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "matHang")
	private List<DonHang_MatHang> danhSachDonHang;

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getSoLuotDanhGia() {
		return soLuotDanhGia;
	}

	public void setSoLuotDanhGia(int soLuotDanhGia) {
		this.soLuotDanhGia = soLuotDanhGia;
	}

	public List<DonHang_MatHang> getDanhSachDonHang() {
		return danhSachDonHang;
	}

	public void setDanhSachDonHang(List<DonHang_MatHang> danhSachDonHang) {
		this.danhSachDonHang = danhSachDonHang;
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

	@Override
	public boolean equals(Object obj) {
		MatHang matHang = (MatHang) obj;
		return matHang.maMatHang == this.maMatHang;
	}
}
