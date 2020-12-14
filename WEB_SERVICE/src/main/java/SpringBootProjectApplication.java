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
import model.TaiKhoan;
import security.auditor_aware.SpringSecurityAuditorAware;
import security.config.MethodSecurityConfig;
import security.config.WebSecurityConfig;

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
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
    
    @Bean
    public AuditorAware<TaiKhoan> auditorProvider() {
    	return new SpringSecurityAuditorAware();
    }
}
