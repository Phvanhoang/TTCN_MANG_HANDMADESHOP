package model;
import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class DonHang_MatHang_Key implements Serializable{
	private static final long serialVersionUID = 579073343783356009L;

	@Column(name = "MaMatHang")
    private long maMatHang;
 
    @Column(name = "MaDonHang")
    private long maDonHang;
}
