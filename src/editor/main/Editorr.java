package editor.main;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class Editorr {

	public static Editor.Language[] languages = {
		new LanguageJava(),};

	public static String fileName;
	public static String fileExt;
	public static String file;
	public static Editor.Language lang;

	public static String code = "";

	public static void main(String[] args) {
		if (args.length > 0) {
			file = args[0];
		} else {
			lang = languages[0];
			System.out.println("No file provided, defaulting to: ".concat(fileName));
			fileName = "project";
			fileExt = lang.getExtension();
			file = fileName.concat(".").concat(fileExt);
		}
		String extension = Editorr.extractExtension(file); 
		lang = languages[0];
		for (int i = 0; i < languages.length; i++) {
			if (extension.equals(languages[i].getExtension())) {
				lang = languages[i];
			}
		}
		new Editor(lang, loadNodes());
	}

	public static String extractExtension(String file) {
		String ext = ""; 
		int x = file.lastIndexOf('.');
		if (x > 0) {
			ext = file.substring(x + 1);
		}
		return ext; 
	}

	public static String compile(LinkedList<Node> nodes) {
		String code = "";
		for (int i = 0; i < nodes.size(); i++) {
			code += nodes.get(i).parse() + "\n";
		}
		Editorr.code = code;
		return code;
	}

	public static boolean runProgram(String path) {
		Runtime rt = Runtime.getRuntime();
		String[] cmd = {
			"java", "--enable-preview", "--source", "21", path
		};

		try {
			Process process = rt.exec(cmd);
			while (process.isAlive());
			InputStream stdin = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(stdin));

			String line = null;
			String txt = ""; 
			while ((line = br.readLine()) != null) {
				txt = txt.concat(line).concat(txt); 
			}

			if (txt.isBlank() || txt.isEmpty()) return false;
			
			System.out.println("Output: ".concat(txt));
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static void saveNodes(LinkedList<Node> nodes) {
		try {
			Files.deleteIfExists(Paths.get(file));
			Files.deleteIfExists(Paths.get(file + "+"));
			String code = compile(nodes);
			Files.write(Paths.get(file), code.getBytes());
			System.out.println(code);

			FileOutputStream stringOut = new FileOutputStream(file + "+");
			ObjectOutputStream out = new ObjectOutputStream(stringOut);
			out.writeObject(nodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<Node> loadNodes() {
		if (Files.exists(Paths.get(file + "+"))) {
			try {
				FileInputStream stringIn = new FileInputStream(file + "+");
				ObjectInputStream in = new ObjectInputStream(stringIn);
				LinkedList<Node> nodes = (LinkedList<Node>) in.readObject();
				in.close();
				return nodes;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
