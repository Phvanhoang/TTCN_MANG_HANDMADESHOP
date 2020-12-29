package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import exception.TaiKhoanNotFoundException;
import model.DacQuyen;
import model.DacQuyenNames;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import service.TaiKhoanService;
import utils.GetTaiKhoanFromTokenService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(path = "/tai-khoan-management")
public class TaiKhoanController {
	@Autowired
	private TaiKhoanService taiKhoanService;

	@Autowired
	private GetTaiKhoanFromTokenService getTaiKhoanFromTokenService;

	/*
	 * API khoa tai khoan
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping("/authorized/tai-khoan/lock/{maTaiKhoan}")
	public ResponseEntity<Void> khoaTaiKhoan(@PathVariable long maTaiKhoan) {
		try {
			taiKhoanService.lockTaiKhoan(maTaiKhoan);
		} catch (TaiKhoanNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khong tim thay tai khoan trong CSDL", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * API mo khoa tai khoan
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping("/authorized/tai-khoan/unlock/{maTaiKhoan}")
	public ResponseEntity<Void> moKhoaTaiKhoan(@PathVariable long maTaiKhoan) {
		try {
			taiKhoanService.unlockTaiKhoan(maTaiKhoan);
		} catch (TaiKhoanNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khong tim thay tai khoan trong CSDL", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * API thay doi mat khau
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_USER)")
	@PutMapping("/authorized/tai-khoan/change/{maTaiKhoan}")
	public ResponseEntity<Void> doiMatKhau(@PathVariable long maTaiKhoan, @RequestBody JSONObject jsonObject,
			@RequestHeader("Authorization") String token) {
		TaiKhoan taiKhoan = getTaiKhoanFromTokenService.getTaiKhoan(token);
		DacQuyen dacQuyen = taiKhoan.getDacQuyen();
		String role = dacQuyen.getTenDacQuyen();
		boolean checkPermission = false;
		if (DacQuyenNames.ROLE_USER.equals(role)) {
			if (maTaiKhoan == taiKhoan.getMaTaiKhoan()) {
				checkPermission = true;
			}
		}
		if (checkPermission) {
			try {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String matKhauCu = jsonObject.getAsString("matKhauCu");
				String matKhauMoi = jsonObject.getAsString("matKhauMoi");
				if (!passwordEncoder.matches(matKhauCu, taiKhoan.getMatKhau())) {
					new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
				}
				;
				taiKhoanService.changPassword(maTaiKhoan, passwordEncoder.encode(matKhauMoi));
				return new ResponseEntity<Void>(HttpStatus.OK);
			} catch (TaiKhoanNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khong tim thay tai khoan trong CSDL", e);
			}
		} else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

	}
}
