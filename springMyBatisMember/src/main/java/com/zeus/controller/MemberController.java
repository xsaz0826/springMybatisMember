package com.zeus.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeus.domain.Member;
import com.zeus.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@MapperScan(basePackages = "com.zeus.mapper")
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/insertForm")
	public String memberInsertForm(Model model) {
		return "member/insertForm";
	}
	
	@PostMapping("/insert")
	public String memberInsert(Member member, Model model) {
		log.info("insert member=" + member.toString());
		try {
			int count = memberService.register(member);
			if (count > 0) {
				model.addAttribute("message", "%s 님이 회원가입하였습니다.".formatted(member.getName()));
				return "member/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s 님이 회원가입에 실패하였습니다. 다시 시도해주세요.".formatted(member.getName()));
		return "member/failed";
	}

	@GetMapping("/memberList")
	public String memberList(Model model) {
		log.info("memberList");
		try {
			List<Member> memberList = memberService.list();
			model.addAttribute("memberList", memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "member/memberList";
	}
	
	@GetMapping("/detail")
	public String memberDetail(Member m, Model model) {
		log.info("Detail =" + m.toString());
		try {
			Member member = memberService.read(m);
			if (member == null) {
				model.addAttribute("message", "%d 번 상세정보가 없습니다.".formatted(member.getNo()));
				return "member/failed";
			}
			log.info("detail member ="+ member.toString());
			model.addAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/detail";
	}

	@GetMapping("/updateForm")
	public String memberUpdateForm(Member m, Model model) {
		log.info("UpdateForm =" + m.toString());
		try {
			Member member = memberService.read(m);
			if (member == null) {
				model.addAttribute("message", "%d 번 정보가 없습니다.".formatted(member.getNo()));
				return "member/failed";
			}
			model.addAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/updateForm";
	}

	@PostMapping("/update")
	public String memberUpdate(Member m, Model model) {
		log.info("Update member=" + m.toString());
		try {
			int count = memberService.update(m);
			if (count > 0) {
				model.addAttribute("message", "%s 님의 회원 정보가 수정되었습니다.".formatted(m.getName()));
				return "member/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s 님의 회원 정보를 수정하는 데 실패하였습니다.".formatted(m.getName()));
		return "member/failed";
	}
/*
	@GetMapping("/delete")
	public String boardDelete(Board board, Model model) {
		log.info("boardDelete board=" + board.toString());
		try {
			int count = boardService.delete(board);
			if (count > 0) {
				model.addAttribute("message", "%d 번 게시판이 삭제되었습니다.".formatted(board.getNo()));
				return "board/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%d 번 게시판을 삭제하는 데 실패하였습니다.".formatted(board.getNo()));
		return "board/failed";
	}

	


	@GetMapping("/search")
	public String boardSearch(Board board, Model model) {
		log.info("boardSearch board" + board.toString());
		try {
			List<Board> boardList = boardService.search(board);
			model.addAttribute("boardList", boardList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/boardList";
	}
	*/
}
