package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Embeddable
public class DonHang_MatHang_Key implements Serializable {
	private static final long serialVersionUID = 579073343783356009L;

	@Column(name = "ma_mat_hang")
	private long maMatHang;

	@Column(name = "ma_don_hang")
	private long maDonHang;

	public long getMaDonHang() {
		return maDonHang;
	}

	public long getMaMatHang() {
		return maMatHang;
	}

	public void setMaDonHang(long maDonHang) {
		this.maDonHang = maDonHang;
	}

	public void setMaMatHang(long maMatHang) {
		this.maMatHang = maMatHang;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonHang_MatHang_Key other = (DonHang_MatHang_Key) obj;
		return Objects.equals(getMaMatHang(), other.getMaMatHang())
				&& Objects.equals(getMaDonHang(), other.getMaDonHang());
	}
}
