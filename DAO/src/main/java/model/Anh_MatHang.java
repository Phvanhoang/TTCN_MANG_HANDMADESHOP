package model;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "ANH_MATHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anh_MatHang {
	@Id
	@Column(name = "MaAnhMatHang")
	@GeneratedValue
	private long maAnhMatHang;
	
	@Lob
	@Column(name = "Anh")
	private byte[] anh;
	
	@ManyToOne
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;
}
