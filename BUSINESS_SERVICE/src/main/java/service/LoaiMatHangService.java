package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.LoaiMatHang;

public interface LoaiMatHangService {
	void save(LoaiMatHang loaiMatHang);
	LoaiMatHang findByMaLoaiMatHangAndDeletedFalse(long maLoaiMatHang);
	Page<LoaiMatHang> findByDeletedFalse(Pageable pageable);

}
