package simple.jersey.samples.config;

import com.sun.jersey.api.core.PackagesResourceConfig;

public class MyApplication extends PackagesResourceConfig {
    public MyApplication() {
        super("simple.jersey.samples");
    }
}
