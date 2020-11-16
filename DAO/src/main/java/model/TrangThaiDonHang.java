package model;
import java.util.Set;

import javax.persistence.*;
import lombok.*;
@Entity
@Table(name = "TRANGTHAIDONHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrangThaiDonHang {
	@Id
	@Column(name = "MaTrangThai")
	@GeneratedValue
	private long matrangThai;
	
	@Column(name = "TenTrangThai")
	private String tenTrangThai;
	
	@OneToMany(mappedBy = "trangThaiDonHang")
	private Set<DonHang> danhSachDonHang;
}
