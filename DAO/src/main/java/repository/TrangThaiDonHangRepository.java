package repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.TrangThaiDonHang;

@Repository
public interface TrangThaiDonHangRepository extends PagingAndSortingRepository<TrangThaiDonHang, Long>{
	TrangThaiDonHang findByTenTrangThai(String tenTrangThai);
}
