package security.auditor_aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import model.TaiKhoan;
import security.user.CustomUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<TaiKhoan> {

	public Optional<TaiKhoan> getCurrentAuditor() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()) {
				return null;
			}
			TaiKhoan taiKhoan = ((CustomUserDetails) authentication.getPrincipal()).getTaiKhoan();
			return Optional.of(taiKhoan);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return Optional.of(new TaiKhoan());

	}
}
