package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "NGUOIDUNG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {
	@Id
	@Column(name = "MaNguoiDung")
	@GeneratedValue
	private long maNguoiDung;
	
	@Lob
	@Column(name = "AnhDaiDien", nullable = true)
	private byte[] anhDaiDien;
	
	@ManyToOne
	@JoinColumn(name = "MaGioiTinh", nullable = true)
	private GioiTinh gioiTinh;
	
	@Column(name = "ThanhPho", nullable = true)
	private String ThanhPho;
	
	@Column(name = "SDT", nullable = true)
	private String SDT;
	
	@Column(name = "NgaySinh", nullable = true)
	private Date NgaySinh;
	
	@OneToOne(optional=false, mappedBy="nguoiDung")
	private TaiKhoan taiKhoan;
	
	@OneToMany(mappedBy = "nguoiDung")
	private Set<DonHang> sanhSachDonHang;
}
