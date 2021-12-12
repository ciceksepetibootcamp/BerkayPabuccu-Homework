package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    RequestSpecification requestSpecification;
    private String baseUrl = "https://api.spotify.com";
    private String token = "BQBVyfRqIO8pM1tUA74QWsMhAD0qVNbDZZKfOGcec43JfF0C_w9eYmQcXjc0FK_lPghNMfj_dKPin7FEUYqCotmX57VtHjvtU6aDISZ6g6Xa3OUOW90vMk6Peede768fFbRjXED9xtpOwnrzFEQdvFOGp2c5usOcm8T1iy8cMpwR1TT6sTbXc05ZhVhCNap0HTz8mjOsywnX1Q";

    public RequestSpec() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + token)
                .setContentType("application/json")
                .setAccept("application/json")
                .build();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

}
