package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import model.TaiKhoan;
import security.jwt.JwtTokenProvider;
import service.TaiKhoanService;

@Component
public class GetTaiKhoanFromTokenService {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private TaiKhoanService taiKhoanService;
	public TaiKhoan getTaiKhoan(String token) {
		long id = jwtTokenProvider.getUserIdFromJWT(token);
		return taiKhoanService.findByMaTaiKhoanAndIsDeletedFalse(id);
	}
}
