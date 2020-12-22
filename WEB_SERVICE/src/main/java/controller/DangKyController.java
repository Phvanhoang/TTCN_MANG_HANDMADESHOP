package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import model.DacQuyen;
import model.DacQuyenNames;
import model.NguoiDung;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import security.user.CustomUserDetails;
import service.NguoiDungService;
import service.QuyenService;
import service.TaiKhoanService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class DangKyController {
	@Autowired
	private TaiKhoanService taiKhoanService;
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired 
	private QuyenService quyenService;
	@PostMapping("/register")
	public ResponseEntity<Void> dangKy(@RequestBody JSONObject taiKhoanDangKy) {
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String matKhau = (String) taiKhoanDangKy.get("matKhau");
			String tenDangKy = (String) taiKhoanDangKy.get("tenDangKy");
			String tenNguoiDung = (String) taiKhoanDangKy.get("tenNguoiDung");
			String matKhauMaHoa = passwordEncoder.encode(matKhau);
			if (taiKhoanService.existsByTenDangNhap(tenDangKy)) 
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			NguoiDung nguoiDung = new NguoiDung();
			nguoiDung.setHoTen(tenNguoiDung);	
			
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setTenDangNhap(tenDangKy);
			taiKhoan.setMatKhau(matKhauMaHoa);
			
			ArrayList<DacQuyen> danhSachDacQuyen = quyenService.getDanhSachDacQuyen();
			for(int i=0;i<danhSachDacQuyen.size();i++) {
				if(danhSachDacQuyen.get(i).getTenDacQuyen().equals(DacQuyenNames.ROLE_USER)) {
					taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
					break;
				}
			}
	
			TaiKhoan createdTaiKhoan = taiKhoanService.save(taiKhoan);
			UserDetails userDetails = new CustomUserDetails(createdTaiKhoan);
			UsernamePasswordAuthenticationToken authReq
			 = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authReq);
	
			nguoiDung.setTaiKhoan(createdTaiKhoan);
			createdTaiKhoan.setNguoiDung(nguoiDung);
			nguoiDungService.save(nguoiDung);
			
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception exc) {
	         throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi tạo tài khoản tại Server", exc);
	    }
	}
	
}
