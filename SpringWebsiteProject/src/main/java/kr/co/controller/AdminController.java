package kr.co.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.service.AdminService;
import net.sf.json.JSONArray;
import kr.co.domain.CategoryVO;
import kr.co.domain.FMemberVO;
import kr.co.domain.GoodsVO;
import kr.co.domain.GoodsViewVO;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Inject
	private AdminService aservice;

	/* 관리자 메인 페이지 이동 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception {

		System.out.println("관리자 페이지 이동");
	}
	
	/* 회원정보 리스트 이동 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String List(Model model){
		
		List<FMemberVO> list = aservice.list();
		model.addAttribute("list", list);
		
		return "list";

	}
	
	//회원목록 조회
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public void read(String memId, Model model) {
		FMemberVO vo = aservice.read(memId);
		model.addAttribute("vo", vo);
	}

	//회원 수정
	@RequestMapping(value = "update/{memId}", method = RequestMethod.GET)
	public String update(@PathVariable("memId") String memId, Model model) {
		FMemberVO vo = aservice.updateUI(memId);
		model.addAttribute("vo", vo);
		return "update";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(FMemberVO vo, Model model) {
		
		int successCount = aservice.update(vo);
		
		if(successCount >= 1) {
			return"redirect: /list";
		} else {
			model.addAttribute("errmsg", "업데이트 실패");
		}
		
		return "redirect:/update/"+vo.getMemId();
	}
	
	//상품등록
	@RequestMapping(value = "/goods/register", method = RequestMethod.GET)
	public void getGoodsRegister(Model model) throws Exception{
		logger.info("get goods register");
		
		List<CategoryVO> category = null;
		category = aservice.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	@RequestMapping(value = "/goods/register", method = RequestMethod.POST)
	public String postGoodRegister(GoodsVO vo) throws Exception {
		aservice.register(vo);
		
		return "redirect:/admin/main";
	}
	
	//상품 목록
		@RequestMapping(value = "/goods/list", method = RequestMethod.GET)
		public void getGoodsList(Model model) throws Exception{
			logger.info("get goods list");
			
			List<GoodsVO> list = aservice.goodslist();
			
			model.addAttribute("list", list);
		}
		
		// 상품 조회
		@RequestMapping(value = "/goods/view", method = RequestMethod.GET)
		public void getGoodsview(@RequestParam("n") int fNum, Model model) throws Exception{
			logger.info("get goods view");
			
			GoodsViewVO goods = aservice.goodsView(fNum);
			
			model.addAttribute("goods", goods);
		}
		
		// 상품 수정
				@RequestMapping(value = "/goods/modify", method = RequestMethod.GET)
				public void getGoodsModify(@RequestParam("n") int fNum, Model model) throws Exception{
					logger.info("get goods modify");
					
					GoodsViewVO goods = aservice.goodsView(fNum);
					
					model.addAttribute("goods", goods);
					
					List<CategoryVO> category = null;
					category = aservice.category();
					model.addAttribute("category", JSONArray.fromObject(category));
				}
	
}