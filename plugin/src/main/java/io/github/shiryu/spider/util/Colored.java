package io.github.shiryu.spider.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public final class Colored {

	@NotNull
	public Component component(@NotNull final String text){
		return MiniMessage.miniMessage().deserialize(text)
				.decoration(TextDecoration.ITALIC, false);
	}

	@NotNull
	public String pretty(@NotNull final Enum<?> enumeration){
		final String name = enumeration.name().toLowerCase(Locale.ENGLISH);
		final String[] parts = name.split("_");

		final StringBuilder sb = new StringBuilder();

		for (final String part : parts){
			if (part.isEmpty())
				continue;

			sb.append(Character.toUpperCase(part.charAt(0)))
					.append(part.substring(1))
					.append(" ");
		}

		return sb.toString().trim();
	}

	@NotNull
	public String vanilla(@NotNull final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

	@NotNull
	public String convert(@NotNull final String text, @NotNull final Function<String, String> function){
		return function.apply(Colored.convert(text));
	}

	@NotNull
	public String convert(@NotNull String message) {
		final Pattern pattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");

		Matcher matcher = pattern.matcher(message);

		while(matcher.find()){
			final ChatColor color = ChatColor.valueOf(matcher.group().substring(1, matcher.group().length() - 1));
			final String before = message.substring(0, matcher.start());
			final String after = message.substring(matcher.end());

			message = before + color + after;

			matcher = pattern.matcher(message);
		}

        return vanilla(message);
	}

	@NotNull
	public List<String> convert(@NotNull final List<String> list) { return list.stream().map(Colored::convert).collect(Collectors.toList());}

	@NotNull
	public List<String> convert(@NotNull final String... texts) { return convert(Arrays.asList(texts));}

	@NotNull
	public ListReplacer replaceAll(@NotNull final List<String> list) { return new ListReplacer(convert(list)); }

	@NotNull
	public String strip(@NotNull final String text) { return ChatColor.stripColor(text); }

	public class ListReplacer{

		private List<String> list;

		public ListReplacer(@NotNull final List<String> list){
			this.list = list;
		}

		@NotNull
		public ListReplacer replaceAll(@NotNull final String regex, @NotNull final String replacement){
			this.list = list.stream().map(l -> l.replaceAll(regex, replacement)).collect(Collectors.toList());

			return this;
		}

		@NotNull
		public ListReplacer replaceIf(@NotNull final String regex, @NotNull final String replacement, @NotNull final Predicate<String> predicate){
			if (predicate.test(regex))
				return this.replaceAll(regex, replacement);

			return this;
		}

		@NotNull
		public ListReplacer replaceFunctional(@NotNull final String regex, @NotNull final Function<String, String> function){
			this.list = list.stream().map(l -> l.replaceAll(regex, function.apply(l))).collect(Collectors.toList());

			return this;
		}

		@NotNull
		public List<String> build(){
			return this.list;
		}
	}

}