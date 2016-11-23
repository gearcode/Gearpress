package com.gearcode.gearpress.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FileUploadUtils {
	public static String uploadRandomName(MultipartFile file, String rootDir) throws IllegalStateException, IOException {
		String path = rootDir + File.separatorChar;
		File directory = new File(path);
		directory.mkdirs();
		String name = "" + System.currentTimeMillis() + "_" + getRandomString(8) + getExtName(file);
		
		file.transferTo(new File(directory, name));
		return name;
	}
	
	public static String getExtName(MultipartFile file) {
		return getExtName(file.getOriginalFilename());
	}
	
	public static String getExtName(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	private static final char[] chars = new char[]{
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for(int i=length; i>0; i--) {
			sb.append(chars[r.nextInt(chars.length)]);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		for(int i=0;i<26;i++) {
			System.out.print("'" + (char)(i+97) + "', ");
		}
		System.out.println();
		for(int i=0;i<26;i++) {
			System.out.print("'" + (char)(i+65) + "', ");
		}
	}
}
