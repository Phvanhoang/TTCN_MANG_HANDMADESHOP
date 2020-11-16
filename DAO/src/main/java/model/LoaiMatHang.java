package model;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOAIMATHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiMatHang {
	@Id
	@Column(name = "MaLoaiMatHang")
	@GeneratedValue
	private long maLoaiMatHang;
	
	@Column(name = "TenLoaiMtHang")
	private String tenLoaiMatHang;
	
	@OneToMany(mappedBy = "loaiMatHang")
	private Set<MatHang> danhSachMatHang;
}
