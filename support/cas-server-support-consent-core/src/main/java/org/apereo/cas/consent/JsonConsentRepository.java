package org.apereo.cas.consent;

import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.io.FileWatcherService;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.hjson.JsonValue;
import org.jooq.lambda.Unchecked;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This is {@link JsonConsentRepository}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
public class JsonConsentRepository extends BaseConsentRepository {
    private static final long serialVersionUID = -402728417464783825L;

    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder()
        .defaultTypingEnabled(false).build().toObjectMapper();

    private final Resource jsonResource;

    private FileWatcherService watcherService;

    
    public JsonConsentRepository(final Resource resource) throws Exception {
        this.jsonResource = resource;
        setConsentDecisions(readDecisionsFromJsonResource());
        if (ResourceUtils.isFile(this.jsonResource)) {
            this.watcherService = new FileWatcherService(resource.getFile(),
                Unchecked.consumer(file -> setConsentDecisions(readDecisionsFromJsonResource())));
            this.watcherService.start(getClass().getSimpleName());
        }
    }

    @Override
    public ConsentDecision storeConsentDecision(final ConsentDecision decision) {
        val result = super.storeConsentDecision(decision);
        writeAccountToJsonResource();
        return result;
    }

    @Override
    public boolean deleteConsentDecision(final long decisionId, final String principal) {
        val result = super.deleteConsentDecision(decisionId, principal);
        writeAccountToJsonResource();
        return result;
    }

    @Override
    public boolean deleteConsentDecisions(final String principal) {
        val result = super.deleteConsentDecisions(principal);
        writeAccountToJsonResource();
        return result;
    }

    @SneakyThrows
    private Set<ConsentDecision> readDecisionsFromJsonResource() {
        if (ResourceUtils.doesResourceExist(jsonResource)) {
            try (val reader = new InputStreamReader(jsonResource.getInputStream(), StandardCharsets.UTF_8)) {
                val personList = new TypeReference<Set<ConsentDecision>>() {
                };
                return MAPPER.readValue(JsonValue.readHjson(reader).toString(), personList);
            }
        }
        return new LinkedHashSet<>(0);
    }

    @SneakyThrows
    private boolean writeAccountToJsonResource() {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(this.jsonResource.getFile(), getConsentDecisions());
        readDecisionsFromJsonResource();
        return true;
    }
}
