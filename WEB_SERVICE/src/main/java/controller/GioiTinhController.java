package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.GioiTinh;
import service.GioiTinhService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(path="/gioi-tinh-management")
public class GioiTinhController {
	@Autowired
	private GioiTinhService gioiTinhService;
	
	// Lay thong tin danh sach gioi tinh
	@GetMapping("/gioi-tinh")
	public ResponseEntity<ArrayList<GioiTinh>> danhSachGioiTinh() throws JSONException{
			ArrayList<GioiTinh> dsGioiTinh = new ArrayList<GioiTinh>();
			dsGioiTinh = gioiTinhService.getDanhSachGioiTinh();
			if(dsGioiTinh != null) {
				return new ResponseEntity<ArrayList<GioiTinh>>(dsGioiTinh, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ArrayList<GioiTinh>>(dsGioiTinh, HttpStatus.NOT_FOUND);
			}
	}
}
