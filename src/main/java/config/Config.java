package config;

public class Config {
    public static String getBaseUrl() {

         String env = System.getProperty("env", System.getenv().getOrDefault("ENV", "qa")).toLowerCase();

        switch (env) {
            case "dev":
                return "http://dev-api.bookstore.com";
            case "qa":
                return "http://localhost:8000";
            case "prod":
                return "https://api.bookstore.com";
            default:
                throw new IllegalArgumentException("Unknown environment: " + env);
        }
        
    }
}
