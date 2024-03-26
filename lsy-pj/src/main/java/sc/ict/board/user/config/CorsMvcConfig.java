package sc.ict.board.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS 설정을 구성하는 역할
//WebMvcConfigurer는 MVC의 설정을 커스터마이징할 수 있는 메서드를 제공
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        // corsRegistry를 사용하여 CORS 설정을 추가
        // addMapping("/**")은 모든 URL 패턴에 대해 CORS를 허용함을 의미
        // allowedOrigins("http://localhost:3000")은 "http://localhost:3000" 도메인에서의 요청을 허용함을 의미
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}
