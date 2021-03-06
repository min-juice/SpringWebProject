package kr.co.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.domain.FMemberVO;

public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

HttpSession session = request.getSession();
        
        FMemberVO lvo = (FMemberVO)session.getAttribute("member");

        if(lvo == null || lvo.getAdminCK() == 0) {    // 관리자 계정 아닌 경우
            
            response.sendRedirect("/");    // 메인페이지로 리다이렉트
            
            return false;
            
        }
        
        return true;    // 관리자 계정 로그인 경우(lvo != null && lvo.getAdminCk() == 1)
        
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
