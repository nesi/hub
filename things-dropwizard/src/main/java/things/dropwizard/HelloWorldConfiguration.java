package things.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by markus on 22/05/14.
 */
public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    private String defaultName = "Stranger";
    @NotEmpty
    private String template;

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }
}
