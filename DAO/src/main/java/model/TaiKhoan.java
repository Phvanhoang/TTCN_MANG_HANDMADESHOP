package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "TAIKHOAN")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maTaiKhoan")
public class TaiKhoan extends AuditModel<TaiKhoan> {
	private static final long serialVersionUID = 485219662594825884L;
	@Id
	@Column(name = "MaTaiKhoan")
	@GeneratedValue
	private long maTaiKhoan;

	@Column(name = "TenDangNhap", nullable = false)
	private String tenDangNhap;

	@Column(name = "MatKhau", nullable = false)
	private String matKhau;

	@OneToOne
	@JoinColumn(name = "MaDacQuyen", nullable = false)
	private DacQuyen dacQuyen;

	@Column(name = "Locked", nullable = false, columnDefinition = "boolean default false")
	private boolean locked;

	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "taiKhoan", cascade = CascadeType.PERSIST)
	private NguoiDung nguoiDung;

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
	private Set<DonHang> sanhSachDonHang;

	public long getMaTaiKhoan() {
		return maTaiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public Set<DonHang> getSanhSachDonHang() {
		return sanhSachDonHang;
	}

	public void setSanhSachDonHang(Set<DonHang> sanhSachDonHang) {
		this.sanhSachDonHang = sanhSachDonHang;
	}

	public DacQuyen getDacQuyen() {
		return dacQuyen;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
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

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}