/**
 *
 */
package net.npg.abattle.common.model.client;

import net.npg.abattle.common.error.BaseException;

/**
 * @author Cymric
 * 
 */
public interface ClientIdRegister {

	/**
	 * @throws BaseException if login failed
	 */
	public void waitForClientId();

	public void setClientId(int id);

	public void setFailed(String message);

	ClientPlayer getLocalPlayer() throws BaseException;

	void resetClientPlayer();

	void setName(String string);

}
