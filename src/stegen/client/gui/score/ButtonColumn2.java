package stegen.client.gui.score;

import com.google.gwt.cell.client.*;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.*;
import com.google.gwt.user.cellview.client.*;

public class ButtonColumn2 extends Column<ScoreTableRow, String> {
	private final String buttonText;

	public ButtonColumn2(String buttonText) {
		super(new ButtonCell());
		this.buttonText = buttonText;
	}

	@Override
	public String getValue(ScoreTableRow cell) {
		return buttonText;
	}

	@Override
	public void render(Context context, ScoreTableRow cell, SafeHtmlBuilder sb) {
		dontDisplayButtonsForCurrentUser(context, cell, sb);
	}

	private void dontDisplayButtonsForCurrentUser(Context context, ScoreTableRow cell, SafeHtmlBuilder sb) {
		if (!cell.currentUser) {
			super.render(context, cell, sb);
		}
	}
}
