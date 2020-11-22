package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.Anh_MatHang;
import model.MatHang;
import service.Anh_MatHangService;
import service.MatHangService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/anh_mat_hang")
public class Anh_MatHangController {
	@Autowired
	private Anh_MatHangService anh_MatHangService;
	
	@Autowired 
	private MatHangService matHangService;
	
	@PostMapping("/upload")
	private ResponseEntity<Void> themAnhMatHang(@RequestParam("image") MultipartFile multipartFile,
												@RequestParam("maMatHang") long maMatHang) {
		try {
			MatHang matHang = matHangService.findOne(maMatHang);
			Anh_MatHang anh_MatHang = new Anh_MatHang(multipartFile.getBytes(), matHang);
			anh_MatHangService.save(anh_MatHang);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
