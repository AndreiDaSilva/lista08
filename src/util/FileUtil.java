package com.andrei.crypto.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

	public static void writeBytes(Path path, byte[] data) throws Exception {
		Files.write(path, data);
	}

	public static byte[] readBytes(Path path) throws Exception {
		return Files.readAllBytes(path);
	}
}
