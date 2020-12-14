package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import model.LoaiMatHang;
import service.LoaiMatHangService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class LoaiMatHangController {
	@Autowired
	private LoaiMatHangService loaiMatHangService;
	
	@GetMapping("/loai_mat_hang/{maLoaiMatHang}")
	public ResponseEntity<LoaiMatHang> timLoaiMatHang(@PathVariable Long maLoaiMatHang){
		LoaiMatHang loaiMatHang = loaiMatHangService.findByMaLoaiMatHangAndDeletedFalse(maLoaiMatHang);
		if(loaiMatHang==null) {
			return new ResponseEntity<LoaiMatHang>(loaiMatHang, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LoaiMatHang>(loaiMatHang, HttpStatus.OK);
	}
}
