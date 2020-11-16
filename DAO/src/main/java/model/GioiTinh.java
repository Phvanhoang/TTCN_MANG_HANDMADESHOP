package model;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "GIOITINH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GioiTinh {
	@Id
	@Column(name = "MaGioiTinh", nullable = false)
	private long maGioiTinh;
	
	@Column(name = "TenGioiTinh", nullable = false)
	private String tenGioiTinh;
	
	@OneToMany(mappedBy = "gioiTinh")
	private Set<NguoiDung> danhSachNguoiDung;
}
