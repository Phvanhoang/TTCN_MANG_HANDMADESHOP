package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;
import lombok.*;
@Entity
@Table(name = "DONHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHang {
	@Id
	@Column(name = "MaDonHang")
	@GeneratedValue
	private long maDonHang;
	
	@ManyToOne
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung;
	
	@ManyToOne
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
	
	@OneToMany(mappedBy = "donHang")
	private Set<DonHang_MatHang> danhSachMatHang;
	
}
