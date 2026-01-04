package io.github.shiryu.spider.api.executable.parseable;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Parser {

    @NotNull
    public List<Parseable> parse(@NotNull final String raw){
        final List<String> tokens = tokenize(raw);

        final List<Parseable> result = Lists.newArrayList();

        boolean parsedTargeter = false;

        for (final String token : tokens){
            if (token.startsWith("@")){
                if (!parsedTargeter){
                    result.add(create(ParseableType.TARGETER, token));
                    parsedTargeter = true;
                }else{
                    result.add(create(ParseableType.TRIGGER, token));
                }
            }else{
                result.add(create(ParseableType.ACTION, token));
            }
        }

        return result;
    }

    @NotNull
    public Parseable create(@NotNull final ParseableType type, @NotNull final String token){
        switch (type){
            case ACTION ->{
                final int index = token.indexOf('{');
                final String name = token.substring(0, index);
                final Map<String, Object> args = parseArgs(token.substring(index));

                return new Parseable(ParseableType.ACTION, name, create(args));
            }
            case TRIGGER -> {
                if (token.contains("{")){
                    final int index = token.indexOf('{');
                    final String name = token.substring(0, index);
                    final Map<String, Object> args = parseArgs(token.substring(index));

                    return new Parseable(ParseableType.TRIGGER, name, create(args));
                } else {
                    return new Parseable(ParseableType.TRIGGER, token, Lists.newArrayList());
                }
            }
            case TARGETER ->{
                if (token.contains("{")){
                    final int index = token.indexOf('{');
                    final String name = token.substring(0, index);
                    final Map<String, Object> args = parseArgs(token.substring(index));

                    return new Parseable(ParseableType.TARGETER, name, create(args));
                } else {
                    return new Parseable(ParseableType.TARGETER, token, Lists.newArrayList());
                }
            }
        }

        throw new IllegalArgumentException("Unsupported parseable type: " + type);
    }

    @NotNull
    public List<ParseArgument> create(@NotNull final Map<String, Object> args){
        final List<ParseArgument> list = Lists.newArrayList();

        args.forEach((key, value) ->{
            if (value instanceof String text){
                list.add(new ParseArgument(key, text));
            }else if (value instanceof Map<?, ?> map){
                final List<ParseArgument> nested = Lists.newArrayList();

                map.forEach((x, y) ->{
                    if (!(x instanceof String k))
                        return;

                    if (!(y instanceof String v))
                        return;

                    nested.add(new ParseArgument(k, v));
                });

                list.add(new ParseArgument(key, "", nested));
            }
        });

        return list;
    }

    @NotNull
    public Map<String, Object> parseArgs(@NotNull String block) {
        Map<String, Object> map = new HashMap<>();

        if (block.startsWith("{"))
            block = block.substring(1);

        if (block.endsWith("}"))
            block = block.substring(0, block.length() - 1);

        final List<String> parts = splitTopLevelArgs(block);

        for (String part : parts) {
            part = part.trim();

            if (part.isEmpty())
                continue;

            int braceIndex = part.indexOf('{');

            if (braceIndex != -1) {
                String key = part.substring(0, braceIndex).trim();
                String nested = part.substring(braceIndex).trim();

                map.put(key, parseArgs(nested)); // recursion
                continue;
            }

            if (part.contains(":")) {
                String[] kv = part.split(":", 2);

                map.put(kv[0].trim(), kv[1].trim());
                continue;
            }

            map.put(part, true);
        }

        return map;
    }

    private List<String> splitTopLevelArgs(String s) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '{') {
                depth++;
                current.append(c);
                continue;
            }

            if (c == '}') {
                depth--;
                current.append(c);
                continue;
            }

            // split points: ; or space when not in block
            if ((c == ';' || c == ' ') && depth == 0) {
                if (!current.isEmpty()) {
                    parts.add(current.toString());
                    current.setLength(0);
                }
                continue;
            }

            current.append(c);
        }

        if (!current.isEmpty())
            parts.add(current.toString());

        return parts;
    }


    @NotNull
    public List<String> tokenize(@NotNull final String line){
        final List<String> tokens = Lists.newArrayList();

        final StringBuilder current = new StringBuilder();

        int braceDepth = 0;

        for (final char c : line.toCharArray()){
            if (c == '{') {
                braceDepth++;
                current.append(c);
            } else if (c == '}') {
                braceDepth--;
                current.append(c);
            } else if (c == ' ' && braceDepth == 0) {
                if (!current.isEmpty()) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }

        if (!current.isEmpty())
            tokens.add(current.toString());

        return tokens;
    }


}
