package model;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "DANHGIA")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maDanhGia")
public class DanhGia {
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
	
	@JsonIgnore
	@Column(name = "IsDeleted", nullable = false)
	private boolean isDeleted;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "UpdatedBy", nullable = true)
	private TaiKhoan updatedBy;
	
	@JsonIgnore
	@Column(name = "UpdatedAt", nullable = true)
	private Date updatedAt;
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public TaiKhoan getUpdatedBy() {
		return updatedBy;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void setUpdatedBy(TaiKhoan updatedBy) {
		this.updatedBy = updatedBy;
	}
	
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
