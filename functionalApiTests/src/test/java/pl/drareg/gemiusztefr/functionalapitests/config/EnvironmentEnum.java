package pl.drareg.gemiusztefr.functionalapitests.config;

public enum EnvironmentEnum {
    DEV("http://127.0.0.1:8181"), UAT("http://127.0.0.1:8282");

    private String host;

    EnvironmentEnum(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
