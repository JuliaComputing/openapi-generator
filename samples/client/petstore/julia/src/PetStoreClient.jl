# This file was generated by the Julia OpenAPI Code Generator
# Do not modify this file directly. Modify the OpenAPI specification instead.

module PetStoreClient

using Dates, TimeZones
using OpenAPIClient
import OpenAPIClient: field_name, property_type, hasproperty, validate_property, APIImpl, APIModel
import Base: convert, propertynames

include("modelincludes.jl")

include("api_PetApi.jl")
include("api_StoreApi.jl")
include("api_UserApi.jl")

# export models
export convert, ApiResponse
export convert, Category
export convert, Order
export convert, Pet
export convert, Tag
export convert, User

# export operations
export convert, PetApi, StoreApi, UserApi

export check_required, field_name, property_type, hasproperty, propertynames, validate_property, convert

end
