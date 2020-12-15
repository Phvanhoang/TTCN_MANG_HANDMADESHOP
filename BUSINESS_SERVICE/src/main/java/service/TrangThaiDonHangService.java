package service;

import model.TrangThaiDonHang;

public interface TrangThaiDonHangService {
	boolean checkTenTrangThai(String tenTrangThai);
	void save(TrangThaiDonHang trangThaiDonHang);
}
