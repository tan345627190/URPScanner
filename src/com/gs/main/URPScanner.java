package com.gs.main;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;

public class URPScanner {

	public String scan(String un, String pw) throws HttpException, IOException {
		HttpClient hc = new HttpClient();
		hc.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");//设置信息 
		PostMethod p = new PostMethod("http://jw.hebust.edu.cn/loginAction.do");
		p.addParameter("zjh", un);
		p.addParameter("mm", pw);
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
				"http://jw.hebust.edu.cn/bxqcjcxAction.do");
		// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
		postMethod.setRequestHeader("cookie", tmpcookies);
		hc.executeMethod(postMethod);
		String s = postMethod.getResponseBodyAsString();
		String regex = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"titleTop2\">(.*?)</TABLE>";
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(s);
		String re = "";
		if (mt.find()) {
			re = mt.group(1);
		}
		re = "<table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" class=\"titleTop2\">"+re+"</TABLE>";
		re.replaceAll("\"0\"", "\"1\"");
		System.out.println(re);
		return re;

	}
}
