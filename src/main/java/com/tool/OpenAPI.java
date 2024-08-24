package com.tool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(

		info = @Info(

				title = "Tool Management System", description = "A web application for managing tool configurations. It includes functionalities for creating, updating, deleting, and exporting tool configurations. The application uses a Spring Boot backend, MySQL database, and a frontend developed with HTML, CSS, and JavaScript.", version = "v1.0"

		)

)
public class OpenAPI {

}
