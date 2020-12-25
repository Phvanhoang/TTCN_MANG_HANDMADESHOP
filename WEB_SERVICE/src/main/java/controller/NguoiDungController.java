package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.DacQuyen;
import model.DacQuyenNames;
import model.GioiTinh;
import model.NguoiDung;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import service.NguoiDungService;
import utils.GetTaiKhoanFromTokenService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("nguoi-dung-management")
public class NguoiDungController {
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private GetTaiKhoanFromTokenService getTaiKhoanFromTokenService;

	/*
	 * API lay thong tin nguoi dung
	 */
	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
	@GetMapping("/authorized/nguoi-dung/{maTaiKhoan}")
	public ResponseEntity<JSONObject> getNguoiDung(@PathVariable Long maTaiKhoan,
			@RequestHeader("Authorization") String token) {
		JSONObject returnedObject = new JSONObject();
		TaiKhoan taiKhoan = getTaiKhoanFromTokenService.getTaiKhoan(token);
		DacQuyen dacQuyen = taiKhoan.getDacQuyen();
		String role = dacQuyen.getTenDacQuyen();
		boolean checkPermission = false;
		if (DacQuyenNames.ROLE_ADMIN.equals(role)) {
			checkPermission = true;
		} else if (DacQuyenNames.ROLE_USER.equals(role)) {
			if (maTaiKhoan == taiKhoan.getMaTaiKhoan()) {
				checkPermission = true;
			}
		}
		if (checkPermission) {
			NguoiDung nguoiDung = taiKhoan.getNguoiDung();
			returnedObject.put("hoTen", nguoiDung.getHoTen());
			returnedObject.put("ngaySinh", nguoiDung.getNgaySinh());
			returnedObject.put("sdt", nguoiDung.getSDT());
			returnedObject.put("thanhPho", nguoiDung.getThanhPho());
			returnedObject.put("anhDaiDien", nguoiDung.getAnhDaiDien());
			returnedObject.put("gioiTinh",
					!(nguoiDung.getGioiTinh() == null) ? nguoiDung.getGioiTinh().getTenGioiTinh() : null);
			return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.OK);
		} else {
			return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.FORBIDDEN);
		}

	}

	/*
	 * API chinh sua thong tin nguoi dung
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_USER)")
	@PutMapping("/authorized/nguoi-dung/{maTaiKhoan}")
	public ResponseEntity<Void> chinhSuaNguoiDung(@PathVariable Long maTaiKhoan, @RequestParam("hoTen") String hoTen,
			@RequestParam("ngaySinh") String ngaySinh, @RequestParam("maGioiTinh") Integer maGioiTinh,
			@RequestParam("thanhPho") String thanhPho, @RequestParam("sdt") String sdt,
			@RequestParam("anhDaiDien") MultipartFile multipartFile, @RequestHeader("Authorization") String tokenJWT) {
		TaiKhoan taiKhoan = getTaiKhoanFromTokenService.getTaiKhoan(tokenJWT);
		DacQuyen dacQuyen = taiKhoan.getDacQuyen();
		String role = dacQuyen.getTenDacQuyen();
		boolean checkPermission = false;
		if (DacQuyenNames.ROLE_USER.equals(role)) {
			if (maTaiKhoan == taiKhoan.getMaTaiKhoan()) {
				checkPermission = true;
			}
		}
		if (checkPermission) {
			NguoiDung nguoiDung = taiKhoan.getNguoiDung();
			nguoiDung.setHoTen(hoTen);

			DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date ngaySinh_convert = null;
			try {
				ngaySinh_convert = new Date(simpleDateFormat.parse(ngaySinh).getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			nguoiDung.setNgaySinh(ngaySinh_convert);
			nguoiDung.setSDT(sdt);
			nguoiDung.setThanhPho(thanhPho);

			GioiTinh gioiTinh = new GioiTinh();
			gioiTinh.setMaGioiTinh(maGioiTinh);
			nguoiDung.setGioiTinh(gioiTinh);

			try {
			 	nguoiDung.setAnhDaiDien(multipartFile.getBytes());
			} catch (IOException e) {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			nguoiDungService.save(nguoiDung);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
	}

	/*
	 * API lay thong tin tat ca nguoi dung
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/nguoi-dung")
	public ResponseEntity<JSONObject> getAllNguoiDung(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("maNguoiDung").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("maNguoiDung").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<NguoiDung> returnedPage = nguoiDungService.findByDeletedFalse(pageable);
		List<NguoiDung> listNguoiDung = returnedPage.getContent();
		for (int i = 0; i < listNguoiDung.size(); i++) {
			listNguoiDung.get(i).getTaiKhoan().getDacQuyen().setDanhSachTaiKhoan(null);
		}
		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", listNguoiDung);
		returnedObject.put("currentPage", returnedPage.getNumber());
		returnedObject.put("totalItems", returnedPage.getTotalElements());
		returnedObject.put("totalPages", returnedPage.getTotalPages());

		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.CREATED);
	}
}
