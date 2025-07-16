# Spring OTEL Instrumentation

Projeto experimental de instrumentação e exportação de traces de uma aplicação Java Spring Boot, utilizando a tecnologia OpenTelemetry.

## Requisitos

- JDK 21
- Maven

## Executar a Aplicação

Para rodar a aplicação basta executar o seguinte comando:

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:target/opentelemetry-javaagent.jar -Dotel.exporter.otlp.endpoint=http://localhost:4318 -Dotel.metrics.exporter=none -Dotel.logs.exporter=none -Dotel.resource.attributes=service.name=spring-demo,service.version=1.0.0"
```

>[!IMPORTANT]
> É necessário alterar o caminho do OpenTelemetry Java Agent. Para isso, atltere o valor da flag `-javaagent` para `-javaagent:path/to/javaagent.jar`.

> [!TIP]
> Você pode alterar o endereço para onde as métricas, logs e traces são enviados. Basta modificar o atributo `-Dotel.exporter.otlp.endpoint=http://localhost:4318` do comando a cima.
>
> Por padrão, logs e traces não são enviados. Para enviar esses dados, basta remover os atributos `-Dotel.metrics.exporter=none` e `-Dotel.logs.exporter=none`.

## Rotas

### `/dice`

Roda um dado e mostra na tela. Dados que são enviados via OTLP:

- Span contendo informações da chamada HTTP

### `/message`

Dispara uma mensagem aleatória e mostra na tela. Dados que são enviados via OTLP:

- Span contendo a mensagem disparada
- Histogram que conta o número de vezes que cada mensagem foi disparada
- Contador de números de vezes que o endpoint foi chamado
- Logs contendo a mensagem disparada

### `/ping`

Simula um processamento de 0 - 200 ms. Dados que são enviados via OTLP:

- Gauge contendo o tempo de processamento simulado
- Contador de numero de vezes que o endpoint foi chamado
- Span com atributo contendo a informação de quanto tempo durou o processo simulado
- Logs informando inicio e fim da simulação de processamento

### `/inventory`

Simula um sistema de inventário, onde é possível adicionar, remover e listar os itens contidos.

> [!NOTE]
> Nenhum dado é enviado via OTLP até o momento.

- `/invetory/`: Lista os itens contidos no inventário.
- `/inventory/add`: Adiciona um item ao inventário.
    - **name**: Nome do item.
    - **count**: Quantidade a ser adicionada ao inventário

```json
{
    "name": "Some Item",
    "count": 1
}
```

- `/inventory/remove?id={item_id}`: Remove um item do invetário.