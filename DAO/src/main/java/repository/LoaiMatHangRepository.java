package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.LoaiMatHang;

@Repository
public interface LoaiMatHangRepository extends PagingAndSortingRepository<LoaiMatHang, Long>{
	Page<LoaiMatHang> findByDeletedFalse(Pageable pageable);
	LoaiMatHang findByMaLoaiMatHangAndDeletedFalse(Long maLoaiMatHang);

}
