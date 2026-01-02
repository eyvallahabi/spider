package io.github.shiryu.spider.api.executable.parseable;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

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
                final Map<String, String> args = parseArgs(token.substring(index));

                return new Parseable(ParseableType.ACTION, name, create(args));
            }
            case TRIGGER -> {
                if (token.contains("{")){
                    final int index = token.indexOf('{');
                    final String name = token.substring(0, index);
                    final Map<String, String> args = parseArgs(token.substring(index));

                    return new Parseable(ParseableType.TRIGGER, name, create(args));
                } else {
                    return new Parseable(ParseableType.TRIGGER, token, Lists.newArrayList());
                }
            }
            case TARGETER ->{
                if (token.contains("{")){
                    final int index = token.indexOf('{');
                    final String name = token.substring(0, index);
                    final Map<String, String> args = parseArgs(token.substring(index));

                    return new Parseable(ParseableType.TARGETER, name, create(args));
                } else {
                    return new Parseable(ParseableType.TARGETER, token, Lists.newArrayList());
                }
            }
        }

        throw new IllegalArgumentException("Unsupported parseable type: " + type);
    }

    @NotNull
    public List<ParseArgument> create(@NotNull final Map<String, String> map){
        final List<ParseArgument> args = Lists.newArrayList();

        for (final Map.Entry<String, String> entry : map.entrySet()){
            args.add(new ParseArgument(entry.getKey(), entry.getValue()));
        }

        return args;
    }

    @NotNull
    public Map<String, String> parseArgs(@NotNull String block){
        final Map<String, String> map = new HashMap<>();

        if (block.startsWith("{"))
            block = block.substring(1);

        if (block.endsWith("}"))
            block = block.substring(0, block.length() - 1);

        if (block.isBlank())
            return map;

        // hem "a=b;c=d" hem "a=b c=d" destekliyoruz
        String normalized = block.replace(";", " ");

        for (String entry : normalized.split(" ")) {
            if (!entry.contains(":")) continue;
            String[] kv = entry.split(":", 2);
            map.put(kv[0].trim(), kv[1].trim());
        }

        return map;
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
