package security.payload;

public class LoginResponse {
    private String accessToken;
    private String tokenType = "HANDMADE_SHOP";
    private String tenDangNhap;
    public LoginResponse(String accessToken, String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
		return accessToken;
	}
    
    public String getTokenType() {
		return tokenType;
	}
    
    public String getTenDangNhap() {
		return tenDangNhap;
	}
    
    public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
    
    public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
    
    public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
}
