package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.main.ClassPOJO;

public class TestClass {

	@Test
	public void test() throws IOException {
		String s = FileUtils.readFileToString(new File("D://Test//a.txt"), "gb2312");
		s = replaceBlank(s);
		String regex = "<tdalign=\"center\">(.*?)</td>";
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(s);
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
				p.setScore(Integer.valueOf(re.equals("")?"0":re));
			} catch (NumberFormatException e) {
				break;
			}
			System.out.println(p);
		}
		
		
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		dest = m.replaceAll("");
		}
		return dest;
		}

}
