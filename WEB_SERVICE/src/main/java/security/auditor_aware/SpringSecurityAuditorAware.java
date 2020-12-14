package security.auditor_aware;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import model.DacQuyen;
import model.TaiKhoan;
import repository.TaiKhoanRepository;
import security.user.CustomUserDetails;
import service.QuyenService;
import service.TaiKhoanService;

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
//		System.out.println(maTaiKhoan);
		return Optional.of(taiKhoan);
	}
}
