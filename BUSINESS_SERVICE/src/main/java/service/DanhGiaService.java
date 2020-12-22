package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exception.DanhGiaNotFoundException;
import model.DanhGia;
import model.MatHang;
import model.TaiKhoan;

public interface DanhGiaService {
	Page<DanhGia> findAll(Pageable pageable);
	Page<DanhGia> findByMatHang(Pageable pageable, MatHang matHang);
	Page<DanhGia> findByCreatedBy(Pageable pageable, TaiKhoan taiKhoan);
	Page<DanhGia> findByMatHangAndCreatedBy(Pageable pageable, MatHang matHang, TaiKhoan taiKhoan);
	DanhGia save(DanhGia danhGia) throws DanhGiaNotFoundException;
	boolean exist(long maTaiKhoan);
	boolean delete(long maDanhGia) throws DanhGiaNotFoundException;
}
