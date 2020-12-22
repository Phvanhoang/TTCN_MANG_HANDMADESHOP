package service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.LoaiMatHang;

public interface LoaiMatHangService {
	void save(LoaiMatHang loaiMatHang);
	void delete(LoaiMatHang loaiMatHang);
	LoaiMatHang findByMaLoaiMatHang(long maLoaiMatHang);
	ArrayList<LoaiMatHang> findAll();
	
	Page<LoaiMatHang> findWithFilter(Pageable pageable, String tenLoaiMatHang);

}
