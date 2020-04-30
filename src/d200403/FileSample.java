package d200403;

import java.io.File;
import java.io.IOException;

public class FileSample {
	public static void main(String[] args) {
		FileSample sample = new FileSample();
		String pathName = "c:\\basicjava\\test";
//		sample.checkPath(pathName);
		
		String fileName="test.txt";
		sample.checkFile(pathName, fileName);
	}

	public void checkFile(String pathName, String fileName) {
		File file = new File(pathName, fileName);
		try {
			System.out.println("Create result = " + file.createNewFile());
			/*
			 canonical
			1. (성경이) 정본에 속하는; (문학 작품이) 고전으로 여겨지는
			2. 교회법에 따른
			3. (수학에서) 표준이 되는
			*/
			System.out.println("Absolute path = " + file.getAbsolutePath());
			System.out.println("Absolute file = " + file.getAbsoluteFile());
			System.out.println("Canonical path = " + file.getCanonicalPath());
			System.out.println("Caonical File = " + file.getCanonicalFile());
			System.out.println("getName = " + file.getName());
			System.out.println("getPath = " + file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkPath(String pathName) {
		File file = new File(pathName);
		System.out.println(pathName + " is exists? " + file.exists());
//		System.out.println("Make " + pathName + " result = " + file.mkdirs());

		System.out.println(pathName + " is directory? " + file.isDirectory());
		System.out.println(pathName + " is file? " + file.isDirectory());
		System.out.println(pathName + " is hidden? " + file.isDirectory());

		System.out.println(pathName + " can read? " + file.isDirectory());
		System.out.println(pathName + " can write? " + file.isDirectory());
		System.out.println(pathName + " can execute? " + file.isDirectory());

		System.out.println(pathName + " last modified = " + file.isDirectory());
//		System.out.println("Delete " + pathName + "  result " + file.delete());
	}
}
