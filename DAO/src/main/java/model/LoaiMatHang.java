package model;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "LOAIMATHANG")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maLoaiMatHang")
public class LoaiMatHang extends AuditModel<TaiKhoan>{
	private static final long serialVersionUID = 395474701066691753L;

	@Id
	@Column(name = "MaLoaiMatHang")
	@GeneratedValue
	private long maLoaiMatHang;
	
	@Column(name = "TenLoaiMatHang")
	private String tenLoaiMatHang;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiMatHang")
	private Set<MatHang> danhSachMatHang;
	
	public Set<MatHang> getDanhSachMatHang() {
		return danhSachMatHang;
	}
	
	public long getMaLoaiMatHang() {
		return maLoaiMatHang;
	}
	
	public String getTenLoaiMatHang() {
		return tenLoaiMatHang;
	}
	
	public void setDanhSachMatHang(Set<MatHang> danhSachMatHang) {
		this.danhSachMatHang = danhSachMatHang;
	}
	
	public void setMaLoaiMatHang(long maLoaiMatHang) {
		this.maLoaiMatHang = maLoaiMatHang;
	}
	
	public void setTenLoaiMatHang(String tenLoaiMatHang) {
		this.tenLoaiMatHang = tenLoaiMatHang;
	}
	
}
