package com.mkyong.filter;

import brave.Span;
import brave.Tracer;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpFilter;
import java.io.IOException;
import java.net.Inet4Address;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Servlet Filter implementation class Testfilter
 */
@Component
@Order(1)
public class LogHandleFilter extends HttpFilter implements Filter {
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	public Tracer tracer;

    public LogHandleFilter() {
        super();

    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String className = this.getClass().toString();
		String endpoint = req.getRequestURI();
		String reqMethod = req.getMethod();
		String hostIp = Inet4Address.getLocalHost().getHostAddress();
		String clientIp = req.getRemoteAddr();
		// String clientIp = req.getRemoteAddr();

		if(clientIp != null){
			MDC.put("ClientIP", clientIp);
		}
		if(hostIp != null){
			MDC.put("HostIP", hostIp);
		}

		HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
		String methodName = "";
		if (handlerMethod != null) {
			// Get the method name
			methodName = handlerMethod.getMethod().getName();
		}

		String traceId = "123456"; // Generate a unique Trace ID
		String spanId = "654321"; // Generate a unique Span ID
		//Tags tags = Tags.of("traceId", traceId, "spanId", spanId);
		Span span = tracer.currentSpan();
		span.tag(traceId, spanId);
		span.start();
		// ================================ GET LOG ==========================
		try {
			MDC.put("LOG_PATH", "logconsole");
			MDC.put("TraceID", String.valueOf(this.tracer.currentSpan().context().traceIdString()));
			MDC.put("ApplicationName", applicationName);
			MDC.put("ClassName", className.substring(6));
			MDC.put("RequestMethod", reqMethod);
			MDC.put("Endpoint", endpoint);
			MDC.put("StatusResponse", HttpStatus.OK.toString());

		}
		finally {
			span.finish();
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
