package com.gs.main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class URPScanner {
	private String name;
	public String scan(String un, String pw) throws HttpException, IOException {
		HttpClient hc = new HttpClient();
		hc.getParams()
				.setParameter(
						HttpMethodParams.USER_AGENT,
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");// 设置信息
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
		Pattern pt = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mt = pt.matcher(s);
		String re = "";
		if (mt.find()) {
			re = mt.group(1);
		}
		re.replaceAll("\"0\"", "\"1\"");
		
		
		PostMethod postMethod2 = new PostMethod(
				"http://jw.hebust.edu.cn/menu/s_top.jsp");
		// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
		postMethod2.setRequestHeader("cookie", tmpcookies);
		hc.executeMethod(postMethod2);
		String nameHtml = postMethod2.getResponseBodyAsString();
		String regex2 = "欢迎光临&nbsp;(.*?)&nbsp;";
		Pattern pt2 = Pattern.compile(regex2, Pattern.DOTALL);
		Matcher mt2 = pt2.matcher(nameHtml);
		String re2 = "";
		if (mt2.find()) {
			re2 = mt2.group(1);
		}
		name = re2;
		return re;
	}

	public List<ClassPOJO> revert(String html) {
		List<ClassPOJO> list = new LinkedList<ClassPOJO>();
		html = replaceBlank(html);
		String regex = "<tdalign=\"center\">(.*?)</td>";
		Pattern pt = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mt = pt.matcher(html);
		String re = "";
		while (mt.find()) {
			ClassPOJO p = new ClassPOJO();
			mt.find();
			mt.find();
			re = mt.group(1);
			p.setName(re);
			mt.find();
			mt.find();
			re = mt.group(1);
			p.setCredit(Float.parseFloat(re));
			mt.find();
			mt.find();
			re = mt.group(1);
			try {
				p.setScore(re);
			} catch (NumberFormatException e) {
				continue;
			}
			list.add(p);
		}
		return list;
	}

	private String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public String getName(){
		return name;
	}
}
