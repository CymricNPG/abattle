package net.npg.abattle.common.configuration

@PropertyStored
class NetworkConfigurationData implements SaveableData{
	@PropertyName(name="network.searchTimeout", defaultValue="2000")
	@DirtyAccessors int searchTimeout

	@PropertyName(name="network.connectTimeout", defaultValue="5000")
	@DirtyAccessors int connectTimeout

	@PropertyName(name="network.port", defaultValue="54666")
	@DirtyAccessors int port

}