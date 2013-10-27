package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import util.textToSpeech;

public class SelectionTextPane extends JTextPane {
	
	//KEY BINDINGS: w to advance to the next word
	//				s to advance to the next sentance

	private String words[];
	private String sentances[];
	private int cursor;
	private DefaultHighlighter.DefaultHighlightPainter highlightPainter;

	public SelectionTextPane(String text) {
		
		//TODO: We need to tell the user instructions (audio instructions) on how to advance in the help.
		this.setText(text);
		this.setEditable(false);
		setKeyBindings();
		cursor = 0;
		words = getText().split(" ");
		sentances = getText().split("[.!?]");
		highlightPainter = new DefaultHighlightPainter(Color.RED);
	}
	
	//sets up key bindings for advancing through the string.
	private void setKeyBindings() {
		getInputMap().put(KeyStroke.getKeyStroke('w'), "moveWordRight");
		getActionMap().put("moveWordRight", new SelectNextWordAction());

		getInputMap().put(KeyStroke.getKeyStroke('s'), "moveSentanceRight");
		getActionMap().put("moveSentanceRight", new SelectNextSentanceAction());
	}

	private class SelectNextWordAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			String string = SelectionTextPane.this.getText();
			int endWord = string.indexOf(' ', cursor);
			if (endWord != -1) {
				SelectionTextPane.this.getHighlighter().removeAllHighlights();
				try {
					SelectionTextPane.this.getHighlighter().addHighlight(
							cursor, endWord, highlightPainter);
					cursor = endWord + 1;
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				SelectionTextPane.this.getHighlighter().removeAllHighlights();
				try {
					SelectionTextPane.this.getHighlighter().addHighlight(
							cursor, string.length(), highlightPainter);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	private class SelectNextSentanceAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			String string = SelectionTextPane.this.getText();
			int indexPeriod = string.indexOf('.', cursor);
			int indexExclaimation = string.indexOf('!', cursor);
			int indexQuestionMark = string.indexOf('?', cursor);
			if (indexPeriod == -1 && indexExclaimation == -1
					&& indexQuestionMark == -1) {
				SelectionTextPane.this.getHighlighter().removeAllHighlights();
				try {
					SelectionTextPane.this.getHighlighter().addHighlight(
							cursor, string.length(), highlightPainter);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else {
				int[] indices = new int[] { indexPeriod, indexExclaimation,
						indexQuestionMark };
				int endSentance = min(indices);
				SelectionTextPane.this.getHighlighter().removeAllHighlights();
				try {
					SelectionTextPane.this.getHighlighter().addHighlight(
							cursor, endSentance, highlightPainter);
					cursor = endSentance + 1;
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public static int min(int[] values) {
		int min = Integer.MAX_VALUE;
		for (int value : values) {
			if (value < min && value >= 0)
				min = value;
		}
		return min;
	}
}