package model;
import java.sql.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "DANHGIA")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maDanhGia")
public class DanhGia extends AuditModel<TaiKhoan>{
	private static final long serialVersionUID = 2025865206338089513L;
	@Id
	@Column(name = "MaDanhGia")
	@GeneratedValue
	private long maDanhGia;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaNguoiDung", nullable = false)
	private NguoiDung nguoiDung;
	
	@Column(name = "SoSao", nullable = false)
	private int soSao;
	
	@Column(name = "NoiDung", nullable = false, columnDefinition = "TEXT")
	private int noiDung;
	
	@Column(name = "ThoiGianDanhGia", nullable = false)
	private Date thoiGianDanhGia;

	public long getMaDanhGia() {
		return maDanhGia;
	}
	
	public MatHang getMatHang() {
		return matHang;
	}
	
	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}
	
	public int getNoiDung() {
		return noiDung;
	}
	
	public int getSoSao() {
		return soSao;
	}
	
	public Date getThoiGianDanhGia() {
		return thoiGianDanhGia;
	}
	
	public void setMaDanhGia(long maDanhGia) {
		this.maDanhGia = maDanhGia;
	}
	
	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}
	
	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}
	
	public void setNoiDung(int noiDung) {
		this.noiDung = noiDung;
	}
	
	public void setSoSao(int soSao) {
		this.soSao = soSao;
	}
	
	public void setThoiGianDanhGia(Date thoiGianDanhGia) {
		this.thoiGianDanhGia = thoiGianDanhGia;
	}
	
}
