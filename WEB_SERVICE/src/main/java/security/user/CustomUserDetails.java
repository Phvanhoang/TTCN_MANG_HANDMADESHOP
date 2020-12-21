package security.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import model.TaiKhoan;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 385616071421254980L;
	private TaiKhoan taiKhoan;

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
	public CustomUserDetails(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(taiKhoan.getDacQuyen().getTenDacQuyen()));
        return list;
    }

    public String getPassword() {
        return taiKhoan.getMatKhau();
    }

    public String getUsername() {
        return taiKhoan.getTenDangNhap();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return !taiKhoan.isLocked();
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
