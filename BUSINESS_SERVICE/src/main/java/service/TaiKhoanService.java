package service;

import model.TaiKhoan;

public interface TaiKhoanService {
	TaiKhoan findByMaTaiKhoanAndIsDeletedFalse(Long maTaiKhoan);
	void save(TaiKhoan taiKhoan);
}
