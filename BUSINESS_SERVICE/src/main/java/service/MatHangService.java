package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exception.MatHangNotFoundException;
import model.LoaiMatHang;
import model.MatHang;

public interface MatHangService {
	MatHang save(MatHang matHang);
	MatHang findByMaMatHang(long maMatHang);
	Page<MatHang> findAll(Pageable pageable);
	Page<MatHang> findByLoaiMatHang(Pageable pageable, LoaiMatHang loaiMatHang);
	boolean delete(long maMatHang) throws MatHangNotFoundException;
	boolean existMatHangByMaMatHang(long maMatHang);
	Page<MatHang> findByTenMatHang(Pageable pageable, String tenMatHang);
	Page<MatHang> findByLoaiMatHangAndTenMatHang(Pageable pageable, LoaiMatHang loaiMatHang, String tenMatHang);
}
