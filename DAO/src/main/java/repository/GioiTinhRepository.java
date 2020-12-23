package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.GioiTinh;

@Repository
public interface GioiTinhRepository extends CrudRepository<GioiTinh, Long> {
	GioiTinh findByTenGioiTinh(String tenGioiTinh);
}
