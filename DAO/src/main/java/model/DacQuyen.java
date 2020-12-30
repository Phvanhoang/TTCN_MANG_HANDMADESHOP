package model;

import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "DACQUYEN")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maDacQuyen")
public class DacQuyen {
	@Id
	@Column(name = "MaDacQuyen")
	@GeneratedValue
	private long maDacQuyen;

	@Column(name = "TenDacQuyen", nullable = false)
	private String tenDacQuyen;

	@OneToMany(mappedBy = "dacQuyen")
	private Set<TaiKhoan> danhSachTaiKhoan;

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
