/*
 * OpenAPI Petstore
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.openapitools.controller;

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.convert.format.Format;
import reactor.core.publisher.Mono;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import java.time.OffsetDateTime;
import org.openapitools.model.User;
import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Generated(value="org.openapitools.codegen.languages.JavaMicronautServerCodegen", comments = "Generator version: 7.8.0-SNAPSHOT")
@Controller
@Tag(name = "User", description = "The User API")
public class UserController {
    /**
     * Create user
     * This can only be done by the logged in user.
     *
     * @param user Created user object (required)
     */
    @Operation(
        operationId = "createUser",
        summary = "Create user",
        responses = {
            @ApiResponse(responseCode = "0", description = "successful operation")
        },
        parameters = {
            @Parameter(name = "user", description = "Created user object", required = true)
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Post(uri="/user")
    @Produces(value = {})
    @Consumes(value = {"application/json"})
    public Mono<Void> createUser(
        @Body @NotNull @Valid User user
    ) {
        // TODO implement createUser();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Creates list of users with given input array
     * 
     *
     * @param user List of user object (required)
     */
    @Operation(
        operationId = "createUsersWithArrayInput",
        summary = "Creates list of users with given input array",
        responses = {
            @ApiResponse(responseCode = "0", description = "successful operation")
        },
        parameters = {
            @Parameter(name = "user", description = "List of user object", required = true)
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Post(uri="/user/createWithArray")
    @Produces(value = {})
    @Consumes(value = {"application/json"})
    public Mono<Void> createUsersWithArrayInput(
        @Body @NotNull List<@Valid User> user
    ) {
        // TODO implement createUsersWithArrayInput();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Creates list of users with given input array
     * 
     *
     * @param user List of user object (required)
     */
    @Operation(
        operationId = "createUsersWithListInput",
        summary = "Creates list of users with given input array",
        responses = {
            @ApiResponse(responseCode = "0", description = "successful operation")
        },
        parameters = {
            @Parameter(name = "user", description = "List of user object", required = true)
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Post(uri="/user/createWithList")
    @Produces(value = {})
    @Consumes(value = {"application/json"})
    public Mono<Void> createUsersWithListInput(
        @Body @NotNull List<@Valid User> user
    ) {
        // TODO implement createUsersWithListInput();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Delete user
     * This can only be done by the logged in user.
     *
     * @param username The name that needs to be deleted (required)
     */
    @Operation(
        operationId = "deleteUser",
        summary = "Delete user",
        responses = {
            @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
        },
        parameters = {
            @Parameter(name = "username", description = "The name that needs to be deleted", required = true)
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Delete(uri="/user/{username}")
    @Produces(value = {})
    public Mono<Void> deleteUser(
        @PathVariable(value="username") @NotNull String username
    ) {
        // TODO implement deleteUser();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Get user by user name
     * 
     *
     * @param username The name that needs to be fetched. Use user1 for testing. (required)
     * @return User
     */
    @Operation(
        operationId = "getUserByName",
        summary = "Get user by user name",
        responses = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {
                @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class)),
                @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
        },
        parameters = {
            @Parameter(name = "username", description = "The name that needs to be fetched. Use user1 for testing.", required = true)
        }
    )
    @Get(uri="/user/{username}")
    @Produces(value = {"application/xml", "application/json"})
    public Mono<User> getUserByName(
        @PathVariable(value="username") @NotNull String username
    ) {
        // TODO implement getUserByName();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Logs user into the system
     * 
     *
     * @param username The user name for login (required)
     * @param password The password for login in clear text (required)
     * @return String
     */
    @Operation(
        operationId = "loginUser",
        summary = "Logs user into the system",
        responses = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {
                @Content(mediaType = "application/xml", schema = @Schema(implementation = String.class)),
                @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid username/password supplied")
        },
        parameters = {
            @Parameter(name = "username", description = "The user name for login", required = true),
            @Parameter(name = "password", description = "The password for login in clear text", required = true)
        }
    )
    @Get(uri="/user/login")
    @Produces(value = {"application/xml", "application/json"})
    public Mono<String> loginUser(
        @QueryValue(value="username") @NotNull @Pattern(regexp="^[a-zA-Z0-9]+[a-zA-Z0-9\\.\\-_]*[a-zA-Z0-9]+$") String username, 
        @QueryValue(value="password") @NotNull String password
    ) {
        // TODO implement loginUser();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Logs out current logged in user session
     * 
     *
     */
    @Operation(
        operationId = "logoutUser",
        summary = "Logs out current logged in user session",
        responses = {
            @ApiResponse(responseCode = "0", description = "successful operation")
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Get(uri="/user/logout")
    @Produces(value = {})
    public Mono<Void> logoutUser() {
        // TODO implement logoutUser();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }


    /**
     * Updated user
     * This can only be done by the logged in user.
     *
     * @param username name that need to be deleted (required)
     * @param user Updated user object (required)
     */
    @Operation(
        operationId = "updateUser",
        summary = "Updated user",
        responses = {
            @ApiResponse(responseCode = "400", description = "Invalid user supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
        },
        parameters = {
            @Parameter(name = "username", description = "name that need to be deleted", required = true),
            @Parameter(name = "user", description = "Updated user object", required = true)
        },
        security = {
            @SecurityRequirement(name = "api_key")
        }
    )
    @Put(uri="/user/{username}")
    @Produces(value = {})
    @Consumes(value = {"application/json"})
    public Mono<Void> updateUser(
        @PathVariable(value="username") @NotNull String username, 
        @Body @NotNull @Valid User user
    ) {
        // TODO implement updateUser();
        return Mono.error(new HttpStatusException(HttpStatus.NOT_IMPLEMENTED, null));
    }

}
