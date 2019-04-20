[![Build Status](https://travis-ci.org/ONSdigital/census-int-product-reference.svg?branch=master)](https://travis-ci.org/ONSdigital/census-int-product-reference)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/37fdebe43c0f467ead6394a3d43d90f4)](https://www.codacy.com/app/sdcplatform/census-int-product-reference?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ONSdigital/census-int-product-reference&amp;utm_campaign=Badge_Grade)

# Census Integration Product Reference
This project contains reusable service layer code to facilitate the lookup of Census product types (aka fulfilments).
and returns a list of product objects, encapsulating :

*  fulfilmentCode
*  description
*  language
*  caseType
*  regions []
*  deliveryChannel
*  requestChannels []
*  handler

The ProductReference class is a Spring Component that can be injected into an application, and provides a searchProducts
method that takes an example Product. That example is compared with all known Products, fields by field, and those matching
are returned. The non null example fields are ANDed together. ie Setting the language field in the example to 'eng' will yield
all the English language products. Additionally setting the deliveryChannel to 'POST' will yield only the English language
products that can be posted.

The ProductReference class loads the available products from a JSON file when it is instantiated, kept in memory.
The searchProducts method additionally uses Spring caching to save the comparison process on subsequent identical requests.

The Product class used as the example and the return type contains enum definitions of certain fields - those enums may have counterparts
in the application that uses the Component. This project has no dependency on either the Census Contact Centre Service or the 
RH Service, so needs it's own enums.
## Copyright
Copyright (C) 2019 Crown Copyright (Office for National Statistics)
