package com.epam.sma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ReaderWriter extends ArrayList<String> {

	private static final long serialVersionUID = 3092420954191323675L;

	public static String read(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(
					fileName).getAbsoluteFile()));
			try {
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	public static void write(String fileName, String text) {
		try {
			File file = new File(fileName).getAbsoluteFile();
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
			PrintWriter out = new PrintWriter(file);
			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ReaderWriter(String fileName, String splitter) {
		super(Arrays.asList(read(fileName).split(splitter)));
		if (get(0).equals(""))
			remove(0);
	}

	public ReaderWriter(String fileName) {
		this(fileName, "\n");
	}

}
