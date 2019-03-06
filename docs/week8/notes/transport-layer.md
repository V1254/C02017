# Transport Layer.

## Overview

- Main functions of the **transport layer**.
- Transport layer services
- Transport addressing.

## Aims

Aims to provide **efficient, reliable and cost-effective** services to processes in the
application layer.

**Services**: Connectionless or connection-oriented.

**Functions**: Sequencing, error or congestion control.

### Protocol Headers

Transport layer entities exchange info in the unit of **Transport Protocol Data Unit** (TPDU)

Nesting of TPDUs, packets and frames:

![](https://i.imgur.com/nm1aIZb.png)

### Comparison with Network Layer

Similarities:
- Both do connectionless or connection-oriented services.
- For connection-oriented services:
    - Establish connection
    - Data transfer
    - Release
- Addressing & Flow control.


Differences:
- Network layer may be **unreliable** as routers may crash or packets may be discarded to reduce congestion.
- Transport layer provides a **reliable** service.
- Transport layer entities run entirely on **end nodes**.
- Transport layer primitives are intended for **programs** and **programmers** and hence are convenient to use.

Transport Service Primities:

![](https://i.imgur.com/9V1qIHd.png)

### Comparison with Data-Link Layer

![](https://i.imgur.com/BqCR4pi.png)

## Making and Releasing Connections

Transport service corresponds to a **Transport Service Access Point**(TSAP)

Transport address = TSAP + **Network SAP**(NSAP)

A service address can be obtained from a **name server**.

New Services must be **registered** with the name server.

![](https://i.imgur.com/hVCd6tz.png)

### Making & Releasing Connections

Making Connections:

![](https://i.imgur.com/mtKF4FS.png)

Three-Way Handshake Protocol:

![](https://i.imgur.com/uoj8Qg4.png)


Releasing Connections:

![](https://i.imgur.com/eeJTExd.png)

Releasing Connections Four Scenarios:

![](https://i.imgur.com/tAe5lvU.png)

## Summary

-  The Transport Layer provides efficient, reliable, cost-effective services
to processes in the application layer.
- Transport layer address: TSAP + NSAP
- Connection establishing and releasing requires a strict protocol.