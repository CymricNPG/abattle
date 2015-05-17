package net.npg.abattle.client.view.screens;

public enum Icons {

	Join, JoinActivate, A, B, T, L, E, Single, Help, Local, New, Cloud, Options, Message, Slider, Knob, Start, //
	Back, Impressum, Win, Loose, Cursor, Textarea, on, off, Reset, Invites, Quick;

	public final String filename;

	private Icons() {
		this.filename = buildFilename(this.toString());
	}

	private static String buildFilename(final String iconName) {
		return "icons" + "/" + iconName + ".png";
	}
}
