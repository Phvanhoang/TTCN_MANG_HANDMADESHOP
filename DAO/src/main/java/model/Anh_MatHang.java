package model;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ANH_MATHANG")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "maAnhMatHang")
public class Anh_MatHang {
	@Id
	@Column(name = "MaAnhMatHang")
	@GeneratedValue
	private long maAnhMatHang;
	
	@Lob
	@Column(name = "Anh")
	private byte[] anh;
	
	@JsonManagedReference // Annotation de ko tra ve hoan toan doi tuong, chi tra ve ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaMatHang", nullable = false)
	private MatHang matHang;
	
	@JsonIgnore // Annotion de bo qua truong ko them vao doi tuowng tra ve
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
