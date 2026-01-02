package io.github.shiryu.spider.api.executable.parseable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ParseContext {

    @NotNull
    public static ParseContext from(@NotNull final String raw){
        return from(Parser.parse(raw));
    }

    @NotNull
    public static ParseContext from(@NotNull final List<Parseable> parseables){
        return new ParseContext(parseables);
    }

    private final List<Parseable> parseables;

    @NotNull
    public Parseable action(){
        return this.byType(ParseableType.ACTION)
                .orElseThrow(() -> new IllegalStateException("No action parseable found in context"));
    }

    @NotNull
    public Parseable targeter(){
        return this.byType(ParseableType.TARGETER)
                .orElseThrow(() -> new IllegalStateException("No targeter parseable found in context"));
    }

    @NotNull
    public Parseable trigger(){
        return this.byType(ParseableType.TRIGGER)
                .orElseThrow(() -> new IllegalStateException("No trigger parseable found in context"));
    }

    @NotNull
    public Optional<Parseable> byType(@NotNull final ParseableType type){
        return this.parseables
                .stream()
                .filter(parseable -> parseable.getType() == type)
                .findFirst();
    }
}
