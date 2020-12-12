package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends CrudRepository<TaiKhoan, Long>{
	TaiKhoan findByTenDangNhap(String tenDangNhap);
	TaiKhoan findByMaTaiKhoanAndIsDeletedFalse(Long maTaiKhoan);
}
