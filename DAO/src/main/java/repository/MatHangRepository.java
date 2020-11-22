package repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.MatHang;

@Repository
public interface MatHangRepository extends PagingAndSortingRepository<MatHang, Long>{

}
