[![Build Status](https://travis-ci.org/ONSdigital/census-int-product-reference.svg?branch=master)](https://travis-ci.org/ONSdigital/census-int-product-reference)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/37fdebe43c0f467ead6394a3d43d90f4)](https://www.codacy.com/app/sdcplatform/census-int-product-reference?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ONSdigital/census-int-product-reference&amp;utm_campaign=Badge_Grade)

# Census Integration Product Reference
This project contains reusable service layer code to facilitate the lookup of Census product types (aka fulfilments).
The initial release allows product lookup based on case type and region, and returns a list of product objects, encapsulating :

*  productCode
*  description
*  caseType
*  regions []
*  productType
*  channels []
*  handler

## Copyright
Copyright (C) 2019 Crown Copyright (Office for National Statistics)
