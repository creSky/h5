package h5.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultInterceptor extends HandlerInterceptorAdapter {

	// 参数中的Object handler是下一个拦截器。

	// 最后执行，可用于释放资源
	// 在afterCompletion中，可以根据e是否为null判断是否发生了异常，进行日志记录

	private void executeException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, Exception exception) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置头信息,字符集UTF-8
		httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(exception));
		httpServletResponse.getWriter().close();

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, Exception exception) throws Exception {
		// TODO Auto-generated method stub

		if (null != exception) {

			// executeException(httpServletRequest, httpServletResponse, object, exception);
			try {
				httpServletRequest.getRequestDispatcher("500.html").forward(httpServletRequest, httpServletResponse);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// Action之后,生成视图之前执行
	// 在postHandle中，有机会修改ModelAndView
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		if (null != modelAndView) {
			// // 设置头信息,字符集UTF-8
			// httpServletResponse.setHeader("Content-type",
			// "text/html;charset=UTF-8");
			// httpServletResponse.getWriter().println(modelAndView.getModel().get("json"));
			//
			// httpServletResponse.getWriter().close();
			//
			// LogUtil.getLogger(LOG_TYPE.OTHER)
			// .info("[" + httpServletRequest.getRemoteAddr() + "]" +
			// modelAndView.getModel().get("json"));
		}
		System.out.println("postHandler");
	}

	// Action之前执行
	// 在preHandle中，可以进行编码、安全控制等处理

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object) throws Exception {
		// TODO Auto-generated method stub

		ObjectMapper objectMapper = new ObjectMapper();

		System.out.println("[" + new Date() + "]{" + httpServletRequest.getRemoteAddr() + "} 执行"
				+ getClassMethod(object) + "[" + httpServletRequest.getMethod() + "]" + ":{"
				+ objectMapper.writeValueAsString(httpServletRequest.getParameterMap()) + "}");

		return true;
	}

	protected String getClassMethod(Object object) {
		if (object instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) object;
			Class<?> clazz = handlerMethod.getBeanType();
			Method method = handlerMethod.getMethod();

			return clazz.getName() + "." + method.getName();
		} else if (object instanceof RequestMappingHandlerMapping) {
			RequestMappingHandlerMapping handlerMethod = (RequestMappingHandlerMapping) object;
			
			return null;

		} else {
			return "执行未知操作:" + object.getClass();
		}

	}

}
