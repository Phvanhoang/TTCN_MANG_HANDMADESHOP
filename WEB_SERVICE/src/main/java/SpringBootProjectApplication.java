import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import impl.TaiKhoanServiceImpl;
import model.DacQuyen;
import model.TaiKhoan;
import repository.TaiKhoanRepository;
import security.auditor_aware.SpringSecurityAuditorAware;
import security.config.MethodSecurityConfig;
import security.config.WebSecurityConfig;
import service.QuyenService;
import service.TaiKhoanService;

@EnableSpringConfigured
@Configuration
@SpringBootApplication
//@EnableTransactionManagement
@EntityScan(basePackages = "model")
@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"model", "impl", "controller","service", "security.*", "utils"}, 
				basePackageClasses = {WebSecurityConfig.class, MethodSecurityConfig.class})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SpringBootProjectApplication {

	public static void main(String[] args) {
//		createDacQuyenList(quyenService);
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
	
//	public static void createDacQuyenList(QuyenService quyenService){
//		List<String> dacQuyenList = new ArrayList<String>();
//		dacQuyenList.add("ROLE_ADMIN");
//		dacQuyenList.add("ROLE_USER");
//		for (int i = 0; i < dacQuyenList.size(); i++) {
//			if (!quyenService.checkTenDacQuyen(dacQuyenList.get(i))) {
//				DacQuyen dacQuyen = new DacQuyen();
//				dacQuyen.setTenDacQuyen(dacQuyenList.get(i));
//				quyenService.save(dacQuyen);
//			}
//		}
//	}
	
    @Bean
    public AuditorAware<TaiKhoan> auditorProvider(TaiKhoanService taiKhoanService, QuyenService quyenService) {
    	return new SpringSecurityAuditorAware(taiKhoanService, quyenService);
    }
}
