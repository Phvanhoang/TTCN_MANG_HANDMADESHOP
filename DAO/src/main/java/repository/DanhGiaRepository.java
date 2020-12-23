package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.DanhGia;
import model.MatHang;
import model.TaiKhoan;

@Repository
public interface DanhGiaRepository extends PagingAndSortingRepository<DanhGia, Long> {
	Page<DanhGia> findByDeletedFalse(Pageable pageable);

	Page<DanhGia> findByDeletedFalseAndMatHang(Pageable pageable, MatHang matHang);

	Page<DanhGia> findByDeletedFalseAndCreatedBy(Pageable pageable, TaiKhoan taiKhoan);

	Page<DanhGia> findByDeletedFalseAndMatHangAndCreatedBy(Pageable pageable, MatHang matHang, TaiKhoan taiKhoan);

	boolean existsByDeletedFalseAndMaDanhGiaEquals(long maDanhGia);
}
