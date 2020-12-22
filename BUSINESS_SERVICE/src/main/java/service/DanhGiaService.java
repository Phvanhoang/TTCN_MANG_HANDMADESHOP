package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exception.DanhGiaNotFoundException;
import model.DanhGia;

public interface DanhGiaService {
	Page<DanhGia> findAll(Pageable pageable);
	Page<DanhGia> findByMatHang(Pageable pageable, long maMatHang);
	Page<DanhGia> findByCreatedBy(Pageable pageable, long maTaiKhoan);
	DanhGia save(DanhGia danhGia) throws DanhGiaNotFoundException;
	boolean exist(long maTaiKhoan);
	boolean delete(long maDanhGia) throws DanhGiaNotFoundException;
}
