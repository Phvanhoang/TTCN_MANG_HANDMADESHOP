package controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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

import model.LoaiMatHang;
import net.minidev.json.JSONObject;
import service.LoaiMatHangService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class LoaiMatHangController {
	@Autowired
	private LoaiMatHangService loaiMatHangService;

	@GetMapping("/loai_mat_hang/{maLoaiMatHang}")
	public ResponseEntity<JSONObject> timLoaiMatHang(@PathVariable Long maLoaiMatHang) {
		LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHangAndDeletedFalse(maLoaiMatHang);
		JSONObject returnedLMH = new JSONObject();

		if (loaiMatHang == null) {
			return new ResponseEntity<JSONObject>(returnedLMH, HttpStatus.NOT_FOUND);
		}
		returnedLMH.put("maLoaiMatHang", loaiMatHang.getMaLoaiMatHang());
		returnedLMH.put("tenLoaiMatHang", loaiMatHang.getTenLoaiMatHang());
		returnedLMH.put("createdAt", loaiMatHang.getCreatedAt());
		returnedLMH.put("createdBy", loaiMatHang.getCreatedBy().getMaTaiKhoan());
		returnedLMH.put("updatedAt", loaiMatHang.getUpdatedAt());
		returnedLMH.put("UpdatedBy", loaiMatHang.getUpdatedBy().getMaTaiKhoan());
		returnedLMH.put("deleted", loaiMatHang.isDeleted());

		return new ResponseEntity<JSONObject>(returnedLMH, HttpStatus.OK);
	}

	@GetMapping("/loai_mat_hang")
	public ResponseEntity<JSONObject> getAllLoaiMatHang(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("maLoaiMatHang").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("maLoaiMatHang").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<LoaiMatHang> returnedPage = loaiMatHangService.findByDeletedFalse(pageable);
		List<LoaiMatHang> listLoaiMatHang = returnedPage.getContent();

		List<JSONObject> data = new ArrayList<JSONObject>();
		for (int i = 0; i < listLoaiMatHang.size(); i++) {
			JSONObject matHang = new JSONObject();
			LoaiMatHang lmh = listLoaiMatHang.get(i);
			matHang.put("maLoaiMatHang", lmh.getMaLoaiMatHang());
			matHang.put("tenLoaiMatHang", lmh.getTenLoaiMatHang());
			matHang.put("createdAt", lmh.getCreatedAt());
			matHang.put("createdBy", lmh.getCreatedBy().getMaTaiKhoan());
			matHang.put("updatedAt", lmh.getUpdatedAt());
			matHang.put("updatedBy", lmh.getUpdatedBy().getMaTaiKhoan());
			matHang.put("deleted", lmh.isDeleted());

			data.add(matHang);
		}
		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", data);
		returnedObject.put("currentPage", returnedPage.getNumber());
		returnedObject.put("totalItems", returnedPage.getTotalElements());
		returnedObject.put("totalPages", returnedPage.getTotalPages());
		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@PostMapping("/loai_mat_hang")
	public ResponseEntity<Void> themLoaiMatHang(@RequestBody JSONObject lmh) {
		try {
			LoaiMatHang loaiMatHang = new LoaiMatHang();
			loaiMatHang.setTenLoaiMatHang((String) lmh.get("tenLoaiMatHang"));
			loaiMatHangService.save(loaiMatHang);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	@PutMapping("/loai_mat_hang/{maLoaiMatHang}")
	public ResponseEntity<Void> suaLoaiMatHang(@PathVariable Long maLoaiMatHang, @RequestBody JSONObject lmh) {
		try {
			LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHangAndDeletedFalse(maLoaiMatHang);

			loaiMatHang.setTenLoaiMatHang(lmh.getAsString("tenLoaiMatHang"));
			loaiMatHangService.save(loaiMatHang);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
