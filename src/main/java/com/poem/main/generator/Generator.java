package com.poem.main.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.poem.main.exceptions.Exceptions;
import com.poem.main.model.Model;
import com.poem.main.util.Load;
import com.poem.main.util.Pending;

public class Generator {

	private static final String KEYWORDS_LINE_BREAK = "$LINEBREAK";
	private static final String[] RULES_WITHOUT_VALUES = { "<LINE>", "<POEM>" };

	public Map<String, Model> processFile(String path) {
		Map<String, Model> models = new HashMap<String, Model>();
		List<String> listOfLines;
		listOfLines = Load.readLines(path);
		listOfLines.stream().forEach((line) -> {
			System.out.println(line);
			try {
				Model model = extractCheck(line);
				models.put(model.getName(), model);
			} catch (Exceptions e) {
				System.out.println("Invalid check: " + e.getMessage());
			}
		});
		return models;
	}

	public List<String> generatePoem(Map<String, Model> fullCheck) {
		List<String> listOfLines = new ArrayList<String>();

		Model poem = fullCheck.get("<POEM>");
		if (poem == null) {
			throw new IllegalArgumentException("Poem check do not exists");
		} else {
			listOfLines.add(evaluateCheckReference(fullCheck, poem));
		}
		return listOfLines;
	}

	private String evaluateCheckReference(Map<String, Model> allCheck, Model currenCheck) {
		int wordIndex = 0;
		String preparedText = "";
		for (String[] elements : currenCheck.getElements()) {
			wordIndex = ThreadLocalRandom.current().nextInt(0, elements.length);
			String word = elements[wordIndex];

			if (Pending.isTheSame(word)) {
				if (KEYWORDS_LINE_BREAK.equals(word)) {
					preparedText += "\n";
				}
			} else if (Pending.Matches(word)) {
				Model nextCheck = allCheck.get(word);
				preparedText += evaluateCheckReference(allCheck, nextCheck);
			} else {
				preparedText += (word + " ");
			}
		}
		return preparedText;
	}

	public void stringsPoemPrinting(List<String> stringsPoem) {
		System.out.println("\nMy auto generated poem:");
		stringsPoem.stream().forEach((line) -> {
			System.out.print(line);
		});
	}

	@SuppressWarnings("null")
	private Model extractCheck(String line) {
		Model model = null;
		String checkName = "";
		List<String[]> elements = new ArrayList<>();
		if (line == null && line.isEmpty()) {
			throw new Exceptions("Line is null or empty");
		} else {
			String checkParts[] = line.trim().split(" ");
			if (checkParts != null) {
				checkName = checkParts[0].trim().replace(":", "");

				if (Arrays.binarySearch(RULES_WITHOUT_VALUES, checkName) < 0) {
					if (checkParts.length >= 2) {
						for (int i = 1; i < checkParts.length; i++) {
							String[] possibleElements = checkParts[i].trim().split("\\|");
							elements.add(possibleElements);
						}
					} else {
						throw new Exceptions(
								String.format("[%s], has only (%d) parts  ", checkName, checkParts.length));
					}
				}
			} else {
				throw new Exceptions("Reading: Empty line parts");
			}
		}

		model = new Model(String.format("<%s>", checkName), elements);
		return model;
	}

	public void printLoadedCheck(Map<String, Model> allCheck) {
		System.out.println("\nPrinting loaded Check ... ");
		allCheck.values().stream().forEach((check) -> {
			System.out.println("\n");
			System.out.println(check.getName());
			for (String[] elements : check.getElements()) {
				System.out.println(Arrays.toString(elements));
			}
		});
	}
}
