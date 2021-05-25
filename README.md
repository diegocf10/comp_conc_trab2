# Monitor de temperatura

## Javadoc (Documentação das classes)

[Acesse aqui](https://diegocf10.github.io/comp_conc_trab2/doc/)

## Como compilar tudo

> ./compilar.sh

ou

> javac src/br/ufrj/diegocf/monitor/*.java -d dist

Os bytecodes java serão criados na pasta dist

## Executar módulo que testa o padrão leitores-escritores

> ./testaEscritorLeitor.sh > dist/testa_escritor_leitor.py

ou

> java -classpath dist br.ufrj.diegocf.monitor.TestReaderWriter > dist/testa_escritor_leitor.py

A saída é um script python que pode ser executado para verificar
a corretude do programa

> python3 dist/testa_escritor_leitor.py

## Executar módulo principal (monitoramento de temperatura)

sem log

> ./app.sh

com log

> ./app.sh --log > dist/testa_app.py

ou

> java -classpath dist br.ufrj.diegocf.monitor.App --log > dist/testa_app.py

A saída é um script python que pode ser executado para verificar
a corretude do programa

> python3 dist/testa_app.py
