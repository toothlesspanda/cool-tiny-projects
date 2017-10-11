package presentation.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(ApplicationConfig.HREF)
public class ApplicationConfig extends Application {

    public static final String HREF = "rest";

}
