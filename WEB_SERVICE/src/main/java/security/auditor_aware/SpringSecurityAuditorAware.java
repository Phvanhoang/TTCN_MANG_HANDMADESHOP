package security.auditor_aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import model.TaiKhoan;
import security.user.CustomUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<TaiKhoan> {
	private long maTaiKhoan = 1L;
	private TaiKhoan taiKhoan;
	public SpringSecurityAuditorAware(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
	public Optional<TaiKhoan> getCurrentAuditor() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()) {
				return null;
			}
			TaiKhoan taiKhoanNguoiDung = ((CustomUserDetails) authentication.getPrincipal()).getTaiKhoan();
			return Optional.of(taiKhoanNguoiDung);
		} catch (Exception e) {
		}
		if (taiKhoan == null) {
			taiKhoan = new TaiKhoan();
			taiKhoan.setMaTaiKhoan(maTaiKhoan);
		}
		return Optional.of(taiKhoan);
	}
}
