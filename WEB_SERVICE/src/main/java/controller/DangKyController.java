package controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.DacQuyen;
import model.NguoiDung;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import service.NguoiDungService;
import service.QuyenService;
import service.TaiKhoanService;

@CrossOrigin(origins = {"http://localhost:3000"})
//@SessionAttributes("taiKhoan")
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
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String matKhau = (String) taiKhoanDangKy.get("matKhau");
		String tenDangKy = (String) taiKhoanDangKy.get("tenDangKy");
		String tenNguoiDung = (String) taiKhoanDangKy.get("tenNguoiDung");
		
		String matKhauMaHoa = passwordEncoder.encode(matKhau);
		
		NguoiDung nguoiDung = new NguoiDung();
		nguoiDung.setHoTen(tenNguoiDung);	
		
		TaiKhoan taiKhoan = new TaiKhoan();
		taiKhoan.setTenDangNhap(tenDangKy);
		taiKhoan.setMatKhau(matKhauMaHoa);
//		taiKhoan.setNguoiDung(nguoiDung);
		
		ArrayList<DacQuyen> danhSachDacQuyen = quyenService.getDanhSachDacQuyen();
		for(int i=0;i<danhSachDacQuyen.size();i++) {
			if(danhSachDacQuyen.get(i).getTenDacQuyen().equals("ROLE_USER")) {
				taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
				break;
			}
		}
		String thoiGianString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date thoiGian = null;
		try {
			thoiGian = new Date(simpleDateFormat.parse(thoiGianString).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		taiKhoan.setThoiGianDangKy(thoiGian);
		taiKhoan.setCreatedBy(null);
		try {
			taiKhoanService.save(taiKhoan);
		}
		catch(Exception e) {
			System.out.println("loi tai cho nay!");
		}
		
		//nguoiDung.setCreatedBy(Long.toString(createdTaiKhoan.getMaTaiKhoan()));
		//nguoiDung.setTaiKhoan(createdTaiKhoan);
		//nguoiDungService.save(nguoiDung);
		
		if(nguoiDung == null || taiKhoan == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
