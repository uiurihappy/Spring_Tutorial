package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
    */
    /*
    //datasource를 스프링이 제공해준다.
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */
    //memberService를 로직을 호출하여 스프링 빈에 등록
    @Bean // 1) 스프링 빈 등록
    public MemberService memberService(){
        // 3) 스프링 빈에 등록되어 있는 memberRepository를 MemberService에 넣어준다.
        return new MemberService(memberRepository);
    }
    /*
    @Bean // 2) 스프링 빈 등록
    public MemberRepository memberRepository(){
        // 1. 메모리 작업으로 회원 저장
        // return new MemoryMemberRepository();

        // 2. DB로 회원 저장
        //return new JdbcMemberRepository(dataSource);

        // 3. jdbcTemplate로 회원 저장
        //return new JdbcTemplateMemberRepository(dataSource);

        // 4. jpa로 회원 저장
        //return new JpaMemberRepository(em);


    }
    */
    /* 컴포넌트 스캔을 사용하지 않는 방법
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }
    */
}
