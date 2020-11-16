package model;
import java.sql.Date;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "TAIKHOAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
	@Id
	@Column(name = "MaTaiKhoan")
	@GeneratedValue
	private long maTaiKhoan;
	
	@Column(name = "TenDangNhap", nullable = false)
	private String tenDangNhap;
	
	@Column(name = "MatKhau", nullable = false)
	private String matKhau;
	
	@OneToOne
	@JoinColumn(name = "MaQuyen", nullable = false)
	private Quyen quyen;
	
	@Column(name = "ThoiGianDangKy", nullable = false)
	private Date thoiGianDangKy;
	
	@Column(name = "TrangThai", nullable = false)
	private boolean trangThai;
	
	@OneToOne(optional=false)
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung;
}