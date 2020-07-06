package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet Filter implementation class LoginCheck
 */
//index.jsp、login.jsp、register.jsp、confirm.jspを除く全てのjspで適用//@WebFilter(urlPatterns={"/ChatApp/jsp/chatroom.jsp", "/community.jsp", "/game.jsp", "/gameList.jsp", "/group.jsp", "/groupCreate.jsp", "/home.jsp", "/user.jsp", "/usersList.jsp"})
@WebFilter(urlPatterns = {"/jsp/home.jsp","/jsp/chatroom.jsp", "/jsp/community.jsp","/jsp/game.jsp","/jsp/gameList.jsp","/jsp/group.jsp","/jsp/groupCreate.jsp","/jsp/user.jsp","/jsp/usersList.jsp","/HomeServlet","/ChatroomServlet","/CommunityServlet","/GameListServlet", "/GameServlet","/GroupCreateServlet","/LikeServlet","/UserServlet","/UsersListServlet","/WasReadServlet"})
public class LoginCheck implements Filter {

    /**
     * Default constructor.
     */
    public LoginCheck() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//ログイン状態をチェックするフィルター
		//ログインしていない場合はindex.jspにリダイレクト
		System.out.println("ログインフィルターを適用");
		HttpSession session = ((HttpServletRequest)request).getSession();
		User self = (User)session.getAttribute("self");
		if(self == null) {
			System.out.println("ログインしていません");
			((HttpServletResponse)response).sendRedirect("/ChatApp/jsp/index.jsp");
		}else {


		// pass the request along the filter chain
		chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
