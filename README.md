# TowertexBloom

This project is providing implementation of Bloom SDK.
For more details see WIKI in this repository.
- sdk module
    - network layer
      - Ktor
      - models for UI form
      - models for reported data
      - services definition for retrieving UI form
      - services definition for reporting data
      - unit test coverage via JUnit - automated via Github Action
    - persistence layer
      - Room
      - models for reported data
      - database definition for reported data
      - unit test coverage via AndroidInstrumentation
    - datastore layer
      - preferenceDataStore
      - models for UI form
      - data store definition for UI form
      - unit test coverage via AndroidInstrumentation
    - sdk layer
      - definition of SDK interface
      - model of SDK configuration
- sdkcompose module
  - uses sdk module and converts it to Jetpack Compose component
- app module
  - demo application showing usage of sdkcompose module