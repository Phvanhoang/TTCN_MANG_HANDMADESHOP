package model;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "DONHANG_MATHANG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DonHang_MatHang extends AuditModel<TaiKhoan> {
	private static final long serialVersionUID = 4241860765887281677L;
	@EmbeddedId
	private DonHang_MatHang_Key id = new DonHang_MatHang_Key();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("maDonHang")
	@JoinColumn(name = "ma_don_hang")
	private DonHang donHang;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("maMatHang")
	@JoinColumn(name = "ma_mat_hang")
	private MatHang matHang;

	@Column(name = "SoLuong", nullable = true)
	private int soLuong;

	public DonHang getDonHang() {
		return donHang;
	}

	public MatHang getMatHang() {
		return matHang;
	}

	public DonHang_MatHang_Key getId() {
		return id;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setDonHang(DonHang donHang) {
		this.donHang = donHang;
	}

	public void setId(DonHang_MatHang_Key id) {
		this.id = id;
	}

	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

}
