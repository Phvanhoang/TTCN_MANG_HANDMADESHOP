package security.auditor_aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import model.TaiKhoan;
import security.user.CustomUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<TaiKhoan> {

	public Optional<TaiKhoan> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getTaiKhoan());
	}
}
