# Monitor de temperatura

## Javadoc (Documentação das classes)

[Acesse aqui]()

## Como compilar tudo

> javac src/br/ufrj/diegocf/monitor/*.java -d dist

Os bytecodes java serão criados na pasta bin

## Executar módulo que testa o padrão leitores-escritores

> java -classpath dist br.ufrj.diegocf.monitor.TestReaderWriter

A saída é um arquivo python que pode ser executado para verificar
a corretude do programa

## Executar módulo principal (monitoramento de temperatura)

> java -classpath dist br.ufrj.diegocf.monitor.App

A saída é um arquivo python que pode ser executado para verificar
a corretude do programa
