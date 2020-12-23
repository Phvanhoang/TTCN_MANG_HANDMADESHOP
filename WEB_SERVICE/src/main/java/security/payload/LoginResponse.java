package security.payload;

public class LoginResponse {
	private String accessToken;
	private String tokenType = "HANDMADE_SHOP";
	private String tenDangNhap;
	private Long id;
	private String role;

	public LoginResponse(String accessToken, String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
		this.accessToken = accessToken;
	}

	public LoginResponse() {

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

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
