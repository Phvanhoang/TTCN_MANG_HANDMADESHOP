package model;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "DONHANG_MATHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHang_MatHang {
	@EmbeddedId
	private DonHang_MatHang_Key id;
	
	@ManyToOne
	@MapsId("maDonHang")
	@JoinColumn(name = "MaDonHang")
	private DonHang donHang;
	
	@ManyToOne
	@MapsId("maMatHang")
	@JoinColumn(name = "MaMatHang")
	private MatHang matHang;
	
	@Column(name = "SoLuong", nullable = true)
	private int soLuong;
}
