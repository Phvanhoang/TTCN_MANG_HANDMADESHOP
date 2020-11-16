package model;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "QUYEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quyen {
	@Id
	@Column(name = "MaQuyen")
	@GeneratedValue
	private long maQuyen;
	
	@Column(name = "TenQuyen", nullable = false)
	private String tenQuyen;
	
	@OneToMany(mappedBy = "quyen")
	private Set<TaiKhoan> danhSachTaiKhoan;
}
