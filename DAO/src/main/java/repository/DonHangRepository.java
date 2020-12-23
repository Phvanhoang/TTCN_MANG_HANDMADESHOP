package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.DonHang;
import model.MatHang;
import model.TaiKhoan;
import model.TrangThaiDonHang;

@Repository
public interface DonHangRepository extends PagingAndSortingRepository<DonHang, Long> {
	Page<DonHang> findByDeletedFalseAndCreatedBy(Pageable pageable, TaiKhoan taiKhoan);

	@Query(value = "select * from DonHang dh inner join DonHang_MatHang dh_mh "
			+ " on dh.ma_don_hang = dh_mh.ma_don_hang where dh_mh.ma_mat_hang = ?1", nativeQuery = true)
	Page<DonHang> findByDeletedFalseAndMatHang(Pageable pageable, MatHang matHang);

	Page<DonHang> findByDeletedFalseAndTrangThaiDonHangOrderByCreatedAtDesc(Pageable pageable,
			TrangThaiDonHang trangThaiDonHang);

	Page<DonHang> findByDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

	boolean existsByDeletedFalseAndMaDonHangEquals(long maDonHang);
}
