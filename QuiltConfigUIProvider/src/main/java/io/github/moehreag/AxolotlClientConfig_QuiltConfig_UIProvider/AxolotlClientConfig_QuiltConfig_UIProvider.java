package io.github.moehreag.AxolotlClientConfig_QuiltConfig_UIProvider;

import io.github.axolotlclient.AxolotlClientConfig.AxolotlClientConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.common.ConfigManager;
import io.github.moehreag.AxolotlClientConfig_Quilt_Config.QuiltConfigConverter;
import org.quiltmc.config.api.Configs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class AxolotlClientConfig_QuiltConfig_UIProvider implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		Configs.getAll().forEach(c -> AxolotlClientConfigManager.getInstance().registerConfig(c.family(), new QuiltConfigConverter(c), new ConfigManager() {
			@Override
			public void save() {}

			@Override
			public void load() {}

			@Override
			public void loadDefaults() {}
		}));
	}
}
