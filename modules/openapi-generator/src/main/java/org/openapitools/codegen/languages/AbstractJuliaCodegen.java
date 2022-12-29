package org.openapitools.codegen.languages;

import org.openapitools.codegen.*;

import java.io.File;
import java.util.*;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.servers.Server;

import org.apache.commons.lang3.StringUtils;
import static org.openapitools.codegen.utils.StringUtils.camelize;

import org.openapitools.codegen.utils.CamelizeOption;
import org.openapitools.codegen.utils.ModelUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CaseFormat;

public abstract class AbstractJuliaCodegen extends DefaultCodegen {
    protected final Logger LOGGER = LoggerFactory.getLogger(AbstractJuliaCodegen.class);

    protected String srcPath = "src";
    protected String apiSrcPath = srcPath + "/apis/";
    protected String modelSrcPath = srcPath + "/models/";

    protected String apiDocPath = "docs/";
    protected String modelDocPath = "docs/";

    protected String packageName;
    protected Boolean exportModels;
    protected Boolean exportOperations;

    public AbstractJuliaCodegen() {
        super();

        reservedWords = new HashSet<String> (
            Arrays.asList(
                "if", "else", "elseif", "while", "for", "begin", "end", "quote",
                "try", "catch", "return", "local", "function", "macro", "ccall", "finally", "break", "continue",
                "global", "module", "using", "import", "export", "const", "let", "do", "baremodule",
                "Type", "Enum", "Any", "DataType", "Base"
            )
        );

        // Language Specific Primitives.  These types will not trigger imports by the client generator
        languageSpecificPrimitives = new HashSet<String>(
            Arrays.asList("Integer", "Int128", "Int64", "Int32", "Int16", "Int8", "UInt128", "UInt64", "UInt32", "UInt16", "UInt8", "Float64", "Float32", "Float16", "Char", "Vector", "Dict", "Vector{UInt8}", "Bool", "String", "Date", "DateTime", "ZonedDateTime", "Nothing", "Any")
        );

        typeMapping.clear();
        typeMapping.put("int", "Int64");
        typeMapping.put("integer", "Int64");
        typeMapping.put("long", "Int64");
        typeMapping.put("short", "Int32");
        typeMapping.put("byte", "UInt8");
        typeMapping.put("float", "Float32");
        typeMapping.put("double", "Float64");
        typeMapping.put("string", "String");
        typeMapping.put("char", "String");
        typeMapping.put("binary", "Vector{UInt8}");
        typeMapping.put("boolean", "Bool");
        typeMapping.put("number", "Float64");
        typeMapping.put("decimal", "Float64");
        typeMapping.put("array", "Vector");
        typeMapping.put("set", "Vector");
        typeMapping.put("map", "Dict");
        typeMapping.put("date", "Date");
        typeMapping.put("DateTime", "ZonedDateTime");
        typeMapping.put("File", "String");
        typeMapping.put("file", "String");
        typeMapping.put("UUID", "String");
        typeMapping.put("URI", "String");
        typeMapping.put("ByteArray", "Vector{UInt8}");
        typeMapping.put("object", "Any");
        typeMapping.put("Object", "Any");
        typeMapping.put("AnyType", "Any");
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setExportModels(Boolean exportModels) {
        this.exportModels = exportModels;
    }

    public void setExportOperations(Boolean exportOperations) {
        this.exportOperations = exportOperations;
    }

    protected static String dropDots(String str) {
        return str.replaceAll("\\.", "_");
    }

    /**
     * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
     * those terms here.  This logic is only called if a variable matches the reseved words
     * 
     * @return the escaped term
     */
    @Override
    public String escapeReservedWord(String name) {
        if (reservedWords.contains(name)) {
            return "__" + name + "__";  // add underscores to reserved words, and also to obscure it to lessen chances of clashing with any other names
        } else {
            return name;
        }
    }

    /**
     * Location to write model files.
     */
    @Override
    public String modelFileFolder() {
        return (outputFolder + "/" + modelSrcPath).replace('/', File.separatorChar);
    }

    /**
     * Location to write api files.
     */
    @Override
    public String apiFileFolder() {
        return (outputFolder + "/" + apiSrcPath).replace('/', File.separatorChar);
    }

    @Override
    public String apiDocFileFolder() {
        return (outputFolder + "/" + apiDocPath).replace('/', File.separatorChar);
    }

    @Override
    public String modelDocFileFolder() {
        return (outputFolder + "/" + modelDocPath).replace('/', File.separatorChar);
    }

    @Override
    public String toModelFilename(String name) {
        return "model_" + toModelName(name);
    }

    @Override
    public String toApiFilename(String name) {
        name = name.replaceAll("-", "_");
        return "api_" + camelize(name) + "Api";
    }

    @Override
    public String toApiName(String name) {
        if (name.length() == 0) {
            return "DefaultApi";
        }
        // e.g. phone_number_api => PhoneNumberApi
        return camelize(name) + "Api";
    }

    @Override
    public String toParamName(String name) {
        CamelizeOption camelizeOption = CamelizeOption.UPPERCASE_FIRST_CHAR;
        name = camelize(sanitizeName(name), camelizeOption);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        return escapeReservedWord(name);
    }

    @Override
    public String toApiVarName(String name) {
        CamelizeOption camelizeOption = CamelizeOption.UPPERCASE_FIRST_CHAR;
        name = camelize(sanitizeName(name), camelizeOption);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        return escapeReservedWord(name);
    }

    @Override
    public String toVarName(String name) {
        return name;
    }

    /**
     * Sanitize name (parameter, property, method, etc)
     *
     * @param name string to be sanitize
     * @return sanitized string
     */
    @Override
    @SuppressWarnings("static-method")
    public String sanitizeName(String name) {
        if (name == null) {
            LOGGER.error("String to be sanitized is null. Default to ERROR_UNKNOWN");
            return "ERROR_UNKNOWN";
        }

        // if the name is just '$', map it to 'value', as that's sometimes used in the spec
        if ("$".equals(name)) {
            return "value";
        }

        name = name.replaceAll("\\[\\]", "");
        name = name.replaceAll("\\[", "_");
        name = name.replaceAll("\\]", "");
        name = name.replaceAll("\\(", "_");
        name = name.replaceAll("\\)", "");
        name = name.replaceAll("\\.", "_");
        name = name.replaceAll("-", "_");
        name = name.replaceAll(" ", "_");
        name = name.replaceAll("/", "_");
        return name.replaceAll("[^a-zA-Z0-9_{}]", "");
    }

    protected boolean needsVarEscape(String name) {
        return (!name.matches("[a-zA-Z0-9_]*") && !name.matches("var\".*\"")) || reservedWords.contains(name);
    }

    /**
     * Output the proper Julia model name.
     *
     * @param name the name of the model
     * @return Julia model name
     */
    @Override
    public String toModelName(final String name) {
        String result = sanitizeName(name);

        // remove dollar sign
        result = result.replaceAll("$", "");

        // model name cannot use reserved keyword, e.g. return
        if (isReservedWord(result)) {
            LOGGER.warn(result + " (reserved word) cannot be used as model name. Renamed to " + camelize("model_" + result));
            result = "model_" + result; // e.g. return => ModelReturn (after camelize)
        }

        // model name starts with number
        if (result.matches("^\\d.*")) {
            LOGGER.warn(result + " (model name starts with number) cannot be used as model name. Renamed to " + camelize("model_" + result));
            result = "model_" + result; // e.g. 200Response => Model200Response (after camelize)
        }

        if (!StringUtils.isEmpty(modelNamePrefix)) {
            result = modelNamePrefix + "_" + result;
        }

        if (!StringUtils.isEmpty(modelNameSuffix)) {
            result = result + "_" + modelNameSuffix;
        }

        result = dropDots(result);
        // camelize the model name
        // phone_number => PhoneNumber
        result = camelize(result);

        return result;
    }

    @Override
    public String getTypeDeclaration(Schema schema) {
        if (ModelUtils.isArraySchema(schema)) {
            ArraySchema ap = (ArraySchema) schema;
            Schema inner = ap.getItems();
            return getSchemaType(schema) + "{" + getTypeDeclaration(inner) + "}";
        } else if (ModelUtils.isSet(schema)) {
            Schema inner = getAdditionalProperties(schema);
            return getSchemaType(schema) + "{" + getTypeDeclaration(inner) + "}";
        } else if (ModelUtils.isMapSchema(schema)) {
            Schema inner = getAdditionalProperties(schema);
            return getSchemaType(schema) + "{String, " + getTypeDeclaration(inner) + "}";
        }
        return super.getTypeDeclaration(schema);
    }


    /**
     * Return the type declaration for a given schema
     *
     * @param schema the schema
     * @return the type declaration
     */
    @Override
    public String getSchemaType(Schema schema) {
        String openAPIType = super.getSchemaType(schema);
        String type = null;

        if (openAPIType == null) {
            LOGGER.error("OpenAPI Type for {} is null. Default to Object instead.", schema.getName());
            openAPIType = "Object";
        }

        if (typeMapping.containsKey(openAPIType)) {
            type = typeMapping.get(openAPIType);
            if(languageSpecificPrimitives.contains(type)) {
                return type;
            }
        } else {
            type = openAPIType;
        }

        return toModelName(type);
    }

    /**
     * Return the default value of the property
     * @param schema OpenAPI property object
     * @return string presentation of the default value of the property
     */
    @Override
    public String toDefaultValue(Schema schema) {
        if (ModelUtils.isBooleanSchema(schema)) {
            if (schema.getDefault() != null) {
                return schema.getDefault().toString();
            }
        } else if (ModelUtils.isDateSchema(schema)) {
            // TODO
        } else if (ModelUtils.isDateTimeSchema(schema)) {
            // TODO
        } else if (ModelUtils.isIntegerSchema(schema) || ModelUtils.isLongSchema(schema) || ModelUtils.isNumberSchema(schema)) {
            if (schema.getDefault() != null) {
                return schema.getDefault().toString();
            }
        } else if (ModelUtils.isStringSchema(schema)) {
            if (schema.getDefault() != null) {
                String _default = (String) schema.getDefault();
                if (schema.getEnum() == null) {
                    return "\"" + _default + "\"";
                } else {
                    // convert to enum var name later in postProcessModels
                    return _default;
                }
            }
        }

        return "nothing";
    }

    @Override
    public String escapeUnsafeCharacters(String input) {
        return input;
    }

    /**
     * Escape single and/or double quote to avoid code injection 
     * @param input String to be cleaned up
     * @return string with quotation mark removed or escaped
     */
    public String escapeQuotationMark(String input) {
        return input.replace("\"", "\\\"");
    }

    protected String escapeBaseName(String name) {
        // replace all $ not prefixed with \ with a \
        name = name.replaceAll("(?<!\\\\)\\$", "\\\\\\$");
        return name;
    }

    protected String escapeRegex(String pattern) {
        pattern = pattern.replaceAll("\\\\\\\\", "\\\\");
        pattern = pattern.replaceAll("^/", "");
        pattern = pattern.replaceAll("/$", "");
        return pattern;
    }

    /**
     * Convert OpenAPI Parameter object to Codegen Parameter object
     *
     * @param imports set of imports for library/package/module
     * @param param   OpenAPI parameter object
     * @return Codegen Parameter object
     */
    @Override
    public CodegenParameter fromParameter(Parameter param, Set<String> imports) {
        CodegenParameter parameter = super.fromParameter(param, imports);
        parameter.baseName = escapeBaseName(parameter.baseName);
        if (parameter.pattern != null) {
            parameter.pattern = escapeRegex(parameter.pattern);
        }
        return parameter;
    }

    /**
     * Convert OAS Property schema to Codegen Property object.
     * <p>
     * The return value is cached. An internal cache is looked up to determine
     * if the CodegenProperty return value has already been instantiated for
     * the (String name, Schema schema) arguments.
     * Any subsequent processing of the CodegenModel return value must be idempotent
     * for a given (String name, Schema schema).
     *
     * @param name     name of the property
     * @param schema   OAS property schema
     * @param required true if the property is required in the next higher object schema, false otherwise
     * @return Codegen Property object
     */    
    @Override
    public CodegenProperty fromProperty(String name, Schema schema, boolean required) {
        CodegenProperty property = super.fromProperty(name, schema, required);
        property.baseName = escapeBaseName(property.baseName);
        // if the name needs any escaping, we set it to var"name"
        if (needsVarEscape(property.name)) {
            property.name = "var\"" + property.name + "\"";
        }
        if (property.pattern != null) {
            property.pattern = escapeRegex(property.pattern);
        }
        return property;
    }

    /**
     * Return the operation ID (method name)
     *
     * @param operationId operation ID
     * @return the sanitized method name
     */
    @SuppressWarnings("static-method")
    public String toOperationId(String operationId) {
        CamelizeOption camelizeOption = CamelizeOption.UPPERCASE_FIRST_CHAR;
        operationId = camelize(super.toOperationId(operationId), camelizeOption);
        operationId = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, operationId);
        return sanitizeName(operationId);
    }

