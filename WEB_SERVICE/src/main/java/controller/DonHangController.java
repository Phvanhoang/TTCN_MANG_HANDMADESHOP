package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.management.relation.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import exception.DonHangNotFoundException;
import model.DonHang;
import model.DonHang_MatHang;
import model.LoaiMatHang;
import model.MatHang;
import model.TaiKhoan;
import model.TrangThaiDonHang;
import net.minidev.json.JSONObject;
import service.DonHangService;
import service.TrangThaiDonHangService;
import utils.GetTaiKhoanFromTokenService;
import model.DacQuyenNames;
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/don-hang-management")
public class DonHangController {
	@Autowired
	private DonHangService donHangService;
	
	@Autowired
	private TrangThaiDonHangService trangThaiDonHangService;

	/*
	 * API tao don hang
   	 */
	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
	@PostMapping(value = "/authorized/don-hang", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createDonHang(@RequestBody JSONObject jObject){
		DonHang donHang = new DonHang();
		Gson gson = new Gson();
		donHang = gson.fromJson(jObject.toString(), DonHang.class);
		donHangService.createDonHang(donHang);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	/*
	 * API thay doi trang thai don hang
   	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping(value = "/authorized/don-hang/{maDonHang}/trang-thai-don-hang/{maTTDH}")
	public ResponseEntity<Void> thayDoiTTDonHang(@PathVariable long maDonHang, 
				@PathVariable long maTTDH){
		try {
			donHangService.changeTrangThaiDonHang(maDonHang, maTTDH);
		} catch (DonHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cung cấp đúng mã đơn hàng", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * API xoa don hang
   	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@PutMapping(value = "/authorized/don-hang/{maDonHang}")
	public ResponseEntity<Void> xoaDonHang(@PathVariable long maDonHang){
		try {
			donHangService.delete(maDonHang);
		} catch (DonHangNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cung cấp đúng mã đơn hàng", e);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * API lay danh sach trang thai don hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping(value = "/authorized/don-hang/trang-thai-don-hang")
	public ResponseEntity<List<TrangThaiDonHang>> getTTDH() {
		List<TrangThaiDonHang> list = trangThaiDonHangService.getAll();
		return new ResponseEntity<List<TrangThaiDonHang>>(list, HttpStatus.OK);
	}
	
	/*
	 * API lay danh sach don hang theo ma trang thai don hang 
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/trang-thai-don-hang/{maTTDH}")
	public ResponseEntity<JSONObject> getAllDonHangByTTDH(
			@PathVariable long maTTDH,
			@RequestParam(name="maDonHang", required=false, defaultValue="") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name="sortType", required=false, defaultValue="createdAt") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<DonHang> returnedPage = donHangService.findByTrangThaiDonHang(pageable, maTTDH);
		return new ResponseEntity<JSONObject>(getResultData(returnedPage), HttpStatus.OK);
	}

	/*
	 * API lay danh sach don hang theo ma tai khoan nguoi tao
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/tai-khoan/{maTaiKhoan}")
	public ResponseEntity<JSONObject> getAllDonHangByCreatedBy(
			@PathVariable long maTaiKhoan,
			@RequestParam(name="maDonHang", required=false, defaultValue="") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name="sortType", required=false, defaultValue="createdAt") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<DonHang> returnedPage = donHangService.findByCreatedBy(pageable, maTaiKhoan);
		return new ResponseEntity<JSONObject>(getResultData(returnedPage), HttpStatus.OK);
	}
	
	/*
	 * API lay tat ca don hang trong db
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang")
	public ResponseEntity<JSONObject> getAllDonHang(
			@RequestParam(name="maDonHang", required=false, defaultValue="") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name="sortType", required=false, defaultValue="createdAt") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<DonHang> returnedPage = donHangService.findOrderByCreateAtDesc(pageable);
		return new ResponseEntity<JSONObject>(getResultData(returnedPage), HttpStatus.OK);
	}
	
	/*
	 * API lay danh sach don hang co mat hang trong danh sach cua no
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/mat-hang/{maMatHang}")
	public ResponseEntity<JSONObject> getAllDonHangByMatHang(
			@PathVariable long maMatHang,
			@RequestParam(name="maDonHang", required=false, defaultValue="") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name="sortType", required=false, defaultValue="created_at") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws JSONException {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<DonHang> returnedPage = donHangService.findByMatHang(pageable, maMatHang);
		return new ResponseEntity<JSONObject>(getResultData(returnedPage), HttpStatus.OK);
	}
	
	/*
	 * Lay du lieu tra ve cho Client
	 */
	private JSONObject getResultData(Page<DonHang> returnedPage) {
		List<DonHang> listDonHang = returnedPage.getContent();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (int i = 0; i < listDonHang.size(); i++) {
			JSONObject donHang = new JSONObject();
			DonHang dh = listDonHang.get(i);
			donHang.put("maDonHang", dh.getMaDonHang());
			donHang.put("giaTongCong", dh.getGiaTongCong());
			donHang.put("diaChiGiaoHang", dh.getDiaChiGiaoHang());
			donHang.put("SDTGiaoHang", dh.getSDTGiaoHang());
			donHang.put("tenNguoiNhanHang", dh.getTenNguoiNhanHang());
//			Iterator itr = dh.getDanhSachMatHang().iterator();
//			while (itr.hasNext()) {
//				DonHang_MatHang donHang_MatHang = (DonHang_MatHang) itr.next();
//				donHang_MatHang.getDonHang().getDanhSachMatHang();
//			}
			donHang.put("maTrangThaiDonHang", dh.getTrangThaiDonHang().getMaTrangThai());
			donHang.put("danhSachMatHang", dh.getDanhSachMatHang());
			donHang.put("createdAt", dh.getCreatedAt());
			donHang.put("createdBy", dh.getCreatedBy().getMaTaiKhoan());
			donHang.put("updatedAt", dh.getUpdatedAt());
			donHang.put("updatedBy", dh.getUpdatedBy().getMaTaiKhoan());
			donHang.put("deleted", dh.isDeleted());

			data.add(donHang);
		}

		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", data);
		returnedObject.put("currentPage", returnedPage.getNumber());
	    returnedObject.put("totalItems", returnedPage.getTotalElements());
	    returnedObject.put("totalPages", returnedPage.getTotalPages());
	    return returnedObject;
	}
}
