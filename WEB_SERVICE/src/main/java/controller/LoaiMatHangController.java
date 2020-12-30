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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.LoaiMatHang;
import net.minidev.json.JSONObject;
import service.LoaiMatHangService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3001" })
@RestController
@RequestMapping(path = "/loai-mat-hang-management")
public class LoaiMatHangController {
	@Autowired
	private LoaiMatHangService loaiMatHangService;

	// lấy thông tin loại mặt hàng theo mã loại mặt hàng
	@GetMapping("/loai-mat-hang/{maLoaiMatHang}")
	public ResponseEntity<JSONObject> timLoaiMatHang(@PathVariable Long maLoaiMatHang) {
		LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHang(maLoaiMatHang);
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

//	// lấy thông tin tất cả loại mặt hàng
//	@GetMapping("/loai-mat-hang")
//	public ResponseEntity<JSONObject> getAllLoaiMatHang() {
//		ArrayList<LoaiMatHang> list = loaiMatHangService.findAll();
//		JSONObject returnJson = new JSONObject();
//		for (int i = 0; i < list.size(); i++) {
//			LoaiMatHang loaiMatHang = list.get(i);
//			loaiMatHang.setDanhSachMatHang(null);
//		}
//		returnJson.put("data", list);
//		returnJson.put("size", list.size());
//		return new ResponseEntity<JSONObject>(returnJson, HttpStatus.OK);
//	}
	// lấy thông tin tất cả loại mặt hàng
	@GetMapping("/loai-mat-hang")
	public ResponseEntity<JSONObject> getAllLoaiMatHang(
			@RequestParam(name = "tenLoaiMatHang", required = false, defaultValue = "") String tenLoaiMatHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "maLoaiMatHang") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<LoaiMatHang> returnedPage = loaiMatHangService.findWithFilter(pageable, tenLoaiMatHang);
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

	// thêm một loại mặt hàng vào CSDL
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PostMapping("/authorized/loai-mat-hang")
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

	// chỉnh sửa thông tin loại mặt hàng
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping("/authorized/loai-mat-hang/{maLoaiMatHang}")
	public ResponseEntity<Void> suaLoaiMatHang(@PathVariable Long maLoaiMatHang, @RequestBody JSONObject lmh) {
		try {
			LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHang(maLoaiMatHang);
			loaiMatHang.setTenLoaiMatHang(lmh.getAsString("tenLoaiMatHang"));
			loaiMatHangService.save(loaiMatHang);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
