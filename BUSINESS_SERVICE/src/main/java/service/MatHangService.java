package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exception.MatHangNotFoundException;
import model.MatHang;

public interface MatHangService {
	MatHang save(MatHang matHang);
	MatHang findByMaMatHang(long maMatHang);
	Page<MatHang> findAll(Pageable pageable);
	Page<MatHang> findByMaLoaiMatHang(Pageable pageable, long maLoaiMatHang);
	boolean delete(long maMatHang) throws MatHangNotFoundException;
	boolean existMatHangByMaMatHang(long maMatHang);
}
