package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//스프링 컨테이너나 창에 뜰 때 스프링 컨테이너 박스가 생긴다고 가정
//거기에 멤버 컨트롤러 객체로 생성해서 스프링에 넣어두고 스프링이 관리
@Controller
public class MemberController {
    //memberService를 가져다 쓴다.
    //new로 선언하면 다른 여러 컨트롤러들이 서비스를 가져다 쓸 수 있는거다.
    private final MemberService memberService;

    //스프링 컨테이너 앞에 등록을 하고 사용한다.
    @Autowired // memberService를 스프링이 스프링 컨테이너에 있는 MemberService를 가져다가 연결을 해준다.
    // 생성자
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass()); // 프록시 확인 방법
    }

    @GetMapping("/members/new")
    public String createForm(){

        return "members/createMemberForm";

    }

    //method post로 받아와 등록한다.
    @PostMapping("/members/new") //home.html에서 submit한 데이터를 받는다.
    public String create(MemberForm form){
        //member객체 생성 후 form에 이름 저장
        Member member = new Member();
        member.setName(form.getName());

        //System.out.println("member = " + member.getName());

        //저장된 이름을 Service에 join메소드를 호출해 회원등록
        memberService.join(member);

        //회원가입이 끝나면 홈 화면으로 보낸다.
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        //member를 다 끄집어 온다.
        List<Member> members = memberService.findMembers();

        //member 리스트를 모델에 담아서 화면에 넘긴다.
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
