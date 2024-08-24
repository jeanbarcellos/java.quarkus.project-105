## Estudar:

- Sessão
- Transações
- LazyLoading

## Transação

@Transactional

JTA: Java Transactional API

https://www.alura.com.br/artigos/jta-java-transaction-api

O JAVA EE entende que uma exception checada tem a ver com o negócio. Tanto que elas são chamadas de ApplicationException

Agora, caso ela seja uma exception não checada, filha de RuntimeException, o comportamento padrão do interceptor vai ser marcar a transação para rollback. Esse último tipo de exception, é entendida pelo JAVA EE como uma SystemException

## Exception

Por padrão, as exceções checadas não resultam em rollback da transação, porém instâncias de RuntimeException e suas subclasses resultam em rollback da transação.

## Validação

https://stackoverflow.com/questions/18129300/get-parameter-value-if-parameter-annotation-exists

https://github.com/quarkusio/quarkus/blob/main/extensions/hibernate-validator/runtime/src/main/java/io/quarkus/hibernate/validator/runtime/interceptor/AbstractMethodValidationInterceptor.java
