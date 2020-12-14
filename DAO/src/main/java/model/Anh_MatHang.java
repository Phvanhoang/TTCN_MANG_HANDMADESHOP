package model;
import java.sql.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ANH_MATHANG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maAnhMatHang")
public class Anh_MatHang extends AuditModel<TaiKhoan>{
	private static final long serialVersionUID = 1597388159413762079L;
	@Id
	@Column(name = "MaAnhMatHang")
	@GeneratedValue
	private long maAnhMatHang;
	
	@Lob
	@Column(name = "Anh")
	private byte[] anh;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;
	
	public Anh_MatHang(byte[] anh, MatHang matHang) {
		this.anh = anh;
		this.matHang = matHang;
	}
	
	public Anh_MatHang() {}
	
	public byte[] getAnh() {
		return anh;
	}
	
	public long getMaAnhMatHang() {
		return maAnhMatHang;
	}
	
	public MatHang getMatHang() {
		return matHang;
	}
	
	public void setAnh(byte[] anh) {
		this.anh = anh;
	}
	
	public void setMaAnhMatHang(long maAnhMatHang) {
		this.maAnhMatHang = maAnhMatHang;
	}
	
	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}
	
}
