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

https://github.com/quarkusio/quarkus/blob/main/extensions/narayana-jta/runtime/src/main/java/io/quarkus/narayana/jta/runtime/interceptor/TransactionalInterceptorBase.java

## Exception

Por padrão, as exceções checadas não resultam em rollback da transação, porém instâncias de RuntimeException e suas subclasses resultam em rollback da transação.

## Validação

https://stackoverflow.com/questions/18129300/get-parameter-value-if-parameter-annotation-exists

https://github.com/quarkusio/quarkus/blob/main/extensions/hibernate-validator/runtime/src/main/java/io/quarkus/hibernate/validator/runtime/interceptor/AbstractMethodValidationInterceptor.java

---

PersistentBag.java

AbstractPersistentCollection.java

LazyAttributeLoadingInterceptor

---

[org.hib.col.spi.AbstractPersistentCollection] (executor-thread-1) HHH000496: Detaching an uninitialized collection with queued operations from a session: [com.jeanbarcellos.project106.domain.Post.comments#4

---

## Hibernate

---

Exemplo 419. Verificando a preguiça com Jakarta Persistence

```java
PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();

boolean personInitialized = persistenceUnitUtil.isLoaded(person);

boolean personBooksInitialized = persistenceUnitUtil.isLoaded(person.getBooks());

boolean personNameInitialized = persistenceUnitUtil.isLoaded(person, "name");
```

Exemplo 420. Verificando a preguiça com a API do Hibernate

```java
boolean personInitialized = Hibernate.isInitialized(person);

boolean personBooksInitialized = Hibernate.isInitialized(person.getBooks());

boolean personNameInitialized = Hibernate.isPropertyInitialized(person, "name");
```

### 9. Transações

É importante entender que o termo transação tem muitos significados diferentes, mas relacionados, em relação à persistência e ao Mapeamento Objeto/Relacional. Na maioria dos casos de uso, essas definições se alinham, mas nem sempre é esse o caso.

Pode se referir à transação física com o banco de dados.

Pode se referir à noção lógica de uma transação relacionada a um contexto de persistência.

Pode se referir à noção de aplicação de uma Unidade de Trabalho, conforme definido pelo padrão arquetípico.

#### 9.1. Transações físicas

O Hibernate usa a API JDBC para persistência.

No mundo do Java, há dois mecanismos bem definidos para lidar com transações em JDBC: o próprio JDBC e o JTA. O Hibernate suporta ambos os mecanismos para integração com transações e permite que aplicativos gerenciem transações físicas.

## ModelMapper

https://www.dio.me/articles/uso-avancado-do-modelmapper
