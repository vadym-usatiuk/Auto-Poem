/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poem.main;

import java.util.List;
import java.util.Map;

import com.poem.main.generator.Generator;
import com.poem.main.model.Model;

public class Main {

	private static void argumentsChecker(String[] args) {
		if (args != null && args.length > 0) {
			inputFile = args[0];
			System.out.println("Input argument of inputFile: " + inputFile);
			if (args.length > 1 && args[1].equalsIgnoreCase("true")) {
				debugFile = true;
				System.out.println("Input argument of debugFile: " + debugFile);
			}
		} else {
			inputFile = DEFAULT_FILE;
		}
	}

	private static final String DEFAULT_FILE = "classpath:text";
	private static String inputFile;
	private static boolean debugFile;

	public static void main(String[] args) {
		argumentsChecker(args);
		Map<String, Model> Implementation;
		List<String> stringsPoem;
		Generator generator = new Generator();
		Implementation = generator.processFile(inputFile);
		if (debugFile)
			generator.printLoadedCheck(Implementation);
		stringsPoem = generator.generatePoem(Implementation);
		generator.stringsPoemPrinting(stringsPoem);
	}
}
