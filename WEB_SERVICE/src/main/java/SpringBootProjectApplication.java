
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import model.DacQuyen;
import model.GioiTinh;
import model.TaiKhoan;
import model.TrangThaiDonHang;
import security.auditor_aware.SpringSecurityAuditorAware;
import security.config.MethodSecurityConfig;
import security.config.WebSecurityConfig;
import service.GioiTinhService;
import service.QuyenService;
import service.TaiKhoanService;
import service.TrangThaiDonHangService;

@EnableSpringConfigured
@Configuration
@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = "model")
@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"model", "impl", "controller", "service", "security.*", "utils"}, 
				basePackageClasses = {WebSecurityConfig.class, MethodSecurityConfig.class})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SpringBootProjectApplication {
	private static String username = "UserForCreatedByTaiKhoan";
	private static String password = "PasswordUserForCreatedByTaiKhoan";

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootProjectApplication.class, args);
		QuyenService quyenService = context.getBean(QuyenService.class);
		TaiKhoanService taiKhoanService = context.getBean(TaiKhoanService.class);
		TrangThaiDonHangService trangThaiDonHangService = context.getBean(TrangThaiDonHangService.class);
		GioiTinhService gioiTinhService = context.getBean(GioiTinhService.class);
		createDacQuyenList(quyenService);
		createGioiTinhList(gioiTinhService);
		createTrangThaiDonHangList(trangThaiDonHangService);
		TaiKhoan createdTaiKhoan = taiKhoanService.findByTenDangNhapAndMatKhau(username, password);
		if (createdTaiKhoan == null) {
			creatTaiKhoanForCreatedBy(quyenService, taiKhoanService);
		};
		createAdminAccount(taiKhoanService, quyenService, "Hoangpv6681", "Hoangpv6681");
	}
	
    @Bean
    public AuditorAware<TaiKhoan> auditorProvider(TaiKhoanService taiKhoanService) {
		TaiKhoan createdTaiKhoan = taiKhoanService.findByTenDangNhapAndMatKhau(username, password);
    	return new SpringSecurityAuditorAware(createdTaiKhoan);
    }
    
    public static void createAdminAccount(TaiKhoanService taiKhoanService, QuyenService quyenService ,
    							String tenDangNhap, String matKhau) {
    	if (taiKhoanService.existsByTenDangNhap(tenDangNhap)) return;
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String encodedPassword = passwordEncoder.encode(matKhau);
		TaiKhoan taiKhoan = new TaiKhoan();
		taiKhoan.setTenDangNhap(tenDangNhap);
		taiKhoan.setMatKhau(encodedPassword);
		taiKhoan.setThoiGianDangKy(new Date());
	  	ArrayList<DacQuyen> danhSachDacQuyen = quyenService.getDanhSachDacQuyen();
  		for(int i=0; i<danhSachDacQuyen.size(); i++) {
  			if(danhSachDacQuyen.get(i).getTenDacQuyen().equals("ROLE_ADMIN")) {
  				taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
  				break;
  			}
  		}
    	taiKhoanService.save(taiKhoan);
    }
    
	public static void creatTaiKhoanForCreatedBy(QuyenService quyenService, 
										TaiKhoanService taiKhoanService) {
		TaiKhoan taiKhoan = new TaiKhoan();
		ArrayList<DacQuyen> danhSachDacQuyen = quyenService.getDanhSachDacQuyen();
		for(int i=0; i<danhSachDacQuyen.size(); i++) {
			if(danhSachDacQuyen.get(i).getTenDacQuyen().equals("ROLE_USER")) {
				taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
				break;
			}
		}
		taiKhoan.setTenDangNhap(username);
		taiKhoan.setMatKhau(password);
		taiKhoan.setThoiGianDangKy(new Date());
		taiKhoanService.save(taiKhoan);
	}
	
	public static void createGioiTinhList(GioiTinhService gioiTinhService){
		List<String> gioiTinhList = new ArrayList<String>();
		gioiTinhList.add("Nam");
		gioiTinhList.add("Nữ");
		gioiTinhList.add("Khác");
		for (int i = 0; i < gioiTinhList.size(); i++) {
			if (!gioiTinhService.checkTenGioiTinh(gioiTinhList.get(i))) {
				GioiTinh gioiTinh = new GioiTinh();
				gioiTinh.setTenGioiTinh(gioiTinhList.get(i));
				gioiTinhService.save(gioiTinh);
			}
		}
	}

	public static void createTrangThaiDonHangList(TrangThaiDonHangService trangThaiDHService){
		List<String> trangThaiDHList = new ArrayList<String>();
		trangThaiDHList.add("PENDING");
		trangThaiDHList.add("ACCEPTED");
		trangThaiDHList.add("SENDING");
		trangThaiDHList.add("COMPLETED");
		trangThaiDHList.add("CANCELED");
		for (int i = 0; i < trangThaiDHList.size(); i++) {
			if (!trangThaiDHService.checkTenTrangThai((trangThaiDHList.get(i)))) {
				TrangThaiDonHang trangThaiDH = new TrangThaiDonHang();
				trangThaiDH.setTenTrangThai(trangThaiDHList.get(i));
				trangThaiDHService.save(trangThaiDH);
			}
		}
	}

	public static void createDacQuyenList(QuyenService quyenService){
		List<String> dacQuyenList = new ArrayList<String>();
		dacQuyenList.add("ROLE_ADMIN");
		dacQuyenList.add("ROLE_USER");
		for (int i = 0; i < dacQuyenList.size(); i++) {
			if (!quyenService.checkTenDacQuyen(dacQuyenList.get(i))) {
				DacQuyen dacQuyen = new DacQuyen();
				dacQuyen.setTenDacQuyen(dacQuyenList.get(i));
				quyenService.save(dacQuyen);
			}
		}
	}

}
