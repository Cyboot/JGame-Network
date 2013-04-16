package de.timweb.networkgame.common.log;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ServerLog implements Log {
	private int				level	= Log.ALL;

	public static JTextPane	textArea;

	public ServerLog(JTextPane textArea2) {
		textArea = textArea2;
	}

	@Override
	public void debug(String msg) {
		log(DEBUG, msg);
	}

	@Override
	public void info(String msg) {
		log(INFO, msg);
	}

	@Override
	public void warn(String msg) {
		log(WARN, msg);
	}

	@Override
	public void error(String msg) {
		log(ERROR, msg);
	}

	private void log(int level, String msg) {
		if (this.level > level)
			return;

		Color color = null;

		switch (level) {
		case INFO:
			color = Color.gray;
			break;
		case WARN:
			color = Color.orange;
			break;
		case ERROR:
			color = Color.red;
			break;
		default:
			color = Color.BLACK;
			break;
		}

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

		DateFormat df = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss.SSS with
															// Millis
		String date = df.format(Calendar.getInstance().getTime());

		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.setCharacterAttributes(aset, false);
		textArea.replaceSelection(date + " - " + msg + "\n");
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}
}
