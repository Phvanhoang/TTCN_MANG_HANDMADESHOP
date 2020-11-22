package model;
import java.sql.Date;

import javax.persistence.*;
import lombok.*;
@Entity
@Table(name = "DANHGIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {
	@Id
	@Column(name = "MaDanhGia")
	@GeneratedValue
	private long maDanhGia;
	
	@ManyToOne
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;
	
	@ManyToOne
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung;
	
	@Column(name = "SoSao", nullable = false)
	private int soSao;
	
	@Column(name = "NoiDung", nullable = false, columnDefinition = "TEXT")
	private int noiDung;
	
	@Column(name = "ThoiGianDanhGia", nullable = false)
	private Date thoiGianDanhGia;
}
