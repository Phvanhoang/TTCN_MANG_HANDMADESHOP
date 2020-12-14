package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import model.LoaiMatHang;
import model.MatHang;
import net.minidev.json.JSONObject;
import service.LoaiMatHangService;
import service.MatHangService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class MatHangController {
	@Autowired
	private MatHangService matHangService;
	
	@Autowired LoaiMatHangService loaiMatHangService;

	// tạo mặt hàng
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@PostMapping(value = "/authorized/mat_hang")
	public ResponseEntity<Void> taoMatHang(@RequestBody JSONObject matHang) {
		try {
			Gson gson = new Gson();
			matHangService.save(gson.fromJson(matHang.toString(), MatHang.class));
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// lấy thông tin mặt hàng theo mã mặt hàng
	@GetMapping("/mat_hang/{maMatHang}")
	public ResponseEntity<JSONObject> timMatHang(@PathVariable Long maMatHang) {
		try {
			MatHang matHang = matHangService.findByMaMatHangAndDeletedFalse(maMatHang);

			JSONObject returnedMatHang = new JSONObject();
			returnedMatHang.put("maMatHang", matHang.getMaMatHang());
			returnedMatHang.put("tenMatHang", matHang.getTenMatHang());
			returnedMatHang.put("maLoaiMatHang", matHang.getLoaiMatHang().getMaLoaiMatHang());
			returnedMatHang.put("gia", matHang.getGia());
			returnedMatHang.put("soLuong", matHang.getSoLuong());
			returnedMatHang.put("soLuongDaBan", matHang.getSoLuongDaBan());
			returnedMatHang.put("moTa", matHang.getMoTa());
			returnedMatHang.put("createdAt", matHang.getCreatedAt());
			returnedMatHang.put("createdBy", matHang.getCreatedBy().getMaTaiKhoan());
			returnedMatHang.put("updatedAt", matHang.getUpdatedAt());
			returnedMatHang.put("updatedBy", matHang.getUpdatedBy().getMaTaiKhoan());

			return new ResponseEntity<JSONObject>(returnedMatHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
		}

	}

	// lấy thông tin tất cả mặt hàng
	@GetMapping("/mat_hang")
	public ResponseEntity<JSONObject> getAllMatHang(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("maMatHang").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("maMatHang").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<MatHang> returnedPage = matHangService.findByDeletedFalse(pageable);
		List<MatHang> listMatHang = returnedPage.getContent();

		List<JSONObject> data = new ArrayList<JSONObject>();
		for (int i = 0; i < listMatHang.size(); i++) {
			JSONObject matHang = new JSONObject();
			MatHang mh = listMatHang.get(i);
			matHang.put("maMatHang", mh.getMaMatHang());
			matHang.put("tenMatHang", mh.getTenMatHang());
			matHang.put("maLoaiMatHang", mh.getLoaiMatHang().getMaLoaiMatHang());
			matHang.put("gia", mh.getGia());
			matHang.put("soLuong", mh.getSoLuong());
			matHang.put("soLuongDaBang", mh.getSoLuongDaBan());
			matHang.put("moTa", mh.getMoTa());
			matHang.put("createdAt", mh.getCreatedAt());
			matHang.put("createdBy", mh.getCreatedBy().getMaTaiKhoan());
			matHang.put("updatedAt", mh.getUpdatedAt());
			matHang.put("updatedBy", mh.getUpdatedBy().getMaTaiKhoan());
			matHang.put("deleted", mh.isDeleted());

			data.add(matHang);
		}

		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", data);
		returnedObject.put("currentPage", returnedPage.getNumber());
		returnedObject.put("totalItems", returnedPage.getTotalElements());
		returnedObject.put("totalPages", returnedPage.getTotalPages());

		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@PutMapping("/authorized/mat_hang/{maMatHang}")
	public ResponseEntity<Void> suaMatHang(@PathVariable Long maMatHang, @RequestBody net.minidev.json.JSONObject mhInput){
		org.springframework.boot.configurationprocessor.json.JSONObject mh = new org.springframework.boot.configurationprocessor.json.JSONObject(mhInput);
		System.out.println(mh.toString());
		try {
			MatHang matHang = matHangService.findByMaMatHangAndDeletedFalse(maMatHang);
			try {
				
				String tenMatHang = mh.getString("tenMatHang");
				System.out.println("tenMatHang: "+ tenMatHang );
				Long maLoaiMatHang = mh.getLong("maLoaiMatHang");
				Long gia = mh.getLong("gia");
				int soLuong = mh.getInt("soLuong");
				int soLuongDaBan = mh.getInt("soLuongDaBan");
				String moTa = mh.getString("moTa");
				
				LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHangAndDeletedFalse(maLoaiMatHang);
				
				matHang.setTenMatHang(mh.getString("tenMatHang"));
				matHang.setLoaiMatHang(loaiMatHang);
				matHang.setGia(gia);
				matHang.setSoLuong(soLuong);
				matHang.setSoLuongDaBan(soLuongDaBan);
				matHang.setMoTa(moTa);
				
				matHangService.save(matHang);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);	
	}
	
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@DeleteMapping("/authorized/mat_hang/{maMatHang}")
	public ResponseEntity<Void> xoaMatHang(@PathVariable Long maMatHang){
		try {
			MatHang matHang = matHangService.findByMaMatHangAndDeletedFalse(maMatHang);
			matHang.setDeleted(true);
			matHangService.save(matHang);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);	
	}
}
