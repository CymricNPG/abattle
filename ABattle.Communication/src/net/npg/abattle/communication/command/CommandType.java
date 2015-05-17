package net.npg.abattle.communication.command;


public enum CommandType {
	TOSERVER, TOCLIENT;

	public CommandType inverse() {
		return TOSERVER == this ? TOCLIENT : TOSERVER;
	}
}
