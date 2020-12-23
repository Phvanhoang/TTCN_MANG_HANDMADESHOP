package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.DonHang_MatHang;

@Repository
public interface DonHang_MatHangRepository extends PagingAndSortingRepository<DonHang_MatHang, Long>{
//	Page<>
}
