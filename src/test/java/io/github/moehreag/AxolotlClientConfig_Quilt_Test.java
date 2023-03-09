package io.github.moehreag;

import io.github.axolotlclient.AxolotlClientConfig.AxolotlClientConfigManager;
import io.github.ennuil.ok_zoomer.config.OkZoomerConfigManager;
import io.github.moehreag.AxolotlClientConfig_Quilt_Config.QuiltConfigConverter;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class AxolotlClientConfig_Quilt_Test implements ClientModInitializer {

	private static final String MODID = "axolotlclientconfig-quilt-configconverter-test";

	@Override
	public void onInitializeClient(ModContainer mod) {
		AxolotlClientConfigManager.getInstance().registerConfig(MODID, new QuiltConfigConverter(OkZoomerConfigManager.CONFIG));
	}
}
