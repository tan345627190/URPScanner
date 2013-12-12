package com.gs.main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShouFeiScanner {
	public StudentPOJO scan(String html) throws IOException {
		String regex = "<span class=\"ÕýÎÄ×ÖÌå\">(.*?)</span>";
		Pattern pt = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mt = pt.matcher(html);
		String re = "";
		StudentPOJO pojo = new StudentPOJO();
		find(3,mt);
		re = mt.group(1);
		pojo.setId(re.substring(0, 9));
		find(2,mt);
		re = mt.group(1);
		pojo.setName(re);
		find(2,mt);
		re = mt.group(1);
		pojo.setSchool(re);
		find(2,mt);
		re = mt.group(1);
		pojo.setMajor(re);
		find(2,mt);
		re = mt.group(1);
		pojo.setClazz(re);
		find(2,mt);
		re = mt.group(1);
		pojo.setPrice(re);
		//System.out.println(pojo);
		return pojo;
	}

	private void find(int i, Matcher mt) {
		for (int j = 0; j < i; j++) {
			mt.find();
		}
	}
}
