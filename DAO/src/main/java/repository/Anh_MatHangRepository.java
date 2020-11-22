package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import model.Anh_MatHang;
@Repository
public interface Anh_MatHangRepository extends CrudRepository<Anh_MatHang, Long>{
}