    private void changeParamNames(List<CodegenParameter> paramsList, HashSet<String> reservedNames) {
        // check if any param name clashes with type name and rename it
        for (CodegenParameter param : paramsList) {
            if (reservedNames.contains(param.paramName)) {
                do {
                    param.paramName = param.paramName + "_";
                } while (reservedNames.contains(param.paramName + "param"));
                param.paramName = param.paramName + "param";
            }
        }
    }
    
    /**
     * Convert OAS Operation object to Codegen Operation object
     *
     * @param httpMethod HTTP method
     * @param operation  OAS operation object
     * @param path       the path of the operation
     * @param servers    list of servers
     * @return Codegen Operation object
     */
    @Override
    public CodegenOperation fromOperation(String path,
                                          String httpMethod,
                                          Operation operation,
                                          List<Server> servers) {
        CodegenOperation op = super.fromOperation(path, httpMethod, operation, servers);

        // collect all reserved names
        HashSet<String> reservedNames = new HashSet<String>();
        reservedNames.add(op.returnType);
        reservedNames.add(op.operationId);
        for (CodegenParameter param : op.allParams) {
            reservedNames.add(param.dataType);
        }

        changeParamNames(op.allParams, reservedNames);
        changeParamNames(op.bodyParams, reservedNames);
        changeParamNames(op.headerParams, reservedNames);
        changeParamNames(op.pathParams, reservedNames);
        changeParamNames(op.queryParams, reservedNames);
        changeParamNames(op.formParams, reservedNames);

        return op;
    }
}
