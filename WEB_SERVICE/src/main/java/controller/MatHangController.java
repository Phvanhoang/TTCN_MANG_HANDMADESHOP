package controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import exception.Anh_MatHangNotFoundException;
import exception.MatHangNotFoundException;
import model.Anh_MatHang;
import model.LoaiMatHang;
import model.MatHang;
import net.minidev.json.JSONObject;
import service.Anh_MatHangService;
import service.LoaiMatHangService;
import service.MatHangService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(path = "mat-hang-management")
public class MatHangController {
	@Autowired
	private Anh_MatHangService anh_MatHangService;
	@Autowired
	private MatHangService matHangService;
	@Autowired
	private LoaiMatHangService loaiMatHangService;

	/*
	 * API thêm ảnh - mặt hàng
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PostMapping("/authorized/mat-hang/{maMatHang}/anh-mat-hang")
	public ResponseEntity<Void> upload(@RequestParam("image") MultipartFile multipartFile,
			@PathVariable("maMatHang") long maMatHang) {
		MatHang matHang = matHangService.findByMaMatHang(maMatHang);
		try {
			anh_MatHangService.save(new Anh_MatHang(multipartFile.getBytes(), matHang));
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	/*
	 * API nhiều thêm ảnh - mặt hàng
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PostMapping("/authorized/mat-hang/{maMatHang}/anh-mat-hang-list")
	public ResponseEntity<Void> uploadMulti(@RequestParam("images") MultipartFile[] multipartFiles,
			@PathVariable("maMatHang") long maMatHang) {
		MatHang matHang = matHangService.findByMaMatHang(maMatHang);
		try {
			for (int i = 0; i < multipartFiles.length; i++) {
				anh_MatHangService.save(new Anh_MatHang(multipartFiles[i].getBytes(), matHang));
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	/*
	 * API xóa ảnh - mặt hàng
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@DeleteMapping("/authorized/mat-hang/anh-mat-hang/{maAnh}")
	public ResponseEntity<Void> xoaAnh(@PathVariable("maAnh") long maAnh) {
		try {
			anh_MatHangService.delete(maAnh);
		} catch (Anh_MatHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ANh khong ton tai trong CSDL", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * API thay thế ảnh - mặt hàng
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping("/authorized/mat-hang/anh-mat-hang/{maAnh}")
	public ResponseEntity<Void> thayTheAnh(@PathVariable("maAnh") long maAnh,
			@RequestParam("image") MultipartFile multipartFile) {
		try {
			anh_MatHangService.replace(maAnh, multipartFile.getBytes());
		} catch (Anh_MatHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anh khong ton tai trong CSDL", e);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Loi SERVER", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * API lấy ảnh - mặt hàng
	 */
	@GetMapping("/mat-hang/anh-mat-hang/{maAnh}")
	public ResponseEntity<Anh_MatHang> getAnh(@PathVariable("maAnh") long maAnh) {
		JSONObject jsonObject = new JSONObject();
		Anh_MatHang anh_MatHang = null;
		try {
			anh_MatHang = anh_MatHangService.findOne(maAnh);
//			jsonObject.put("", value)
		} catch (Anh_MatHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anh khong ton tai trong CSDL", e);
		}
		return new ResponseEntity<Anh_MatHang>(anh_MatHang, HttpStatus.OK);
	}

