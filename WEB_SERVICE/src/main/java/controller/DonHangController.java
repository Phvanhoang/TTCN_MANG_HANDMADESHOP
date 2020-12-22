package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.management.relation.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import model.DonHang;
import model.DonHang_MatHang;
import model.TaiKhoan;
import net.minidev.json.JSONObject;
import service.DonHangService;
import utils.GetTaiKhoanFromTokenService;
import model.DacQuyenNames;
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class DonHangController {
	@Autowired
	private DonHangService donHangService;
	
	@Autowired
	private GetTaiKhoanFromTokenService getTaiKhoanFromTokenService;

	@PreAuthorize("hasAnyAuthority(T(model.DacQuyenNames).ALL_ROLES)")
//	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_ADMIN)")
//	@PreAuthorize("hasAuthority(T(model.DacQuyenNames).ROLE_USER)")
	@PostMapping(value = "/authorized/don_hang/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createDonHang(@RequestBody JSONObject jObject){
		DonHang donHang = new DonHang();
		Gson gson = new Gson();
		donHang = gson.fromJson(jObject.toString(), DonHang.class);
//		TaiKhoan taiKhoan = getTaiKhoanFromTokenService.getTaiKhoan(token);
//		donHang.setNguoiDung(taiKhoan.getNguoiDung());
//		try {
			donHangService.createDonHang(donHang);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
