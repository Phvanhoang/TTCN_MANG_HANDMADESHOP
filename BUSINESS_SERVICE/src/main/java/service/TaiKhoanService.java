package service;

import model.TaiKhoan;

public interface TaiKhoanService {
	TaiKhoan findByMaTaiKhoanAndDeletedFalse(Long maTaiKhoan);
	void save(TaiKhoan taiKhoan);
	boolean existsById(long id);
	TaiKhoan findByMaTaiKhoan(long id);
}
