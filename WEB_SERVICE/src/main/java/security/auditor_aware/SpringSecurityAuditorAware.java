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

	private TaiKhoan taiKhoan;
	private static long maTaiKhoan = 1L;

	public SpringSecurityAuditorAware(TaiKhoanService taiKhoanService, QuyenService quyenService) {
		if (taiKhoanService.existsById(maTaiKhoan)) {
			taiKhoan = taiKhoanService.findByMaTaiKhoan(1L);
		} else {
			ArrayList<DacQuyen> danhSachDacQuyen = quyenService.getDanhSachDacQuyen();
			for(int i=0;i<danhSachDacQuyen.size();i++) {
				if(danhSachDacQuyen.get(i).getTenDacQuyen().equals("ROLE_USER")) {
					taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
					break;
				}
			}
			taiKhoan = new TaiKhoan();
			taiKhoan.setMaTaiKhoan(maTaiKhoan);
			taiKhoan.setTenDangNhap("username");
			taiKhoan.setMatKhau("password");
			taiKhoanService.save(taiKhoan);
		}
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
		return Optional.of(taiKhoan);

	}
}
