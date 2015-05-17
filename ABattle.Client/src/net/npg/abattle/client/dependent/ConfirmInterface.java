/**
 *
 */
package net.npg.abattle.client.dependent;

/**
 * @author cymric
 *
 */
public interface ConfirmInterface {
	void yes();

	void no();

	String headerText();

	String questionText();

	String yesText();

	String noText();
}
