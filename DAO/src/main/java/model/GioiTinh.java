package model;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "GIOITINH")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maGioiTinh")
public class GioiTinh extends AuditModel<TaiKhoan>{
	private static final long serialVersionUID = -2020016808764045972L;

	@Id
	@Column(name = "MaGioiTinh", nullable = false)
	@GeneratedValue
	private long maGioiTinh;
	
	@Column(name = "TenGioiTinh", nullable = false)
	@Nationalized
	private String tenGioiTinh;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gioiTinh")
	private Set<NguoiDung> danhSachNguoiDung;

	public long getMaGioiTinh() {
		return maGioiTinh;
	}
	
	public String getTenGioiTinh() {
		return tenGioiTinh;
	}
	
	public void setDanhSachNguoiDung(Set<NguoiDung> danhSachNguoiDung) {
		this.danhSachNguoiDung = danhSachNguoiDung;
	}
	
	public void setMaGioiTinh(long maGioiTinh) {
		this.maGioiTinh = maGioiTinh;
	}
	
	public void setTenGioiTinh(String tenGioiTinh) {
		this.tenGioiTinh = tenGioiTinh;
	}
}
