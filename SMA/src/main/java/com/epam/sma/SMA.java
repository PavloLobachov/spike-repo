package com.epam.sma;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.epam.sma.util.ReaderWriter;

public class SMA {

	private Map<Integer, Double> map;
	private Map<Integer, Double> res;
	private Set<Integer> keys;
	private StringBuilder sb;

	public SMA() {
		map = new TreeMap<Integer, Double>();
		res = new TreeMap<Integer, Double>();
		sb = new StringBuilder();
	}

	public String startSMA(String line, int n) {
		if (n == 0 || n == 1) {
			n = 2;
		}
		stringToMap(line);
		for (Integer i : keys) {
			double tmp = 0;
			if (i >= n) {

				if (i == n) {
					tmp = processData(n, i, tmp);

					res.put(i, tmp / n);
				} else {
					tmp = processDataOptimal(n, i, tmp);
					res.put(i, tmp);
				}
			}
		}
		mapToString();
		map = null;
		keys = null;
		return sb.toString();
	}

	private double processDataOptimal(int n, Integer i, double tmp) {
		for (int j = 1, z = i - 1; j <= n; j++) {
			if (z <= keys.size()) {
				double map_value = map.get(z - 1) / 2;
				double next_map_value = map.get(z + 1) / 2;

				tmp = res.get(z) - map_value + next_map_value;
			}
		}
		return tmp;
	}

	private double processData(int n, Integer i, double tmp) {
		for (int j = 1, z = i - 1; j <= n; j++, z++) {
			if (z <= keys.size()) {
				tmp = tmp + map.get(z);
			}
		}
		return tmp;
	}

	private void mapToString() {
		for (Integer i : keys) {
			sb.append(i + "\t").append(map.get(i) + "\t\t")
					.append(res.containsKey(i) ? res.get(i) : " ").append("\n");
		}
	}

	private void stringToMap(String line) {
		String[] strings = line.split("\n");
		for (String string : strings) {
			String[] s = string.split("\t");
			map.put(Integer.valueOf(s[0]), Double.valueOf(s[1]));
		}
		keys = map.keySet();
	}

	public static void main(String[] args) {
		String file = ReaderWriter.read("Start.java");
		SMA sma = new SMA();
		ReaderWriter.write("test.txt", sma.startSMA(file, 2));
		System.exit(0);
	}
}
