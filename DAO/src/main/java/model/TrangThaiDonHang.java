package model;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "TRANGTHAIDONHANG")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maTrangThai")
public class TrangThaiDonHang {
	@Id
	@Column(name = "MaTrangThai")
	@GeneratedValue
	private long maTrangThai;

	@Column(name = "TenTrangThai")
	private String tenTrangThai;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trangThaiDonHang")
	private Set<DonHang> danhSachDonHang;

	public Set<DonHang> getDanhSachDonHang() {
		return danhSachDonHang;
	}

	public long getMaTrangThai() {
		return maTrangThai;
	}

	public String getTenTrangThai() {
		return tenTrangThai;
	}

	public void setDanhSachDonHang(Set<DonHang> danhSachDonHang) {
		this.danhSachDonHang = danhSachDonHang;
	}

	public void setMaTrangThai(long matrangThai) {
		this.maTrangThai = matrangThai;
	}

	public void setTenTrangThai(String tenTrangThai) {
		this.tenTrangThai = tenTrangThai;
	}
}
