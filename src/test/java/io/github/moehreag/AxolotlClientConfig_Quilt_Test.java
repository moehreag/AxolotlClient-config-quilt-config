package io.github.moehreag;

import io.github.axolotlclient.AxolotlClientConfig.AxolotlClientConfigManager;
import io.github.moehreag.AxolotlClientConfig_Quilt_Config.QuiltConfigConverter;
import org.quiltmc.config.api.Configs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class AxolotlClientConfig_Quilt_Test implements ClientModInitializer {

	private static final String MODID = "axolotlclientconfig-quilt-configconverter-test";

	@Override
	public void onInitializeClient(ModContainer mod) {
		Configs.getAll().forEach(c -> AxolotlClientConfigManager.getInstance().registerConfig(c.family(), new QuiltConfigConverter(c)));
	}
}
