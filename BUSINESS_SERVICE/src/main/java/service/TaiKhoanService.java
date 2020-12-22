package service;

import model.TaiKhoan;

public interface TaiKhoanService {
	TaiKhoan findByMaTaiKhoanAndDeletedFalse(Long maTaiKhoan);
	TaiKhoan save(TaiKhoan taiKhoan);
	boolean existsById(long id);
	boolean existsByTenDangNhap(String tenDangNhap);
	TaiKhoan findByMaTaiKhoan(long id);
	TaiKhoan findTopByMaTaiKhoanDesc();
	TaiKhoan findByTenDangNhapAndMatKhau(String tenDangNhap,String matKhau);
}
