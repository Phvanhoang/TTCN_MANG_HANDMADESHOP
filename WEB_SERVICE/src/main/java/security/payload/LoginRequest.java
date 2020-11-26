package security.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
    private String tenDangNhap;

    @NotBlank
    private String matKhau;
    
    public String getMatKhau() {
		return matKhau;
	}
    
    public String getTenDangNhap() {
		return tenDangNhap;
	}
    
    public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
    
    public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
}
