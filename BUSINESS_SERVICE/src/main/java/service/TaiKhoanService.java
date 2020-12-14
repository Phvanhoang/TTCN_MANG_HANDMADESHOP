package service;

import model.TaiKhoan;

public interface TaiKhoanService {
	TaiKhoan findByMaTaiKhoanAndDeletedFalse(Long maTaiKhoan);
	TaiKhoan save(TaiKhoan taiKhoan);
}
