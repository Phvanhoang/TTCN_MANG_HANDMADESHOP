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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import exception.DanhGiaNotFoundException;
import model.DanhGia;
import model.MatHang;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import service.DanhGiaService;
import service.MatHangService;
import service.NguoiDungService;
import service.TaiKhoanService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(path = "/danh-gia-management")
public class DanhGiaController {
	@Autowired
	private DanhGiaService danhGiaService;
	@Autowired
	private MatHangService matHangService;
	@Autowired
	private TaiKhoanService taiKhoanService;
	
	/*
	 * API tao moi danh gia
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_USER)")
	@PostMapping("/authorized/danh-gia")
	public ResponseEntity<Void> taoDanhGia(@RequestBody JSONObject jsonObject) {
		try {
			Gson gson = new Gson();
			DanhGia danhGia = gson.fromJson(jsonObject.toString(), DanhGia.class);
			danhGiaService.save(danhGia);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * API xoa thong tin danh gia theo ma danh gia
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@DeleteMapping("/authorized/danh-gia/{maDanhGia}")
	public ResponseEntity<Void> xoaDanhGia(@PathVariable long maDanhGia) {
		try {
			danhGiaService.delete(maDanhGia);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (DanhGiaNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cung cấp đúng Ma Danh Gia", e);
		} 
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * API lay danh sach danh gia theo ma mat hang
	 */
	@GetMapping("/danh-gia/mat-hang/{maMatHang}")
	public ResponseEntity<JSONObject> GetAllDanhGiaByMaMatHang(
		@PathVariable int maMatHang,	
		@RequestParam(name="page", required=false, defaultValue="0") int page,
		@RequestParam(name="size", required=false, defaultValue="15") int size,
		@RequestParam(name="sort", required=false, defaultValue="ASC") String sort) throws JSONException{
		Sort sortable = null;
		JSONObject result = new JSONObject();
		if(sort.equals("ASC")) {
			sortable = Sort.by("maDanhGia").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("maDanhGia").descending();
		}
		MatHang matHang = matHangService.findByMaMatHang(maMatHang);
		if(matHang == null) {
			return new ResponseEntity<JSONObject>(result, HttpStatus.NOT_FOUND);
		}
		System.out.println("matHang:" + matHang.getMaMatHang());
		
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<DanhGia> returnedPage = danhGiaService.findByMatHang(pageable, matHang);
		result = getResultData(returnedPage);
		return new ResponseEntity<JSONObject>(result, HttpStatus.CREATED);
	}

	/*
	 * API lay danh sach danh gia theo ma tai khoan
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/danh-gia/tai-khoan/{maTaiKhoan}")
	public ResponseEntity<JSONObject> GetAllDanhGiaByTaiKhoan(
		@PathVariable int maTaiKhoan,	
		@RequestParam(name="page", required=false, defaultValue="0") int page,
		@RequestParam(name="size", required=false, defaultValue="15") int size,
		@RequestParam(name="sort", required=false, defaultValue="ASC") String sort) throws JSONException{
		Sort sortable = null;
		JSONObject result = new JSONObject();
		if(sort.equals("ASC")) {
			sortable = Sort.by("maDanhGia").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("maDanhGia").descending();
		}
		TaiKhoan taiKhoan = taiKhoanService.findByMaTaiKhoan(maTaiKhoan);
		if(taiKhoan==null) {
			return new ResponseEntity<JSONObject>(result, HttpStatus.NOT_FOUND);
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<DanhGia> returnedPage = danhGiaService.findByCreatedBy(pageable, taiKhoan);
		result = getResultData(returnedPage);
		return new ResponseEntity<JSONObject>(result, HttpStatus.CREATED);
	}
	
	/*
	 * API filter danh sach danh gia
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/danh-gia")
	public ResponseEntity<JSONObject> GetAllDanhGia(
		@RequestParam(name="maTaiKhoan", required=false, defaultValue="0") int maTaiKhoan,
		@RequestParam(name="maMatHang", required=false, defaultValue="0") int maMatHang,
		@RequestParam(name="page", required=false, defaultValue="0") int page,
		@RequestParam(name="size", required=false, defaultValue="15") int size,
		@RequestParam(name="sortType", required=false, defaultValue="maDanhGia") String sortType,
		@RequestParam(name="sort", required=false, defaultValue="ASC") String sort) throws JSONException{
		Sort sortable = null;
		JSONObject result = new JSONObject();
		if(sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		if(maTaiKhoan==0&&maMatHang==0) {
			Page<DanhGia> returnedPage = danhGiaService.findAll(pageable);
			result = getResultData(returnedPage);
		}
		else if(maTaiKhoan==0&maMatHang!=0) {
			MatHang matHang = matHangService.findByMaMatHang(maMatHang);
			Page<DanhGia> returnedPage = danhGiaService.findByMatHang(pageable, matHang);
			result = getResultData(returnedPage);
		}
		else if(maTaiKhoan!=0&&maMatHang==0) {
			TaiKhoan taiKhoan = taiKhoanService.findByMaTaiKhoan(maTaiKhoan);
			Page<DanhGia> returnedPage = danhGiaService.findByCreatedBy(pageable, taiKhoan);
			result = getResultData(returnedPage);
		}
		else {
			MatHang matHang = matHangService.findByMaMatHang(maMatHang);
			TaiKhoan taiKhoan = taiKhoanService.findByMaTaiKhoan(maTaiKhoan);
			Page<DanhGia> returnedPage = danhGiaService.findByMatHangAndCreatedBy(pageable, matHang, taiKhoan);
			result = getResultData(returnedPage);


		}
		return new ResponseEntity<JSONObject>(result, HttpStatus.CREATED);
	}
	
	/*
	 * Ham tao danh sach tra ve cho Client
	 */
	private JSONObject getResultData(Page<DanhGia> returnedPage) {
		List<DanhGia> listDanhGia = returnedPage.getContent();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for(int i=0; i<listDanhGia.size(); i++) {
			JSONObject danhGia = new JSONObject();
			DanhGia dg = listDanhGia.get(i);
			danhGia.put("maMatHang", dg.getMatHang().getMaMatHang());
			danhGia.put("maDanhGia", dg.getMaDanhGia());
			danhGia.put("noiDung", dg.getNoiDung());
			danhGia.put("soSao", dg.getSoSao());
			danhGia.put("createdAt", dg.getCreatedAt());
			danhGia.put("maNguoiDung", dg.getCreatedBy().getMaTaiKhoan());
			danhGia.put("updatedAt", dg.getUpdatedAt());
			danhGia.put("updatedBy", dg.getUpdatedBy().getMaTaiKhoan());
			danhGia.put("deleted", dg.isDeleted());
			data.add(danhGia);
		}

		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", data);
		returnedObject.put("currentPage", returnedPage.getNumber());
	    returnedObject.put("totalItems", returnedPage.getTotalElements());
	    returnedObject.put("totalPages", returnedPage.getTotalPages());
	    return returnedObject;
	}
}
