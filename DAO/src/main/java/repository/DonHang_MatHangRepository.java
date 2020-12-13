package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.DonHang_MatHang;

@Repository
public interface DonHang_MatHangRepository extends CrudRepository<DonHang_MatHang, Long>{
	
}
