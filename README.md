# Monitor de temperatura

## Javadoc (Documentação das classes)

[Acesse aqui](https://diegocf10.github.io/comp_conc_trab2/doc/)

## Como compilar tudo

> ./compilar.sh

ou

> javac src/br/ufrj/diegocf/monitor/*.java -d dist

Os bytecodes java serão criados na pasta dist

## Executar módulo que testa o padrão leitores-escritores

> ./testaEscritorLeitor.sh

ou

> java -classpath dist br.ufrj.diegocf.monitor.TestReaderWriter

A saída é um script python que pode ser executado para verificar
a corretude do programa

## Executar módulo principal (monitoramento de temperatura)

> ./app.sh

ou

> java -classpath dist br.ufrj.diegocf.monitor.App

A saída é um script python que pode ser executado para verificar
a corretude do programa
