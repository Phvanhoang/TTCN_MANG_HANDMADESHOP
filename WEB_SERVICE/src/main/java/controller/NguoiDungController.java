package controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.GioiTinh;
import model.NguoiDung;
import net.minidev.json.JSONObject;
import service.NguoiDungService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class NguoiDungController {
	@Autowired
	private NguoiDungService nguoiDungService;

	// @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@GetMapping("/nguoi_dung/{maNguoiDung}")
	public ResponseEntity<JSONObject> getNguoiDung(@PathVariable Long maNguoiDung) {
		JSONObject returnedObject = new JSONObject();
		NguoiDung nguoiDung = nguoiDungService.findOne(maNguoiDung);
		returnedObject.put("hoTen", nguoiDung.getHoTen());
		returnedObject.put("ngaySinh", nguoiDung.getNgaySinh());
		returnedObject.put("sdt", nguoiDung.getSDT());
		returnedObject.put("thanhPho", nguoiDung.getThanhPho());
		returnedObject.put("anhDaiDien", nguoiDung.getAnhDaiDien());
		returnedObject.put("gioiTinh", nguoiDung.getGioiTinh().getTenGioiTinh());

		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@PostMapping("/nguoi_dung/{maNguoiDung}")
	public ResponseEntity<Void> chinhSuaNguoiDung(@PathVariable Long maNguoiDung,
			@RequestBody JSONObject nguoiDungUpdate) {
		NguoiDung nguoiDung = nguoiDungService.findOne(maNguoiDung);
		nguoiDung.setHoTen((String) nguoiDungUpdate.get("hoTen"));

		String ngaySinh = (String) nguoiDungUpdate.get("ngaySinh");
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date ngaySinh_convert = null;
		try {
			ngaySinh_convert = new Date(simpleDateFormat.parse(ngaySinh).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		nguoiDung.setNgaySinh(ngaySinh_convert);
		nguoiDung.setSDT((String) nguoiDungUpdate.get("sdt"));
		nguoiDung.setThanhPho((String) nguoiDungUpdate.get("thanhPho"));

		int maGioiTinh = (Integer) nguoiDungUpdate.get("maGioiTinh");
		System.out.println("maGioiTinh: " + maGioiTinh);

		GioiTinh gioiTinh = new GioiTinh();
		System.out.println("gioiTinh :" + gioiTinh);
		gioiTinh.setMaGioiTinh(maGioiTinh);
		nguoiDung.setGioiTinh(gioiTinh);

		nguoiDungService.save(nguoiDung);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
