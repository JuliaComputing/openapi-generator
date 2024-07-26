/*
 * ByRefOrValue
 *
 * This tests for a oneOf interface representation 
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 * Generated by: https://openapi-generator.tech
 */

use crate::models;
use serde::{Deserialize, Serialize};

#[derive(Clone, Default, Debug, PartialEq, Serialize, Deserialize)]
pub struct FooRef {
    #[serde(rename = "foorefPropA", skip_serializing_if = "Option::is_none")]
    pub fooref_prop_a: Option<String>,
    /// Name of the related entity.
    #[serde(rename = "name", skip_serializing_if = "Option::is_none")]
    pub name: Option<String>,
    /// The actual type of the target instance when needed for disambiguation.
    #[serde(rename = "@referredType", skip_serializing_if = "Option::is_none")]
    pub at_referred_type: Option<String>,
    /// Hyperlink reference
    #[serde(rename = "href", skip_serializing_if = "Option::is_none")]
    pub href: Option<String>,
    /// unique identifier
    #[serde(rename = "id", skip_serializing_if = "Option::is_none")]
    pub id: Option<String>,
    /// A URI to a JSON-Schema file that defines additional attributes and relationships
    #[serde(rename = "@schemaLocation", skip_serializing_if = "Option::is_none")]
    pub at_schema_location: Option<String>,
    /// When sub-classing, this defines the super-class
    #[serde(rename = "@baseType", skip_serializing_if = "Option::is_none")]
    pub at_base_type: Option<String>,
    /// When sub-classing, this defines the sub-class Extensible name
    #[serde(rename = "@type")]
    pub at_type: String,
}

impl FooRef {
    pub fn new(at_type: String) -> FooRef {
        FooRef {
            fooref_prop_a: None,
            name: None,
            at_referred_type: None,
            href: None,
            id: None,
            at_schema_location: None,
            at_base_type: None,
            at_type,
        }
    }
}

