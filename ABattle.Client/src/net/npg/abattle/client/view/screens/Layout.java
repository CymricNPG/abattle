package net.npg.abattle.client.view.screens;

/*
 * Screen is:
 * ^
 * |
 * |--->
 */
public enum Layout {

	// Common Buttons
	Back(Coor.MENU_X + 32 + 50 * 1, Coor.BASE_Y, 1), //
	Options(Coor.MENU_X + 32 + 50 * -3, 25, 1), //
	//
	// MENU
	Menu_A1(Coor.MENU_X + 50 * -4, Coor.MENU_Y, 1), //
	Menu_B(Coor.MENU_X + 50 * -3, Coor.MENU_Y - 32, 1), //
	Menu_A2(Coor.MENU_X + 50 * -2, Coor.MENU_Y, 1), //
	Menu_T1(Coor.MENU_X + 50 * -1, Coor.MENU_Y - 32, 1), //
	Menu_T2(Coor.MENU_X + 50 * 0, Coor.MENU_Y, 1), //
	Menu_L(Coor.MENU_X + 50 * 1, Coor.MENU_Y - 32, 1), //
	Menu_E(Coor.MENU_X + 50 * 2, Coor.MENU_Y, 1), //
	// ---------------
	Menu_Option(Coor.MENU_X + 32 + 50 * -4, Coor.BASE_Y - 32, 1), //
	Menu_Single(Coor.MENU_X + 32 + 50 * -3, Coor.BASE_Y, 1), //
	Menu_Cloud(Coor.MENU_X + 32 + 50 * -2, Coor.BASE_Y - 32, 1), //
	Menu_Local(Coor.MENU_X + 32 + 50 * -1, Coor.BASE_Y, 1), //
	Menu_Help(Coor.MENU_X + 32 + 50 * 0, Coor.BASE_Y - 32, 1), //
	Menu_Impr(Coor.MENU_X + 32 + 50 * 1, Coor.BASE_Y, 1), //
	// ----
	MENU_MBOX(Coor.MENU_X - 160, 10 + Coor.BASE_Y + 70, 1), //
	MENU_MTEXT(Coor.MENU_X - 160 + 20, 15 + Coor.BASE_Y + 70, 1), //
	// ----
	// Impressum
	IMPR_TEXT(Coor.MID_X, Coor.BASE_Y + 200, 1), //
	// ----
	// Game Parameter
	Game_Para_X(Coor.MID_X, Coor.MID_Y + 48 * 0, 1), //
	Game_Para_Y(Coor.MID_X, Coor.MID_Y + 48 * 1, 1), //
	Game_Para_PLAYER(Coor.MID_X, Coor.MID_Y + 48 * 2, 1), //
	Game_Para_START(Coor.MENU_X + 32 + 50 * -1, 25, 1), //
	// Multi
	MULTI_QUICK(Coor.MENU_X + 32 + 50 * -4, Coor.BASE_Y, 1), //
	MULTI_INVITE(Coor.MENU_X + 32 + 50 * -2, Coor.BASE_Y, 1), //
	MULTI_NEW(Coor.MENU_X + 32 + 50 * 0, Coor.BASE_Y, 1), //
	// ----
	// Waiting
	WAIT_START(100, 70, 1), //
	// ----
	// Local Screen (LAN Search Games)
	LOCAL_SCROLL(Coor.MID_X, Coor.BASE_Y + 200, 1), //
	LOCAL_SIZE(300, 200), //
	LOCAL_NEW(166, 25, 1), //

	// ----
	// options
	OPTION_SCROLL(-16, 200, 1), //
	OPTION_SIZE(BasicScreen.VIRTUAL_WIDTH, 500), //
	;

	private class Coor {
		private static final int MENU_X = BasicScreen.VIRTUAL_WIDTH / 2;
		private static final int MENU_Y = BasicScreen.VIRTUAL_HEIGHT - 70;
		private static final int MID_X = Coor.MENU_X + 32 + 50 * -4;
		private static final int MID_Y = MENU_Y - 64;
		private static final int BASE_Y = 64;
	}

	public float x = 0;
	public float y = 0;
	public float scale = 1;

	/** absolute constructor */
	private Layout(final double x, final double y) {
		this.x = (float) x;
		this.y = (float) y;
		this.scale = 1;
	}

	/** relative constructor to screen coordinates */
	private Layout(final double x, final double y, final double scale) {
		this.x = (float) (x - BasicScreen.XD);
		this.y = (float) (y - BasicScreen.YD);
		this.scale = (float) scale;
	}
}
