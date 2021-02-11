
package com.poem.main.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Load {

	public final static List<String> checkClasspathLines(String url) {
		List<String> listOfLines = null;
		try {
			ClassLoader classLoader = Load.class.getClassLoader();
			InputStream is = classLoader.getResourceAsStream(url.substring("classpath:".length()));
			byte[] bytes = getBytesFromInputStream(is);
			Path path = Paths.get(Files.createTempFile("tmp", "File").toUri());
			Files.write(path, bytes);
			listOfLines = readLines(path.toFile());
		} catch (IOException ex) {
			Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
		}
		return listOfLines;
	}

	public final static List<String> readLines(String url) {
		if (url != null && url.startsWith("classpath:")) {
			return checkClasspathLines(url);
		}
		return readLines(new File(url));
	}

	public final static List<String> readLines(File f) {
		List<String> loadedLines = null;
		String readLine;
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(f));
			System.out.println(String.format("Reading file %s", f.getAbsolutePath()));
			loadedLines = new ArrayList<>();
			while ((readLine = br.readLine()) != null) {
				loadedLines.add(readLine);
			}
		} catch (IOException e) {
			System.out.println(String.format("Cannot load the file [%s] - %s", f.getName(), e.getMessage()));
			e.printStackTrace();
		}
		return loadedLines;
	}

	private static byte[] getBytesFromInputStream(InputStream is) {
		DataInputStream dis = null;
		byte[] data = null;
		try {
			data = new byte[(int) is.available()];
			dis = new DataInputStream(is);
			dis.readFully(data);
		} catch (IOException ex) {
			Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException ex) {
					Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return data;
	}
}
