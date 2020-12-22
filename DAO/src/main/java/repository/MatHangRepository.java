package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.LoaiMatHang;
import model.MatHang;

@Repository
public interface MatHangRepository extends PagingAndSortingRepository<MatHang, Long>{
	Page<MatHang> findByDeletedFalse(Pageable pageable);
	MatHang findByMaMatHangAndDeletedFalse(Long maMatHang);
	Page<MatHang> findByDeletedFalseAndLoaiMatHang(Pageable pageable, LoaiMatHang loaiMatHang);
	Page<MatHang> findByDeletedFalseAndTenMatHangContainingAndGiaGreaterThanEqualAndGiaLessThanEqual(Pageable pageable, String tenMatHang, long giaBatDau, long giaKetThuc);
	Page<MatHang> findByDeletedFalseAndLoaiMatHangAndTenMatHangContainingAndGiaGreaterThanEqualAndGiaLessThanEqual(Pageable pageable, LoaiMatHang loaiMatHang, String tenMatHang, long giaBatDau, long giaKetThuc);

}
