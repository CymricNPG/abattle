/**
 *
 */
package net.npg.abattle.communication.network.impl;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import net.npg.abattle.communication.CommunicationConstants;

/**
 * @author Spatzenegger
 * 
 */
public class AddressFinder {

	public static InetAddress getInetAddress() {
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				final NetworkInterface networkInterface = networkInterfaces.nextElement();
				final InetAddress inetAddress = getInterfaceAddress(networkInterface);
				if (inetAddress != null) {
					return inetAddress;
				}
			}
		} catch (final SocketException e) {
			CommunicationConstants.LOG.info("Ignored, try to use localhost:" + e.getMessage(), e);
		}
		try {
			return InetAddress.getLocalHost();
		} catch (final UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static InetAddress getInterfaceAddress(final InterfaceAddress interfaceAddress) {
		if (interfaceAddress.getBroadcast() != null && !interfaceAddress.getAddress().isAnyLocalAddress() && !interfaceAddress.getAddress().isLoopbackAddress()
				&& !interfaceAddress.getAddress().isLinkLocalAddress()) {
			CommunicationConstants.LOG.debug("Found:" + interfaceAddress.getAddress().toString() + " " + interfaceAddress.getBroadcast().toString());
			return interfaceAddress.getAddress();
		}
		return null;
	}

	private static InetAddress getInterfaceAddress(final NetworkInterface networkInterface) {
		final List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
		for (final InterfaceAddress interfaceAddress : interfaceAddresses) {
			final InetAddress inetAddress = getInterfaceAddress(interfaceAddress);
			if (inetAddress != null) {
				return inetAddress;
			}
		}
		return null;
	}
}
