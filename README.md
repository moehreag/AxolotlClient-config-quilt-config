# AxolotlClientConfig-Quilt-config

### Usage

```java
public class Example implements ClientModInitializer {

    private static final String MODID = "example_id";

    @Override
    public void onInitializeClient(ModContainer mod) {
        Config CONFIG = QuiltConfig.create("example_family", MODID, Config.class);
        AxolotlClientConfigManager.getInstance().registerConfig(MODID, new QuiltConfigConverter(CONFIG));
    }
}
```

You may also look at [AxolotlClientConfig_Quilt_Test.java](QuiltConfigUIProvider/src/main/java/io/github/moehreag/AxolotlClientConfig_QuiltConfig_UIProvider/AxolotlClientConfig_QuiltConfig_UIProvider.java)
for further usage details.


