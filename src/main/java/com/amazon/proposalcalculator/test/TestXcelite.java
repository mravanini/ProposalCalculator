package com.amazon.proposalcalculator.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.amazon.proposalcalculator.bean.User;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;

public class TestXcelite {

	public static void main(String[] args) {
		testWrite();
		testRead();
	}

	private static void testWrite() {
		Xcelite xcelite = new Xcelite();
		XceliteSheet sheet = xcelite.createSheet("users");
		SheetWriter<User> writer = sheet.getBeanWriter(User.class);
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setFirstName("angelo");
		user.setBirthDate(new Date());
		users.add(user);
		// ...fill up users
		writer.write(users);
		// xcelite.write(new File("users_doc.xlsx"));
		xcelite.write(new File("/Users/carvaa/Desktop/xcelite.xlsx"));
	}

	private static void testRead() {
		Xcelite xcelite = new Xcelite(new File("/Users/carvaa/Desktop/xcelite.csv"));
		XceliteSheet sheet = xcelite.getSheet("users");
		SheetReader<User> reader = sheet.getBeanReader(User.class);
		Collection<User> users = reader.read();
		for (User user : users) {
			System.out.println(user.getFirstName());
		}
	}

}
