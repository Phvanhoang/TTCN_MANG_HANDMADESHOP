package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.GioiTinh;
import model.NguoiDung;
import net.minidev.json.JSONObject;
import service.NguoiDungService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class NguoiDungController {
	@Autowired
	private NguoiDungService nguoiDungService;

	@PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@GetMapping("/authorized/nguoi_dung/{maNguoiDung}")
	public ResponseEntity<JSONObject> getNguoiDung(@PathVariable Long maNguoiDung) {
		JSONObject returnedObject = new JSONObject();
		NguoiDung nguoiDung = nguoiDungService.findOne(maNguoiDung);
		if (nguoiDung == null) return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.NOT_FOUND);
		returnedObject.put("hoTen", nguoiDung.getHoTen());
		returnedObject.put("ngaySinh", nguoiDung.getNgaySinh());
		returnedObject.put("sdt", nguoiDung.getSDT());
		returnedObject.put("thanhPho", nguoiDung.getThanhPho());
		returnedObject.put("anhDaiDien", nguoiDung.getAnhDaiDien());
		returnedObject.put("gioiTinh", nguoiDung.getGioiTinh().getTenGioiTinh());
		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.OK);
	}


	@PostMapping("/nguoi_dung/create")
	public ResponseEntity<Void> createND(@RequestParam String hoTen, @RequestParam("avatar") MultipartFile multipartFile) {
		NguoiDung nguoiDung = new NguoiDung();
		nguoiDung.setHoTen(hoTen);
		try {
			nguoiDung.setAnhDaiDien(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		nguoiDungService.save(nguoiDung);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	@PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@PostMapping("/authorized/nguoi_dung/{maNguoiDung}")
	public ResponseEntity<Void> chinhSuaNguoiDung(@PathVariable Long maNguoiDung, @RequestParam("hoTen") String hoTen,
			@RequestParam("ngaySinh") String ngaySinh, @RequestParam("maGioiTinh") Integer maGioiTinh,
			@RequestParam("thanhPho") String thanhPho, @RequestParam("sdt") String sdt,
			@RequestParam("anhDaiDien") MultipartFile multipartFile) {
		NguoiDung nguoiDung = nguoiDungService.findOne(maNguoiDung);
		nguoiDung.setHoTen(hoTen);

		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date ngaySinh_convert = null;
		try {
			ngaySinh_convert = new Date(simpleDateFormat.parse(ngaySinh).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		nguoiDung.setNgaySinh(ngaySinh_convert);
		nguoiDung.setSDT(sdt);
		nguoiDung.setThanhPho(thanhPho);

		GioiTinh gioiTinh = new GioiTinh();
		System.out.println("gioiTinh :" + gioiTinh);
		gioiTinh.setMaGioiTinh(maGioiTinh);
		nguoiDung.setGioiTinh(gioiTinh);

		try {
			nguoiDung.setAnhDaiDien(multipartFile.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		nguoiDungService.save(nguoiDung);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@GetMapping("/authorized/nguoi_dung")
	public ResponseEntity<JSONObject> getAllNguoiDung(
			@RequestParam(name="page", required=false, defaultValue="0") int page,
			@RequestParam(name="size", required=false, defaultValue="15") int size,
			@RequestParam(name="sort", required=false, defaultValue="ASC") String sort){
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("maNguoiDung").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("maNguoiDung").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		
		Page<NguoiDung> returnedPage = nguoiDungService.findByIsDeletedFalse(pageable);
		List<NguoiDung> listNguoiDung = returnedPage.getContent();
		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", listNguoiDung);
		returnedObject.put("currentPage", returnedPage.getNumber());
	    returnedObject.put("totalItems", returnedPage.getTotalElements());
	    returnedObject.put("totalPages", returnedPage.getTotalPages());
		
		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.CREATED);

	}
}
