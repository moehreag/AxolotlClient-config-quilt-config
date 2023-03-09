package io.github.moehreag.AxolotlClientConfig_Quilt_Config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import io.github.axolotlclient.AxolotlClientConfig.common.ConfigHolder;
import io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory;
import io.github.axolotlclient.AxolotlClientConfig.options.*;
import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.Constraint;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueTreeNode;

public class QuiltConfigConverter extends ConfigHolder {

	private final List<OptionCategory> list;

	public QuiltConfigConverter(Config config) {
		list = Collections.singletonList(parseNodes(config.nodes(), config));
	}

	@Override
	public List<OptionCategory> getCategories() {
		return list;
	}

	@SuppressWarnings("unchecked")
	private OptionCategory parseNodes(Iterable<ValueTreeNode> nodes, Config config){
		OptionCategory category;
		if(nodes instanceof ValueTreeNode.Section n) {
			String name = config.id() +"."+ config.family() +"."+ n.key().toString();
			category = new io.github.axolotlclient.AxolotlClientConfig.options.OptionCategory(name){
				@Override
				public String getTooltip() {
					return super.getTooltip().replaceAll("\n", "<br>");
				}
			};
		} else {
			String name = config.id() +"."+ config.family() +".title";
			category = new io.github.axolotlclient.AxolotlClientConfig.options.OptionCategory(name){
				@Override
				public String getTooltip() {
					return super.getTooltip().replaceAll("\n", "<br>");
				}
			};
		}

		nodes.forEach(node -> {
			if(node instanceof TrackedValue<?> t){
				Class<?> c = t.value().getClass();

				String name = config.id() +"."+ config.family() +"."+ t.key().toString();

				if(c == Integer.TYPE || c == Integer.class){
					Range r = parseRange(t.constraints());
					IntegerOption o = new IntegerOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(Integer) t.getDefaultValue(), (Integer) r.min, (Integer) r.max){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((Integer) t.value());
					category.add(o);
				} else if(c == Long.TYPE || c == Long.class){
					Range r = parseRange(t.constraints());
					DoubleOption o = new DoubleOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(Double) t.getDefaultValue(), (Double) r.min, (Double) r.max){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((Double) t.value());
					category.add(o);
				} else if(c == Float.TYPE || c == Float.class){
					Range r = parseRange(t.constraints());
					FloatOption o = new FloatOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(Float) t.getDefaultValue(), (Float) r.min, (Float) r.max){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((Float) t.value());
					category.add(o);
				} else if (c ==	Double.TYPE || c == Double.class){
					Range r = parseRange(t.constraints());
					DoubleOption o = new DoubleOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(Double) t.getDefaultValue(), (Double) r.min, (Double) r.max){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((Double) t.value());
					category.add(o);
				} else if (c == Boolean.TYPE || c == Boolean.class){
					BooleanOption o = new BooleanOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(Boolean) t.getDefaultValue()){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((Boolean) t.value());
					category.add(o);
				} else if (c ==String.class){
					StringOption o = new StringOption(name, value -> ((TrackedValue<Object>)t).setValue(value, true),
							(String) t.getDefaultValue()){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set((String) t.value());
					category.add(o);
				} else if (c.isEnum()){
					EnumOption o = new EnumOption(name, value -> {
						try {
							((TrackedValue<Object>)t).setValue(c.getMethod("valueOf", String.class).invoke(config, value), true);
						} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
							throw new RuntimeException(e);
						}},
							c.getEnumConstants(), t.getDefaultValue().toString()){
						@Override
						public String getTooltip() {
							return super.getTooltip().replaceAll("\n", "<br>");
						}
					};
					o.set(t.value().toString());
					category.add(o);
				}
			} else if (node instanceof ValueTreeNode.Section n){
				category.add(parseNodes(n, config));
			}
		});

		return category;
	}

	private Range parseRange(Iterable<? extends Constraint<?>> constraints){
		Range range = new Range(null, null);
		constraints.forEach(constraint -> {
			if(constraint instanceof Constraint.Range<?>){
				try {
					Field minF = constraint.getClass().getDeclaredField("min");
					Field maxF = constraint.getClass().getDeclaredField("max");
					minF.setAccessible(true);
					maxF.setAccessible(true);
					range.min = minF.get(constraint);
					range.max = maxF.get(constraint);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});
		return range;
	}

	private static class Range {
		private Object min;
		private Object max;

		private Range(Object min, Object max) {
			this.min = min;
			this.max = max;
		}
	}
}
