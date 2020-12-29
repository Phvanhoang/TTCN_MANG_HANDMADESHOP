package model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "NGUOIDUNG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maNguoiDung")
public class NguoiDung extends AuditModel<TaiKhoan> {
	private static final long serialVersionUID = 799225384863590495L;

	@Id
	@Column(name = "MaNguoiDung")
	@GeneratedValue
	private long maNguoiDung;

	@JsonIgnore
	@Lob
	@Column(name = "AnhDaiDien", nullable = true)
	private byte[] anhDaiDien;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaGioiTinh", nullable = true)
	private GioiTinh gioiTinh;

	@Column(name = "HoTen", nullable = true)
	private String hoTen;

	@Column(name = "ThanhPho", nullable = true)
	private String ThanhPho;

	@Column(name = "SDT", nullable = true)
	private String sdt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NgaySinh", nullable = true)
	private Date ngaySinh;

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MaTaiKhoan", nullable = false)
	private TaiKhoan taiKhoan;

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
		return ngaySinh;
	}

	public String getSDT() {
		return sdt;
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
		this.ngaySinh = ngaySinh;
	}

	public void setSDT(String sDT) {
		sdt = sDT;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public void setThanhPho(String thanhPho) {
		ThanhPho = thanhPho;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getHoTen() {
		return hoTen;
	}
}
