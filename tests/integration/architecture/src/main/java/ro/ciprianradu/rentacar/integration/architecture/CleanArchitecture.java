package ro.ciprianradu.rentacar.integration.architecture;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;
import static com.tngtech.archunit.thirdparty.com.google.common.collect.Lists.newArrayList;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.thirdparty.com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an {@link ArchRule} for Clean Architecture. It is inspired from {@link
 * com.tngtech.archunit.library.Architectures.OnionArchitecture}.
 */
public final class CleanArchitecture implements ArchRule {

    private static final String ENTITIES_LAYER = "entities";
    private static final String USE_CASES_LAYER = "use cases";
    private static final String INTERFACE_ADAPTER_LAYER = "interface adapter";
    private static final String EXTERNAL_INTERFACE_LAYER = "external interface";

    private String[] entitiesPackageIdentifiers = new String[0];
    private String[] useCasesPackageIdentifiers = new String[0];
    private Map<String, String[]> interfaceAdapterPackageIdentifiers = new LinkedHashMap<>();
    private Map<String, String[]> externalInterfacePackageIdentifiers = new LinkedHashMap<>();

    private final Optional<String> overriddenDescription;

    CleanArchitecture() {
        overriddenDescription = Optional.absent();
    }

    private CleanArchitecture(String[] entitiesPackageIdentifiers,
        String[] useCasesPackageIdentifiers,
        Map<String, String[]> interfaceAdapterPackageIdentifiers,
        Map<String, String[]> externalInterfacePackageIdentifiers,
        Optional<String> overriddenDescription) {
        this.entitiesPackageIdentifiers = entitiesPackageIdentifiers;
        this.useCasesPackageIdentifiers = useCasesPackageIdentifiers;
        this.interfaceAdapterPackageIdentifiers = interfaceAdapterPackageIdentifiers;
        this.externalInterfacePackageIdentifiers = externalInterfacePackageIdentifiers;
        this.overriddenDescription = overriddenDescription;
    }

    @PublicAPI(usage = ACCESS)
    public CleanArchitecture entities(String... packageIdentifiers) {
        entitiesPackageIdentifiers = packageIdentifiers;
        return this;
    }

    @PublicAPI(usage = ACCESS)
    public CleanArchitecture useCases(String... packageIdentifiers) {
        useCasesPackageIdentifiers = packageIdentifiers;
        return this;
    }

    @PublicAPI(usage = ACCESS)
    public CleanArchitecture interfaceAdapter(String name, String... packageIdentifiers) {
        interfaceAdapterPackageIdentifiers.put(name, packageIdentifiers);
        return this;
    }

    @PublicAPI(usage = ACCESS)
    public CleanArchitecture externalInterface(String name, String... packageIdentifiers) {
        externalInterfacePackageIdentifiers.put(name, packageIdentifiers);
        return this;
    }

    private LayeredArchitecture layeredArchitectureDelegate() {
        LayeredArchitecture layeredArchitectureDelegate = Architectures.layeredArchitecture()
            .layer(ENTITIES_LAYER).definedBy(entitiesPackageIdentifiers) //
            .layer(USE_CASES_LAYER).definedBy(useCasesPackageIdentifiers) //
            .layer(INTERFACE_ADAPTER_LAYER)
            .definedBy(concatenateAll(interfaceAdapterPackageIdentifiers.values())) //
            .layer(EXTERNAL_INTERFACE_LAYER)
            .definedBy(concatenateAll(externalInterfacePackageIdentifiers.values())) //
            .whereLayer(ENTITIES_LAYER)
            .mayOnlyBeAccessedByLayers(USE_CASES_LAYER, INTERFACE_ADAPTER_LAYER) //
            .whereLayer(USE_CASES_LAYER)
            .mayOnlyBeAccessedByLayers(INTERFACE_ADAPTER_LAYER, EXTERNAL_INTERFACE_LAYER) //
            .whereLayer(INTERFACE_ADAPTER_LAYER)
            .mayOnlyBeAccessedByLayers(EXTERNAL_INTERFACE_LAYER);

        for (Map.Entry<String, String[]> adapter : externalInterfacePackageIdentifiers.entrySet()) {
            String adapterLayer = getAdapterLayer(adapter.getKey());
            layeredArchitectureDelegate = layeredArchitectureDelegate.layer(adapterLayer)
                .definedBy(adapter.getValue()).whereLayer(adapterLayer)
                .mayNotBeAccessedByAnyLayer();
        }
        return layeredArchitectureDelegate.as(getDescription());
    }

    private String[] concatenateAll(Collection<String[]> arrays) {
        List<String> resultList = new ArrayList<>();
        for (String[] array : arrays) {
            resultList.addAll(Arrays.asList(array));
        }
        return resultList.toArray(new String[0]);
    }

    private String getAdapterLayer(String name) {
        return String.format("%s %s", name, INTERFACE_ADAPTER_LAYER);
    }

    @Override
    public void check(JavaClasses classes) {
        layeredArchitectureDelegate().check(classes);
    }

    @Override
    public ArchRule because(String reason) {
        return ArchRule.Factory.withBecause(this, reason);
    }

    @Override
    public CleanArchitecture as(String newDescription) {
        return new CleanArchitecture(entitiesPackageIdentifiers, useCasesPackageIdentifiers,
            interfaceAdapterPackageIdentifiers, externalInterfacePackageIdentifiers,
            Optional.of(newDescription));
    }

    @Override
    public EvaluationResult evaluate(JavaClasses classes) {
        return layeredArchitectureDelegate().evaluate(classes);
    }

    @Override
    public String getDescription() {
        if (overriddenDescription.isPresent()) {
            return overriddenDescription.get();
        }

        List<String> lines = newArrayList("Clean Architecture consisting of");
        if (entitiesPackageIdentifiers.length > 0) {
            lines.add(String
                .format("entities ('%s')", Joiner.on("', '").join(entitiesPackageIdentifiers)));
        }
        if (useCasesPackageIdentifiers.length > 0) {
            lines.add(String
                .format("use cases ('%s')", Joiner.on("', '").join(useCasesPackageIdentifiers)));
        }
        for (Map.Entry<String, String[]> adapter : interfaceAdapterPackageIdentifiers.entrySet()) {
            lines.add(String.format("interface adapter '%s' ('%s')", adapter.getKey(),
                Joiner.on("', '").join(adapter.getValue())));
        }
        for (Map.Entry<String, String[]> adapter : externalInterfacePackageIdentifiers.entrySet()) {
            lines.add(String.format("external interface '%s' ('%s')", adapter.getKey(),
                Joiner.on("', '").join(adapter.getValue())));
        }
        return Joiner.on(System.lineSeparator()).join(lines);
    }
}