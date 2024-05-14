package editor.main;

import editor.main.Editor;
import editor.main.Node;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

import static editor.main.Editor.NodeType;

public class LanguageJava implements Serializable, Editor.Language {

	public NodeType[] nodeTypes = new NodeType[]{
		new NodeType() {
			{
				name = "Code";
				outputNum = 0;
				isTextEditor = true;
				syntaxColor = Color.CYAN;
				width = 512 - 128;
				height = 128 - 32;
			}

			public String parse(String meta, Node[] outputNodes) {
				return meta.concat("\n");
			}
		},
		new NodeType() {
			{
				name = "Expression";
				outputNum = 0;
				isTextEditor = true;
				syntaxColor = Color.CYAN;
				width = 512 - 128;
				height = 128 - 32;
			}

			public String parse(String meta, Node[] outputNodes) {
				return meta;
			}
		},
		new NodeType() {
			{
				name = "import";
				outputNum = 0;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30 * 3;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "import ".concat(meta).concat(";\n");
				return data;
			}
		},
		new NodeType() {
			{
				name = "package";
				outputNum = 1;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30 * 3;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "//package ".concat(meta).concat("\n");
				if (outputNodes[0] != null) {
					data += outputNodes[0].parse() + ' ';
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "static";
				outputNum = 1;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "static";
				if (outputNodes[0] != null) {
					data += outputNodes[0].parse() + ' ';
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "public";
				outputNum = 1;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "public";
				if (outputNodes[0] != null) {
					data += outputNodes[0].parse() + ' ';
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "private";
				outputNum = 1;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "private";
				if (outputNodes[0] != null) {
					data += outputNodes[0].parse() + ' ';
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "protected";
				outputNum = 1;
				isTextEditor = false;
				syntaxColor = Color.CYAN;
				width = 32 * 3;
				height = 30;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "protected";
				if (outputNodes[0] != null) {
					data += outputNodes[0].parse() + ' ';
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Scope";
				outputNum = 1;
				isTextEditor = true;
				syntaxColor = Color.YELLOW;
				width = 32 * 3;
				height = 30;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = block("");
				if (outputNodes[0] != null) {
					data = block(outputNodes[0].parse()); 
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Method";
				outputNum = 1;
				isTextEditor = true;
				syntaxColor = Color.GREEN;
				width = 512 - 128;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "";
				if (outputNodes[0] != null) {
					data += meta.concat(" ").concat(outputNodes[0].parse() + "\n");
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Interface";
				outputNum = 1;
				isTextEditor = true;
				syntaxColor = Color.ORANGE;
				width = 128;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "";
				if (outputNodes[0] != null) {
					data += "interface " + meta + " " + outputNodes[0].parse() + "\n";
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Variable";
				outputNum = 1;
				isTextEditor = true;
				syntaxColor = Color.BLACK;
				width = 512 - 128;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = meta;
				if (outputNodes[0] != null) {
					data += " = " + outputNodes[0].parse();
				}
				return data.isBlank() || data.isEmpty() ? "" : data.concat(";\n");
			}
		},
		new NodeType() {
			{
				name = "Class";
				outputNum = 1;
				isTextEditor = true;
				syntaxColor = Color.BLACK;
				width = 128;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "class " + meta ;
				if (outputNodes[0] != null) {
					data += " " + outputNodes[0].parse();
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Split 2";
				outputNum = 2;
				isTextEditor = true;
				syntaxColor = Color.MAGENTA;
				width = 128;
				height = 128;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "";
				for (int i = 0; i < outputNum; i++) {
					if (outputNodes[i] != null) {
						data += "\n" + outputNodes[i].parse();
					}
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Split 4";
				outputNum = 4;
				isTextEditor = true;
				syntaxColor = Color.MAGENTA;
				width = 128;
				height = 256;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "";
				for (int i = 0; i < outputNum; i++) {
					if (outputNodes[i] != null) {
						data += "\n" + outputNodes[i].parse();
					}
				}
				return data;
			}
		},
		new NodeType() {
			{
				name = "Split 8";
				outputNum = 8;
				isTextEditor = true;
				syntaxColor = Color.MAGENTA;
				width = 128;
				height = 512;
			}

			public String parse(String meta, Node[] outputNodes) {
				String data = "";
				for (int i = 0; i < outputNum; i++) {
					if (outputNodes[i] != null) {
						data += "\n" + outputNodes[i].parse();
					}
				}
				return data;
			}
		},};

	public NodeType nodeTemplate = new NodeType() {
		{
			name = "Code";
			outputNum = 0;
			isTextEditor = true;
			syntaxColor = Color.CYAN;
			width = 512 - 128;
			height = 128 - 32;
		}

		public String parse(String meta, Node[] outputNodes) {
			return "\n" + meta + "\n";
		}
	};

	public static String block(String code) {
		return "{" + code + "}";
	}

	public NodeType defaultNode() {
		return nodeTemplate;
	}

	public NodeType[] getNodeTypes() {
		return nodeTypes;
	}

	public String getName() {
		return "Java";
	}

	@Override
	public String getExtension() {
		return "java";
	}

	@Override
	public String getComment() {
		return "//";
	}

	public LinkedList<Node> loadFromSource(String source) {
		LinkedList<Node> nodes = new LinkedList<>();

		return nodes;
	}

	public class TNode {

		public NodeType type;

	}

}
