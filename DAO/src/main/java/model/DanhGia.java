package model;
import java.util.Date;

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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;

	@Column(name = "SoSao", nullable = false)
	private int soSao;
	
	@Column(name = "NoiDung", nullable = false, columnDefinition = "TEXT")
	private String noiDung;
	
	public long getMaDanhGia() {
		return maDanhGia;
	}
	
	public MatHang getMatHang() {
		return matHang;
	}

	public String getNoiDung() {
		return noiDung;
	}
	
	public int getSoSao() {
		return soSao;
	}

	public void setMaDanhGia(long maDanhGia) {
		this.maDanhGia = maDanhGia;
	}
	
	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
	public void setSoSao(int soSao) {
		this.soSao = soSao;
	}
	
}
