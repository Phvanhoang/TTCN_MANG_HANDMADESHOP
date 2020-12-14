package model;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.*;
@Entity
@Table(name = "TRANGTHAIDONHANG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maTrangThai")
public class TrangThaiDonHang extends AuditModel<TaiKhoan>{
	private static final long serialVersionUID = 1106034794734485969L;

	@Id
	@Column(name = "MaTrangThai")
	@GeneratedValue
	private long matrangThai;
	
	@Column(name = "TenTrangThai")
	private String tenTrangThai;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trangThaiDonHang")
	private Set<DonHang> danhSachDonHang;
	
	public Set<DonHang> getDanhSachDonHang() {
		return danhSachDonHang;
	}
	
	public long getMatrangThai() {
		return matrangThai;
	}
	
	public String getTenTrangThai() {
		return tenTrangThai;
	}
	
	public void setDanhSachDonHang(Set<DonHang> danhSachDonHang) {
		this.danhSachDonHang = danhSachDonHang;
	}
	
	public void setMatrangThai(long matrangThai) {
		this.matrangThai = matrangThai;
	}
	
	public void setTenTrangThai(String tenTrangThai) {
		this.tenTrangThai = tenTrangThai;
	}
}
