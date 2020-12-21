import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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

import impl.TaiKhoanServiceImpl;
import model.DacQuyen;
import model.DacQuyenNames;
import model.GioiTinh;
import model.GioiTinhNames;
import model.TaiKhoan;
import model.TinhTrangDonHangNames;
import model.TrangThaiDonHang;
import repository.TaiKhoanRepository;
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
			if(DacQuyenNames.ROLE_ADMIN.equals(danhSachDacQuyen.get(i).getTenDacQuyen())) {
				taiKhoan.setDacQuyen(danhSachDacQuyen.get(i));
				break;
			}
		}
		taiKhoan.setTenDangNhap(username);
		taiKhoan.setMatKhau(password);
		taiKhoanService.save(taiKhoan);
	}
	
	public static void createGioiTinhList(GioiTinhService gioiTinhService){
		String[] gioiTinhList = GioiTinhNames.ALL_GT;
		for (int i = 0; i < gioiTinhList.length; i++) {
			if (!gioiTinhService.checkTenGioiTinh(gioiTinhList[i])) {
				GioiTinh gioiTinh = new GioiTinh();
				gioiTinh.setTenGioiTinh(gioiTinhList[i]);
				gioiTinhService.save(gioiTinh);
			}
		}
	}

	public static void createTrangThaiDonHangList(TrangThaiDonHangService trangThaiDHService){
		String[] trangThaiDHList = TinhTrangDonHangNames.ALL_TTDH;
		for (int i = 0; i < trangThaiDHList.length; i++) {
			if (!trangThaiDHService.checkTenTrangThai((trangThaiDHList[i]))) {
				TrangThaiDonHang trangThaiDH = new TrangThaiDonHang();
				trangThaiDH.setTenTrangThai(trangThaiDHList[i]);
				trangThaiDHService.save(trangThaiDH);
			}
		}
	}

	public static void createDacQuyenList(QuyenService quyenService){
		String[] dacQuyenList = DacQuyenNames.ALL_ROLES;
		for (int i = 0; i < dacQuyenList.length; i++) {
			if (!quyenService.checkTenDacQuyen(dacQuyenList[i])) {
				DacQuyen dacQuyen = new DacQuyen();
				dacQuyen.setTenDacQuyen(dacQuyenList[i]);
				quyenService.save(dacQuyen);
			}
		}
	}

}
