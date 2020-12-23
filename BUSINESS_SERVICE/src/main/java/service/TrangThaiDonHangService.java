package service;

import java.util.List;

import model.TrangThaiDonHang;

public interface TrangThaiDonHangService {
	boolean checkTenTrangThai(String tenTrangThai);
	void save(TrangThaiDonHang trangThaiDonHang);
	List<TrangThaiDonHang> getAll();
}
