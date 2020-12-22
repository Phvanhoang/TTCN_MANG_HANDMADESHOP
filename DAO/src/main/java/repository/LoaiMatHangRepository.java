package repository;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.LoaiMatHang;

@Repository
public interface LoaiMatHangRepository extends CrudRepository<LoaiMatHang, Long>{
	ArrayList<LoaiMatHang> findByDeletedFalse();
	LoaiMatHang findByMaLoaiMatHangAndDeletedFalse(Long maLoaiMatHang);
	Page<LoaiMatHang> findByDeletedFalseAndTenLoaiMatHangContaining(Pageable pageable, String tenLoaiMatHang);

}
