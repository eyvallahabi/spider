package io.github.shiryu.spider.integration.economy;

import io.github.shiryu.spider.integration.economy.impl.VaultEconomyProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum EconomyType {

    VAULT("Vault", VaultEconomyProvider.class);

    private final String name;
    private final Class<? extends EconomyProvider> providerClass;

    @NotNull
    public EconomyProvider create(){
        final EconomyProvider provider;

        try {
            provider = this.providerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create economy provider", e);
        }

        provider.enable();

        return provider;
    }

}
