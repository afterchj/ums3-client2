package com.utils;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.AbstractXmlWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class MyPrettyPrintWriter extends AbstractXmlWriter {

	public static int XML_QUIRKS = -1;
	public static int XML_1_0 = 0;
	public static int XML_1_1 = 1;

	private final QuickWriter writer;
	private final FastStack elementStack = new FastStack(16);
	private final char[] lineIndenter;
	private final int mode;

	private boolean tagInProgress;
	protected int depth;
	private boolean readyForNewLine;
	private boolean tagIsEmpty;
	private String newLine;

	private static final char[] NULL = "&#x0;".toCharArray();
	private static final char[] AMP = "&amp;".toCharArray();
	private static final char[] LT = "&lt;".toCharArray();
	private static final char[] GT = "&gt;".toCharArray();
	private static final char[] CR = "&#xd;".toCharArray();
	private static final char[] QUOT = "&quot;".toCharArray();
	private static final char[] APOS = "&apos;".toCharArray();
	private static final char[] CLOSE = "</".toCharArray();

	@SuppressWarnings("deprecation")
	private MyPrettyPrintWriter(Writer writer, int mode, char[] lineIndenter,
			NameCoder nameCoder, String newLine) {
		super(nameCoder);
		this.writer = new QuickWriter(writer);
		this.lineIndenter = lineIndenter;
		this.newLine = newLine;
		this.mode = mode;
		if (mode < XML_QUIRKS || mode > XML_1_1) {
			throw new IllegalArgumentException("Not a valid XML mode");
		}
	}

	/**
	 * @since 1.2
	 * @deprecated As of 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, char[] lineIndenter,
			String newLine, XmlFriendlyReplacer replacer) {
		this(writer, XML_QUIRKS, lineIndenter, replacer, newLine);
	}

	/**
	 * @since 1.4
	 */
	public MyPrettyPrintWriter(Writer writer, int mode, char[] lineIndenter,
			NameCoder nameCoder) {
		this(writer, mode, lineIndenter, nameCoder, "\n");
	}

	/**
	 * @since 1.3
	 * @deprecated As of 1.4 use
	 *             {@link MyPrettyPrintWriter#MyPrettyPrintWriter(Writer, int, char[], NameCoder)}
	 *             instead
	 */
	public MyPrettyPrintWriter(Writer writer, int mode, char[] lineIndenter,
			XmlFriendlyReplacer replacer) {
		this(writer, mode, lineIndenter, replacer, "\n");
	}

	/**
	 * @deprecated As of 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, char[] lineIndenter,
			String newLine) {
		this(writer, lineIndenter, newLine, new XmlFriendlyReplacer());
	}

	/**
	 * @since 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, int mode, char[] lineIndenter) {
		this(writer, mode, lineIndenter, new XmlFriendlyNameCoder());
	}

	public MyPrettyPrintWriter(Writer writer, char[] lineIndenter) {
		this(writer, XML_QUIRKS, lineIndenter);
	}

	/**
	 * @deprecated As of 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, String lineIndenter,
			String newLine) {
		this(writer, lineIndenter.toCharArray(), newLine);
	}

	/**
	 * @since 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, int mode, String lineIndenter) {
		this(writer, mode, lineIndenter.toCharArray());
	}

	public MyPrettyPrintWriter(Writer writer, String lineIndenter) {
		this(writer, lineIndenter.toCharArray());
	}

	/**
	 * @since 1.4
	 */
	public MyPrettyPrintWriter(Writer writer, int mode, NameCoder nameCoder) {
		this(writer, mode, new char[] { ' ', ' ' }, nameCoder);
	}

	/**
	 * @since 1.3
	 * @deprecated As of 1.4 use
	 *             {@link MyPrettyPrintWriter#MyPrettyPrintWriter(Writer, int, NameCoder)}
	 *             instead
	 */
	public MyPrettyPrintWriter(Writer writer, int mode,
			XmlFriendlyReplacer replacer) {
		this(writer, mode, new char[] { ' ', ' ' }, replacer);
	}

	/**
	 * @since 1.4
	 */
	public MyPrettyPrintWriter(Writer writer, NameCoder nameCoder) {
		this(writer, XML_QUIRKS, new char[] { ' ', ' ' }, nameCoder, "\n");
	}

	/**
	 * @deprecated As of 1.4 use
	 *             {@link MyPrettyPrintWriter#MyPrettyPrintWriter(Writer, NameCoder)}
	 *             instead.
	 */
	public MyPrettyPrintWriter(Writer writer, XmlFriendlyReplacer replacer) {
		this(writer, new char[] { ' ', ' ' }, "\n", replacer);
	}

	/**
	 * @since 1.3
	 */
	public MyPrettyPrintWriter(Writer writer, int mode) {
		this(writer, mode, new char[] { ' ', ' ' });
	}

	public MyPrettyPrintWriter(Writer writer) {
		this(writer, new char[] { ' ', ' ' });
	}

	public void startNode(String name) {
		String escapedName = encodeNode(name);
		tagIsEmpty = false;
		finishTag();
		writer.write('<');
		writer.write(escapedName);
		elementStack.push(escapedName);
		tagInProgress = true;
		depth++;
		readyForNewLine = true;
		tagIsEmpty = true;
	}

	public void startNode(String name, Class clazz) {
		startNode(name);
	}

	public void setValue(String text) {
		readyForNewLine = false;
		tagIsEmpty = false;
		finishTag();

		writeText(writer, text);
	}

	public void addAttribute(String key, String value) {
		writer.write(' ');
		writer.write(encodeAttribute(key));
		writer.write('=');
		writer.write('\"');
		writeAttributeValue(writer, value);
		writer.write('\"');
	}

	protected void writeAttributeValue(QuickWriter writer, String text) {
		writeText(text, true);
	}

	protected void writeText(QuickWriter writer, String text) {
		writeText(text, false);
	}

	
	private void writeText(String text, boolean isAttribute) {
		int length = text.length();
		String CDATAPrefix = "<![CDATA[";
		if (!text.startsWith(CDATAPrefix)) {
			for (int i = 0; i < length; i++) {
				char c = text.charAt(i);
				switch (c) {
				case '\0':
					if (mode == XML_QUIRKS) {
						this.writer.write(NULL);
					} else {
						throw new StreamException(
								"Invalid character 0x0 in XML stream");
					}
					break;
				case '&':
					this.writer.write(AMP);
					break;
				case '<':
					this.writer.write(LT);
					break;
				case '>':
					this.writer.write(GT);
					break;
//				case '"':
//					this.writer.write(QUOT);
//					break;
				case '\'':
					this.writer.write(APOS);
					break;
				case '\r':
					this.writer.write(CR);
					break;
				case '\t':
				case '\n':
					if (!isAttribute) {
						this.writer.write(c);
						break;
					}
				default:
					if (Character.isDefined(c) && !Character.isISOControl(c)) {
						if (mode != XML_QUIRKS) {
							if (c > '\ud7ff' && c < '\ue000') {
								throw new StreamException(
										"Invalid character 0x"
												+ Integer.toHexString(c)
												+ " in XML stream");
							}
						}
						this.writer.write(c);
					} else {
						if (mode == XML_1_0) {
							if (c < 9 || c == '\u000b' || c == '\u000c'
									|| c == '\u000e'
									|| (c >= '\u000f' && c <= '\u001f')) {
								throw new StreamException(
										"Invalid character 0x"
												+ Integer.toHexString(c)
												+ " in XML 1.0 stream");
							}
						}
						if (mode != XML_QUIRKS) {
							if (c == '\ufffe' || c == '\uffff') {
								throw new StreamException(
										"Invalid character 0x"
												+ Integer.toHexString(c)
												+ " in XML stream");
							}
						}
						this.writer.write("&#x");
						this.writer.write(Integer.toHexString(c));
						this.writer.write(';');
					}
				}
			}
		} else {
			for (int i = 0; i < length; i++) {
				char c = text.charAt(i);
				this.writer.write(c);
			}
		}
	}
	
	public void endNode() {
		depth--;
		if (tagIsEmpty) {
			writer.write('/');
			readyForNewLine = false;
			finishTag();
			elementStack.popSilently();
		} else {
			finishTag();
			writer.write(CLOSE);
			writer.write((String) elementStack.pop());
			writer.write('>');
		}
		readyForNewLine = true;
		if (depth == 0) {
			writer.flush();
		}
	}

	private void finishTag() {
		if (tagInProgress) {
			writer.write('>');
		}
		tagInProgress = false;
		if (readyForNewLine) {
			endOfLine();
		}
		readyForNewLine = false;
		tagIsEmpty = false;
	}

	protected void endOfLine() {
		writer.write(getNewLine());
		for (int i = 0; i < depth; i++) {
			writer.write(lineIndenter);
		}
	}

	public void flush() {
		writer.flush();
	}

	public void close() {
		writer.close();
	}

	protected String getNewLine() {
		return newLine;
	}
}