	/*
	 * API thêm mặt hàng
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PostMapping(value = "/authorized/mat-hang")
	public ResponseEntity<Void> taoMatHang(@RequestBody JSONObject matHang) {
		try {
			Gson gson = new Gson();
			matHangService.save(gson.fromJson(matHang.toString(), MatHang.class));
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	

	/*
	 * API lấy thông tin mặt hàng theo mã mặt hàng
	 */
	@GetMapping("/mat-hang/{maMatHang}")
	public ResponseEntity<MatHang> timMatHang(@PathVariable Long maMatHang) {
		MatHang matHang = matHangService.findByMaMatHang(maMatHang);
		if (matHang == null) {
			return new ResponseEntity<MatHang>(matHang, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MatHang>(matHang, HttpStatus.OK);
	}

	/*
	 * API lấy thông tin tất cả mặt hàng theo loại mặt hàng
	 */
	@GetMapping("/mat-hang/loai-mat-hang/{maLoaiMatHang}")
	public ResponseEntity<JSONObject> getAllMatHangByLoaiMatHang(@PathVariable long maLoaiMatHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) throws JSONException {
		Sort sortable = null;
		JSONObject result = new JSONObject();
		if (sort.equals("ASC")) {
			sortable = Sort.by("maMatHang").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("maMatHang").descending();
		}
		LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHang(maLoaiMatHang);
		if (loaiMatHang == null) {
			return new ResponseEntity<JSONObject>(result, HttpStatus.NOT_FOUND);

		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<MatHang> returnedPage = matHangService.findByLoaiMatHang(pageable, loaiMatHang);
		result = getResultData(returnedPage);
		return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
	}

	/*
	 * Ham tra ve ket qua cho Client
	 */
	private JSONObject getResultData(Page<MatHang> returnedPage) {
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
			matHang.put("danhSachHinhAnh", mh.getDanhSachHinhAnh());
			matHang.put("soLuongDaBan", mh.getSoLuongDaBan());
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
		returnedObject.put("totalItems", returnedPage.getNumberOfElements());
		returnedObject.put("totalPages", returnedPage.getTotalPages());
		return returnedObject;
	}

	/*
	 * API chinh sua mat hang theo ma mat hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping("/authorized/mat-hang/{maMatHang}")
	public ResponseEntity<Void> suaMatHang(@PathVariable Long maMatHang, @RequestBody JSONObject mh) {
		try {
			Gson gson = new Gson();
			MatHang updateMatHang = matHangService.findByMaMatHang(maMatHang);
			MatHang matHang = gson.fromJson(mh.toString(), MatHang.class);
			matHang.setMaMatHang(updateMatHang.getMaMatHang());
			matHangService.save(matHang);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cập nhật mặt hàng thất bại");
		}
	}

	/*
	 * API xoa mat hang theo ma mat hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@DeleteMapping("/authorized/mat-hang/{maMatHang}")
	public ResponseEntity<Void> xoaMatHang(@PathVariable Long maMatHang) {
		try {
			matHangService.delete(maMatHang);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (MatHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cung cấp đúng Ma Mat Hang", e);
		}
	}

	/*
	 * API la filter mat hang
	 */
	@GetMapping("/mat-hang")
	public ResponseEntity<JSONObject> timKiemMatHang(
			@RequestParam(name = "maLoaiMatHang", required = false, defaultValue = "0") int maLoaiMatHang,
			@RequestParam(name = "tenMatHang", required = false, defaultValue = "") String tenMatHang,
			@RequestParam(name = "giaBatDau", required = false, defaultValue = "0") long giaBatDau,
			@RequestParam(name = "giaKetThuc", required = false, defaultValue = "9999999999") long giaKetThuc,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "maMatHang") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		try {
			Sort sortable = null;
			JSONObject result = new JSONObject();
			if (sort.equals("ASC")) {
				sortable = Sort.by(sortType).ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by(sortType).descending();
			}

			Pageable pageable = PageRequest.of(page, size, sortable);
			if (maLoaiMatHang == 0) {
				Page<MatHang> returnedPage = matHangService.findWithoutLoaiMatHang(pageable, tenMatHang, giaBatDau,
						giaKetThuc);
				result = getResultData(returnedPage);
			} else {
				LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHang(maLoaiMatHang);
				Page<MatHang> returnedPage = matHangService.findWithFilter(pageable, loaiMatHang, tenMatHang, giaBatDau,
						giaKetThuc);
				result = getResultData(returnedPage);
			}

			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tìm kiếm mặt hàng thất bại");
		}
	}
}
