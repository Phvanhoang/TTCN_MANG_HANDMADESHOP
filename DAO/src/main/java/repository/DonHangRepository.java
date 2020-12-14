package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.DonHang;
import model.NguoiDung;

@Repository
public interface DonHangRepository extends PagingAndSortingRepository<DonHang, Long>{
	Page<DonHang> findByNguoiDung(Pageable pageable, NguoiDung nguoiDung);
}
