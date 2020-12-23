package service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exception.DonHangNotFoundException;
import model.DonHang;
import model.DonHang_MatHang;
import model.LoaiMatHang;
import model.MatHang;
import model.NguoiDung;
import model.TaiKhoan;

public interface DonHangService {
	void createDonHang(DonHang donHang);
	Page<DonHang> findByCreatedBy(Pageable pageable, long maTaiKhoan);
	Page<DonHang> findByMatHang(Pageable pageable, long maMatHang);
	Page<DonHang> findByTrangThaiDonHang(Pageable pageable, long maTTDH);
	Page<DonHang> findOrderByCreateAtDesc(Pageable pageable);
	boolean changeTrangThaiDonHang(long maDonHang, long maTTDH) throws DonHangNotFoundException;
	boolean delete(long maDonHang) throws DonHangNotFoundException;
}
