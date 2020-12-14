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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import model.MatHang;
import net.minidev.json.JSONObject;
import service.MatHangService;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class MatHangController {
	@Autowired
	private MatHangService matHangService;

	
	// tạo mặt hàng
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@PostMapping(value="/authorized/mat_hang")
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
	public ResponseEntity<MatHang> timMatHang(@PathVariable Long maMatHang) {
		MatHang matHang = matHangService.findByMaMatHangAndDeletedFalse(maMatHang);
		if(matHang==null) {
			return new ResponseEntity<MatHang>(matHang, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MatHang>(matHang, HttpStatus.OK);
	}
	
	// lấy thông tin tất cả mặt hàng 
	@GetMapping("/mat_hang")
	public ResponseEntity<JSONObject> getAllMatHang(
			@RequestParam(name="page", required=false, defaultValue="0") int page,
			@RequestParam(name="size", required=false, defaultValue="15") int size,
			@RequestParam(name="sort", required=false, defaultValue="ASC") String sort) throws JSONException{
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("maMatHang").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("maMatHang").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		
		Page<MatHang> returnedPage = matHangService.findByDeletedFalse(pageable);
		List<MatHang> listMatHang = returnedPage.getContent();
		
		List<JSONObject> data = new ArrayList<JSONObject>();
		for(int i=0;i<listMatHang.size();i++) {
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
	
//	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
//	@PutMapping("/mat_hang/{maMatHang}")
//	public ResponseEntity<Void> suaMatHang(@PathVariable Long maMatHang, @RequestBody JSONObject mh){
//		try {
//			MatHang matHang = matHangService.findByMaMatHangAndDeletedFalse(maMatHang);
//			
//		}
//	}
}
