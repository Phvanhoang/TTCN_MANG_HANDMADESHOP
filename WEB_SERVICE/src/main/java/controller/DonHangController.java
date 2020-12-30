package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import exception.DonHangNotFoundException;
import model.DonHang;
import model.NguoiDung;
import model.TinhTrangDonHangNames;
import model.TrangThaiDonHang;
import net.minidev.json.JSONObject;
import service.DonHangService;
import service.NguoiDungService;
import service.TrangThaiDonHangService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/don-hang-management")
public class DonHangController {
	@Autowired
	private DonHangService donHangService;

	@Autowired
	private TrangThaiDonHangService trangThaiDonHangService;

	@Autowired
	private NguoiDungService nguoiDungService;

	/*
	 * API tao don hang
	 */
	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
	@PostMapping(value = "/authorized/don-hang", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createDonHang(@RequestBody JSONObject jObject) {
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
	public ResponseEntity<Void> thayDoiTTDonHang(@PathVariable long maDonHang, @PathVariable long maTTDH) {
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
	public ResponseEntity<Void> xoaDonHang(@PathVariable long maDonHang) {
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
	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
	@GetMapping(value = "/authorized/don-hang/trang-thai-don-hang")
	public ResponseEntity<List<TrangThaiDonHang>> getTTDH() {
		List<TrangThaiDonHang> list = trangThaiDonHangService.getAll();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setDanhSachDonHang(null);
		}
		return new ResponseEntity<List<TrangThaiDonHang>>(list, HttpStatus.OK);
	}

	/*
	 * API lay danh sach don hang theo ma trang thai don hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/trang-thai-don-hang/{maTTDH}")
	public ResponseEntity<JSONObject> getAllDonHangByTTDH(@PathVariable long maTTDH,
			@RequestParam(name = "maDonHang", required = false, defaultValue = "") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "createdAt") String sortType,
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
	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
	@GetMapping("/authorized/don-hang/tai-khoan/{maTaiKhoan}")
	public ResponseEntity<JSONObject> getAllDonHangByCreatedBy(@PathVariable long maTaiKhoan,
			@RequestParam(name = "maDonHang", required = false, defaultValue = "") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "createdAt") String sortType,
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
			@RequestParam(name = "maDonHang", required = false, defaultValue = "") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "createdAt") String sortType,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) throws JSONException {
		Sort sortable = null;
		JSONObject result = new JSONObject();
		
		if (sort.equals("ASC")) {
			sortable = Sort.by(sortType).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(sortType).descending(); 
		}
		Pageable pageable = PageRequest.of(page, size, sortable);

		Page<DonHang> returnedPage = donHangService.findOrderByCreateAtDesc(pageable);
		if(returnedPage == null) {
			return new ResponseEntity<JSONObject>(result, HttpStatus.NOT_FOUND);
		}
		List<DonHang> dsDonHang = returnedPage.getContent();
		ArrayList<JSONObject> data = new ArrayList<JSONObject>();
		
		for(int i=0;i<dsDonHang.size();i++) {
			DonHang donHang = dsDonHang.get(i);
			long maNguoiDung = donHang.getCreatedBy().getNguoiDung().getMaNguoiDung();
			JSONObject dh = new JSONObject();
			dh.put("maNguoiDung", maNguoiDung);
			donHang.getTrangThaiDonHang().setDanhSachDonHang(null);
			dh.put("donHang", donHang);
			
			data.add(dh);
		}
		result.put("data", data);
		result.put("currentPage", returnedPage.getNumber());
		result.put("totalItems", returnedPage.getTotalElements());
		result.put("totalPages", returnedPage.getTotalPages());

		return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
	}

	/*
	 * API lay don hang theo ma don hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/{maDonHang}")
	public ResponseEntity<DonHang> getDonHang(@PathVariable Long maDonHang) {

		DonHang donHang = donHangService.findByMaDonHang(maDonHang);
		if (donHang == null) {
			return new ResponseEntity<DonHang>(donHang, HttpStatus.NOT_FOUND);
		}

		donHang.getTrangThaiDonHang().setDanhSachDonHang(null);
		return new ResponseEntity<DonHang>(donHang, HttpStatus.OK);
	}

	/*
	 * API lay danh sach don hang co mat hang trong danh sach cua no
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/mat-hang/{maMatHang}")
	public ResponseEntity<JSONObject> getAllDonHangByMatHang(@PathVariable long maMatHang,
			@RequestParam(name = "maDonHang", required = false, defaultValue = "") String maDonHang,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortType", required = false, defaultValue = "created_at") String sortType,
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
		returnedObject.put("totalItems", returnedPage.getNumberOfElements());
		returnedObject.put("totalPages", returnedPage.getTotalPages());
		return returnedObject;
	}

	/*
	 * API thong ke doanh thu theo ngay trong 30 ngay gan nhat
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/doanh-thu")
	public ResponseEntity<HashMap<String, Long>> getDoanhThu() throws ParseException {
		HashMap<String, Long> doanhThu = new HashMap<String, Long>();

		LocalDate localEndDate = LocalDate.now().plusDays(1);
		System.out.println("end:" + localEndDate);

		LocalDate localStartDate = localEndDate.plusDays(-32);
		System.out.println("start:" + localStartDate);
		List<TrangThaiDonHang> dsTrangThai = trangThaiDonHangService.getAll();
		TrangThaiDonHang trangThai = new TrangThaiDonHang();
		for (int i = 0; i < dsTrangThai.size(); i++) {
			if (TinhTrangDonHangNames.ACCEPTED.equals(dsTrangThai.get(i).getTenTrangThai())) {
				trangThai = dsTrangThai.get(i);
				break;
			}
		}
		java.util.Date endDate = (java.util.Date) java.sql.Date.valueOf(localEndDate);
		System.out.println("a" + endDate);
		java.util.Date startDate = (java.util.Date) java.sql.Date.valueOf(localStartDate);
		System.out.println("b" + startDate);

		ArrayList<String> arrayDate = new ArrayList<String>();

		for (int i = 2; i <= 31; i++) {
			String localDate = localStartDate.plusDays(i).toString();
			arrayDate.add(localDate);
			System.out.println("date: " + arrayDate.get(i - 2));
		}
		
		long tongDoanhThu = 0;
		ArrayList<DonHang> dsDonHang = donHangService.findByThoiGian(trangThai, startDate, endDate);
		for (int i = 0; i < dsDonHang.size(); i++) {
			DonHang donHang = dsDonHang.get(i);
			// System.out.println("donHang: " + donHang);
			Date donHangCreatedAt = donHang.getCreatedAt();
			String date = DateTimeFormatter.ofPattern("yyyy-MM-dd")
					.format(donHangCreatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			System.out.println("date: " + donHangCreatedAt);

			long hoaDon = donHang.getGiaTongCong();
			System.out.println("hoadon: " + hoaDon);

			if (!doanhThu.containsKey(date)) {
				doanhThu.put(date, hoaDon);
			} else {
				long doanhThuDate = doanhThu.get(date) + hoaDon;
				doanhThu.put(date, doanhThuDate);
			}
			tongDoanhThu += hoaDon;

		}

		for (int i = 0; i < arrayDate.size(); i++) {
			if (!doanhThu.containsKey(arrayDate.get(i))) {
				doanhThu.put(arrayDate.get(i), (long) 0);
			}
		}
		doanhThu.put("tongDoanhThu", tongDoanhThu);
		System.out.println("tongDoanhThu: " + tongDoanhThu);

		return new ResponseEntity<HashMap<String, Long>>(doanhThu, HttpStatus.OK);
	}

	/*
	 * API lay thong tin TK va Nguoi dung khi biet ma don hang
	 */
	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
	@GetMapping("/authorized/don-hang/{maDonHang}/nguoi-dung")
	public ResponseEntity<NguoiDung> getTaiKhoanByMaDonHang(@PathVariable long maDonHang) {
		DonHang donHang = donHangService.findByMaDonHang(maDonHang);
		NguoiDung nguoiDung = new NguoiDung();
		if (donHang == null) {
			return new ResponseEntity<NguoiDung>(nguoiDung, HttpStatus.NOT_FOUND);
		}
		long maNguoiDung = donHang.getCreatedBy().getNguoiDung().getMaNguoiDung();
		nguoiDung = nguoiDungService.findByDeletedFalse(maNguoiDung);
		if (nguoiDung == null) {
			return new ResponseEntity<NguoiDung>(nguoiDung, HttpStatus.NOT_FOUND);
		}
		nguoiDung.getTaiKhoan().getDacQuyen().setDanhSachTaiKhoan(null);
		nguoiDung.getTaiKhoan().setMatKhau(null);
		return new ResponseEntity<NguoiDung>(nguoiDung, HttpStatus.OK);
	}
}
