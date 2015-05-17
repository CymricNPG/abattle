/**
 *
 */
package net.npg.abattle.client.dependent;

/**
 * @author cymric
 *
 */
public interface RequestHandler {
	void confirm(ConfirmInterface confirmInterface);

	void showHTMLView(String text, ReturnControl switchBackFunction);
}
