package model;
import java.util.List;

import javax.persistence.*;
import lombok.*;
@Entity
@Table(name = "MATHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatHang {
	@Id
	@Column(name = "MaMatHang")
	@GeneratedValue
	private long maMatHang;
	
	@Column(name = "TenMatHang", nullable = false)
	private String tenMatHang;
	
	@ManyToOne
	@JoinColumn(name = "MaLoaiMatHang", nullable = false)
	private LoaiMatHang loaiMatHang;
	
	@Column(name = "Gia", nullable = false)
	private long gia;
	
	@Column(name = "SoLuong", nullable = false)
	private int soLuong;
	
	@Column(name = "SoLuongDaBan", nullable = false)
	private int soLuongDaBan;
	
	@Column(name = "MoTa", nullable = false)
	private String moTa;
	
	@OneToMany(mappedBy = "matHang")
	private List<DanhGia> danhSachDanhGia;
	
	@OneToMany(mappedBy = "matHang")
	private List<Anh_MatHang> danhSachHinhAnh;
}
