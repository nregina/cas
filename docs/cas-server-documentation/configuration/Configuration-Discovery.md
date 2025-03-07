---
layout: default
title: CAS - Configuration Discovery
category: Configuration
---
       
{% include variables.html %}

# Configuration Discovery

Certain aspects of the CAS server deployment may be advertised via a discovery endpoint to indicate to client applications and consumers the set of features and capabilities that are turned on. The [discovery profile endpoint](../monitoring/Monitoring-Statistics.html) is enabled by including the following module in the overlay:

{% include_cached casmodule.html group="org.apereo.cas" module="cas-server-support-discovery-profile" %}

The metadata reported in the discovery profile generally includes two categories of items:

- Capabilities that **could be** supported by the CAS server where the feature is available and yet isn't quite configured and turned on.
- Capabilities that **are** actively and currently supported by and configured in the running CAS server.

Examples of reported items include:

- Service definitions types (CAS, SAML, OAuth, etc)
- Multifactor Authentication Provider  types (Authy, Duo Security, etc)
- ...

<div class="alert alert-info"><strong>Docs Grow Old</strong><p>To examine the latest collection of reported metadata, turn on the endpoint and observe the behavior in action. The metadata will continue to grow and improve per every CAS release to accommodate fancier discovery attempts.</p></div>

Note that this capability and endpoint is turned off by default and its access is controlled similar to all other CAS administrative endpoints. Once the endpoint is turned on, you will need to ensure proper access is granted only to authorized parties via appropriate [security options provided by CAS](../monitoring/Monitoring-Statistics.html).

## Actuator Endpoints

The following endpoints are provided by CAS:

{% include_cached actuators.html endpoints="discoveryProfile" %}
