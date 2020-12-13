package service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.DonHang;
import model.DonHang_MatHang;
import model.NguoiDung;

public interface DonHangService {
	Page<DonHang> findAllByNguoiDung(Pageable pageable, NguoiDung nguoiDung);
	void createDonHang(DonHang donHang);
}
