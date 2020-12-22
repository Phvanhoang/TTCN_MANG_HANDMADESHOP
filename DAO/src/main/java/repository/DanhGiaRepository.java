package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.DanhGia;

@Repository
public interface DanhGiaRepository extends PagingAndSortingRepository<DanhGia, Long>{
	Page<DanhGia> findByDeletedFalse(Pageable pageable);
	Page<DanhGia> findByDeletedFalseAndMatHang(Pageable pageable, long maMatHang);
	Page<DanhGia> findByDeletedFalseAndCreatedBy(Pageable pageable, long maTaiKhoan);
	boolean existsByDeletedFalseAndMaDanhGiaEquals(long maDanhGia);
}
