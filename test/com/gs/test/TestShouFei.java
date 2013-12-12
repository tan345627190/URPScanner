package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.gs.main.IDBuilder;
import com.gs.main.ShouFeiLogin;
import com.gs.main.ShouFeiScanner;

public class TestShouFei {

	@Test
	public void test() throws IOException {
			ShouFeiLogin l = new ShouFeiLogin();
			ShouFeiScanner s = new ShouFeiScanner();
			int i = 1;
			FileWriter fw = new FileWriter(new File("D://Test//stu.txt"),true);
			while (i<40) {
				try {
					fw.write(s.scan(l.login("1307021"
							+ IDBuilder.get())).toString()+"\r\n");
				} catch (Exception e) {
					i++;
					continue;
				}
				i++;
				
			}
			fw.close();
		
	}

}
